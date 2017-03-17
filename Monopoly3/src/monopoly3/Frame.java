/**
 * Created by dgc1
 * @author Denise Grace Crowley
 * @version 1.0
 *
 */
package monopoly3;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 * The display class
 */
public class Frame extends JFrame {
    
    JSplitPane jSplit;
    
    BoardGui board;
    private boolean diceButton=false;
    GuiInt guiInt;

    /**
     * The constructor, assigns the various variables of the JFrame
     */
    public Frame(int numOfPlayers)  {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Monopoly");
        setSize(1800,900);
        setResizable(false);
        
        getContentPane().setLayout(new GridLayout(1,2,0,0));
      
        setLocationRelativeTo(null);
        
        initilize(numOfPlayers);
        
    }
	//Method to update the plaer position on the board
	public void updatePlayerPositions(int[] arrayPlayerPositions){
		board.updatePlayerPositions(arrayPlayerPositions);
	}
    /**
    *A getter method for the first dice value
    *@returns The value of the second dice
    */
    public int getDiceOne(){
      return guiInt.getDiceOne();
    }
    /**
    *A getter method for the second dice value
    *@returns The value of the first dice
    */
    public int getDiceTwo(){
     return guiInt.getDiceTwo();
    }
    /**
    *A setter method for the clients id
    *@param Integer
    */
    public void setClientId(int id){
      board.setClientId(id);
      
    }
    
    /**
     * The assignment method, creates the instance of other JPanels used and adds them to the JFrame
     */
    public void initilize(int numOfPlayers) {        
        board = new BoardGui(numOfPlayers); //num of players
        guiInt  = new GuiInt();
        add(board);
        add(guiInt);
        setVisible(true);
    }
    
 
    /**
    *A getter method for is the roll dice button pressed
    *@returns Is the roll button pressed
    */
    public boolean getIsDiceButtonPressed(){
      return guiInt.getIsDiceButtonPressed();
    }
    /**
    *A setter method for players turn
    *@param Boolean
    */
	public void setMyTurn(boolean b){
		guiInt.setMyTurn(b);
	}
    /**
    *A setter method for player position
    *@param Integer
    */
	public void setPosition(int p){
		guiInt.setPosition(p);
	}
            
}
