import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents a message which is sent through the network.
 */
public class Message implements Serializable {

    //Attributes
    private String message;
    private User user;
    private String type; //notificationPseudonym | requestValidatePseudonym | answerValidatePseudonym | Chat | File
    //private Timestamp timestamp;

    public Message(String message, String type){
        this.message = message;
        this.type = type;
    }
    public Message(User u) {
        this.user = u;
        this.type = "notificationPseudonym";
    }
    public Message(String message) {
        this.message = message;
        //TODO Update timestamp / time
    }

    public String getMessage() { return message; }
    public String getType() { return type; }
    public User getUser() { return user; }
    @Override
    public String toString() {
        return /*user.toString() + " : " +*/ message + " [" + /*timestamp.toString() +*/ "]";
    }
}