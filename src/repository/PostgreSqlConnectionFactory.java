package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlConnectionFactory {
    private final DatabaseConfig databaseConfig;

    public PostgreSqlConnectionFactory(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
        validateDriver();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                databaseConfig.getUrl(),
                databaseConfig.getUser(),
                databaseConfig.getPassword());
    }

    private void validateDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException(
                    "Driver JDBC do PostgreSQL nao encontrado. Adicione o .jar do driver ao classpath.",
                    exception);
        }
    }
}
