package connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public final class JdbcConnection {
    private static volatile JdbcConnection instance;
    private final String url;
    private final Properties props;

    private JdbcConnection(Properties properties) {
        String username = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        url = properties.getProperty("db.url");
        String driverName = properties.getProperty("db.driverName");

        props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
        Locale.setDefault(Locale.ENGLISH);

        try {
            Class.forName(driverName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JdbcConnection getInstance(Properties props) {
        JdbcConnection results = instance;
        if (results != null) {
            return results;
        }

        synchronized (JdbcConnection.class) {
            if (instance == null) {
                instance = new JdbcConnection(props);
            }
            return instance;
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, props);
    }

}
