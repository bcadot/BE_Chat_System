import java.io.Serializable;

public class User implements Serializable {

    private String ip;
    private String name;
    private int rcvPort;


    /**
     * User constructor, used to add new users to the list of active users.
     * @param ip the ip address
     * @param port the listening port
     * @param name the pseudonym of the user
     */
    public User(String ip, int port, String name) {
        this.ip = ip;
        this.rcvPort = port;
        this.name = name;
    }


    public String getIp() { return ip; }
    public int getRcvPort() { return rcvPort; }
    public String getName() { return name; }
    public void setIp(String ip) { this.ip = ip; }
    @Override
    public String toString() {
        return "(User " + ip + " = " + name + " on port " + rcvPort + ")";
    }

}
