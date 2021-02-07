import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;

public class Data_Manager {
    private Chat chat;
    private String url = "jdbc:mysql://localhost:3306/chat_system?useSSL=false";
    private String user = "poo";
    private String password = "projetpoo";
    private Connection con = null;          //TODO penser à fermer la connection quand on ferme l'agent

    public Data_Manager(Chat c) {
        this.chat = c;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(url, user, password);
            System.out.println("! Connection to database is successful !");
        } catch (ClassNotFoundException e) {
            System.err.println("Class error" + e);
        } catch (SQLException e) {
            System.err.println("SQL error" + e);
        }
    }

    /**
     * Fetch and display all messages between you and the recipent.
     *
     * @param dest The recipient
     */
    public void fetch(String dest) {
        try {
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery(
                    "SELECT msg FROM history WHERE src='" + this.chat.getAgent().getId().getId()
                        + "' AND dest ='" + dest + "'");
            while (res.next()) {
                Blob blob = res.getBlob("msg");
                ObjectInputStream ois = new ObjectInputStream(blob.getBinaryStream());
                Message msg = (Message) ois.readObject();
                System.out.println(msg.toString());
            }
            statement.close();
            res.close();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.err.println("Fetch error" + e);
        }
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
            System.out.println("Length : " + blob.length());
            ps.setBlob(3, blob);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException | IOException e) {
            System.err.println("insert error" + e);
        }
    }
}
