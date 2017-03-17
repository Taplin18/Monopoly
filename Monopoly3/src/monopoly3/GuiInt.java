/**
 * Created by dgc1
 * @author Denise Grace Crowley
 * @version 1.0
 *
 */
package monopoly3;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * An integrated GUI panel for combining JPanels with team mate
 */
public class GuiInt extends JPanel{

    int diceOne;
    Buttons btn;
    
    public GuiInt(){
        setLayout(new GridLayout(1,2,0,0));
        btn = new Buttons();
        
        //otherGui oth = new OtherGui();
        add(btn);
        //add(oth);
    }
    /**
    *A getter method for the first dice value
    *@returns The value of the first dice
    */
    public int getDiceOne(){
      return btn.getDiceOne();
    }
    /**
    *A getter method for the second dice value
    *@returns The value of the second dice
    */
    public int getDiceTwo(){
      return btn.getDiceTwo();
    }
    /**
    *A setter method to set player turn
    *@param Boolean
    */
    public void setMyTurn(boolean b){
		btn.setMyTurn(b);
	}
    /**
    *A setter method to set player position
    *@param Integer
    */	
	public void setPosition(int p){
		btn.setPosition(p);
	}
    /**
    *A getter method for if the rolled dicce button is pressed
    *@returns if the rolled dice button is pressed
    */
    public boolean getIsDiceButtonPressed(){
      return btn.getIsDiceButtonPressed();
    }
    
    
}
