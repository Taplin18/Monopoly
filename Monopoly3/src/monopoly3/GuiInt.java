/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly3;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * An integrated GUI panel for combining JPanels with team mate
 * @author cascadafreak07
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
    
    public int getDiceOne(){
      return btn.getDiceOne();
    }
    
    public int getDiceTwo(){
      return btn.getDiceTwo();
    }
    
    public void setMyTurn(boolean b){
		btn.setMyTurn(b);
	}
    
    public boolean getIsDiceButtonPressed(){
      return btn.getIsDiceButtonPressed();
    }
    
    
}
