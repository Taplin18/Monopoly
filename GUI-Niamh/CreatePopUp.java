import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
 
 
public class CreatePopUp extends JPanel implements ActionListener { 
	
	private HashMap<String, String> hashMap;	
   /*
	* Images for each square type stored in static variables.
	
	private static final String GO_IMAGE = "go.jpg";
	// Properties' images
	private static final String MED_AVENUE = "med_avenue.jpg";
	private static final String BALTIC_AVENUE = "baltic_avenue.jpg";
	private static final String ORIENTAL_AVENUE = "oriental_avenue.jpg";
	private static final String VERMONT_AVENUE = "vermont_avenue.jpg";
	private static final String CONNECTICUT_AVENUE = "connecticut_avenue.jpg";
	private static final String ST_CHARLES_PLACE = "st_charles_place.jpg";
	private static final String STATES_AVENUE = "states_avenue.jpg";
	private static final String VIRGINIA_AVENUE = "virginia_avenue.jpg";
	private static final String ST_JAMES_PLACE = "st_james_place.jpg";
	private static final String TENNESSEE_AVENUE = "tennessee_avenue.jpg";
	private static final String NEW_YORK_AVENUE = "new_york_avenue.jpg";
	private static final String KENTUCKY_AVENUE = "kentucky_avenue.jpg";
	private static final String INDIANA_AVENUE = "indiana_avenue.jpg";
	private static final String ILLINOIS_AVENUE = "illinois_avenue.jpg";
	private static final String ATLANTIC_AVENUE = "atlantic_avenue.jpg";
	private static final String VENTNOR_AVENUE = "ventnor_avenue.jpg";
	private static final String MARVIN_GARDENS = "marvin_gardens.jpg";
	private static final String PACIFIC_AVENUE = "pacific_avenue.jpg";
	private static final String NORTH_CAROLINA_AVENUE = "north_carolina_avenue.jpg";
	private static final String PENNSYLVANIA_AVENUE = "pennsylvania_avenue.jpg";
	private static final String PARK_PLACE = "park_place.jpg";
	private static final String BOARDWALK = "boardwalk.jpg";
	
	private static final String CHEST_IMAGE = "chestcard.jpg";
	private static final String CHANCE_IMAGE = "chancecard.jpg";
	private static final String CORNER_IMAGE = "corner.jpg";
	private static final String TRANSPORT_IMAGE = "transport.jpg";
	private static final String UTILITIES_IMAGE = "utilities.jpg";
	private static final String TAXES_IMAGE = "taxes.jpg";
		
   /*
	* actionIdentifier - Identifies what action is to be taken based on the ID of the current square
	* message - Holds the appropriate message displayed in the pop-up depending on what he current square is 
	* title - Holds the appropriate title for the pop-up depending on what he current square is 
	* image - Holds the file name of the appropriate image displayed beside the message in the pop-up depending on the current square
	* popUpIcon - Stores the image for the pop-up as an ImageIcon object
	* popUpImg - Stores the image when it is initially read in 
	* messageType - Defines the type of pop-up message being displayed
    */	   
	
	private String message = "";
	private String title = "";
	private String image = "";
	private ImageIcon popUpIcon = null;
	private BufferedImage popUpImg = null;
	private int messageType = JOptionPane.PLAIN_MESSAGE;
	private JFrame myFrame;
	private JPanel imagePanel;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JLabel imageLabel;
	private JTextArea textArea;
	private static String OK = "OK";
	private static String PAY_RENT = "Pay Rent";
	private static String BUY_PROPERTY = "Buy Property";
	private static String AUCTION_PROPERTY = "Auction Property";
	
	
	/**
	 * A method which may be inserted within the main frame of the game once all of Denise's parts and mine are 
	 * combined into a single frame. The ID of the square the player has just landed on is passed in each time a player moves
	 * to a new square and the output is a pop-up which tells the user what square they have landed on accompanied by an appropriate icon.
	 * For now, the details of each square are broadly divided into Go, Chest, Chance, Property, Corner, Transport, Utilities,
	 * and Taxes - these can be made more specific depending on what specific values we plan on having on each square and I will update
	 * the output accordingly. 
	 */
    	public CreatePopUp(HashMap squareInfo) {
		myFrame = new JFrame();
		imagePanel = new JPanel(new FlowLayout());
		textPanel = new JPanel(new FlowLayout());
		buttonPanel = new JPanel(new FlowLayout());
		hashMap = squareInfo;
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specifies that the application must exit when window is closed
  		this.setOpaque(true); // Makes contentPane opaque 
        	myFrame.setContentPane(this); // Sets contentPane property
		myFrame.getContentPane().setBackground(Color.black);
        	myFrame.setSize(450, 400);
		myFrame.pack();
		myFrame.setLocationRelativeTo(null);
        	myFrame.setVisible(true); // Window is displayed
	}	
		
		
	/*public void displayPopUp(int actionIdentifier) {
		if (actionIdentifier == 0) {
			message = "You are currently on 'Go!' - let's get started!";
			JButton okButton = new JButton("OK");
			okButton.setActionCommand(OK);
			okButton.addActionListener(this);
			buttonPanel.add(okButton);
		    	image = GO_IMAGE;
		} else if (actionIdentifier == 1 || actionIdentifier == 3 || actionIdentifier == 6 || actionIdentifier == 8
		|| actionIdentifier == 9 || actionIdentifier == 11 || actionIdentifier == 13 || actionIdentifier == 14 || actionIdentifier == 16
		|| actionIdentifier == 18 || actionIdentifier == 19 || actionIdentifier == 21 || actionIdentifier == 23
		|| actionIdentifier == 24 || actionIdentifier == 26 || actionIdentifier == 27 || actionIdentifier == 29 || actionIdentifier == 31
		|| actionIdentifier == 32 || actionIdentifier == 34 || actionIdentifier == 37 || actionIdentifier == 39) { // If it's a property
			
			message = "You have landed on States Avenue!\n";
			// if property already bought {
				message += "You must pay rent of 300. ";
				JButton payRentButton = new JButton("Pay Rent"); // Pay rent button
				payRentButton.setActionCommand(PAY_RENT);
				payRentButton.addActionListener(this);
				//buttonPanel.add(payRentButton);
			// } else { 
				message += "Would you like to buy or auction this property?";
				JButton buyButton = new JButton("Buy Property"); // Pay rent button
				buyButton.setActionCommand(BUY_PROPERTY);
				buyButton.addActionListener(this);
				JButton auctionButton = new JButton("Auction Property"); // Pay rent button
				auctionButton.setActionCommand(AUCTION_PROPERTY);
				auctionButton.addActionListener(this);
				// Add buttons to button panel
				buttonPanel.add(buyButton);
				buttonPanel.add(auctionButton);
			// }	
			image = STATES_AVENUE; // Possible new method in Property class 
		} else if (actionIdentifier == 2) {
			message = "You may now take a chest card!";
			title = "Chest Card";
			image = CHEST_IMAGE;
		}  else if (actionIdentifier == 4) {
			message = "Taxes square";
			title = "Taxes";
			image = TAXES_IMAGE;
		} else if (actionIdentifier == 5) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 7) {
			message = "You may now take a chance card!";
			title = "Chance Card";
			image = CHANCE_IMAGE;
		} else if (actionIdentifier == 10) {
			message = "Corner square";
			title = "Corner";
			image = CORNER_IMAGE;
		} else if (actionIdentifier == 12) {
			message = "Utilities square";
			title = "Utilities";
			image = UTILITIES_IMAGE;
		} else if (actionIdentifier == 15) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 17) {
			message = "You may now take a chest card!";
			title = "Chest Card";
			image = CHEST_IMAGE;
		} else if (actionIdentifier == 20) {
			message = "Corner square";
			title = "Corner";
			image = CORNER_IMAGE;
		} else if (actionIdentifier == 22) {
			message = "You may now take a chance card!";
			title = "Chance Card";
			image = CHANCE_IMAGE;
		} else if (actionIdentifier == 25) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 28) {
			message = "Utilities square";
			title = "Utilities";
			image = UTILITIES_IMAGE;
		} else if (actionIdentifier == 30) {
			message = "Corner square";
			title = "Corner";
			image = CORNER_IMAGE;
		} else if (actionIdentifier == 33) {
			message = "You may now take a chest card!";
			title = "Chest Card";
			image = CHEST_IMAGE;
		} else if (actionIdentifier == 35) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 36) {
			message = "You may now take a chance card!";
			title = "Chance Card";
			image = CHANCE_IMAGE;
		} else if (actionIdentifier == 38) {
			message = "Taxes square";
			title = "Taxes";
			image = TAXES_IMAGE;
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
		textArea.setLineWrap(true);				// Set various attributes as required
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
    	/*public void actionPerformed(ActionEvent e) {
        	String command = e.getActionCommand(); // Store value of command
 
        	if (PAY_RENT.equals(command)) { 
			// Code which involves user paying specified amount of rent
            		JOptionPane.showMessageDialog(myFrame, "You have paid your rent.");
			myFrame.dispose();
        	} else if (BUY_PROPERTY.equals(command)) { 
			// Code to purchase property and add it to user's property list
			JOptionPane.showMessageDialog(myFrame, "You have purchased this property.");
			myFrame.dispose();
        	} else if (AUCTION_PROPERTY.equals(command)) {
			JOptionPane.showMessageDialog(myFrame, "You have put this property to auction.");
			myFrame.dispose();
		} else if (OK.equals(command)) {
			myFrame.dispose();
		}
    	}
	
	public static void main(String[] args) {
		CreatePopUp test = new CreatePopUp(1);
	} */
}	
	
