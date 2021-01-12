import javax.swing.*;
import java.io.*;
import java.lang.module.FindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.Serializable;


public class JListUpdate implements Runnable, Serializable {
    private App app;
    private DefaultListModel l;

    public JListUpdate(App app){
        this.app = app;
        l = getUsersOnline();
    }

    public DefaultListModel getUsersOnline (){
        DefaultListModel result = new DefaultListModel();
        for(User u : app.getUser().getUsers().getActiveUsers()){
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
