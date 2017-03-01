import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
 
 
public class CreatePopUp extends JPanel implements ActionListener {    
	
    private String message;					// message - Text to be displayed about the current square
    private String name;					// name - Name of the the property/utility/tranport square
    private String image;					// image - Name of the image associate with the square
    private String positionType;				// positionType - Type of square
    private String ownership;					// ownership - Whether the property/utility/transport is owned/available
    private int rent;						// rent - Cost of rent at an already owned square
    private int price;						// price - Cost of buying at an availabe square
    private int taxOwed;					// taxOwed - The tax owed at a Tax square
    private int messageType = JOptionPane.PLAIN_MESSAGE;	// messageType - Determines style of pop-up
    private ImageIcon popUpIcon = null;				// popUpIcon - Used to store image as ImageIcon object
    private BufferedImage popUpImg = null;			// popUpImg - Stores image when it is being read in
    private JFrame myFrame;					// myFrame - JFrame object which holds all other components
    private JPanel imagePanel;					// imagePanel - Holds image components
    private JPanel textPanel;					// textPanel - Holds text components
    private JPanel buttonPanel;					// buttonPanel - Holds button components
    private JLabel imageLabel;
    private JTextArea textArea;					// textArea - Holds message to be displayed in pop-up
    private static String OK = "OK";				// OK - String to be displayed on button
    private static String PAY_RENT = "Pay Rent";		// PAY_RENT - String to be displayed on button
    private static String BUY = "Buy";				// BUY - String to be displayed on button
    private static String AUCTION = "Auction";			// AUCTION - String to be displayed on button
    private HashMap<String, String> squareInfo;			// squareInfo - HashMap used to store square information
	
   /**
    * The details of the type of square the player is currently on is passed in as a HashMap object, which is then passed 
    * to the displayPopUp() method where the required information is extracted and placed appropriately placed on to the JFrame.
    * @param squareInfo - HashMap object containing information on current square
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
		
   /**
    * Method which takes in as a parameter the HashMap containing info on the current square and then creates appropriate
    * JFrame components based on the values passed in.
    * @param squareInfo - HashMap object containing information on the current square 
    */
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
