import javax.swing.*;


public class displayMessageReceived implements Runnable {
    private App app;
    private Message msg;


    public displayMessageReceived(App app, Message msg) {
        this.app = app;
        this.msg = msg;
    }

    public void run() {
        if (app.destinationUser.getName().equals(msg.getUser().getName()))
            this.app.chatBox.append("<" + msg.getUser().getName() +
                 ">:  " + msg.toString() + "\n");
    }
}
