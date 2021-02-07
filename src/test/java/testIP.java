import java.net.InetAddress;
import java.net.SocketException;

public class testIP {

    public static void main(String[] args) {
        Agent test = new Agent();
        System.out.println("--> IP           : " + test.getId().getId());
        InetAddress bd = null;
        try {
            bd = test.getChat().getNetwork().getBroadcastAddress();
        }
        catch (SocketException e) {
            System.err.println("Can't figure out broadcast address" + e);
        }
        if (bd == null) System.out.println("bd nulle");
        System.out.println("--> IP broadcast : " + bd.getHostAddress());
        System.out.println("Comparaison avec les adresses du NetworkManager :");
        System.out.println("--> Net IP           : " + test.getChat().getNetwork().getIp().getHostAddress());
        System.out.println("--> Net IP broadcast : " + test.getChat().getNetwork().getBroadcastip().getHostAddress());
    }
}
