/**
 * Manage to send and receive messages.
 */

import java.io.IOException;
import java.net.*;

public class Network_Manager implements Runnable {
    private InetAddress ip;

    /**
     * Default constructor, retrieve localhost @ip.
     */
    Network_Manager(){
        try {
            this.ip =  InetAddress.getLocalHost();
        }
        catch (UnknownHostException e){
            System.out.println("Host unknown");
        }
    }

    /**
     *Broadcast the user pseudonym in UDP
     *
     * @param pseudo the pseudo to broadcast
     * @throws IOException when transmission error
     */
    public void broadcastPseudonym(String pseudo) throws IOException {
        try{
            broadcast(pseudo, InetAddress.getByName("255.255.255.255"));
        }
        catch(UnknownHostException e){
            System.out.println("Host unknown");
        }
    }

    /**
     * General broadcast method using UDP
     *
     * @param broadcastMessage message to broadcast
     * @param address broadcast address
     * @throws IOException when transmission error
     */
    public void broadcast(String broadcastMessage, InetAddress address) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] buffer = broadcastMessage.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1234);
        socket.send(packet);
        socket.close();
    }

    //TODO à finir (à mettre dans une autre classe avec un attribut privé boolean par exemple
    // qui est faux si exceptiion du timeout levée et qui est vrai sinon. Puis dans validate pseudonym, accéder à cet attribut)
    public void run(){
            DatagramSocket server = null;
        try{
            server = new DatagramSocket(1234);
            server.setSoTimeout(2000);
            byte[] buffer = new byte[8192];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            server.receive(packet);
        }
        catch(SocketException e){
            System.out.println("Socket could not bind to the specified port");
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            server.close();
        }
    }


    public InetAddress getIp() {
        return ip;
    }
}
