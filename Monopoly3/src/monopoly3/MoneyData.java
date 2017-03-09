package monopoly3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
 
 
public class MoneyData extends JPanel implements ActionListener {

    private String username;
    private String message;
    private int money;


    public MoneyData(String username, int money) {
	setUsername(username);
	setMoney(money);
	display();
    }

    public void display() {
	JFrame frame = new JFrame();
	JPanel textPanel = new JPanel();
	message = "Player: " + username + "\nMoney: " + money;
	JTextArea textArea = new JTextArea(message, 5, 20);
        textArea.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 15));
	textArea.setLineWrap(true);			// Set various attributes as required
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
		
	textPanel.add(textArea);
	
	add(textPanel);
    }
    
    public void setUsername(String name) {
	username = name;
    }
    
    public void setMoney(int mon) {
	money = mon;
    }

} 