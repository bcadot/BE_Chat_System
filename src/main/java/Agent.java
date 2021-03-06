/**
 * Represents the agent.
 */

public class Agent {
    //TODO list attributes
    protected App app;
    private UDP_Serv udpServ;
    private TCP_Serv tcpServ;


    //The following attributes are managers for the Agent class.
    private Id_Manager id;
    private Pseudonym pseudo;
    private User_Manager users;

    //The following attributes are updated when the agent is initialized
    private int tcpPort = -1;   //updated when TCP_Serv starts

    //This a Chat class
    private Chat chat;

    /**
     * This is the main constructor.
     * You need to provide an App for the GUI.
     */
    public Agent(App app) {
        this.app=app;
        this.id = new Id_Manager(this);
        this.pseudo = new Pseudonym(this);
        this.users = new User_Manager(this);
        this.chat = new Chat(this);
        this.udpServ = chat.getNetwork().getBdServer();
        Thread wait_for_answer = new Thread(udpServ);
        wait_for_answer.start();
        this.tcpServ = chat.getNetwork().getTcpServer();
        Thread recep = new Thread(tcpServ);
        recep.start();
    }

    /**
     * This constructor is only used for testing. Do not use it for the main application.
     */
    public Agent() {
        this.id = new Id_Manager(this);
        this.pseudo = new Pseudonym(this);
        this.users = new User_Manager(this);
        this.chat = new Chat(this);
        this.udpServ = chat.getNetwork().getBdServer();
        Thread wait_for_answer = new Thread(udpServ);
        wait_for_answer.start();
        this.tcpServ = chat.getNetwork().getTcpServer();
        Thread recep = new Thread(tcpServ);
        recep.start();
    }

    public int getTcpPort() { return tcpPort; }
    public User_Manager getUsers() { return users; }
    public Chat getChat() { return chat; }
    public Id_Manager getId() { return id; }
    public Pseudonym getPseudo() { return pseudo; }
    public UDP_Serv getServ() { return udpServ; }
    public void setTcpPort(int port) { this.tcpPort = port; }
}
