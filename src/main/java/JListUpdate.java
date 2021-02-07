import javax.swing.*;

public class JListUpdate implements Runnable {
    private App app;
    private DefaultListModel l;

    public JListUpdate(App app){
        this.app = app;
        l = getUsersOnline();
    }

    public DefaultListModel getUsersOnline (){
        DefaultListModel result = new DefaultListModel();
        for(User u : app.getAgent().getUsers().getActiveUsers()){
            result.addElement(u.getName());
        }
        return result;
    }

    public App getApp(){
        return app;
    }

    public void run(){
        while(true){
            l = getUsersOnline();
            getApp().getUsersList().setModel(l);
            try{Thread.sleep(10000);}
            catch(InterruptedException e){
                System.out.println("Thread interrupted");
            }
        }
    }
}
