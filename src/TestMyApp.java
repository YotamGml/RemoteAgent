import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ssh.RemoteSession;

/**
 * Created by YotamG on 29/05/2017.
 */
public class TestMyApp {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        log.info("This is My first log !");
        RemoteSession remoteSession = new RemoteSession("192.168.1.12", 22, "YotamG", "123456" );
        remoteSession.connect();
        System.out.println(remoteSession.getUserName());
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(remoteSession.isConnected());
        System.out.println(remoteSession.getUserName());
        //remoteSession.disconnect();
    }
}
