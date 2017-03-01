package monopoly3;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Dice {
    
    private int dice;  
    
    ImageIcon[] images;
    public int getDice() {
        return dice;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public int rollDice(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(6);
        return randomInt+1;
    }    
    
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

   