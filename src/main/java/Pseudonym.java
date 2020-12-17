import java.io.IOException;

/**
 * A user can choose a pseudonym. It can be changed whenever the user wants to. There can not be two identical
 * pseudonyms.
 */
public class Pseudonym {
    //Attributes
    private String pseudonym;
    private User user;

    /**
     * Pseudonym default constructor
     */
    Pseudonym() {
        Id_Manager id = this.user.getId();
        this.pseudonym = id.getId();
    }

    /**
     * Set pseudonym.
     *
     * @param pseudo The pseudonym you want
     * @return boolean: true if pseudo change, false if not
     */
    public boolean setPseudonym(String pseudo) {
        boolean pseudoChanged = false;
        if (validatePseudonym(pseudo)) {
            pseudonym = pseudo;
            pseudoChanged = true;
            notifyUsers();
        }
        return pseudoChanged;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    /**
     * Check whether the given pseudonym is valid or not.
     *
     * @param pseudonym the pseudonym you want to check
     * @return true if the given pseudonym is valid
     */
    public Boolean validatePseudonym(String pseudonym) {
        Message pseudo = new Message(pseudonym, "BroadcastValidate");
        Network_Manager net = this.user.getChat().getNetwork();
        UDP_Serv serv = this.user.getChat().getNetwork().getBdServer();

        //Send pseudo in broadcast UDP
        try {
            net.broadcastMessage(pseudo);
        } catch (IOException e) {
            System.out.println("Error during broadcast transmission");
        }

        Thread wait_for_answer = new Thread(serv);
        wait_for_answer.start();

        return !serv.isAnswer_received();
    }

    /**
     * Notify other active users that the current user has been changed.
     */
    public void notifyUsers(){
        Message newUser = new Message(this.user);
        Network_Manager net = this.user.getChat().getNetwork();

        //Send pseudo in broadcast UDP
        try {
            net.broadcastMessage(newUser);
        } catch (IOException e) {
            System.out.println("Error during broadcast transmission");
        }
    }

}
