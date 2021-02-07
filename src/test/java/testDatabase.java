public class testDatabase {

    public static void main(String[] args) {
        Agent test = new Agent();
        System.out.println("--> IP           : " + test.getId().getId());
        String dest = "192.168.69.69";
        //General
        for (int i = 0; i < 10; i++) {
            String s = "message " + i;
            Message msg = new Message(s);
            if (i == 0)
                System.out.println(msg.toString());
            test.getChat().getData().insert(dest, msg);
        }
        //Null string
        String nullstring = null;
        Message nullmsg = new Message(nullstring);
        test.getChat().getData().insert(dest, nullmsg);
        //Empty string
        String emptystring = "";
        Message emptymsg = new Message(emptystring);
        test.getChat().getData().insert(dest, nullmsg);
        //Fetch
        test.getChat().getData().fetch(dest);
    }
}
