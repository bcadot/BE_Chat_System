import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 *
 */


public class UDP_Serv implements Runnable{
    private boolean answer_received;
    public static int port = 1234;
    private Pseudonym pseudo;

    UDP_Serv(Pseudonym pseudo) {
        this.answer_received = true;
        this.pseudo = pseudo;
    }

    public boolean isAnswer_received() {
        return answer_received;
    }


    public void run(){
        Message msg = null;

        //Socket creation
        DatagramSocket server = null;
        try{
            server = new DatagramSocket(port);
        } catch (SocketException e){
            System.err.println("Error when socket creation : " + e);
        }

        //Reading of client message
        DatagramPacket packet = null;
        try{
            byte[] buffer = new byte[8192];
            packet = new DatagramPacket(buffer, buffer.length);
            server.receive(packet);
        }catch(IOException e){
            System.err.println("Error during message reception : " + e);
        }

        //Object retrieving
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            msg = (Message) ois.readObject();
        }catch(ClassNotFoundException e){
            System.err.println("Object received unknown : " + e);
        }catch(IOException e){
            System.err.println("Error during object retrieving : " + e);
        }

        String msgType = msg.getType();

        switch(msgType){
            //TODO : changer l'envoi actuel par un envoi de type Message avec le flag answerValidatePseudonym
            case "requestValidatePseudonym":
                if(msg.getMessage() == this.pseudo.getPseudonym()){
                    byte[] buffer2 = new String("Pseudo already taken").getBytes();
                    DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, packet.getAddress(), packet.getPort());
                    try{
                        server.send(packet2);
                    }catch(IOException e){
                        System.err.println("Error during transmission : " + e);
                    }
                }
                else
                break;
            //TODO : sur réception de ce flag, mettre le booléen associé à la validation à faux
            case "answerValidatePseudonym":
        }
    }
}
