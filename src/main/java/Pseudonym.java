import java.io.IOException;

/**
 * An agent can choose a pseudonym. It can be changed whenever the agent wants to. There can not be two identical
 * pseudonyms.
 */
public class Pseudonym {
    //Attributes
    private String pseudonym;
    private Agent agent;

    /**
     * Pseudonym default constructor
     */
    Pseudonym(Agent u) {
        this.agent = u;
        this.pseudonym = this.agent.getId().getId();
    }

    /**
     * Setw pseudonym.
     *
     * @param p The pseudonym you want
     * @return boolean: true if pseudo change, false if not
     * @throws NullPointerException if given pseudonym is empty
     */
    public boolean setPseudonym(String p) throws NullPointerException {
        String pseudo = p.trim();
        if (pseudo.isEmpty()) throw new NullPointerException();
        if (pseudo.equals(this.pseudonym)) return false;
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
     * Checks whether the given pseudonym is valid or not.
     *
     * @param pseudonym the pseudonym you want to check
     * @return true if the given pseudonym is valid
     */
    public Boolean validatePseudonym(String pseudonym) {
        Message pseudo = new Message(pseudonym, "requestValidatePseudonym");
        Network_Manager net = this.agent.getChat().getNetwork();

        //Send pseudo in broadcast UDP
        try {
            net.broadcastMessage(pseudo);
        } catch (IOException e) {
            System.err.println("Error during broadcast transmission");
        }

        for (int i = 0; i < 50000; i++) {
            System.out.print("");
        }
        System.out.println();
        return !agent.getServ().isAnswer_received();
    }

    /**
     * Notifies other active users that the current agent has been changed.
     */
    public void notifyUsers(){
        Message newUser = new Message(new User(this.agent.getId().getId(),
                this.agent.getTcpPort(),
                this.getPseudonym()));
        Network_Manager net = this.agent.getChat().getNetwork();

        //Send pseudo in broadcast UDP
        try {
            net.broadcastMessage(newUser);
        } catch (IOException e) {
            System.out.println("Error during broadcast transmission");
        }
    }

}
