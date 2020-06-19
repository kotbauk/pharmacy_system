package connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public class JdbcConnection implements AutoCloseable {
    private final Connection connection;
    private boolean closed = false;

    public JdbcConnection(Properties properties){
        final String username = properties.getProperty("db.user");
        final String password = properties.getProperty("db.password");
        final String url = properties.getProperty("db.url");
        final String driverName = properties.getProperty("db.driverName");

        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
        Locale.setDefault(Locale.ENGLISH);

        try {
            DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
            connection = DriverManager.getConnection(url, props);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        if (closed){
            throw new IllegalStateException();
        }
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null){
            connection.close();
            closed = true;
        }
    }
}
