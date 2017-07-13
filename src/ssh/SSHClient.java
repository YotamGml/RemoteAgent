package ssh;

/**
 * Created by YotamG on 29/05/2017.
 */

import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

public class SSHClient {

    private static final Logger log = LogManager.getLogger();

    private static final int EXIT_TIMEOUT_CODE = 999;
    private static final int BUFFER_SIZE = 1024;

    private int CONNECTION_TIMEOUT = 5000;
    private int commandTimeout = 0;
    private int exitStatus = 0;

    private Session session;
    private InputStream inputStream = null;
    private boolean printOutputStream = false;
    private boolean printErrStream = true;
    private StringBuilder output;

    public SSHClient(Session session) {
        this.session = session;
    }


    /**
     * @param flag
     * Print output stream to console
     */
    public void printOutputStream(boolean flag) {
        printOutputStream = flag ;
    }


    /**
     * @param flag
     * Print error stream to console
     */
    public void printErrorStream(boolean flag) {
        printErrStream = flag ;
    }


    /**
     * @param time
     * Set command timeout (milliseconds), 0 - no timeout.
     */
    public void setCommandTimeout(int time) {
        commandTimeout = time;
    }


    /**
     * @return exit status of the remote shell execution, 0 - operation success
     */
    public int getExitStatus() {
        return exitStatus;
    }


    /**
     * @return All console output
     */
    public String getConsoleOutput() {
        return output.toString();
    }


    public void sendCommand(String command) throws Exception {

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        // read stream from the remote
        inputStream = channel.getInputStream();

        // no stream send to the remote
        channel.setInputStream(null);

        // in default - errStream will print to the console
        if (printErrStream) {
            ((ChannelExec) channel).setErrStream(System.err);
        }

        output = new StringBuilder("");
        long start = System.currentTimeMillis();
        channel.connect(CONNECTION_TIMEOUT);
        byte[] buf = new byte[BUFFER_SIZE];

        while (true) {
            if ( (commandTimeout!=0) && (System.currentTimeMillis()-start )>commandTimeout ) {
                exitStatus = EXIT_TIMEOUT_CODE;
                break;
            }
            // read input stream from remote shell
            while (inputStream.available() > 0) {
                int i = inputStream.read(buf, 0, BUFFER_SIZE);
                // no more data to read
                if (i < 0) break;
                String out = new String(buf, 0, i);
                if (printOutputStream) System.out.print(out);
                output.append(out);
            }
            if (channel.isClosed()) {
                // command finish but still have data in the input stream
                if (inputStream.available()>0) continue;
                exitStatus = channel.getExitStatus();
                break;
            }
        }
        // close also the inputStream object
        channel.disconnect();
        if (exitStatus == EXIT_TIMEOUT_CODE) {
            String msg = "Command Stopped - The execution time reached to its timeout ("+ commandTimeout +")";
            throw new TimeoutException(msg);
        }
    }

    public static void main (String[] args){

        String command1="zip --help";
        String command2="cmd.exe /c dir";
        // sendCommand("cd " + workingFolder +" && "+ command);


    }

}
