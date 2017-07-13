package ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Properties;

/**
 * Created by YotamG on 26/06/2017.
 */
public final class RemoteSession {

    private static final Logger log = LogManager.getLogger();
    private static final int SESSION_TIMEOUT = 5000;
    private static final int EXIT_TIMEOUT_CODE = 999;

    private Session session = null;
    private String host;
    private int port;
    private String user;
    private String password;
    private Properties sshServerConfiguration;


    // Default constructor
    public RemoteSession(String host, int port, String user, String password){
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        // default configuration for SSH Server - less secured
        sshServerConfiguration = new Properties();
        sshServerConfiguration.put("StrictHostKeyChecking", "no");
    }

    // constructor with server properties
    public RemoteSession(String host, int port, String user, String password, Properties sshServerConfiguration){
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.sshServerConfiguration = sshServerConfiguration;
    }

    public void connect() throws JSchException {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig(sshServerConfiguration);
            log.info("try to connect to ssh server at "+user+"@"+host+"...");
            session.connect(SESSION_TIMEOUT);
            log.info("Connected to ssh server at: "+user+"@"+host+" ...");

        } catch (JSchException e) {
            log.error("Failed to establish connection with the SSH server " + host + ":" + port + " : " + e.getMessage());
            throw e;
        }

    }

    public void disconnect() {
        session.disconnect();
        log.info("Session with "+user+"@"+host+" disconnected");
    }

    public String getUserName() {
        return session.getUserName();
    }

    public boolean isConnected() {
        return session.isConnected();
    }
    public Session getSession() { return session; };

}
