package connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SshConnect implements AutoCloseable {
    private Session session = null;

    public SshConnect(Properties properties) {
        try {
            if(!Boolean.parseBoolean(properties.getProperty("ssh.useSsh"))){
                return;
            }
            final int localPort = Integer.parseInt(properties.getProperty("ssh.localPort"));
            final int remotePort = Integer.parseInt(properties.getProperty("ssh.remotePort"));
            final String remoteHost = properties.getProperty("ssh.remoteHost");
            final String host = properties.getProperty("ssh.host");
            final String user = properties.getProperty("ssh.user");
            final String password = properties.getProperty("ssh.password");

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            session.setPortForwardingL(localPort, remoteHost, remotePort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
