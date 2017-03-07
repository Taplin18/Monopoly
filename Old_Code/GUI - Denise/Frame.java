
package monopoly3;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class Frame extends JFrame {
    
    JSplitPane jSplit;
    public Frame()  {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Monopoly");
        setSize(1800,900);
        setResizable(false);
        
        getContentPane().setLayout(new GridLayout(1,2,0,0));
      
        setLocationRelativeTo(null);
        
        initilize();
        
    }
    
    public void initilize() {        
        Board board = new Board();
        GuiInt guiInt  = new GuiInt();
        add(board);
        add(guiInt);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
            
}
