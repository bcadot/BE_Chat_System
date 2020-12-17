import java.io.Serializable;

public class Data_Manager implements Serializable {
    private Chat chat;

    public Data_Manager(Chat c) {
        this.chat = c;
    }
}
