
package monopoly3;

import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import static monopoly3.Buttons.client;


class Board extends JPanel implements ActionListener {

    //Timer for the game loop
    Timer t;
    //Instance of the player, needs to be static so it can be seen from the Buttons class
    static Player p;
    //Image of the player
    BufferedImage img;
    //List of locations where the player can move
    static ArrayList<Points> array = new ArrayList<Points>();
    
    BufferedImage[]playerImages;
    
    public Board() {
        //Initiliazing the variables
        t = new Timer(10, this);
        t.start();
        setFocusable(true);    
       
        try{            
            img = ImageIO.read(new File("C:\\Users\\cascadafreak07\\Desktop\\Monopoly\\Monopoly3\\test\\monopoly.jpg"));
        }
        catch (IOException ex){            
            System.out.println("Error");
        }
      
        boardPositions();
        p = new Player(array.get(0));
        t.start();   
        
        playerImages =  p.getPlayers();
        p.setPlayer(playerImages[client.getId()]);
        
    }      
    
    //Populating the array with possible board positions
    public void boardPositions(){
        Points p;
        int x = 785;
        int y = 750;
        for(int i = 0; i < 40; i++)
        {
            if(i == 0){
                x = 785;
            }
            else if(i < 10){
                x -= 72;
            }
            else if(i == 10) {
                x = 10;
                y = 710;
            }
            else if(i > 10 && i < 20){
                x = 10;
                y -= 66;
            }
            else if(i == 20){
                x = 69;
                y = 10;
            }
            else if(i > 20 && i <30){
                x += 72;
                y = 10;
            }
            else if(i == 30){
                x = 785;
                y = 55;
            }
            else{
                x = 785;
                y += 66;
            }
            p = new Points(x,y);
            array.add(p);
        }
    }
    
    public void actionPerformed(ActionEvent e) {        
        repaint();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, 900, 800, this);
      
        p.draw(g);
        repaint();
    }
    
}
