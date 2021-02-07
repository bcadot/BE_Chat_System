/**
 * Represents what you need to establish a communication : a network manager and a data manager
 */
public class Chat {
    private Network_Manager network;
    private Data_Manager data;
    private String ipDest;
    //private int unicast = 0;
    //private int broadcast = 0;
    //private int multicast = 0;

    private Agent agent;
    /*
    public Chat() {
        //this.broadcast = 1;
        //TODO gérer broadcast
    }

    public Chat(Agent dest) {
        //this.unicast = 1;
        //TODO gérer unicast
    }

    public Chat(Agent... users) {
        //this.multicast = 1;
        //TODO gérer multicast
    }
    */

    public Chat(Agent u) {
        this.agent = u;
        this.network = new Network_Manager(this);
        this.data = new Data_Manager(this);
    }

    public Agent getAgent() { return agent; }
    public Network_Manager getNetwork() { return network; }
    public Data_Manager getData() { return data; }
}
