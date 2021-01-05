import java.io.IOException;
import java.io.Serializable;

import static java.lang.Thread.sleep;

/**
 * A agent can choose a pseudonym. It can be changed whenever the agent wants to. There can not be two identical
 * pseudonyms.
 */
public class Pseudonym implements Serializable {
    //Attributes
    private String pseudonym;
    private Agent agent;

    /**
     * Pseudonym default constructor
     */
    Pseudonym(Agent u) {
        this.agent = u;
        Id_Manager id = this.agent.getId();
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
        Message pseudo = new Message(pseudonym, "requestValidatePseudonym");
        Network_Manager net = this.agent.getChat().getNetwork();
        UDP_Serv serv = net.getBdServer();

        //Send pseudo in broadcast UDP
        try {
            net.broadcastMessage(pseudo);
        } catch (IOException e) {
            System.out.println("Error during broadcast transmission");
        }

        Thread wait_for_answer = new Thread(serv);
        wait_for_answer.start();
        try {
            sleep(2000);
        }
        catch (Exception e){
            System.out.println("Not tired");
        }

        return !serv.isAnswer_received();
    }

    /**
     * Notify other active users that the current agent has been changed.
     */
    public void notifyUsers(){
        Message newUser = new Message(new User(this.agent.getId().getId(), this.agent.getRcvPort(), this.getPseudonym()));
        Network_Manager net = this.agent.getChat().getNetwork();

        //Send pseudo in broadcast UDP
        try {
            net.broadcastMessage(newUser);
        } catch (IOException e) {
            System.out.println("Error during broadcast transmission");
        }
    }

}
