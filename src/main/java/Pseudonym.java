import java.io.IOException;


/**
 * A user can choose a pseudonym. It can be changed whenever the user wants to. There can not be two identical
 * pseudonyms.
 */
public class Pseudonym {
    //Attributes
    private String pseudonym;

    /**
     * Pseudonym default constructor
     */
    Pseudonym(){
        Id_Manager id = new Id_Manager();
        this.pseudonym = id.getId();
    }

    /**
     * Set pseudonym.
     *
     * @param   pseudo  The pseudonym you want
     */
    public void setPseudonym(String pseudo) {
        if (validatePseudonym(pseudo)) {
            pseudonym = pseudo;
            //TODO Include notifyUsers()
        } else {
            //Pseudo non valide
        }
    }

    public String getPseudonym() {
        return pseudonym;
    }

    /**
     * Check whether the given pseudonym is valid or not.
     *
     * @param   pseudonym   the pseudonym you want to check
     * @return              true if the given pseudonym is valid
     */
    public Boolean validatePseudonym(String pseudonym) {
        Message pseudo = new Message(pseudonym, "BroadcastValidate");
        Network_Manager net = new Network_Manager();
        UDP_Serv serv = new UDP_Serv(this);

        //Send pseudo in broadcast UDP
        try{
            net.broadcastMessage(pseudo);
        }
        catch(IOException e){
            System.out.println("Error during broadcast transmission");
        }

        Thread wait_for_answer = new Thread(serv);
        wait_for_answer.start();

        return serv.isAnswer_received();
    }

}
