
package monopoly3;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import static monopoly3.BoardGui.array;
import static monopoly3.BoardGui.p;
import java.util.Random;

/**
 * The gui functionality of monopoly, this includes the use of the buttons, text-boxes and labels to add functionality and display the dice images 
 * @author cascadafreak07
 */
public class Buttons extends JPanel implements ActionListener {
   
    //Rolling, buying, selling and end turn buttons
    JButton roll;
    JButton buy;
    JButton sell;
    JButton endTurn;
    
    //Display the dice
    JLabel dice1;
    JLabel dice2;   
    //Die class
    Dice dice;
    //Get die images
    ImageIcon[] images;
    
    //Check if player in jail
    boolean inJail = false;
    
    //Chat
    JTextArea chat;
    
    //Send message in chat button
    JButton send;
    
    int diceOne;
    int diceTwo;
    
    //location of player 
    int positionInArray = 0;
    
    boolean diceButtonPressed=false;
    
    public void addToPosition(int number){
      positionInArray = positionInArray + number;
    }
       
    Buttons(){
        init();
    }
    
    /**
     * The initilization method, all the JButtons, JLabels and JTextAreas are created and added to the JPanel
     */
    public void init()
    {
        setLayout(null);
        //Creating instance of dice class
        dice = new Dice();
        //Populating the image array with dice images
        images = dice.getImages();
        
        //Initiliating all the buttons
        roll = new JButton("Roll");
        roll.setBounds(25, 25, 150, 50);
        add(roll);
        roll.addActionListener(this);
           
        buy = new JButton("Buy");
        buy.setBounds(200, 25, 150, 50);
        add(buy);
        buy.addActionListener(this);
               
        sell = new JButton("Sell");
        sell.setBounds(25, 100, 150, 50);
        add(sell);
        sell.addActionListener(this);
    
        endTurn = new JButton("End Turn");
        endTurn.setBounds(200, 100, 150, 50);
        add(endTurn);
        endTurn.addActionListener(this);

        dice1 = new JLabel();
        dice1.setBounds(25, 180, 115, 115);
        add(dice1);
       
        dice2 = new JLabel();
        dice2.setBounds(200, 180, 115, 115);
        add(dice2);
        
        chat = new JTextArea();
        chat.setBounds(25, 325, 300, 300);
        chat.setText("Chat");
        add(chat);
                
        send = new JButton("Send");
        send.setBounds(125, 675, 150,50);
        add(send);
    
    }
    
    public boolean getIsDiceButtonPressed(){
      return diceButtonPressed;
    }
    
    public void setDice(int v, int vt){
      this.diceOne=v;
      this.diceTwo=vt;
    }
    
    public int getDiceOne(){
      this.diceButtonPressed=false;
      return this.diceOne;
    }
    
    public int getDiceTwo(){
      return this.diceTwo;
    }
    
    public void setDiceButtonPressed(){
      this.diceButtonPressed=true;
    }
    
    /**
     * The action performed class, activates when an event is triggered such as pressing a button 
    */
    public void actionPerformed(ActionEvent e) {
         
        String action = e.getActionCommand();
        //When the roll button is clicked
        if(action.equals("Roll")){
            //get the values of the dice
            //MAYBE CHANGE CLIENT.MYTURN()//GETDICE().....ARE CLIENT AND GUI GETTING SAME DICE TOTAL?????
            Random randomGenerator = new Random();
            int value = randomGenerator.nextInt(6)+1;
            int value2 = randomGenerator.nextInt(6)+1;
            
            this.setDice(value, value2);
            
            this.setDiceButtonPressed();
            
            //FOR TESTING
            int diceNumber = value + value2;
                       
            for(int i = 1; i < 7; i++){
                if(value == i){       
                    //Set the image of dice side
                    dice1.setIcon(images[i-1]);
                    
                }               
            }
            for(int i = 1; i < 7; i++){
                if(value2 == i) {                    
                    //set the image o dice side
                    dice2.setIcon(images[i-1]);                    
                }               
            }
            
            //Add to the position of the player
            //positionInArray = client.getPosition();
            //FOR TESTING
            addToPosition(diceNumber);
            
            //Create new point with new player location
            Points points = new Points(array.get(positionInArray).getX(), array.get(positionInArray).getY());
            
            //if player in jail
            if(inJail) {
                positionInArray = 10;
                points = new Points(50,710);
            }
            //Move the player
            p.move(points);
            
            revalidate();
            repaint();
       
        }
        //else if(action.equals("Buy")){

            
        //}
        //else if(action.equals("Sell")){
            
        //}
        else if(action.equals("End Turn")){           
          //  client.sendByeMessage();
            repaint();
        }        
    }
   
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
