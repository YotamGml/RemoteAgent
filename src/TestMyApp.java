import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ssh.RemoteSession;
import ssh.SSHClient;

/**
 * Created by YotamG on 29/05/2017.
 */
public class TestMyApp {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        log.info("This is My first log !");
        RemoteSession remoteSession = new RemoteSession("192.168.1.12", 22, "YotamG", "123456" );
        remoteSession.connect();
        SSHClient ssh = new SSHClient(remoteSession.getSession());
        ssh.printOutputStream(true);
        ssh.sendCommand("cd c:\\Yotam&&dir");
        ssh.sendCommand("echo %USERPROFILE%");
        remoteSession.disconnect();
    }
}
