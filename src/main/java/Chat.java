import java.io.Serializable;

/**
 * Represents what you need to establish a communication : a network manager and a data manager
 */
public class Chat implements Serializable {
    private Network_Manager network;
    private Data_Manager data;
    private String ipDest;
    //private int unicast = 0;
    //private int broadcast = 0;
    //private int multicast = 0;

    private User user;
    /*
    public Chat() {
        //this.broadcast = 1;
        //TODO gérer broadcast
    }

    public Chat(User dest) {
        //this.unicast = 1;
        //TODO gérer unicast
    }

    public Chat(User... users) {
        //this.multicast = 1;
        //TODO gérer multicast
    }
    */

    public Chat(User u) {
        this.user = u;
        this.network = new Network_Manager(this);
        this.data = new Data_Manager(this);
    }

    public User getUser() { return user; }
    public Network_Manager getNetwork() { return network; }
}
