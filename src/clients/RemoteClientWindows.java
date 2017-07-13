package clients;

/**
 * Created by YotamG on 13/07/2017.
 */
public class RemoteClientWindows extends RemoteClient {

    RemoteClientWindows(String host, int port, String user, String password, OS os) {
        super(host, port, user, password, os);
    }

    RemoteClientWindows(RemoteMachine machine) {
        super(machine);
    }
}
