import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A user is given an id by retrieving the localhost @ip.
 * This @ is converted into a string.
 */
public class Id_Manager {

    private String id;


    /**
     * Id_Manager default constructor
     */
    Id_Manager() {
        InetAddress ip = null;
        try {
            ip =  InetAddress.getLocalHost();
        }
        catch (UnknownHostException e){
            System.out.println("Host unknown");
        }
        if (ip != null) {
            this.id = ip.getHostAddress();
        }
    }

    public String getId() { return id; }
}
