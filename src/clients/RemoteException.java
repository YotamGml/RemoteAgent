package clients;

/**
 * Created by YotamG on 12/07/2017.
 */
public class RemoteException extends RuntimeException {

    RemoteException(String message) {
        super(message);
    }

    RemoteException(String message, Throwable t) {
        super(message,t);
    }

    RemoteException(int exitStatus,String message, Throwable t) {
        super("SSH Command Failed With Error "+exitStatus+": "+message,t);
    }
}
