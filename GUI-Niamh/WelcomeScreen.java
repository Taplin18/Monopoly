import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * Class which displays a welcome screen to the user which offers the following options:
 *  - Create User Name: The user types and submits the user name they would like for the game.
 *  - Force Start: The user forces the game to start regardless of whether the game is full yet.
 *  - Rules: The user can familiarise themselves with the rules of the game. 
 */
 
public class WelcomeScreen extends JPanel implements ActionListener {
    
   /**
    * controllingFrame - Represents the JFrame object passed into the constructor
	* CREATE_NAME - Static string to represent text for Create Name button
	* FORCE_START - Static string to represent text for Force Start button
	* RULES - Static string to represent text for Rules button
	* MONOPOLY_LOGO - Holds name of file which contains Zebropoly logo
	* USER_ICON - Holds name of file which contains user icon 
	* userNameField - Empty field where user will enter their username
	* hasUserName - Boolean variable tracking whether or not the user has set a username
	*/
	
	private JFrame myFrame; 
	private static String CREATE_NAME = "Create Name";
	private static String FORCE_START = "Force Start";
    	private static String RULES = "Rules";
	private static final String MONOPOLY_LOGO = "zebropoly2.png";
	private static final String USER_ICON = "usericon2.jpg";
    	private JTextField userNameField; 
	private boolean hasUserName = false;

	/**
	 * Constructor method which creates and sets up various fields/labels
	 * on panels and then adds them to the given JFrame.
	 * @param f A JFrame object
	 */
    	public WelcomeScreen() {
		
		FlowLayout flowLayout = new FlowLayout(); // Create FlowLayout object for later reference
		
        	myFrame = new JFrame("Welcome to Zebropoly!"); 
		myFrame.setLayout(new BoxLayout(myFrame, BoxLayout.Y_AXIS)); // Set layout of the JFrame object
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specifies that the application must exit when closed
        	contentPane.setOpaque(true); // Makes contentPane opaque 
        	myFrame.setContentPane(contentPane); // Sets contentPane property
		myFrame.getContentPane().setBackground(Color.black);
        	myFrame.setSize(550, 500);
		myFrame.setLocationRelativeTo(null);
        	myFrame.setVisible(true); // Window is displayed
		
		// Initialise JLabels variable to null
		JLabel monopolyIconLabel = null;
		JLabel messageLabel = null;
		JLabel userNameLabel = null;
		
		// Initialise ImageIcon variables to null
		ImageIcon monopolyIcon = null;
		ImageIcon userIcon = null;
		
		// Set up user name field
        	userNameField = new JTextField(12); // Length = 12
        	userNameField.setActionCommand(CREATE_NAME);  
        	userNameField.addActionListener(this);
		
		// Create ImageIcon for Monopoly logo and user icon
		try {
			// Monopoly icon creation
			BufferedImage imgMonopoly = ImageIO.read(new File(MONOPOLY_LOGO));
			monopolyIcon = new ImageIcon(imgMonopoly);
			Image image1 = monopolyIcon.getImage(); 
			Image newimg1 = image1.getScaledInstance(400, 140,  java.awt.Image.SCALE_SMOOTH); // Set the size / scaling
			monopolyIcon = new ImageIcon(newimg1); 
			
			// User icon creation
			BufferedImage imgUserIcon = ImageIO.read(new File(USER_ICON));
			userIcon = new ImageIcon(imgUserIcon);
			Image image2 = userIcon.getImage();
			Image newimg2 = image2.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH); // Set the size / scaling
			userIcon = new ImageIcon(newimg2);
		
		} catch (IOException e) { // Exception handling in the case of the image files being inaccessible
			e.printStackTrace();
        	}
 
		// Add Monopoly icon to a JLabel and specify horizontal alignment
		monopolyIconLabel = new JLabel(monopolyIcon, JLabel.CENTER);
		
		// Create JTextArea for welcome message
		JTextArea textArea = new JTextArea(
                "         Welcome to Zebropoly! \n\nPlease enter your username below and then " +
                "either Force Start or wait until 4 players have joined.\n\n                   Have fun!", 
                5, 
                20);
       	 	textArea.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
        	textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setLineWrap(true);				// Set various attributes as required
        	textArea.setWrapStyleWord(true);
        	textArea.setOpaque(false);
        	textArea.setEditable(false);
		
		// Add user icon to a JLabel, specify horizontal alignment, and add a String to tell user what to enter in the box
        	userNameLabel = new JLabel("Username: ", userIcon, JLabel.CENTER);
        	userNameLabel.setLabelFor(userNameField);
		userNameLabel.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 13));
		userNameLabel.setForeground(Color.WHITE);
		
        	//Create, set up, and customise buttons
		JButton createNameButton = new JButton("Create Name"); // Check button
		JButton forceStartButton = new JButton("Force Start"); // Criteria button
		JButton rulesButton = new JButton("Rules"); // Rules button
        	createNameButton.setActionCommand(CREATE_NAME);
        	forceStartButton.setActionCommand(FORCE_START);
		rulesButton.setActionCommand(RULES);
        	createNameButton.addActionListener(this);
        	forceStartButton.addActionListener(this);
		rulesButton.addActionListener(this);
		
		createNameButton.setBackground(new Color(59, 89, 182));
        	createNameButton.setForeground(Color.WHITE);
       		createNameButton.setFocusPainted(false);
        	createNameButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));

		forceStartButton.setBackground(new Color(59, 89, 182));
        	forceStartButton.setForeground(Color.WHITE);
        	forceStartButton.setFocusPainted(false);
        	forceStartButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		
		rulesButton.setBackground(new Color(59, 89, 182));
        	rulesButton.setForeground(Color.WHITE);
        	rulesButton.setFocusPainted(false);
        	rulesButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		
		// Create JPanel object for Monopoly image component
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(flowLayout); // Set layout for panel
		imagePanel.setBackground(Color.BLACK);
		imagePanel.setOpaque(true);
		
		// Create JPanel for welcome message
		JPanel welcomeMessagePanel = new JPanel();
		welcomeMessagePanel.setLayout(flowLayout);
		
		// Create JPanel object for user options
		JPanel userNamePanel = new JPanel();
		userNamePanel = new JPanel(flowLayout);
		userNamePanel.setBackground(Color.BLACK);
		userNamePanel.setOpaque(true);
		
		// Create JPanel object for remaining two buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(flowLayout);
		buttonsPanel.setBackground(Color.BLACK);
		buttonsPanel.setOpaque(true);
		
		// Add Monopoly image component to image panel
		imagePanel.add(monopolyIconLabel);
		
		// Add JLabel welcome message to associated panel
		welcomeMessagePanel.add(textArea);
		welcomeMessagePanel.setBackground(Color.BLACK);
		welcomeMessagePanel.setForeground(Color.WHITE);
		
		// Add user name components to user name panel
        	userNamePanel.add(userNameLabel);
        	userNamePanel.add(userNameField);
		userNamePanel.add(createNameButton);
		
		// Add remaining two buttons to buttons panel
		buttonsPanel.add(forceStartButton);
		buttonsPanel.add(rulesButton);
		
		// Add panels to JFrame
		add(imagePanel);
		add(welcomeMessagePanel);
        	add(userNamePanel);
        	add(buttonsPanel);
    	}
 
	/**
	 * Method which checks what command was made by the user and then responds appropriately.
	 * @param e 
	 */
   	 public void actionPerformed(ActionEvent e) {
         
		 String command = e.getActionCommand(); // Store value of command
 
        	if (CREATE_NAME.equals(command)) { 
           		String newUserName = userNameField.getText(); // Get content of user name text field
            		if (newUserName.trim().isEmpty() == false) { // If at least one character has been entered into the field
				// Code to store user name somewhere 
				hasUserName = true; // Marks that user name has been set
                		JOptionPane.showMessageDialog(controllingFrame,
                    		"Success! You have successfully created a new user name.");	// Display success message
			} else { // If the user name field was empty
				JOptionPane.showMessageDialog(controllingFrame, "Your user name must contain at least one character");
			}
        	} else if (RULES.equals(command)) { //The user wishes to view the rules
            		JOptionPane.showMessageDialog(controllingFrame,
                					"Here are the rules."); // Insert rules here	
        	} else {
			if (hasUserName == false) { // Only allow user to proceed if they have created a user name
				JOptionPane.showMessageDialog(controllingFrame, "You must create a user name before beginning.");
			} else {
				// Launch game
			}
		}
    	 }
}
