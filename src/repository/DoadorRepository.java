package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Doador;

public class DoadorRepository {
    private final PostgreSqlConnectionFactory connectionFactory;

    public DoadorRepository() {
        this.connectionFactory = new PostgreSqlConnectionFactory(DatabaseConfig.fromSystem());
    }

    public Doador save(Doador entidade) {
        if (entidade.getId() == null) {
            return insert(entidade);
        }

        return update(entidade);
    }

    public List<Doador> findAll() {
        String sql = """
                SELECT
                    u.id,
                    u.nome,
                    u.telefone,
                    u.email,
                    u.endereco,
                    COUNT(DISTINCT i.id) AS quantidade_itens,
                    COUNT(DISTINCT de.id) AS quantidade_doacoes
                FROM usuario u
                INNER JOIN doador d ON d.id = u.id
                LEFT JOIN item_doacao i ON i.doador_id = d.id
                LEFT JOIN doacao_efetivada de ON de.doador_id = d.id
                GROUP BY u.id, u.nome, u.telefone, u.email, u.endereco
                ORDER BY u.id
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            List<Doador> doadores = new ArrayList<>();
            while (resultSet.next()) {
                doadores.add(mapDoador(resultSet));
            }
            return doadores;
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel listar os doadores no PostgreSQL.", exception);
        }
    }

    public Optional<Doador> findById(Long id) {
        String sql = """
                SELECT
                    u.id,
                    u.nome,
                    u.telefone,
                    u.email,
                    u.endereco,
                    COUNT(DISTINCT i.id) AS quantidade_itens,
                    COUNT(DISTINCT de.id) AS quantidade_doacoes
                FROM usuario u
                INNER JOIN doador d ON d.id = u.id
                LEFT JOIN item_doacao i ON i.doador_id = d.id
                LEFT JOIN doacao_efetivada de ON de.doador_id = d.id
                WHERE u.id = ?
                GROUP BY u.id, u.nome, u.telefone, u.email, u.endereco
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapDoador(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel buscar o doador no PostgreSQL.", exception);
        }
    }

    private Doador insert(Doador entidade) {
        String insertUsuario = """
                INSERT INTO usuario (nome, telefone, email, endereco)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;
        String insertDoador = "INSERT INTO doador (id) VALUES (?)";

        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long generatedId;
                try (PreparedStatement usuarioStatement = connection.prepareStatement(insertUsuario)) {
                    usuarioStatement.setString(1, entidade.getNome());
                    usuarioStatement.setString(2, entidade.getTelefone());
                    usuarioStatement.setString(3, entidade.getEmail());
                    usuarioStatement.setString(4, entidade.getEndereco());

                    try (ResultSet resultSet = usuarioStatement.executeQuery()) {
                        resultSet.next();
                        generatedId = resultSet.getLong("id");
                    }
                }

                try (PreparedStatement doadorStatement = connection.prepareStatement(insertDoador)) {
                    doadorStatement.setLong(1, generatedId);
                    doadorStatement.executeUpdate();
                }

                connection.commit();
                entidade.setId(generatedId);
                entidade.setQuantidadeItensDoacao(0);
                entidade.setQuantidadeDoacoesEfetivadas(0);
                return entidade;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel salvar o doador no PostgreSQL.", exception);
        }
    }

    private Doador update(Doador entidade) {
        String updateUsuario = """
                UPDATE usuario
                SET nome = ?, telefone = ?, email = ?, endereco = ?
                WHERE id = ?
                """;
        String ensureDoador = """
                INSERT INTO doador (id)
                VALUES (?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement usuarioStatement = connection.prepareStatement(updateUsuario);
                    PreparedStatement doadorStatement = connection.prepareStatement(ensureDoador)) {
                usuarioStatement.setString(1, entidade.getNome());
                usuarioStatement.setString(2, entidade.getTelefone());
                usuarioStatement.setString(3, entidade.getEmail());
                usuarioStatement.setString(4, entidade.getEndereco());
                usuarioStatement.setLong(5, entidade.getId());
                usuarioStatement.executeUpdate();

                doadorStatement.setLong(1, entidade.getId());
                doadorStatement.executeUpdate();

                connection.commit();
                return entidade;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel atualizar o doador no PostgreSQL.", exception);
        }
    }

    private Doador mapDoador(ResultSet resultSet) throws SQLException {
        Doador doador = new Doador(
                resultSet.getString("nome"),
                resultSet.getString("telefone"),
                resultSet.getString("email"),
                resultSet.getString("endereco"));
        doador.setId(resultSet.getLong("id"));
        doador.setQuantidadeItensDoacao(resultSet.getInt("quantidade_itens"));
        doador.setQuantidadeDoacoesEfetivadas(resultSet.getInt("quantidade_doacoes"));
        return doador;
    }
}

