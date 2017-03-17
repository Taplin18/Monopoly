/**
 * Created by dgc1
 * @author Denise Grace Crowley
 * @version 1.0
 *
 */
package monopoly3;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *A class that sets the image for the dice roll
 */
public class Dice {  
    
    ImageIcon[] images;
    /**
    *A method to generate a random number betweeen 1 and 6
    *@returns Integer
    */
    public int rollDice(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(6);
        return randomInt+1;
    }    
    
    /**
     * Gets the images of the dice sides and adds them to an ImageIcon array
     * @return dice
     */
    public ImageIcon[] getImages(){
        ImageIcon [] dice = new ImageIcon[6];
        BufferedImage img;
        try{            
            for(int i = 1; i < 7; i++){
                img = ImageIO.read(new File("monopoly3/dice"+i+".png"));                
                dice[i-1] = new ImageIcon(img);
            }            
        }
        catch (IOException ex) {            
             ex.printStackTrace();
        }
      
        return dice;
    }

}

   
