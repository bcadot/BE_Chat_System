import java.io.Serializable;

/**
 * Represents a user.
 */
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
    @Override
    public String toString() {
        return "(" + ip + ") - " + name;
    }

}
