
package monopoly3;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * The player class assigns the image corresponding to the player and moves the player around the board
 * @author cascadafreak07
 */
public  class Player {

    /**
     * A constructor which takes in the Points class to assign the coordinates on boards
     * @param point
     */
    public Player(Points point){
        this.xPosition = point.getX();
        this.yPosition = point.getY();
    }
    
    private int xPosition;
    private int yPosition;
    BufferedImage player;
   
    /**
     * Moves the player position on board
     * @param points
     */
    public void move(Points points){
        this.xPosition = points.getX();
        this.yPosition = points.getY();
    } 
    

    /**
     * Return the buffered image icon of the player
     * @return player
     */
    public BufferedImage getPlayer() {
        return player;
    }

    /**
     * Assign a buffered image to the player
     * @param player
     */
    public void setPlayer(BufferedImage player) {
        this.player = player;
    }

    /**
     * Draw the player image icon in the given x and y coordinate
     * @param g
     */
    public void draw(Graphics g) {

        g.drawImage(getPlayer(), xPosition, yPosition, null);
    }
       
  
}
