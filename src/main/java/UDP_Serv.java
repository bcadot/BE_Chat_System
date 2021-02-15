import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDP_Serv implements Runnable {
    private boolean answer_received = false;
    public static int port = 1234;

    private Network_Manager network;

    UDP_Serv(Network_Manager n) {
        this.network = n;
    }

    public boolean isAnswer_received() {
        return answer_received;
    }


    public void run() {
        Message msg = null;

        //Socket creation
        DatagramSocket server = null;
        DatagramPacket packet = null;

        try {
            server = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println("Error when socket creation : " + e);
        }

        while (true) {
            //Reading of client message
            try {
                byte[] buffer = new byte[8192];
                packet = new DatagramPacket(buffer, buffer.length);
                server.receive(packet);
            } catch (IOException e) {
                System.err.println("Error during message reception : " + e);
            }

            //Object retrieving
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                msg = (Message) ois.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println("Object received unknown : " + e);
            } catch (IOException e) {
                System.err.println("Error during object retrieving : " + e);
            }

            String msgType = msg.getType();

            if (!packet.getAddress().equals(this.network.getIp())) {
                switch (msgType) {
                    case "requestValidatePseudonym":

                        if (msg.getMessage().equals(this.network.getChat().getAgent().getPseudo().getPseudonym())) {
                            //Creation of the response Message
                            Message answer = new Message("Pseudo already used", "answerValidatePseudonym");

                            //Transformation of object Message into array of bytes
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(baos);
                                oos.writeObject(answer);
                            } catch (IOException e) {
                                System.err.println("Error during serialisation : " + e);
                            }

                            //Creation and sending of UDP datagram
                            try {
                                byte[] buffer2 = baos.toByteArray();
                                DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, packet.getAddress(), 1234);
                                server.send(packet2);
                            } catch (IOException e) {
                                System.err.println("Error during server answer : " + e);
                            }
                        }
                        break;

                    case "answerValidatePseudonym":
                        this.answer_received = true;
                        break;


                    //TODO : traitement réception notifyUsers
                    case "notificationPseudonym":
                        //user connu : on supprime l'ancien user associé puis on le rajoute avec le nouveau pseudo
                        if (this.network.getChat().getAgent().getUsers().isknown(msg.getUser().getIp())) {
                            this.network.getChat().getAgent().getUsers().delUserfromIP(msg.getUser().getIp());
                        } else {
                            this.network.getChat().getAgent().getPseudo().notifyUsers();
                        }
                        this.network.getChat().getAgent().getUsers().addUser(msg.getUser());
                        break;
                }
            }
        }
    }
}
