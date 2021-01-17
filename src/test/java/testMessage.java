import java.io.IOException;

public class testMessage {

    public static void main(String[] args) {
        Agent ben = new Agent();
        /*
        Choix d'un pseudonyme aléatoire (unique sauf si malchance)
        a pour but d'activer NotifyUsers() et ainsi de remplir
        la liste activeUsers pour l'agent
        */
        System.out.println("Avant changement :");
        System.out.println("-->" + ben.getPseudo().getPseudonym() + " ; " + ben.getId().getId());
        System.out.println("Requête changement de pseudo");
        if(ben.getPseudo().setPseudonym(String.valueOf(Math.random())))
            System.out.println("Pseudo modifié : " + ben.getPseudo().getPseudonym());
        else
            System.out.println("Pseudo déja utilisé");
        System.out.println("-->" + ben.getPseudo().getPseudonym() + " ; " + ben.getId().getId());

        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        ben.getUsers().printUsers();

        try {
            long randomSleepTime = (long) (Math.random() * 1000);
            Thread.sleep(randomSleepTime);
        } catch (Exception e) {}

        User dest = ben.getUsers().getActiveUsers().get(0);
        Message msg = new Message("This is a test message from " + ben.getPseudo().getPseudonym());
        try {
            System.out.println("--- Envoi d'un message ---");
            ben.getChat().getNetwork().send(msg, dest);
        } catch (IOException e) {
            System.err.println("Error" + e);
        }
        msg = new Message("This is another test message from " + ben.getPseudo().getPseudonym());
        try {
            System.out.println("--- Envoi d'un message ---");
            ben.getChat().getNetwork().send(msg, dest);
        } catch (IOException e) {
            System.err.println("Error" + e);
        }
    }
}
