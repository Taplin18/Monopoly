
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
    

    /**
     * The constructor, assigns the various variables of the JFrame
     */
    public Frame()  {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Monopoly");
        setSize(1800,900);
        setResizable(false);
        
        getContentPane().setLayout(new GridLayout(1,2,0,0));
      
        setLocationRelativeTo(null);
        
        initilize();
        
    }
    
    public void setClientId(int id){
      board.setClientId(id);
      //System.out.println(id)
    }
    
    /**
     * The assignment method, creates the instance of other JPanels used and adds them to the JFrame
     */
    public void initilize() {        
        board = new BoardGui();
        GuiInt guiInt  = new GuiInt();
        add(board);
        add(guiInt);
        setVisible(true);
    }
    
    /**
     * The main method, creates the instance of the class
     * @param args
     */
    
            
}
