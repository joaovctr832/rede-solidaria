package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.CategoriaItem;
import model.Doador;
import model.EstadoConservacao;
import model.ItemDoacao;
import model.StatusItem;

public class ItemDoacaoRepository {
    private final PostgreSqlConnectionFactory connectionFactory;

    public ItemDoacaoRepository() {
        this.connectionFactory = new PostgreSqlConnectionFactory(DatabaseConfig.fromSystem());
    }

    public ItemDoacao save(ItemDoacao entidade) {
        if (entidade.getId() == null) {
            return insert(entidade);
        }

        return update(entidade);
    }

    public List<ItemDoacao> findAll() {
        String sql = """
                SELECT
                    i.id,
                    i.nome,
                    i.categoria,
                    i.descricao,
                    i.quantidade,
                    i.estado_conservacao,
                    i.data_cadastro,
                    i.status,
                    u.id AS doador_id,
                    u.nome AS doador_nome,
                    u.telefone AS doador_telefone,
                    u.email AS doador_email,
                    u.endereco AS doador_endereco
                FROM item_doacao i
                INNER JOIN doador d ON d.id = i.doador_id
                INNER JOIN usuario u ON u.id = d.id
                ORDER BY i.id
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            List<ItemDoacao> itens = new ArrayList<>();
            while (resultSet.next()) {
                itens.add(mapItemDoacao(resultSet));
            }
            return itens;
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Nao foi possivel listar os itens no PostgreSQL. " + SqlExceptionFormatter.format(exception),
                    exception);
        }
    }

    public Optional<ItemDoacao> findById(Long id) {
        String sql = """
                SELECT
                    i.id,
                    i.nome,
                    i.categoria,
                    i.descricao,
                    i.quantidade,
                    i.estado_conservacao,
                    i.data_cadastro,
                    i.status,
                    u.id AS doador_id,
                    u.nome AS doador_nome,
                    u.telefone AS doador_telefone,
                    u.email AS doador_email,
                    u.endereco AS doador_endereco
                FROM item_doacao i
                INNER JOIN doador d ON d.id = i.doador_id
                INNER JOIN usuario u ON u.id = d.id
                WHERE i.id = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapItemDoacao(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Nao foi possivel buscar o item no PostgreSQL. " + SqlExceptionFormatter.format(exception),
                    exception);
        }
    }

    private ItemDoacao insert(ItemDoacao entidade) {
        String sql = """
                INSERT INTO item_doacao (
                    doador_id,
                    nome,
                    categoria,
                    descricao,
                    quantidade,
                    estado_conservacao,
                    data_cadastro,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bindItemDoacao(statement, entidade);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                entidade.setId(resultSet.getLong("id"));
                return entidade;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Nao foi possivel salvar o item no PostgreSQL. " + SqlExceptionFormatter.format(exception),
                    exception);
        }
    }

    private ItemDoacao update(ItemDoacao entidade) {
        String sql = """
                UPDATE item_doacao
                SET doador_id = ?,
                    nome = ?,
                    categoria = ?,
                    descricao = ?,
                    quantidade = ?,
                    estado_conservacao = ?,
                    data_cadastro = ?,
                    status = ?
                WHERE id = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bindItemDoacao(statement, entidade);
            statement.setLong(9, entidade.getId());
            statement.executeUpdate();
            return entidade;
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "Nao foi possivel atualizar o item no PostgreSQL. " + SqlExceptionFormatter.format(exception),
                    exception);
        }
    }

    private void bindItemDoacao(PreparedStatement statement, ItemDoacao entidade) throws SQLException {
        statement.setLong(1, entidade.getDoador().getId());
        statement.setString(2, entidade.getNome());
        statement.setString(3, entidade.getCategoria().name());
        statement.setString(4, entidade.getDescricao());
        statement.setInt(5, entidade.getQuantidade());
        statement.setString(6, entidade.getEstadoConservacao().name());
        statement.setDate(7, Date.valueOf(entidade.getDataCadastro()));
        statement.setString(8, entidade.getStatus().name());
    }

    private ItemDoacao mapItemDoacao(ResultSet resultSet) throws SQLException {
        Doador doador = new Doador(
                resultSet.getString("doador_nome"),
                resultSet.getString("doador_telefone"),
                resultSet.getString("doador_email"),
                resultSet.getString("doador_endereco"));
        doador.setId(resultSet.getLong("doador_id"));

        ItemDoacao itemDoacao = new ItemDoacao(
                resultSet.getString("nome"),
                CategoriaItem.valueOf(resultSet.getString("categoria")),
                resultSet.getString("descricao"),
                resultSet.getInt("quantidade"),
                EstadoConservacao.valueOf(resultSet.getString("estado_conservacao")),
                doador);
        itemDoacao.setId(resultSet.getLong("id"));
        itemDoacao.setDataCadastro(resultSet.getDate("data_cadastro").toLocalDate());
        itemDoacao.setStatus(StatusItem.valueOf(resultSet.getString("status")));
        return itemDoacao;
    }
}

