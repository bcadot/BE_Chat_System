/**
 * A user can choose a pseudonym. It can be changed whenever the user wants to. There can not be two identical
 * pseudonyms.
 */
public class Pseudonym {
    //Attributes
    private String pseudonym;

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
        Boolean valid = false;
        //TODO broadcastPseudonym()
        return valid;
    }

}
