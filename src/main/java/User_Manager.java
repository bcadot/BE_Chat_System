import java.io.Serializable;
import java.lang.module.FindException;
import java.util.ArrayList;

public class User_Manager {
    private ArrayList<User> activeUsers = new ArrayList<User>();
    private Agent agent;

    public User_Manager(Agent a) {
        this.agent = a;
    }

    public void addUser(User u) {
        activeUsers.add(u);
    }
    //public void addUser(String ip, int port, String name) { activeUsers.add(new Agent(ip, port, name)); }
    public void delUser(User u) {
        try { activeUsers.remove(u); }
        catch (NullPointerException e) { System.out.println("Agent not found"); }
    }

    public void delUserfromIP(String ip) {
        activeUsers.removeIf(u -> u.getIp().equals(ip));
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
    public int getPortfromUsername(String name) throws FindException {
        for (User u : activeUsers) {
            if (u.getName().equals(name)) return u.getRcvPort();
        }
        throw new FindException();
    }

    public void printUsers() {
        System.out.println("List :");
        for (User u : activeUsers) {
            System.out.println("Agent : " + u.getName() + " " + u.getIp());
        }
    }

    public ArrayList<User> getActiveUsers(){
        return activeUsers;
    }
}
