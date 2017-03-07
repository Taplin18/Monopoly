/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly3;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author cascadafreak07
 */
public class GuiInt extends JPanel{
    public GuiInt(){
        setLayout(new GridLayout(1,2,0,0));
        Buttons btn = new Buttons();
        
        //otherGui oth = new OtherGui();
        add(btn);
        //add(oth);
    }
    
}
