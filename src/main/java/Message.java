import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents a message which is sent through the network.
 */
public class Message implements Serializable {

    //Attributes
    private String message;
    private String type; //notifyPseudonym | requestValidatePseudonym | answerValidatePseudonym | Chat | File
    private Timestamp timestamp;

    public Message(String message, String type){
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String toString(){
        return message;
    }
}