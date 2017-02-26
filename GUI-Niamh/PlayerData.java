import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.util.*;

public class PlayerData {

  public void populateTable() {
  
	ArrayList<List<Object>> playerData = new ArrayList<List<Object>>();
	List<Object> player = new List<Object>();
	// Iterate through client objects being stored
		player.add(//Output of getName());
		player.add(//Output of getID());
		player.add(//Output of getMoney());
		player.add(//Output of getSitesOwned());
		player.add(//Output of card details);
		playerData.add(player);
		player.clear();


    Object columnNames[] = { "ID", "Username", "Money", "Properties", "Cards" }; // Define text for columns
    JTable table = new JTable(playerData, columnNames); // Create JTable
    JScrollPane scrollPane = new JScrollPane(table); // Create JScrollPane
	
	// Create panel and add table to it
	playerDataPanel = new JPanel(new flowLayout());
	playerDataPanel.setOpaque(true);
	playerDataPanel.add(scrollPane);

  }
  
}