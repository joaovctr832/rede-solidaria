package repository;

import java.sql.SQLException;

public final class SqlExceptionFormatter {
    private SqlExceptionFormatter() {
    }

    public static String format(SQLException exception) {
        StringBuilder details = new StringBuilder();

        SQLException current = exception;
        while (current != null) {
            if (current.getMessage() != null && !current.getMessage().isBlank()) {
                if (details.length() > 0) {
                    details.append(" | ");
                }
                details.append(current.getMessage().trim());
            }
            current = current.getNextException();
        }

        if (details.length() == 0 && exception.getCause() != null && exception.getCause().getMessage() != null) {
            details.append(exception.getCause().getMessage().trim());
        }

        if (details.length() == 0) {
            details.append("Sem detalhes adicionais do driver JDBC.");
        }

        return details.toString();
    }
}
