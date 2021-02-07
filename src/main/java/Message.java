import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a message which is sent through the network.
 */
public class Message implements Serializable {

    //Attributes
    private String message;
    private User user;
    private String type; //notificationPseudonym | requestValidatePseudonym | answerValidatePseudonym | Chat | File
    private LocalDateTime timestamp;

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
        this.timestamp = LocalDateTime.now();
        this.type = "Chat";
    }

    public String getMessage() { return message; }
    public String getType() { return type; }
    public User getUser() { return user; }
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, HH:mm");
        if (message == null || message.isEmpty())
            return /*user.toString() + " : " +*/ "" + " [" + dtf.format(timestamp) + "]";
        else
            return /*user.toString() + " : " +*/ message + " [" + dtf.format(timestamp) + "]";
    }
}