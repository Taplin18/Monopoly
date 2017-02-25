import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
 
 
	/**
	 * A method which may be inserted within the main frame of the game once all of Denise's parts and mine are 
	 * combined into a single frame. The ID of the square the player has just landed on is passed in each time a player moves
	 * to a new square and the output is a pop-up which tells the user what square they have landed on accompanied by an appropriate icon.
	 * For now, the details of each square are broadly divided into Go, Chest, Chance, Property, Corner, Transport, Utilities,
	 * and Taxes - these can be made more specific depending on what specific values we plan on having on each square and I will update
	 * the output accordingly. 
	 */
    public void getPopUp(int squareID) {
	
	   /*
	    * Images for each square type stored in static variables.
		*/
		private static final String GO_IMAGE = "go.jpg";
		private static final String PROPERTY_IMAGE = "property.jpg";
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
		
		private actionIdentifier = squareID;
		private String message = "";
		private String title = "";
		private String image = "":
		private ImageIcon popUpIcon = null;
		private BufferedImage popUpImg = null;
		private int messageType = JOptionPane.PLAIN_MESSAGE;
		
		
		if (actionIdentifier == 0) {
			message = "You are currently on 'Go!' - let's get started!";
			title = "Go";
			image = GO_IMAGE;
		} else if (actionIdentifier == 1) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 2) {
			message = "You may now take a chest card!";
			title = "Chest Card";
			image = CHEST_IMAGE;
		} else if (actionIdentifier == 3) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 4) {
			message = "Taxes square";
			title = "Taxes";
			image = TAXES_IMAGE;
		} else if (actionIdentifier == 5) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 6) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 7) {
			message = "You may now take a chance card!";
			title = "Chance Card";
			image = CHANCE_IMAGE;
		} else if (actionIdentifier == 8) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 9) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 10) {
			message = "Corner square";
			title = "Corner";
			image = CORNER_IMAGE;
		} else if (actionIdentifier == 11) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 12) {
			message = "Utilities square";
			title = "Utilities";
			image = UTILITIES_IMAGE;
		} else if (actionIdentifier == 13) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 14) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 15) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 16) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 17) {
			message = "You may now take a chest card!";
			title = "Chest Card";
			image = CHEST_IMAGE;
		} else if (actionIdentifier == 18) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 19) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 20) {
			message = "Corner square";
			title = "Corner";
			image = CORNER_IMAGE;
		} else if (actionIdentifier == 21) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 22) {
			message = "You may now take a chance card!";
			title = "Chance Card";
			image = CHANCE_IMAGE;
		} else if (actionIdentifier == 23) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 24) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 25) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 26) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 27) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 28) {
			message = "Utilities square";
			title = "Utilities";
			image = UTILITIES_IMAGE;
		} else if (actionIdentifier == 29) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 30) {
			message = "Corner square";
			title = "Corner";
			image = CORNER_IMAGE;
		} else if (actionIdentifier == 31) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 32) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 33) {
			message = "You may now take a chest card!";
			title = "Chest Card";
			image = CHEST_IMAGE;
		} else if (actionIdentifier == 34) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 35) {
			message = "Transport square";
			title = "Transport";
			image = TRANSPORT_IMAGE;
		} else if (actionIdentifier == 36) {
			message = "You may now take a chance card!";
			title = "Chance Card";
			image = CHANCE_IMAGE;
		} else if (actionIdentifier == 37) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		} else if (actionIdentifier == 38) {
			message = "Taxes square";
			title = "Taxes";
			image = TAXES_IMAGE;
		} else if (actionIdentifier == 39) {
			message = "You have landed on a property!";
			title = "Property";
			image = PROPERTY_IMAGE;
		}
		 // Read in image file
		try {
			popUpImg = ImageIO.read(new File(image));
		} catch (IOException e) { // Catch any errors which may occur when reading in the file
			e.printStackTrace();
		}
		
		popUpIcon = new ImageIcon(popUpImg);
		Image myImage = popUpIcon.getImage(); 
		Image myImageCopy = myImage.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // Scale / re-size the image 
		popUpIcon = new ImageIcon(myImageCopy); 
		
		// Create the pop-up based on the values in the variables above - controllingFrame refers to the name of the parent frame
		JOptionPane.showMessageDialog(controllingFrame, message, title, messageType, popUpIcon);
	}