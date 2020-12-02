import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Network_Manager {
    private InetAddress ip;

    Network_Manager(){
        try {
            this.ip =  InetAddress.getLocalHost();
        }
        catch (UnknownHostException e){
            System.out.println("Host unknown");
        }
    }

    public void broadcastPseudonym(String pseudo) throws IOException {
        BroadcastingClient client = new BroadcastingClient();
        try{
            client.broadcast(pseudo, InetAddress.getByName("255.255.255.255"));
        }
        catch(UnknownHostException e){
            System.out.println("Host unknown");
        }
    }

    public class BroadcastingClient {
        private  DatagramSocket socket = null;

        public void broadcast(String broadcastMessage, InetAddress address) throws IOException {
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = broadcastMessage.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1234);
            socket.send(packet);
            socket.close();
        }
    }

    public InetAddress getIp() {
        return ip;
    }
}
