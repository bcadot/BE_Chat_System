/**
 * Represents the agent.
 */

public class Agent {
    //TODO list attributes
    private UDP_Serv serv;


    //The following attributes are managers for the Agent class.
    private Id_Manager id;
    private Pseudonym pseudo;
    private User_Manager users;

    //The following attributes are determined by the previous managers and will be the ones used.
    private int tcpPort = -1;   //updated when TCP_Serv starts

    //This a Chat class
    private Chat chat;

    public Agent() {
        this.id = new Id_Manager(this);
        this.pseudo = new Pseudonym(this);
        this.users = new User_Manager(this);
        this.chat = new Chat(this);
        this.serv = chat.getNetwork().getBdServer();
        Thread wait_for_answer = new Thread(serv);
        wait_for_answer.start();
        //TODO démarrer le serveur TCP
    }
    /*
    public void startSession(Agent user) {
        Chat chat = new Chat(user);
    }

    public void endSession() {
        //fin ack
    }
    */
    public int getTcpPort() { return tcpPort; }
    public User_Manager getUsers() { return users; }
    public Chat getChat() { return chat; }
    public Id_Manager getId() { return id; }
    public Pseudonym getPseudo() { return pseudo; }
    public UDP_Serv getServ() { return serv; }
    public void setTcpPort(int port) { this.tcpPort = port; }
}
