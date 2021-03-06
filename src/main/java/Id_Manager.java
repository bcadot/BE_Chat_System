import java.net.*;
import java.util.Enumeration;

/**
 * A agent is given an id by retrieving the local class C @ip.
 * This @ is converted into a string.
 */
public class Id_Manager {


    private String id;
    private Agent agent;

    /**
     * Id_Manager default constructor
     */
    Id_Manager(Agent u) {
        this.agent = u;
        InetAddress ip = null;
        boolean found = false;
        try {
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            while (n.hasMoreElements() && !found) {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements() && !found) {
                    InetAddress addr = a.nextElement();
                    if (addr != null && (addr.getHostAddress().matches("192.168.*.*"))) {
                        ip = addr;
                        found = true;
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("No network interface");
        }
        try {
            this.id = ip.getHostAddress();
        } catch (NullPointerException e) {
            System.err.println("IP vide : " + e);
        }
    }

    public String getId() { return id; }
}
