/**
 * Manager to send and receive messages via sub classes.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

public class Network_Manager implements Serializable {
    private InetAddress ip;

    private Chat chat;
    private UDP_Serv bdServer;

    /**
     * Default constructor, retrieve localhost @ip.
     */
    Network_Manager(Chat c){
        this.chat = c;
        this.bdServer = new UDP_Serv(this);
        try {
            String ownIP = c.getUser().getId().getId();
            this.ip = InetAddress.getByName(ownIP);
        }
        catch(UnknownHostException e){
            System.out.println("Host unknown");
        }
    }

    /**
     *Broadcast the user pseudonym in UDP
     *
     * @param msg the message to broadcast
     * @throws IOException when transmission error
     */
    public void broadcastMessage(Message msg) throws IOException {
        try{
            broadcast(msg, InetAddress.getByName("255.255.255.255"));
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
    public void broadcast(Message broadcastMessage, InetAddress address) throws IOException {
        //Socket creation
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        //Transformation of object Message into array of bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(broadcastMessage); //TODO pb here
        } catch(IOException e){
            System.err.println("Error during serialisation : " + e);
        }

        //Creation and sending of UDP datagram
        try{
            byte[] buffer = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1234);
            socket.send(packet);
        } catch(IOException e){
            System.err.println("Error during pseudonym broadcast : " + e);
        }
        socket.close();
    }

    public InetAddress getIp() { return ip; }
    public Chat getChat() { return chat; }
    public UDP_Serv getBdServer() { return bdServer; }
}
