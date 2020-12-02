/**
 * Represents a conversation between 2 or more users in which you can send or receive messages.
 */
public class Chat {
    private User user;
    private String history;
    //TODO History

    public void startSession(User user) {
        //first : establish a TCP connection between 2 users
        //later : multicast between several users

        //fetch history
    }

    public void endSession() {
        //fin ack
    }
}
