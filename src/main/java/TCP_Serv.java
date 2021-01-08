import java.net.*;
import java.io.*;
import java.lang.Thread;

public class TCP_Serv {

    //PORT A DETERMINER CI DESSOUS
    private static int PORT = 1200;

    private Network_Manager network;

    public TCP_Serv(Network_Manager network) {
        this.network = network;
    }

    public Network_Manager getNetwork() { return network; }

    public void run() throws IOException {
        Message msg = null;

        //TODO Déterminer le port à utiliser

        //Socket creation
        ServerSocket serverSocket = new ServerSocket();
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("--- TCP Server socket created ---");
        }
        catch (IOException e) { System.err.println("!!! Could not create the socket !!!"); }

        while (true) {

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

            //Message display
            if (msg != null) {
                System.out.println(msg.toString());
            }

        }
    }
}
