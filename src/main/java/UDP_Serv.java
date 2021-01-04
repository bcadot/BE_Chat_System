import java.io.*;
import java.lang.module.FindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


/**
 *
 */


public class UDP_Serv implements Runnable, Serializable {
    private boolean answer_received = false;
    public static int port = 1234;
    private Pseudonym name;

    private Network_Manager network;

    UDP_Serv(/*Pseudonym pseudo, */Network_Manager n) {
        this.network = n;
        this.name = this.network.getChat().getUser().getPseudo();
    }

    public boolean isAnswer_received() {
        return answer_received;
    }
    public Network_Manager getNetwork() { return network; }


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
            System.out.println();

            if(!(packet.getAddress().equals(this.network.getIp()))) {
                switch (msgType) {
                    //TODO : changer l'envoi actuel par un envoi de type Message avec le flag answerValidatePseudonym
                    case "requestValidatePseudonym":
                        System.out.println("-- Serveur UDP --");
                        System.out.println("Réception message de type requestValidatePseudonym en provenance de " + packet.getAddress().getHostAddress());
                        System.out.println("Message reçu : " + msg.getMessage() + " ==?? " + this.name.getPseudonym());
                        if (msg.getMessage().equals(this.name.getPseudonym())) {
                            System.out.println("pseudo reçu en UDP correspond à mon pseudo");
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
                                DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, packet.getAddress(), packet.getPort());
                                server.send(packet2);
                                System.out.println("réponse de type answerValidatePseudonym envoyée à " + packet.getAddress().getHostAddress());
                                System.out.println("-- traitement terminé --");
                            } catch (IOException e) {
                                System.err.println("Error during server answer : " + e);
                            }
                        } else {
                            System.out.println("pseudo reçu en UDP ne correspond pas à mon pseudo");
                            System.out.println("-- traitement terminé --");
                        }
                        break;

                    case "answerValidatePseudonym":
                        System.out.println("-- Serveur UDP --");
                        System.out.println("Réception message de type answerValidatePseudonym en provenance de "+ packet.getAddress().getHostAddress());
                        System.out.println("booléen réponse reçue passe à vrai");
                        this.answer_received = true;
                        System.out.println("-- traitement terminé --");
                        break;


                        //TODO : traitement réception notifyUsers
                    case "notificationPseudonym":
                        System.out.println("-- Serveur UDP --");
                        System.out.println("Réception message de type notificationPseudonym en provenance de "+ packet.getAddress().getHostAddress());
                        //user connu : on supprime l'ancien user associé puis on le rajoute avec le nouveau pseudo
                        try {
                            System.out.println("Mise à jour de la liste des utilisateurs");

                            this.network.getChat().getUser().getUsers().delUserfromIP(msg.getUser().getIp());
                            System.out.println("Supression utilisateur");
                            this.network.getChat().getUser().getUsers().addUser(msg.getUser());
                            System.out.println("Ajout utilisateur");
                            System.out.println("-- traitement terminé --");
                        } catch (FindException e) {
                            this.network.getChat().getUser().getUsers().addUser(msg.getUser());
                        }
                        break;
                }
            }
        }
    }
}
