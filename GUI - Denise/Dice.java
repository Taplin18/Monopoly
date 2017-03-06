package monopoly3;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author cascadafreak07
 */
public class Dice {
    
    //private int dice;  
    
    ImageIcon[] images;

    /**
     *
     * @return
     */
  /*  public int getDice() {
        return dice;
    }

    /**
     *
     * @param dice
     */
    /*public void setDice(int dice) {
        this.dice = dice;
    }
*/
    /**
     *
     * @return
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
                img = ImageIO.read(new File("C:\\Users\\cascadafreak07\\Desktop\\Monopoly\\Monopoly3\\test\\dice"+i+".png"));                
                dice[i-1] = new ImageIcon(img);
            }            
        }
        catch (IOException ex) {            
             ex.printStackTrace();
        }
      
        return dice;
    }

}
