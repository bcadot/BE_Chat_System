import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used to get a connection a database and perform operations on it.
 */
public class Data_Manager {
    private Chat chat;
    private Connection con = null;          //TODO penser à fermer la connexion quand on ferme l'agent

    public Data_Manager(Chat c) {
        this.chat = c;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String[] dbinfos = getDBinfo();
            String url = dbinfos[0];
            String user = dbinfos[1];
            String password = dbinfos[2];
            this.con = DriverManager.getConnection(url, user, password);
            System.out.println("! Connection to database is successful !");
        } catch (ClassNotFoundException e) {
            System.err.println("Class error" + e);
        } catch (SQLException e) {
            System.err.println("SQL error" + e);
        }
    }

    /**
     * Fetches all messages between you and the recipient and returns it in a ArrayList of Message.
     *
     * @param dest The recipient
     * @return ArrayList(Message)
     */
    public ArrayList<Message> fetch(String dest) {
        ArrayList<Message> history = new ArrayList<>();
        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(
                    "SELECT msg FROM history WHERE src='" + this.chat.getAgent().getId().getId()
                            + "' AND dest ='" + dest + "' " +
                            "OR src='" + dest + "' AND dest ='" + this.chat.getAgent().getId().getId() + "'");
            while (res.next()) {
                Blob blob = res.getBlob("msg");
                ObjectInputStream ois = new ObjectInputStream(blob.getBinaryStream());
                Message msg = (Message) ois.readObject();
                history.add(history.size(), msg);
            }
            statement.close();
            res.close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println("Fetch error" + e);
        }
        return history;
    }
    
    /**
     * Insert a message into the database.
     *
     * @param dest The recipient
     * @param message The message
     */
    public void insert(String dest, Message message) {
        try {
            PreparedStatement ps=con.prepareStatement("INSERT INTO history (id, src, dest, msg)" +
                    "VALUES (default,?,?,?)");
            ps.setString(1, this.chat.getAgent().getId().getId());
            ps.setString(2, dest);
            Blob blob = con.createBlob();
            ObjectOutputStream oos = new ObjectOutputStream(blob.setBinaryStream(1));
            oos.writeObject(message);
            oos.close();
            ps.setBlob(3, blob);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException | IOException e) {
            System.err.println("insert error" + e);
        }
    }

    public String[] getDBinfo() {
        int i = 0;
        String[] dbinfos = new String[3];
        try {
            FileInputStream fis=new FileInputStream("database_setup.txt");
            Scanner sc=new Scanner(fis);
            while (sc.hasNextLine() && i<=3) {
                dbinfos[i] = sc.nextLine();
                i++;
            }
            sc.close();
        }
        catch(IOException e) {
            System.out.println("Le fichier database_setup.txt n'existe pas ou n'est pas au bon endroit.\n" +
                    "Se référer au manuel d'utilisation.");
        }
        return dbinfos;
    }

}
