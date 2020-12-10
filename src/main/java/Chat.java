/**
 * Represents a conversation between 2 or more users in which you can send or receive messages.
 */
public class Chat {
    private Network_Manager User;
    private String ipDest;
    private String history;
    //private int unicast = 0;
    //private int broadcast = 0;
    //private int multicast = 0;

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

}
