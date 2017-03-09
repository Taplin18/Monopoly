package monopoly3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
 
 
public class CreatePopUp extends JPanel implements ActionListener {    
	
    private String message;					// - Text to be displayed about the current square
    private String name;					// - Name of the the property/utility/tranport square
    private String image;					// - Name of the image associate with the square
    private String positionType;				// - Type of square
    private String ownership;					// - Whether the property/utility/transport is owned/available
    private int rent;						// - Cost of rent at an already owned square
    private int price;						// - Cost of buying at an availabe square
    private int taxOwed;					// - The tax owed at a Tax square
    private int messageType = JOptionPane.PLAIN_MESSAGE;	// - Determines style of pop-up
    private ImageIcon popUpIcon = null;				// - Used to store image as ImageIcon object
    private BufferedImage popUpImg = null;			// - Stores image when it is being read in
    private JFrame myFrame;					// - JFrame object which holds all other components
    //private JPanel imagePanel;					// - Holds image components
    //private JPanel textPanel;					// - Holds text components
    //private JPanel buttonPanel;					// - Holds button components
    //private JLabel imageLabel;					// - ImageIcon is inserted here
    //private JTextArea textArea;					// - Holds message to be displayed in pop-up
    //private JButton okButton;					// - JButton for OK button
    //private JButton payRentButton;				// - JButton for Pay Rent button
    //private JButton auctionButton;				// - JButton for Auction button
    //private JButton buyButton;					// - JButton for Buy button
    private static String OK = "OK";				// - String to be displayed on button
    private static String PAY_RENT = "Pay Rent";		// - String to be displayed on button
    private static String BUY = "Buy";				// - String to be displayed on button
    private static String AUCTION = "Auction";			// - String to be displayed on button
	
   /**
    * The details of the type of square the player is currently on is passed in as a HashMap object, which is then passed 
    * to the displayPopUp() method where the required information is extracted and placed appropriately on to the JFrame.
    * @param squareInfo - HashMap object containing information on current square
    */
    public CreatePopUp(HashMap<String, String> squareInfo) {
	// Create JFrame and JPanels
	HashMap<String, String> test = squareInfo;
	myFrame = new JFrame();
	JPanel imagePanel = new JPanel(new FlowLayout());
	JPanel textPanel = new JPanel(new FlowLayout());
	JPanel buttonPanel = new JPanel(new FlowLayout());
	buttonPanel.setLayout(new FlowLayout());
	buttonPanel.setBackground(Color.BLACK);
	buttonPanel.setOpaque(true);
		
	positionType = squareInfo.get("positionType");
        image = "monopoly3/" + squareInfo.get("picture") + ".jpg";
		
	if (positionType == "chest" || positionType == "chance") {
		message = squareInfo.get("message");
		// Create OK button, add action command and listener, and add to button panel
		JButton okButton = new JButton("OK");
		okButton.setActionCommand(OK);
		okButton.addActionListener(this);
		
		okButton.setBackground(new Color(59, 89, 182));
        	okButton.setForeground(Color.WHITE);
        	okButton.setFocusPainted(false);
		okButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		//okbutton.setEnabled(true);
		
		buttonPanel.add(okButton);
		
	} else if (positionType == "property" || positionType == "transport" || positionType == "utilities") {		
		name = squareInfo.get("name");
		message = "You have landed on " + name + "!\n";
		ownership = squareInfo.get("ownership");
		if (ownership == "owned") {
			rent = Integer.valueOf(String.valueOf(squareInfo.get("rent")));
			message += "This is already owned by another player. You must pay rent of " + rent + "dollars.";
					
			// Create Pay Rent button, add action command and listener, and add to button panel
			JButton payRentButton = new JButton("Pay Rent"); 
			payRentButton.setActionCommand(PAY_RENT);
			payRentButton.addActionListener(this);
			
			payRentButton.setBackground(new Color(59, 89, 182));
        		payRentButton.setForeground(Color.WHITE);
        		payRentButton.setFocusPainted(false);
			payRentButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
			
			//payRentbutton.setEnabled(true);
			
			buttonPanel.add(payRentButton);
					
		} else { // Property is available
			price = Integer.valueOf(String.valueOf(squareInfo.get("price")));
			message += "This is available and costs " + price + " dollars.\n Would you like to buy it or go to auction?";
					
			// Create Buy and Auction buttons, add action command and listener, and add to button panel
			JButton buyButton = new JButton("Buy"); // Pay rent button
			buyButton.setActionCommand(BUY);
			buyButton.addActionListener(this);
			
			buyButton.setBackground(new Color(59, 89, 182));
        		buyButton.setForeground(Color.WHITE);
        		buyButton.setFocusPainted(false);
			buyButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
			//buybutton.setEnabled(true);
			
			JButton auctionButton = new JButton("Auction"); // Pay rent button
			auctionButton.setActionCommand(AUCTION);
			auctionButton.addActionListener(this);
			
			auctionButton.setBackground(new Color(59, 89, 182));
        		auctionButton.setForeground(Color.WHITE);
        		auctionButton.setFocusPainted(false);
			auctionButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
			//auctionbutton.setEnabled(true);
			
			// Add buttons to button panel
			buttonPanel.add(buyButton);
			buttonPanel.add(auctionButton);
		}
    	    } else if (positionType == "taxes") {
		taxOwed = Integer.valueOf(String.valueOf(squareInfo.get("amount")));
		message = "You have landed on a Tax square. You must pay a tax of " + taxOwed + " dollars.";
		// Create OK button, add action command and listener, and add to button panel
		JButton okButton = new JButton("OK");
		okButton.setActionCommand(OK);
		okButton.addActionListener(this);
		
		okButton.setBackground(new Color(59, 89, 182));
        	okButton.setForeground(Color.WHITE);
        	okButton.setFocusPainted(false);
		okButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		//okbutton.setEnabled(true);
		
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
		
	JLabel imageLabel = new JLabel(popUpIcon, JLabel.CENTER);
		
	//imagePanel = new JPanel(new FlowLayout());
	imagePanel.setBackground(Color.BLACK);
	imagePanel.setOpaque(true);	
	imagePanel.add(imageLabel); 
	
		
	JTextArea textArea = new JTextArea(message, 5, 20);
        textArea.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
        textArea.setBackground(Color.BLACK);
	textArea.setForeground(Color.WHITE);
	textArea.setLineWrap(true);			// Set various attributes as required
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
		
	textPanel.add(textArea);
	textPanel.setBackground(Color.BLACK);
		
	//buttonPanel.setBackground(Color.BLACK);
		
	add(imagePanel);
	add(textPanel);
	add(buttonPanel);
		
	myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specifies that the application must exit when window is closed
        this.setOpaque(true); // Makes contentPane opaque 
        myFrame.setContentPane(this); // Sets contentPane property
	myFrame.getContentPane().setBackground(Color.black);
        //myFrame.setSize(450, 400);
	myFrame.setTitle("Current Square Update");
	myFrame.setLocationRelativeTo(null);
	myFrame.pack();
        myFrame.setVisible(true); // Window is displayed
    }	
	
	/*
   
     Method which takes in as a parameter the HashMap containing info on the current square and then creates appropriate
     JFrame components based on the values passed in.
     @param squareInfo - HashMap object containing information on the current square 
    
    //public void displayPopUp(HashMap<String, String> squareInfo) {
        
    //}
*/	
	
	
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
			JOptionPane.showMessageDialog(myFrame, "Your purchase was successful.");
			myFrame.dispose();
        } else if (AUCTION.equals(command)) { // If Auction button was pressed
			JOptionPane.showMessageDialog(myFrame, "This will now be put to auction.");
			myFrame.dispose();
	} else if (OK.equals(command)) { // If OK button was pressed
			myFrame.dispose();
	}
    }
}
