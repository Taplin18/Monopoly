import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
 
 
public class CreatePopUp extends JPanel implements ActionListener {    
	
    private String message;
    private String name;
    private String title;
    private String image;
    private String positionType;
    private String ownership;
    private int rent;
    private int price;
    private int taxOwed;
    private int messageType = JOptionPane.PLAIN_MESSAGE;
    private ImageIcon popUpIcon = null;
    private BufferedImage popUpImg = null;
    private JFrame myFrame;
    private JPanel imagePanel;
    private JPanel textPanel;
    private JPanel buttonPanel;
    private JLabel imageLabel;
    private JTextArea textArea;
    private static String OK = "OK";
    private static String PAY_RENT = "Pay Rent";
    private static String BUY = "Buy";
    private static String AUCTION = "Auction";
    private HashMap<String, String> squareInfo;
	
   /**
    * The ID of the square the player has just landed on is passed in each time a player moves to a new square and the output is a pop-up which tells the user what square they have landed on accompanied by an appropriate icon.
    * For now, the details of each square are broadly divided into Go, Chest, Chance, Property, Corner, Transport, Utilities,
    * and Taxes - these can be made more specific depending on what specific values we plan on having on each square and I will update
    * the output accordingly. 
    */
    public CreatePopUp(HashMap<String, String> squareInfo) {
	// Create JFrame and JPanels
	myFrame = new JFrame();
	imagePanel = new JPanel(new FlowLayout());
	textPanel = new JPanel(new FlowLayout());
	buttonPanel = new JPanel(new FlowLayout());
		
	displayPopUp(squareInfo);
		
	myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specifies that the application must exit when window is closed
        this.setOpaque(true); // Makes contentPane opaque 
        myFrame.setContentPane(this); // Sets contentPane property
	myFrame.getContentPane().setBackground(Color.black);
        myFrame.setSize(450, 400);
	myFrame.setTitle("Current Square Update");
	myFrame.pack();
	myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true); // Window is displayed
    }	
		
		
    public void displayPopUp(HashMap<String, String> squareInfo) {
        positionType = squareInfo.get("positionType");
        image = squareInfo.get("picture");
		
	switch(positionType) {
		case "chest":
		case "chance":
			message = squareInfo.get("message");
			// Create OK button, add action command and listener, and add to button panel
			JButton okButton = new JButton("OK");
			okButton.setActionCommand(OK);
			okButton.addActionListener(this);
			buttonPanel.add(okButton);
			
		case "property":
		case "transport":
		case "utilities":
			name = squareInfo.get("name");
			message = "You have landed on " + name + "!\n";
			ownership = squareInfo.get("ownership");
			if (ownership == "owned") {
				rent = squareInfo.get("rent");
				message += "This is already owned by another player. You must pay rent of " + rent + "dollars.";
					
				// Create Pay Rent button, add action command and listener, and add to button panel
				JButton payRentButton = new JButton("Pay Rent"); 
				payRentButton.setActionCommand(PAY_RENT);
				payRentButton.addActionListener(this);
				buttonPanel.add(payRentButton);
					
			} else { // Property is available
				price = squareInfo.get("price");
				message += "This is available and costs " + price + " dollars.\n Would you like to buy it or go to auction?";
					
				// Create Buy and Auction buttons, add action command and listener, and add to button panel
				JButton buyButton = new JButton("Buy"); // Pay rent button
				buyButton.setActionCommand(BUY);
				buyButton.addActionListener(this);
				JButton auctionButton = new JButton("Auction"); // Pay rent button
				auctionButton.setActionCommand(AUCTION);
				auctionButton.addActionListener(this);
					
				// Add buttons to button panel
				buttonPanel.add(buyButton);
				buttonPanel.add(auctionButton);
				}
				
		case "taxes":
			taxOwed = squareInfo.get("amount");
			message = "You have landed on a Tax square. You must pay a tax of " + taxOwed + " dollars.";
			// Create OK button, add action command and listener, and add to button panel
			JButton okButton = new JButton("OK");
			okButton.setActionCommand(OK);
			okButton.addActionListener(this);
			buttonPanel.add(okButton);
        }
			
        // Read in image file
	try {
	    popUpImg = ImageIO.read(new File(image));
	    popUpIcon = new ImageIcon(popUpImg);
	    Image myImage = popUpIcon.getImage(); 
	    Image myImageCopy = myImage.getScaledInstance(350, 150,  java.awt.Image.SCALE_SMOOTH); // Scale / re-size the image 
	    popUpIcon = new ImageIcon(myImageCopy); 
	} catch (IOException e) { // Catch any errors which may occur when reading in the file
	    e.printStackTrace();
	}
		
	imageLabel = new JLabel(popUpIcon, JLabel.CENTER);
		
	imagePanel = new JPanel(new FlowLayout());
	imagePanel.setBackground(Color.BLACK);
	imagePanel.setOpaque(true);	
	imagePanel.add(imageLabel);
		
	textArea = new JTextArea(message, 5, 20);
        textArea.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
        textArea.setBackground(Color.BLACK);
	textArea.setForeground(Color.WHITE);
	textArea.setLineWrap(true);			// Set various attributes as required
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
		
	textPanel.add(textArea);
	textPanel.setBackground(Color.BLACK);
		
	buttonPanel.setBackground(Color.BLACK);
		
	add(imagePanel);
	add(textPanel);
	add(buttonPanel);
    }
	
   /**
    * Method which checks what command was made by the user and then responds appropriately.
    * @param e 
    */
    public void actionPerformed(ActionEvent e) {
    
	String command = e.getActionCommand(); // Store value of command
 
        if (PAY_RENT.equals(command)) { // If Pay Rent button was pressed
            JOptionPane.showMessageDialog(myFrame, "You have paid your rent.");
			myFrame.dispose();
        } else if (BUY.equals(command)) { // If Buy button was pressed
			JOptionPane.showMessageDialog(myFrame, "You have purchased this property.");
			myFrame.dispose();
        } else if (AUCTION.equals(command)) { // If Auction button was pressed
			JOptionPane.showMessageDialog(myFrame, "You have put this property to auction.");
			myFrame.dispose();
	} else if (OK.equals(command)) { // If OK button was pressed
			myFrame.dispose();
	}
    }
}
