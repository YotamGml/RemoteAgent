package clients;

import ssh.RemoteSession;
import ssh.SSHClient;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by YotamG on 12/07/2017.
 */
public abstract class RemoteClient implements Closeable {

    private RemoteSession remoteSession;
    private SSHClient sshClient;
    private String host;
    private int port;
    private String user;
    private String password;
    private String machineName;
    private OS machineOs;


    RemoteClient(String host, int port, String user, String password, OS os) {
        try {
            remoteSession = new RemoteSession(host,port,user,password);
            remoteSession.connect();
            this.machineOs = os;
        } catch (Exception e) {
            remoteSession.disconnect();
            throw new RemoteException("Failed To create RemoteClient instance:"+e.getMessage(), e);
        }
    }

    RemoteClient(RemoteMachine machine) {
        this(machine.getMachineIP(), machine.getSSHServerPort(), machine.getUserName(), machine.getUserPassword(), machine.getMachineOS());
        this.machineName = machine.getMachineName();
    }

    protected void setShellConfig(SSHClient client, ShellConf conf) {
        client.setCommandTimeout(conf.COMMAND_TIMEOUT);
        client.printOutputStream(conf.PRINT_OUTPUT_STREAM);
        client.printErrorStream(conf.PRINT_ERROR_STREAM);
    }

    protected SSHClient getSSHCLient(ShellConf conf) {
        SSHClient client = new SSHClient(this.remoteSession.getSession());
        if (conf!=null) setShellConfig(client, conf);
        return client;
    }

    @Override
    public void close() throws IOException {
        remoteSession.disconnect();
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }


    public void sendCommand(String command) throws Exception {
        this.sendCommand(command, (ShellConf)null);
    }

    public void sendCommand(String command, ShellConf conf) throws Exception {
        SSHClient sshClient = getSSHCLient(conf);
        sshClient.sendCommand(command);
    }


    public void sendCommand(String workingFolder, String command) throws Exception {
        this.sendCommand(workingFolder, command, null);
    }

    public void sendCommand(String workingFolder, String command, ShellConf conf) throws Exception {
        SSHClient sshClient = getSSHCLient(conf);
        sshClient.sendCommand("cd " + workingFolder +" && "+ command);
    }





}
