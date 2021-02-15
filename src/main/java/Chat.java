/**
 * Represents what you need to establish a communication : a network manager and a data manager
 */
public class Chat {
    private Network_Manager network;
    private Data_Manager data;

    private Agent agent;

    public Chat(Agent u) {
        this.agent = u;
        this.network = new Network_Manager(this);
        this.data = new Data_Manager(this);
    }

    public Agent getAgent() { return agent; }
    public Network_Manager getNetwork() { return network; }
    public Data_Manager getData() { return data; }
}
