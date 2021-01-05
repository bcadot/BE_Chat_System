import java.io.Serializable;
import java.net.*;
import java.util.Enumeration;

/**
 * A agent is given an id by retrieving the localhost @ip.
 * This @ is converted into a string.
 */
public class Id_Manager implements Serializable {


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
            //ip =  InetAddress.getLocalHost();
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            while (n.hasMoreElements() && !found)
            {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements() && !found)
                {
                    InetAddress addr = a.nextElement();
                    if ((addr instanceof Inet4Address) && !addr.getHostAddress().equals("127.0.0.1") && !addr.getHostAddress().equals("127.0.1.1") && !addr.getHostAddress().equals("10.193.228.180")) {  //TODO PENSER A ENLEVER LE DERNIER
                        ip = addr;
                        System.out.println(ip);
                        found = true;
                    }
                }
            }
        }
        /*catch (UnknownHostException e){
            System.out.println("Host unknown");
        }*/
        catch(SocketException e) { System.out.println("No network interface"); }
        if (ip != null) {
            this.id = ip.getHostAddress();
        }
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }
}
/*
    Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
    while (n.hasMoreElements())
    {
        NetworkInterface e = n.nextElement();
        System.out.println("Interface: " + e.getName());
        Enumeration<InetAddress> a = e.getInetAddresses();
        for (; a.hasMoreElements();)
        {
        InetAddress addr = a.nextElement();
        System.out.println("  " + addr.getHostAddress());
        }
    }
 */
