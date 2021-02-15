import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    //used if we want to send a file
    private byte[] fileBytes;
    private File file;

    public Message(User u, String message, String type) {
        this.user = u;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.type = type;
    }

    public Message(User u, File file) throws IOException {
        this.user = u;
        this.fileBytes = this.prepareFile(file);
        this.file = file;
        this.timestamp = LocalDateTime.now();
        this.type = "File";
    }

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

    private byte[] prepareFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        return fis.readAllBytes();
    }

    public String getMessage() { return message; }
    public String getType() { return type; }
    public User getUser() { return user; }
    public File getFile() { return file; }
    public byte[] getFileBytes() { return fileBytes; }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM, HH:mm");
        //Chat and non null user
        if (type.equals("Chat") && user != null) {
            if (message == null || message.isEmpty())
                return user.toString() + " : " + "" + " [" + timestamp.format(dtf) + "]";
            else
                return user.toString() + " : " + message + " [" + timestamp.format(dtf) + "]";
        //Chat and null user
        } else if (type.equals("Chat")) {
            if (message == null || message.isEmpty())
                return "" + " [" + timestamp.format(dtf) + "]";
            else
                return message + " [" + timestamp.format(dtf) + "]";
        //File and non null user
        //TODO modifier proprement
        } else if (type.equals("File") && user != null) {
            return user.toString() + " : " + file.getName() + " [" + timestamp.format(dtf) + "]";
        } else if (type.equals("File")) {
            return file.getName() + " [" + timestamp.format(dtf) + "]";
        } else {
            return "{" + type + "}";
        }
    }
}