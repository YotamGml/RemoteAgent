package clients;

/**
 * RemtoeMachine holds the machine data that needs for creating SSH session
 */
public class RemoteMachine {


    private String machineName;
    private String machineIP;
    private int SSHServerPort;
    private String userName;
    private String userPassword;
    private OS machineOS = null;

    public RemoteMachine(String machineName, String machineIP, int SSHServerPort, String userName, String userPassword, OS machineOS) {
        this.machineName = machineName;
        this.machineIP = machineIP;
        this.SSHServerPort = SSHServerPort;
        this.userName = userName;
        this.userPassword = userPassword;
        this.machineOS = machineOS;
    }

    public String getMachineName() { return machineName; }

    public String getMachineIP() { return machineIP; }

    public int getSSHServerPort() { return SSHServerPort; }

    public String getUserName() { return userName; }

    public String getUserPassword() { return userPassword; }

    public OS getMachineOS() { return machineOS; }

}
