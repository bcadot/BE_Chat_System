/**
 * Represents a conversation between 2 or more users in which you can send or receive messages.
 */
public class Chat {
    private Network_Manager network;
    private Data_Manager data;
    private String ipDest;
    //private int unicast = 0;
    //private int broadcast = 0;
    //private int multicast = 0;

    private User user;

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

    public User getUser() { return user; }
    public Network_Manager getNetwork() { return network; }
}
