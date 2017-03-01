
package monopoly3;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


public  class Player {

    public Player(Points point){
        this.xPosition = point.getX();
        this.yPosition = point.getY();
    }
    
    private int xPosition;
    private int yPosition;
    BufferedImage player;
   
    public void move(Points points){
        this.xPosition = points.getX();
        this.yPosition = points.getY();
    } 
    
    public BufferedImage[] getPlayers(){
        BufferedImage [] players = new BufferedImage[8];
        BufferedImage img;
        try{            
            for(int i = 0; i < 8; i++){
                img = ImageIO.read(new File("C:\\Users\\cascadafreak07\\Desktop\\Monopoly\\Monopoly3\\test\\player"+i+".jpg"));                
                players[i] = img;
            }            
        }
        catch (IOException ex) {            
             ex.printStackTrace();
        }
      
        return players;
    }
    public BufferedImage getPlayer() {
        return player;
    }

    public void setPlayer(BufferedImage player) {
        this.player = player;
    }

    public void draw(Graphics g) {

        g.drawImage(getPlayer(), xPosition, yPosition, null);
    }
       
  
}
