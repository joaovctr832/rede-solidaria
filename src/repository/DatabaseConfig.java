package repository;

public class DatabaseConfig {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/rede_solidaria";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "postgres";

    private final String url;
    private final String user;
    private final String password;

    public DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static DatabaseConfig fromSystem() {
        return new DatabaseConfig(
                readConfig("DB_URL", DEFAULT_URL),
                readConfig("DB_USER", DEFAULT_USER),
                readConfig("DB_PASSWORD", DEFAULT_PASSWORD));
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    private static String readConfig(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        String environmentValue = System.getenv(key);
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }

        return defaultValue;
    }
}
