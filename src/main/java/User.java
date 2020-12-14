/**
 * Represents a user.
 */
public class User {
    //TODO list attributes

    //The following attributes are managers for the User class.
    private Id_Manager id = new Id_Manager();
    private Pseudonym pseudo = new Pseudonym();
    private User_Manager users = new User_Manager(); //TODO gérer cette classe

    //The following attributes are determined by the previous managers and will be the ones used.
    private String ip = id.getId();
    private int rcvPort = 1234; //TODO déterminer le port d'écoute attribué à un user
    private String name = pseudo.getPseudonym();

    //This a Chat class
    public Chat chat = new Chat();

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

    public void startSession(User user) {
        Chat chat = new Chat(user);
    }

    public void endSession() {
        //fin ack
    }

    public String getIp() {
        return ip;
    }
    public int getRcvPort() {
        return rcvPort;
    }
    public String getName() {
        return name;
    }
    public User_Manager getUsers() { return users; }
}
