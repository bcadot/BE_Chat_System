import java.io.Serializable;

/**
 * Represents a user.
 */
public class User implements Serializable {
    //TODO list attributes


    //The following attributes are managers for the User class.
    private Id_Manager id;
    private Pseudonym pseudo;
    private User_Manager users; //TODO gérer cette classe

    //The following attributes are determined by the previous managers and will be the ones used.
    private String ip;
    private int rcvPort = 1234; //TODO déterminer le port d'écoute attribué à un user --> classe server
    private String name;

    //This a Chat class
    private Chat chat;

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

    public User() {
        this.id = new Id_Manager(this);
        this.pseudo = new Pseudonym(this);
        this.users = new User_Manager(this);
        this.chat = new Chat(this);
        this.ip = id.getId();
        this.name = pseudo.getPseudonym();
    }
    /*
    public void startSession(User user) {
        Chat chat = new Chat(user);
    }

    public void endSession() {
        //fin ack
    }
    */
    public String getIp() { return ip; }
    public int getRcvPort() { return rcvPort; }
    public String getName() { return name; }
    public User_Manager getUsers() { return users; }
    public Chat getChat() { return chat; }
    public Id_Manager getId() { return id; }
    public Pseudonym getPseudo() { return pseudo; }
}
