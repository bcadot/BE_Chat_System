public class testPseudo {

    public static void main(String[] args) {
        User ben = new User();
        System.out.println("Pseudo avant changement : " + ben.getName());
        System.out.println("Requête changement de pseudo");
        if(ben.getPseudo().setPseudonym("Benjamin KDO") == true)
            System.out.println("Pseudo modifié : " + ben.getName());
        else
            System.out.println("Pseudo déja utilisé");
    }
}
