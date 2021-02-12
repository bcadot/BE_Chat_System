/**
 * Manager to send and receive messages via sub classes.
 */

import java.io.*;
import java.net.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Enumeration;

public class Network_Manager {
    private InetAddress ip;     //TODO voir si vraiment utile
    private InetAddress broadcastip;

    private Chat chat;
    private UDP_Serv bdServer;
    private TCP_Serv tcpServer;

    /**
     * Default constructor, retrieve ip with Id_Manager and broadcast ip.
     */
    Network_Manager(Chat c){
        this.chat = c;
        this.bdServer = new UDP_Serv(this);
        this.tcpServer = new TCP_Serv(this);
        try {
            String ownIP = c.getAgent().getId().getId();
            this.ip = InetAddress.getByName(ownIP);
            this.broadcastip = getBroadcastAddress();
        }
        catch(UnknownHostException e){
            System.err.println("Host unknown" + e);
        }
        catch (SocketException e) {
            System.err.println("Could not determine broadcast address" + e);
        }
    }

    /**
     * Returns the local broadcast address
     *
     * @throws SocketException if the platform does not have at least one configured network interface
     */
    public InetAddress getBroadcastAddress() throws SocketException {
        InetAddress broadcast = null;
        boolean found = false;
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements() && !found) {
            NetworkInterface networkInterface = interfaces.nextElement();

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                broadcast = interfaceAddress.getBroadcast();
                if (broadcast != null && (broadcast.getHostAddress().matches("192.168.*.*")
                        /*|| broadcast.getHostAddress().matches("172.*.*.*")*/)) {
                    found = true;
                    break;
                }
            }
        }
        return broadcast;
    }

    /**
     *Broadcast the user pseudonym in UDP
     *
     * @param msg the message to broadcast
     * @throws IOException when transmission error
     */
    public void broadcastMessage(Message msg) throws IOException {
        try{
            broadcast(msg, this.broadcastip);
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
            oos.writeObject(broadcastMessage);
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

    /**
     * Send a message over TCP to a provided user
     *
     * @param msg message to send
     * @param user msg will be sent to this user
     * @throws IOException when transmission error
     */
    public void send(Message msg, User user) throws IOException {   //TODO ajouter le Data_Manager.insert()

        //Get ip and port from user
        String ip = null;
        int port = -1;
        try {
            ip = this.chat.getAgent().getUsers().getIPfromUsername(user.getName());
            port = this.chat.getAgent().getUsers().getPortfromUsername(user.getName());
        } catch (NullPointerException e) {
            System.err.println("Error during ip retrieving : " + e);
        }

        Socket clientSocket = new Socket();
        try{
            clientSocket = new Socket(InetAddress.getByName(ip), port);
            System.out.println("--- Client socket created ---");
        } catch (IOException e) { System.err.println("!!! Could not create the socket !!!"); }

        //Object writing
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(msg);
            outputStream.close();
        } catch (IOException e) {
            System.err.println("Error during object writing : " + e);
        }

        System.out.println("--- Client socket closed ---");
        clientSocket.close();
    }

    /*public Message receive(ServerSocket serverSocket, int port){
        Message msg = null;
        //Object retrieving
        try {
            Socket link = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(link.getInputStream());
            msg = (Message) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Object received unknown : " + e);
        } catch (IOException e) {
            System.err.println("Error during object retrieving : " + e);
        }

        return msg;
    }*/

    public InetAddress getIp() { return ip; }
    public Chat getChat() { return chat; }
    public UDP_Serv getBdServer() { return bdServer; }
    public TCP_Serv getTcpServer() { return tcpServer; }
    public InetAddress getBroadcastip() { return broadcastip; }
}
