
package monopoly3;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

/**
 * The display class
 * @author cascadafreak07
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
	
	public void updatePlayerPositions(int[] arrayPlayerPositions){
		board.updatePlayerPositions(arrayPlayerPositions);
	}
    
    public int getDiceOne(){
      return guiInt.getDiceOne();
    }
    
    public int getDiceTwo(){
     return guiInt.getDiceTwo();
    }
    
    public void setClientId(int id){
      board.setClientId(id);
      //System.out.println(id)
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
     * The main method, creates the instance of the class
     * @param args
     */
    
    public boolean getIsDiceButtonPressed(){
      return guiInt.getIsDiceButtonPressed();
    }
	
	public void setMyTurn(boolean b){
		guiInt.setMyTurn(b);
	}
	
	public void setPosition(int p){
		guiInt.setPosition(p);
	}
            
}
