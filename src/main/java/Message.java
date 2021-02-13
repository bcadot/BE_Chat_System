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

    public Message (User u, String message, String type) {
        this.user = u;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.type = type;
    }

    public Message(String message, String type){
        this.message = message;
        this.timestamp = LocalDateTime.now();
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM, HH:mm");
        if (user != null) {
            if (message == null || message.isEmpty())
                return user.toString() + " : " + "" + " [" + timestamp.format(dtf) + "]";
            else
                return user.toString() + " : " + message + " [" + timestamp.format(dtf) + "]";
        } else {
            if (message == null || message.isEmpty())
                return "" + " [" + timestamp.format(dtf) + "]";
            else
                return message + " [" + timestamp.format(dtf) + "]";
        }
    }
}