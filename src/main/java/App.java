import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.ArrayList;

/**
 * Provides the GUI
 */
public class App {
    private Agent agent = new Agent(this);
    private JButton button1;
    private JPanel MyPanel;
    private JTextField pseudoInput;
    private JLabel result;
    private JTextArea pseudo;
    private JList usersList;
    private JTextArea PSEUDOTextArea;
    private LocalDateTime timestamp;

    User destinationUser;

    JFrame      newFrame    = new JFrame("ChatBox");
    JButton     sendMessage;
    JButton     sendFile;
    JTextField  messageBox;
    JTextArea   chatBox;

    public App() {
        newFrame.setVisible(false);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (agent.getPseudo().setPseudonym(pseudoInput.getText())) {
                        pseudo.setText(agent.getPseudo().getPseudonym());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Pseudo already used, choose another one.",
                                "Inane warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NullPointerException npe) {
                    JOptionPane.showMessageDialog(null,
                            "You cannot choose an empty pseudo.",
                            "Inane warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JListUpdate jl = new JListUpdate(this);
        new Thread(jl).start();

        usersList.addListSelectionListener(new usersListSelectionListener());
        display();
    }

    public JList getUsersList() {
        return usersList;
    }

    public Agent getAgent() {
        return agent;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().MyPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // get the screen size as a java dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // get 1/3 of the height, and 1/3 of the width
        int height = screenSize.height * 1 / 3;
        int width = screenSize.width * 1 / 3;

        // set the jframe height and width
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void display(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        sendFile = new JButton("Send File");
        sendFile.addActionListener(new sendFileButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);
        southPanel.add(sendFile, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        newFrame.setSize(470, 300);
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                System.out.println("An entry is needed");
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM, HH:mm");
                timestamp = LocalDateTime.now();
                chatBox.append("<" + agent.getPseudo().getPseudonym() + ">:  " + messageBox.getText()
                        + " [" + timestamp.format(dtf) + "]" + "\n");
                try {
                    agent.getChat().getNetwork().send(new Message(new User (agent.getId().getId(),1234,agent.getPseudo().getPseudonym()),
                            messageBox.getText(), "Chat"), destinationUser);
                }
                catch(IOException e){
                    System.out.println("Error during text message sending");
                    //TODO peut-être ajouter un message dans la chatbox comme quoi le message s'est pas envoyé
                }
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }

    //TODO implémenter envoi fichiers
    class sendFileButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION){
                File selectedFile = jfc.getSelectedFile();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM, HH:mm");
                timestamp = LocalDateTime.now();
                chatBox.append("<" + agent.getPseudo().getPseudonym() + ">:  " + selectedFile.getName()
                        + " [" + timestamp.format(dtf) + "]" + "\n");

                try {

                    agent.getChat().getNetwork().sendFile(selectedFile, destinationUser);
                }
                catch(IOException e){
                    System.out.println("Error during file message sending");
                    //TODO same as sendMessageButtonListener
                }

            }
        }
    }

    class usersListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if(!event.getValueIsAdjusting()) {
                if (usersList.getSelectedValue() != null) {
                    destinationUser = agent.getUsers().getUserFromUsername((String) usersList.getSelectedValue());
                    newFrame.setVisible(true);
                    chatBox.setText("");
                    chatBox.revalidate();
                    chatBox.repaint();

                    //Historique
                    ArrayList<Message> history = agent.getChat().getData().fetch(destinationUser.getIp());
                    if (history != null) {
                        history.forEach(msg -> chatBox.append(msg.toString() + "\n"));
                    }
                    usersList.setSelectedValue(null,false);
                }
            }
        }
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MyPanel = new JPanel();
        MyPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        result = new JLabel();
        result.setText("");
        MyPanel.add(result, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pseudo = new JTextArea();
        MyPanel.add(pseudo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        usersList = new JList();
        usersList.setLayoutOrientation(0);
        MyPanel.add(usersList, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        button1 = new JButton();
        button1.setText("Change Pseudo");
        MyPanel.add(button1, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(117, 50), null, 0, false));
        pseudoInput = new JTextField();
        pseudoInput.setDragEnabled(false);
        pseudoInput.setEditable(true);
        pseudoInput.setHorizontalAlignment(10);
        MyPanel.add(pseudoInput, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        PSEUDOTextArea = new JTextArea();
        PSEUDOTextArea.setLineWrap(false);
        PSEUDOTextArea.setText(" PSEUDO");
        MyPanel.add(PSEUDOTextArea, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 1, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MyPanel;
    }

}
