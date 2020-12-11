import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


/**
 *
 */


public class UDP_Serv implements Runnable {
    private boolean answer_received = false;
    public static int port = 1234;
    private Pseudonym pseudo;

    UDP_Serv(Pseudonym pseudo) {
        this.answer_received = true;
        this.pseudo = pseudo;
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

            switch (msgType) {
                //TODO : changer l'envoi actuel par un envoi de type Message avec le flag answerValidatePseudonym
                case "requestValidatePseudonym":
                    if (msg.getMessage() == this.pseudo.getPseudonym()) {
                        Message answer = new Message("Pseudo already used", "answerValidatePseudonym");

                        //Transformation of object Message into array of bytes
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        try{
                            ObjectOutputStream oos = new ObjectOutputStream(baos);
                            oos.writeObject(answer);
                        } catch(IOException e){
                            System.err.println("Error during serialisation : " + e);
                        }

                        //Creation and sending of UDP datagram
                        try{
                            byte[] buffer2 = baos.toByteArray();
                            DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, packet.getAddress(), packet.getPort());
                            server.send(packet);
                        } catch(IOException e){
                            System.err.println("Error during server answer : " + e);
                        }
                    } else
                        break;

                case "answerValidatePseudonym":
                    this.answer_received = true;
                    break;

                //TODO : traitement réception notifyUsers
                case "notificationPseudonym":

            }
        }
    }
}
