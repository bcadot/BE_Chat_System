import java.net.InetAddress;
import java.net.UnknownHostException;

public class Id_Manager {

    private String id;


    //Retrieve localhost @IP and convert it as a String
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

    public String getId() {
        return id;
    }
}
