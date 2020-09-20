import connection.JdbcConnection;
import connection.SshConnect;
import gui.GuiManager;
import gui.StartPage;
import transation.TransactionUtils;


import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Properties dbProperties;
        Properties sshProperties;
        try (InputStream inputStream =
                     Main.class.getClassLoader().getResourceAsStream("db.properties");
             InputStream inputStreamSsh = Main.class.getClassLoader().getResourceAsStream("ssh.properties")) {
            dbProperties = new Properties();
            dbProperties.load(inputStream);
            sshProperties = new Properties();
            sshProperties.load(inputStreamSsh);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SshConnect ignored = new SshConnect(sshProperties);
        JdbcConnection jdbcConnection = new JdbcConnection(dbProperties);
        TransactionUtils.setConnection(jdbcConnection);
        JFrame.setDefaultLookAndFeelDecorated(true);
         new GuiManager(new StartPage()).showPage();
         System.out.println(UUID.randomUUID().toString());
    }
}
