public class testPseudo {

    public static void main(String[] args) {
        User ben = new User();
        System.out.println("Pseudo avant changement : " + ben.getName() +"/"+ ben.getPseudo().getPseudonym());
        System.out.println("Requête changement de pseudo");
        if(ben.getPseudo().setPseudonym("Benjamin KDO"))
            System.out.println("Pseudo modifié : " + ben.getPseudo().getPseudonym());
        else
            System.out.println("Pseudo déja utilisé");
    }
}
