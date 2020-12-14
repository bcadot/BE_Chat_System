import java.lang.module.FindException;
import java.util.ArrayList;

public class User_Manager {
    private ArrayList<User> activeUsers = new ArrayList<User>();

    public User_Manager() { }

    public void addUser(User u) {
        activeUsers.add(u);
    }
    //public void addUser(String ip, int port, String name) { activeUsers.add(new User(ip, port, name)); }
    public void delUser(User u) {
        try { activeUsers.remove(u); }
        catch (NullPointerException e) { System.out.println("User not found"); }
    }
    public void delUserfromIP(String ip) throws FindException {
        for (User u : activeUsers) {
            if (u.getIp().compareTo(ip) == 0) delUser(u);
        }
        throw new FindException();
    }

    public boolean isknown(String ip) {
        for (User u : activeUsers) {
            if (u.getIp().compareTo(ip) == 0) return true;
        }
        return false;
    }
    public String getIPfromUsername(String name) throws FindException {
            for (User u : activeUsers) {
                if (u.getName().compareTo(name) == 0) return u.getIp();
            }
            throw new FindException();
    }
}
