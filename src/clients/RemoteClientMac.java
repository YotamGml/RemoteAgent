package clients;

/**
 * Created by YotamG on 13/07/2017.
 */
public class RemoteClientMac extends RemoteClient {

    RemoteClientMac(String host, int port, String user, String password, OS os) {
        super(host, port, user, password, os);
    }

    RemoteClientMac(RemoteMachine machine) {
        super(machine);
    }
}
