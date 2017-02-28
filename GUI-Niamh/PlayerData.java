import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.util.*;

public class PlayerData {

  JTable playerStats;
  JScrollPane scrollPane;
	
  public PlayerData() {
	
	ArrayList<Player> playerData = new ArrayList<Player>(4); // Initialise storage structure for table contents
	for (int i = 0; i < playerData.size(); i++) {
	    // Add each client object to ArrayList at position i
	}
	  
	Object columnNames[] = { "ID", "Username", "Money", "Properties", "Cards" }; // Define text for columns
        playerStats = new JTable(playerData, columnNames); // Create JTable
    	scrollPane = new JScrollPane(playerStats); // Create JScrollPane
	  
	// Create panel and add table to it
	playerDataPanel = new JPanel(new flowLayout());
	playerDataPanel.setOpaque(true);
	playerDataPanel.add(scrollPane);
  }
  
  public void populateTable() {
	int rowNumber = 0;
	for (int i = 0; i < playerData.size(); i++) {
		// Get client ID and add to first column
		// Get username and add to second column
		// Get money and add to third column
		// Get properties owned and add to fourth column
		// Get GOOJ Card info and add to fifth column
		row++; // Move to next row
	}
  }
 
