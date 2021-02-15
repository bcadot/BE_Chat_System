import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Serv implements Runnable {

    private static int PORT = 0;

    private Message receivedMessage = null;

    private Network_Manager network;

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

                if (receivedMessage.getType().equals("File")) {
                    //Get home directory and create an empty file with the same name as the one sent
                    String home = System.getProperty("user.home");
                    String path = new File(home).getAbsolutePath();
                    File file = new File(path + System.getProperty("file.separator") + receivedMessage.getFile().getName());
                    if (file.createNewFile())
                        System.out.println("Nouveau fichier créé");
                    //Write in the created file
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] b = receivedMessage.getFileBytes();
                    fos.write(b);
                    fos.close();
                }

                this.network.getChat().getAgent().app.destinationUser = receivedMessage.getUser();
                this.network.getChat().getAgent().app.chatBox.setVisible(true);
                displayMessageReceived displayer = new displayMessageReceived(this.network.getChat().getAgent().app, receivedMessage);
                new Thread(displayer).start();
                inputStream.close();

            } catch (ClassNotFoundException e) {
                System.err.println("Object received unknown : " + e);
            } catch (IOException e) {
                System.err.println("Error during object retrieving : " + e);
            }

            //Message display (useful to debug)
            /*
            if (receivedMessage != null) {
                System.out.println(receivedMessage.toString());
            }
            */

        }
    }
}
