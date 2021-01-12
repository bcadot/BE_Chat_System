import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Serv implements Runnable {

    private static int PORT = 0;

    private Network_Manager network;

    public TCP_Serv(Network_Manager network) {
        this.network = network;
    }

    public void run() {
        Message msg = null;

        //Socket creation
        ServerSocket serverSocket = null;
        try {
            //Socket created with port 0 --> port automatically allocated and retrieved next line
            serverSocket = new ServerSocket(PORT);
            PORT = serverSocket.getLocalPort();
            this.network.getChat().getAgent().setTcpPort(PORT);
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
