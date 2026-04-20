package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Beneficiario;
import model.TipoBeneficiario;

public class BeneficiarioRepository {
    private final PostgreSqlConnectionFactory connectionFactory;

    public BeneficiarioRepository() {
        this.connectionFactory = new PostgreSqlConnectionFactory(DatabaseConfig.fromSystem());
    }

    public Beneficiario save(Beneficiario entidade) {
        if (entidade.getId() == null) {
            return insert(entidade);
        }

        return update(entidade);
    }

    public List<Beneficiario> findAll() {
        String sql = """
                SELECT
                    u.id,
                    u.nome,
                    u.telefone,
                    u.email,
                    u.endereco,
                    b.tipo,
                    b.nivel_prioridade,
                    COUNT(DISTINCT s.id) AS quantidade_solicitacoes,
                    COUNT(DISTINCT de.id) AS quantidade_doacoes
                FROM usuario u
                INNER JOIN beneficiario b ON b.id = u.id
                LEFT JOIN solicitacao s ON s.beneficiario_id = b.id
                LEFT JOIN doacao_efetivada de ON de.beneficiario_id = b.id
                GROUP BY u.id, u.nome, u.telefone, u.email, u.endereco, b.tipo, b.nivel_prioridade
                ORDER BY u.id
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            List<Beneficiario> beneficiarios = new ArrayList<>();
            while (resultSet.next()) {
                beneficiarios.add(mapBeneficiario(resultSet));
            }
            return beneficiarios;
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel listar os beneficiarios no PostgreSQL.", exception);
        }
    }

    public Optional<Beneficiario> findById(Long id) {
        String sql = """
                SELECT
                    u.id,
                    u.nome,
                    u.telefone,
                    u.email,
                    u.endereco,
                    b.tipo,
                    b.nivel_prioridade,
                    COUNT(DISTINCT s.id) AS quantidade_solicitacoes,
                    COUNT(DISTINCT de.id) AS quantidade_doacoes
                FROM usuario u
                INNER JOIN beneficiario b ON b.id = u.id
                LEFT JOIN solicitacao s ON s.beneficiario_id = b.id
                LEFT JOIN doacao_efetivada de ON de.beneficiario_id = b.id
                WHERE u.id = ?
                GROUP BY u.id, u.nome, u.telefone, u.email, u.endereco, b.tipo, b.nivel_prioridade
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapBeneficiario(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel buscar o beneficiario no PostgreSQL.", exception);
        }
    }

    private Beneficiario insert(Beneficiario entidade) {
        String insertUsuario = """
                INSERT INTO usuario (nome, telefone, email, endereco)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;
        String insertBeneficiario = """
                INSERT INTO beneficiario (id, tipo, nivel_prioridade)
                VALUES (?, ?, ?)
                """;

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

                try (PreparedStatement beneficiarioStatement = connection.prepareStatement(insertBeneficiario)) {
                    beneficiarioStatement.setLong(1, generatedId);
                    beneficiarioStatement.setString(2, entidade.getTipo().name());
                    beneficiarioStatement.setInt(3, entidade.getNivelPrioridade());
                    beneficiarioStatement.executeUpdate();
                }

                connection.commit();
                entidade.setId(generatedId);
                entidade.setQuantidadeSolicitacoes(0);
                entidade.setQuantidadeDoacoesEfetivadas(0);
                return entidade;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel salvar o beneficiario no PostgreSQL.", exception);
        }
    }

    private Beneficiario update(Beneficiario entidade) {
        String updateUsuario = """
                UPDATE usuario
                SET nome = ?, telefone = ?, email = ?, endereco = ?
                WHERE id = ?
                """;
        String upsertBeneficiario = """
                INSERT INTO beneficiario (id, tipo, nivel_prioridade)
                VALUES (?, ?, ?)
                ON CONFLICT (id) DO UPDATE
                SET tipo = EXCLUDED.tipo,
                    nivel_prioridade = EXCLUDED.nivel_prioridade
                """;

        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement usuarioStatement = connection.prepareStatement(updateUsuario);
                    PreparedStatement beneficiarioStatement = connection.prepareStatement(upsertBeneficiario)) {
                usuarioStatement.setString(1, entidade.getNome());
                usuarioStatement.setString(2, entidade.getTelefone());
                usuarioStatement.setString(3, entidade.getEmail());
                usuarioStatement.setString(4, entidade.getEndereco());
                usuarioStatement.setLong(5, entidade.getId());
                usuarioStatement.executeUpdate();

                beneficiarioStatement.setLong(1, entidade.getId());
                beneficiarioStatement.setString(2, entidade.getTipo().name());
                beneficiarioStatement.setInt(3, entidade.getNivelPrioridade());
                beneficiarioStatement.executeUpdate();

                connection.commit();
                return entidade;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Nao foi possivel atualizar o beneficiario no PostgreSQL.", exception);
        }
    }

    private Beneficiario mapBeneficiario(ResultSet resultSet) throws SQLException {
        Beneficiario beneficiario = new Beneficiario(
                resultSet.getString("nome"),
                resultSet.getString("telefone"),
                resultSet.getString("email"),
                resultSet.getString("endereco"),
                TipoBeneficiario.valueOf(resultSet.getString("tipo")),
                resultSet.getInt("nivel_prioridade"));
        beneficiario.setId(resultSet.getLong("id"));
        beneficiario.setQuantidadeSolicitacoes(resultSet.getInt("quantidade_solicitacoes"));
        beneficiario.setQuantidadeDoacoesEfetivadas(resultSet.getInt("quantidade_doacoes"));
        return beneficiario;
    }
}

