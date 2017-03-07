package monopoly3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AuctionChat extends JPanel implements ActionListener {

	private static String SEND = "Send";
	private JButton sendButton;
	private JFrame myFrame;
	JPanel mainPanel;
	JPanel messagePanel;
	JPanel displayPanel;
	private String username;
	private String message;
	JTextArea chatText;
	JTextField messageField;
	JScrollPane scrollPane;
	
	public AuctionChat() {
	    myFrame = new JFrame("Auction");
	    myFrame.setVisible(true);
	}

    	public void createAndDisplay() {
        
	    // Create main panel which will hold other two panels 
	    mainPanel = new JPanel();
		
	    // Create message panel which will hold editable text box and send button
            messagePanel = new JPanel();
		
	    // Create text field for chat content and send button to send this content
	    messageField = new JTextField(22);
	    sendButton = new JButton("Send Message");
	    sendButton.setActionCommand(SEND);
	    sendButton.addActionListener(this);
		
	    // Add message field and send button to the message panel
	    messagePanel.add(messageField);
            messagePanel.add(sendButton);
		
	    // Create panel to hold chat text
	    displayPanel = new JPanel();
            chatText = new JTextArea();
	    chatText.setPreferredSize(new Dimension(360,200));
            chatText.setEditable(false); // Forbids user from editing content posted in to the chat
	    chatText.setFont(new Font("Sans-Serif", Font.PLAIN, 14)); // Set font
	    scrollPane = new JScrollPane(chatText);
            //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
	    // Add the chat text box to the display panel
	    displayPanel.add(scrollPane);
            chatText.setLineWrap(true);

	    // Add the display and message sending panel to the main panel
	    mainPanel.add(displayPanel);
	    mainPanel.add(BorderLayout.SOUTH, messagePanel);	
       
	    myFrame.add(mainPanel);
            myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myFrame.setSize(400, 310);
    }

    
    public void actionPerformed(ActionEvent e) {
        if (messageField.getText().isEmpty() == true) { // Message field is empty
            // No action required
	    JOptionPane.showMessageDialog(myFrame,
                    "You must enter at least one character in to the message field.");
        } else { // There is content in the message field
            message = messageField.getText();
        }
    }
	
    public String getMessage() {
        return this.message;
    }
    public void display(String message) {
    	chatText.append(message + "\n");
    }
 	
    public static void main(String[] args) {
        AuctionChat chat = new AuctionChat();
	chat.createAndDisplay();
    }
}
