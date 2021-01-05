import java.io.Serializable;

/**
 * Represents a user.
 */
public class Agent implements Serializable {
    //TODO list attributes
    private UDP_Serv serv;


    //The following attributes are managers for the Agent class.
    private Id_Manager id;
    private Pseudonym pseudo;
    private User_Manager users;

    //The following attributes are determined by the previous managers and will be the ones used.
    private int rcvPort = 1234; //TODO déterminer le port d'écoute attribué à un user --> classe server

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
    }
    /*
    public void startSession(Agent user) {
        Chat chat = new Chat(user);
    }

    public void endSession() {
        //fin ack
    }
    */
    public int getRcvPort() { return rcvPort; }
    public User_Manager getUsers() { return users; }
    public Chat getChat() { return chat; }
    public Id_Manager getId() { return id; }
    public Pseudonym getPseudo() { return pseudo; }
    public UDP_Serv getServ() { return serv; }
}
