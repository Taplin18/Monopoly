package monopoly3;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
import java.awt.image.BufferedImage;

public class Client{
//  public String message; //answer
  private int money;
  private int id;
  private int[] sitesOwned = new int[noOfSites];
  private int position;
  private int setNumberOfPlayers;
  private static int startMoney=2000;
  private static int noOfSites=20;
  private int siteArrayPointer=0;
  private int defaultStartingId=100;
  private static int portNumber=2222;
  private static int positionPortNumber=0;
  private Map <Integer, Property> properties = new HashMap<Integer, Property>();
  private int jail_free = 0;
  private int turnsInJail=0;
  private boolean inJail=false;
  private int daysInJail=0;
  private int jailPosition=20;
  private static Socket socket;
  private static boolean closed=false;
  private static boolean getNumPlayers=false;
  private boolean prevInJail=false; //at the beginning of the turn client was in jail (nulls second turn on double)
  private static int goAmount=2000;
  private static int startPosition=0;
  private String username;
  private static BufferedReader br;
  private int noOfDoubles=0;
  private Map <String, Integer> coloursOwned = new HashMap<String, Integer>();
  private Map <String, Integer> coloursTotal = new HashMap<String, Integer>();
  private Map <Integer, Integer> playersPositions = new HashMap<Integer, Integer>();
  private JSONParser parser = new JSONParser();
  private int diceOne;
  private int diceTwo;
  private String picture;
  private CreatePopUp popUp;
 // private static AuctionChat chatBox;
  private static WelcomeScreen welcomeScreen;
  private HashMap <String, String> squareInfo;
  private static JSONObject possibleRent;

  /**
  * Getter method for returning the number dice one has rolled on.
  * @return integer
  */
  public int getDiceOne(){
    return this.diceOne;
  }

  /**
  * Getter method for returning the number dice two has rolled on.
  * @return integer
  */
  public int getDiceTwo(){
    return this.diceTwo;
  }

  /**
  * Setter method for dice one and dice two.
  * @param integer diceOne, integer diceTwo
  */
  public void setDiceNumber(int diceOne, int diceTwo){
    this.diceTwo=diceTwo;
    this.diceOne=diceOne;
  }

  /**
  * Constructor initialising client id, money, position, coloursOwned and the total number of each colour property
  */
  public Client(){
    id=defaultStartingId;
    money=startMoney;
    position=startPosition;

    coloursOwned.put("red", 0);
    coloursOwned.put("orange", 0);
    coloursOwned.put("green", 0);
    coloursOwned.put("darkBlue", 0);
    coloursOwned.put("lightBlue", 0);
    coloursOwned.put("pink", 0);
    coloursOwned.put("brown", 0);

    coloursTotal.put("red", 3);
    coloursTotal.put("orange", 3);
    coloursTotal.put("green", 3);
    coloursTotal.put("darkBlue", 2);
    coloursTotal.put("lightBlue", 3);
    coloursTotal.put("pink", 3);
    coloursTotal.put("brown", 2);
  }
  
  /**
  * Setter method for client's username.
  * @param String username
  */
  public void setUsername(String username){
    this.username=username;
  }

  /**
  * Getter method for client's money.
  * @param integer money
  */
  public int getMoney(){
    return money;
  }

  /**
  * Getter method for client's id.
  * @return integer id
  */
  public int getId(){
    return id;
  }

  /**
  * Setter method for client's money.
  * @param integer money amount
  */
  public void setMoney(int amount){
    money=amount;
  }

  /**
  * Adds an amount of money to client's money.
  * @param integer money amount
  */
  public void addMoney(int amount){
    money=money+amount;
  }
  
  /**
  * Subtracts an amount of money from client's money.
  * @param integer money amount
  */
  public void subMoney(int amount){
    money=money+(amount);
  }

  /**
  * Setter method for client's id.
  * @param integer id
  */
  public void setId(int number){
    id=number;
  }

  /**
  * Adds an amount of spaces moved to client's current position.
  * @param integer number dice rolled
  */
  public void addPosition(int number){
    if (position + number >= 40) {
      position += number - 40;
    } else {
      position += number;
    }
  }

  /**
  * Getter method for client's position.
  * @return integer position
  */
  public int getPosition(){
    return position;
  }

  /**
  * Setter method for client's position.
  * @param integer position
  */
  public void setPosition(int number){
    position=number;
  }

  /**
  * Sends a first contact message to the server.
  */
  public void firstContactServer(String messageType){
    try {
      JSONObject obj = this.sendMessageToServer(this.getId(), messageType, this.getUserName()); //stores the returned reply message from the server
      String mess = String.valueOf(obj.get("id")); //gets the value that is linked to "id" key from the reply JSON message
      int newId = Integer.valueOf(mess);
      this.setId(newId); //sets id
    } catch (Exception e) {
      System.out.println("Failed to get new ID: " + e);
    }
  }

  /**
  * method which pertains to the client's turn. The following occurs:
  * 	1) Checks are made to see if the players is in jail (and if so whether he has a get out of jail free card).
  *		2) Client rolls dice.
  *		3) Records if client rolled a double.
  *		4) Checks are made in relation to jail.
  *		5) Message is sent to the server regarding the client's position.
  *		6) Server replies with position type and checks are made regarding the appropriate action the user makes according to the position type (property, transport, utility, chest, chance).
  *		&) Further action is taken in relation to doubles and jail.
  */
  public void myTurn(){
    System.out.println("\n"+this.getUserName()+": Start turn.");
    if(jail_free>0 || inJail==true){ //you have a get out of jail free card
      boolean decision=true;//pop up window asking if user wants to use his get out of jail free card
      if(decision){
        this.inJail=false;
        jail_free--;
        this.daysInJail=0;
      }
    }
    diceOne=this.rollDice();
    diceTwo=this.rollDice();
    this.setDiceNumber(diceOne, diceTwo);
    System.out.println(this.getUserName()+": rolled = " + diceOne + " and "+ diceTwo);
    if(this.inJail==true && diceOne!=diceTwo){ //in jail and didn't roll double
      daysInJail++;
      if(this.daysInJail==3){
        this.inJail=false;
        this.daysInJail=0;
      }
    }else{ //not in jail or in jail but has rolled double
      if(this.inJail==true && diceOne==diceTwo){ //in jail and rolled double
        this.inJail=false;
        this.daysInJail=0;
        this.prevInJail=true;
      }
      this.moveToPosition(diceOne, diceTwo);//move to position
      JSONObject returnedMessage=this.sendMessageToServer(this.getId(), "position", Integer.toString(this.getPosition()));

      System.out.println(this.getUserName()+": Landed on positionType "+returnedMessage.get("positionType")); 
      squareInfo = new HashMap<String, String>();
      squareInfo.put("positionType", String.valueOf(returnedMessage.get("positionType")));
      //squareInfo.put("picture", String.valueOf(returnedMessage.get("picture")));
      if(returnedMessage.get("positionType")=="chest"){
        squareInfo.put("chestType", String.valueOf(returnedMessage.get("chestType")));
        squareInfo.put("message", String.valueOf(returnedMessage.get("message")));
        if(returnedMessage.get("chestType")=="jail"){
          if(returnedMessage.get("jailType")=="out"){
            this.jail_free++;
            squareInfo.put("jailType", "out");
            popUp = new CreatePopUp(squareInfo);
          }else{
            squareInfo.put("jailType", "in");
            popUp = new CreatePopUp(squareInfo);
            this.setPosition(jailPosition);
          }
        }else{ //money
          squareInfo.put("chestAmount", String.valueOf(returnedMessage.get("chestAmount")));
          popUp = new CreatePopUp(squareInfo);
          if(returnedMessage.get("chestType")=="add"){
            this.addMoney(Integer.parseInt(String.valueOf(returnedMessage.get("chestAmount"))));
          }else{
            this.pay(Integer.parseInt(String.valueOf(returnedMessage.get("chestAmount"))));
          }
        }
      }else if(returnedMessage.get("positionType")=="property"){
        squareInfo.put("name", String.valueOf(returnedMessage.get("name")));
        squareInfo.put("ownership", String.valueOf(returnedMessage.get("ownership")));
          if(returnedMessage.get("ownership")=="owned"){
            squareInfo.put("rent", String.valueOf(returnedMessage.get("rent")));
            popUp = new CreatePopUp(squareInfo);
            int rent= Integer.parseInt(String.valueOf(returnedMessage.get("rent")));//get rent amount from JSON
            this.pay(rent);
          }else{ //vacant
            squareInfo.put("price", String.valueOf(returnedMessage.get("price")));
            popUp = new CreatePopUp(squareInfo);
            Property property=new Property(String.valueOf(returnedMessage.get("positionType")), String.valueOf(returnedMessage.get("name")), String.valueOf(returnedMessage.get("colour")), Integer.parseInt(String.valueOf(returnedMessage.get("price"))), Integer.parseInt(String.valueOf(returnedMessage.get("baseRent"))), Integer.parseInt(String.valueOf(returnedMessage.get("houseCost"))));
            this.optionToBuy(property);
          }
      }else if(returnedMessage.get("positionType")=="transport"){
        squareInfo.put("name", String.valueOf(returnedMessage.get("name")));
        squareInfo.put("ownership", String.valueOf(returnedMessage.get("ownership")));
        if(returnedMessage.get("ownership")=="owned"){
          squareInfo.put("rent", String.valueOf(returnedMessage.get("rent")));
          popUp = new CreatePopUp(squareInfo);
          int rent= Integer.parseInt(String.valueOf(returnedMessage.get("rent")));//GET RENT FROM JSON
          this.pay(rent);
        }else{
          squareInfo.put("price", String.valueOf(returnedMessage.get("price")));
          popUp = new CreatePopUp(squareInfo);
          Property property=new Property(String.valueOf(returnedMessage.get("positionType")), String.valueOf(returnedMessage.get("name")), "null", Integer.parseInt(String.valueOf(returnedMessage.get("price"))), Integer.parseInt(String.valueOf(returnedMessage.get("baseRent"))), 0);
          this.optionToBuy(property);
        }
      }else if(returnedMessage.get("positionType")=="utilities"){
        squareInfo.put("name", String.valueOf(returnedMessage.get("name")));
        squareInfo.put("ownership", String.valueOf(returnedMessage.get("ownership")));
        if(returnedMessage.get("ownership")=="owned"){
          squareInfo.put("rent", String.valueOf(returnedMessage.get("rent")));
          popUp = new CreatePopUp(squareInfo);
          int rent= Integer.parseInt(String.valueOf(returnedMessage.get("rent")));//GET RENT FROM JSON 
          this.pay(rent);
        }else{
          squareInfo.put("price", String.valueOf(returnedMessage.get("price")));
          popUp = new CreatePopUp(squareInfo);
          Property property=new Property(String.valueOf(returnedMessage.get("positionType")), String.valueOf(returnedMessage.get("name")), "null", Integer.parseInt(String.valueOf(returnedMessage.get("price"))), Integer.parseInt(String.valueOf(returnedMessage.get("baseRent"))), 0);
          this.optionToBuy(property);
        }
      }else if(returnedMessage.get("positionType")=="taxes"){
        squareInfo.put("amount", String.valueOf(returnedMessage.get("taxAmount")));
        popUp = new CreatePopUp(squareInfo);
        this.pay(Integer.parseInt(String.valueOf(returnedMessage.get("taxAmount"))));
      }else if(returnedMessage.get("positionType")=="chance"){
        squareInfo.put("chanceType", String.valueOf(returnedMessage.get("chanceType")));
        squareInfo.put("message", String.valueOf(returnedMessage.get("message")));
        if(returnedMessage.get("chanceType")=="jail"){
          squareInfo.put("jailType", String.valueOf(returnedMessage.get("jailType")));
          popUp = new CreatePopUp(squareInfo);
          if(returnedMessage.get("jailType")=="out"){
            squareInfo.put("jailType", String.valueOf(returnedMessage.get("jailType")));
            popUp = new CreatePopUp(squareInfo);
            jail_free++;
          }else{
            this.setPosition(jailPosition);
          }
        }else{ //go to position...
          //String messageToDisplay= String.valueOf(returnedMessage.get("message")); //display on GUI
          squareInfo.put("chancePosition", String.valueOf(returnedMessage.get("chancePosition")));
          popUp = new CreatePopUp(squareInfo);
          int prevPosition=this.getPosition();
          int positionToBeSet=Integer.valueOf(String.valueOf(returnedMessage.get("chancePosition")));
          this.setPosition(positionToBeSet);
          int currentPosition=this.getPosition();
          if(currentPosition-prevPosition<0||currentPosition<prevPosition){
            this.addMoney(goAmount);
          }
        }
      }
      if(diceOne==diceTwo){//rolled doubles
        noOfDoubles++;
        if(noOfDoubles==3){
          this.goToJail();
        }else{
          if(prevInJail==false){
            this.myTurn();
          }else{
            this.prevInJail=false;
          }
        }
      }else{
        noOfDoubles=0;
      }
    }
  }

  /**
  * Builds house on property in parameter
  * @param Property property
  */
  public void build(Property property){
    if(property.getType()=="site"){
      if(this.getMoney()<property.getHouseCost()){
        System.out.println("You do not have enough money");
      }else if(coloursOwned.get(property.getColour())!=coloursTotal.get(property.getColour())){
        System.out.println("You do not own all of the colour "+property.getColour()+" sites.");
      }else if(property.getNumOfHouses()==5){ //you have already built max amount
        System.out.println("You have built the maximum amount.");
      }else{
        this.sendMessageToServer(this.getId(), "incNumOfHouses", String.valueOf(property.getId()));
        this.subMoney(property.incNumOfHouses());
      }
    }
  }

  /**
  * Sets client's position to jail.
  */
  public void goToJail(){
    this.setPosition(jailPosition);
    this.inJail=true;
  }

  /**
  * Moves client's position to specified position number
  * @param integer diceOne, integer diceTwo
  */
  public void moveToPosition(int diceOne, int diceTwo){
    int prevPosition=this.getPosition();
    this.addPosition(diceOne+diceTwo);
    if (this.getPosition()<prevPosition){ //passed Go
      this.addMoney(200);
    }
  }

  /**
  * Gives client the option to buy property, checks whether it has the money, records related information.
  * @param Property property
  */
  public void optionToBuy(Property property){
    //DISPLAY POP UP WINDOW OF CARD DETAILS
    String answer="yes"; //LINK WITH GUI FUNCTION OF BUTTON PRESS
    if(answer=="yes"){
      //CLOSE POP UP
      if(this.getMoney()>property.getPrice()){//you can buy
        this.buyProperty(this.getPosition(), property.getPrice());
        int number=coloursOwned.get(property.getColour());
        String colour=property.getColour();
        number++;
        coloursOwned.put(colour, number);
        this.properties.put(this.getPosition(), property);//STORE PROPERTY OBJECT IN HASHMAP USING position as ID
        property.setId(this.getPosition());
      }
    }
    else{
        //CLOSE POP UP
    }
  }

  /**
  * Allows client to pay a specified amount of money. Allows checks to see if the client is in debt after the payment and allows the client to pay off its debt.
  * @param integer amount
  */
  public void pay(int amount){
    this.subMoney(amount);
    if(this.getMoney()<0){
      int selectedPropertyId= 1; //GUI GIVES SELECTED PROPERTY'S ID
      Property selectedProperty=properties.get(selectedPropertyId);//select property from GUI cards
      if(selectedProperty.getNumOfHouses()>0){
        this.addMoney(this.sellHouse(selectedProperty));
      }else{
        this.addMoney(this.sellSite(selectedProperty));
      }
    }
  }

  /**
  * Getter method for getting the cost of the property.
  * @return integer
  * @param integer position
  */
  public int getCostOfProperty(int position){
    return properties.get(position).getPrice();
  }

  /**
  * Allows the client to sell houses on a specified property.
  * @param Property property
  * @return integer money gained
  */
  public int sellHouse(Property property){
    this.sendMessageToServer(this.getId(), "decNumOfHouses", String.valueOf(property.getId()));
    return property.decNumOfHouses();
  }

  /**
  * Allows the client to sell a site.
  * @param Property property
  * @return integer money gained
  */
  public int sellSite(Property property){
    //auction
    this.sendMessageToServer(this.getId(), "sell", String.valueOf(property.getId())); //sends the id of the property to the server
    return 0; //amount
  }

  //public void bid

  /**
  * Allows the client buy property.
  * @param integer property's id, integer cost
  */
  public void buyProperty(int property_ID, int cost){
    this.subMoney(cost);
    this.sendMessageToServer(this.getId(), "buy", String.valueOf(this.getPosition()));
  }

  /**
  * Allows the client to roll dice.
  * @return integer number dice landed on
  */
  public int rollDice(){
    Random randomGenerator = new Random();
    int randomInt = randomGenerator.nextInt(6);
    return randomInt+1;
  }

  /**
  * Encodes message in a JSON object and sends it to the server via a Java socket. Decodes the reply message from the server and returns it.
  * @param integer client id, String  messageType, String sendMessage
  * @return JSONObject reply from server
  */
  public JSONObject sendMessageToServer(int id, String messageType, String sendMessage){
    JSONObject messageObj=new JSONObject();
    try {
      JSONObject jsonMessage = new JSONObject();
      jsonMessage.put("id",new Integer(id));
      jsonMessage.put("messageType",messageType);
      jsonMessage.put("message", sendMessage);

      //Send the message to the server
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      StringWriter out = new StringWriter();
      jsonMessage.writeJSONString(out);
      String jsonText = out.toString();

      bw.write(jsonText);
      bw.newLine();
      bw.flush();
      System.out.println("Message sent to the server : "+jsonMessage);

      //Get the return message from the server
      BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String message = br.readLine();
      messageObj=decode(message);
      if (messageType.equals("position")) {
        while (!String.valueOf(messageObj.get("messageType")).equals("position")) {
          bw.write(jsonText);
          bw.newLine();
          bw.flush();

          message = br.readLine();
          messageObj=decode(message);
        }
      } else if (messageType.equals("rentDue")) {
        while (!String.valueOf(messageObj.get("messageType")).equals("rent")) {
          bw.write(jsonText);
          bw.newLine();
          bw.flush();

          message = br.readLine();
          messageObj=decode(message);
        }
        
      }
      System.out.println("Message received from the server : " +message);

      return messageObj; //answer
    }catch (IOException exception){
      exception.printStackTrace();
    }
    return messageObj; //answer
  }

  /**
  * Client's sends bye message to server and doesn't wait for a response.
  * @param integer client id, String messageType, String sendMessage
  */
  public void sendByeMessage(){
	int id=this.getId();
	String messageType="Bye";
	String sendMessage="Bye";
	
    JSONObject messageObj=new JSONObject();
    try {
      JSONObject jsonMessage = new JSONObject();
      jsonMessage.put("id",new Integer(id));
      jsonMessage.put("messageType",messageType);
      jsonMessage.put("message", sendMessage);

      //Send the message to the server
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      StringWriter out = new StringWriter();
      jsonMessage.writeJSONString(out);
      String jsonText = out.toString();
      bw.write(jsonText);
      bw.newLine();
      bw.flush();
      System.out.println("Message sent to the server : "+jsonMessage);
    }catch (IOException exception){
      exception.printStackTrace();
    }
  }

  /**
  * Decodes server's reply into a JSON object
  * @param String message
  * @return JSONObject
  */
  public JSONObject decode(String message){
    JSONParser parser = new JSONParser();
    JSONObject obj2=new JSONObject();
    try{
      Object obj = parser.parse(message);
      obj2 = (JSONObject)obj;
      return obj2;
    }catch(Exception pe){
      System.out.println(pe);
    }  
    return obj2;
  }

  /**
  * Setter method for number of players
  * @param integer number
  */
  public void setNumberOfPlayers(int number){
    this.setNumberOfPlayers=number;
  }

  /**
  * Getter method for number of players
  * @return integer number of players
  */
  public int getNumberOfPlayers(){
    return this.setNumberOfPlayers;
  }

  /**
  * Learns from the server how many players there are and sets all of their position values in the hashmap as the startPosition.
  */
  public void makeListOfPlayers(){
    try {
      JSONObject message = this.sendMessageToServer(this.getId(), "noOfPlayers", "noOfPlayers");
      int noOfPlayers=Integer.valueOf(String.valueOf(message.get("number")));
      this.setNumberOfPlayers(noOfPlayers);
    } catch (Exception e) {
      System.out.println("Failed to get number of players: " + e);
    }
    for(int i=0;i<this.getNumberOfPlayers();i++){
      playersPositions.put(i,startPosition);
    }
  }

  /**
  * Updates the player positions.
  */
  public void updatePlayersPositions(){
    try {
      JSONObject obj=this.sendMessageToServer(this.getId(), "playersPositions", "playersPositions");
      for(int i=0;i<this.getNumberOfPlayers();i++){
        int playerPosition= Integer.valueOf(String.valueOf(obj.get(String.valueOf(i))));
        playersPositions.put(i,playerPosition);
      }
    } catch (Exception e) {
      System.out.println("Failed to get updated player positions: " + e);
    }
  }

  /**
  * Sends a message to the server checking a certain condition.
  * @param String equals, Socket socket
  * @return boolean
  */
  public boolean checkWithServer(String equals, Socket socket){
    try{
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
        String message = br.readLine();
        System.out.println("message: "+message);
        JSONObject messageObj=decode(message);
        if(messageObj.get("messageType").equals(equals)){
          return true;
        }else{
          return false;
        }
    }catch(Exception e){
      return false;
    }
  }
  
  public String getUserName(){
	return this.username;
  }

  public static void main(String[] args)throws IOException{
    try{
      String host = "localhost";
      int port = portNumber;
      InetAddress address = InetAddress.getByName(host);
      socket = new Socket(address, port);
    }catch (Exception exception) {
      exception.printStackTrace();
    }
    Client client= new Client();
	  System.out.println("I done got created ");
	JFrame myFrame = new JFrame("Welcome to Zebropoly!"); // Create new JFrame with specified name
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specifies that the application must exit when window is closed
        WelcomeScreen welcomeScreen = new WelcomeScreen(myFrame); // Create instance of WelcomeScreen 
        welcomeScreen.setOpaque(true); // Makes contentPane opaque 
        myFrame.setContentPane(welcomeScreen); // Sets contentPane property
	myFrame.getContentPane().setBackground(Color.black);
        myFrame.setSize(550, 500);
	myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true); // Window is displayed
	  System.out.println("frame done got made ");
	while (welcomeScreen.getUsername().equals("")){System.out.println("LOOP-DEE-LOOP ");}
	  System.out.println("while loop done got exited ");
	client.setUsername(welcomeScreen.getUsername());
	  System.out.println(" username gone done gotten and set hopefree");
	System.out.println("Username: "+client.getUserName());
	boolean start=false;
	String messageType="firstContact";
        System.out.println(client.getUserName()+" sending firstContact");
        client.firstContactServer(messageType);
	while(!start){
		System.out.println("I is in loop yass");
		  if(client.checkWithServer("start", socket)){
			  System.out.println("hello laurentttt");
			welcomeScreen.closeScreen();
			//create board
			start=true;
			  System.out.println("YASSS we startin!");
		  }else if(welcomeScreen.getForceStart()){
			client.sendMessageToServer(client.getId(), "forceStart", "forceStart");
			//create board
			start=true;
			  System.out.println("YASS we force startin");
		  }
	}
    Frame frame = new Frame();
    frame.setClientId(client.getId());
    System.out.println(client.getUserName()+" sent firstContact");
    System.out.println("My new ID: "+client.getId());
    System.out.println(client.getUserName()+" received 'game has started' from server");
    //chatBox=new AuctionChat();
    //chatBox.main();
    while(!closed){
      //display info on GUI
      if(client.checkWithServer("yourTurn", socket)){ //my turn
	  if (!getNumPlayers) {
	    client.makeListOfPlayers();
	    getNumPlayers = true;
	  }
	  possibleRent = client.sendMessageToServer(client.getId(), "rentDue", "rentDue");
	  client.addMoney(Integer.valueOf(String.valueOf(possibleRent.get("rent"))));
	  //client.updatePlayersPositions();//get updated info of positions from server
	  client.myTurn();
	  client.sendByeMessage();	
      } //else {
	  //client.updatePlayersPositions();//get updated info of positions from server
      //}
    }
  }
}
