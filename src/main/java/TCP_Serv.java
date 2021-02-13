import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Serv implements Runnable {

    private static int PORT = 0;

    private Message receivedMessage = null;

    private Network_Manager network;

    public Message getReceivedMessage() { return receivedMessage; }

    public TCP_Serv(Network_Manager network) {
        this.network = network;
    }

    public void run() {
        //Socket creation
        ServerSocket serverSocket = null;
        try {
            //Socket created with port 0 --> port automatically allocated and retrieved next line
            /*serverSocket = new ServerSocket(PORT);
            PORT = serverSocket.getLocalPort();*/
            //TODO
            serverSocket = new ServerSocket(1234);
            PORT=1234;

            this.network.getChat().getAgent().setTcpPort(PORT);
            System.out.println("--- TCP Server socket created ---");
        }
        catch (IOException e) { System.err.println("!!! Could not create the socket !!!"); }

        while (true) {
            //Object retrieving
            try {
                Socket link = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(link.getInputStream());
                receivedMessage = (Message) inputStream.readObject();

                this.network.getChat().getAgent().app.destinationUser = receivedMessage.getUser();
                this.network.getChat().getAgent().app.chatBox.setVisible(true);
                displayMessageReceived displayer = new displayMessageReceived(this.network.getChat().getAgent().app, receivedMessage);
                new Thread(displayer).start();

            } catch (ClassNotFoundException e) {
                System.err.println("Object received unknown : " + e);
            } catch (IOException e) {
                System.err.println("Error during object retrieving : " + e);
            }

            //Message display
            if (receivedMessage != null) {
                System.out.println(receivedMessage.toString());
            }

        }
    }
}
