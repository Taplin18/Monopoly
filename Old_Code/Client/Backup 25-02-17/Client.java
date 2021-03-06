import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Scanner;

import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.*; 

public class Client{
  public String message; //answer
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
  private boolean prevInJail=false; //at the beginning of the turn client was in jail (nulls second turn on double)
  private static int goAmount=2000;
  private static int startPosition=0;
  private String userName;
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
  private Map <String, String> squareInfo;
  
  public int getDiceOne(){
    return this.diceOne;
  }
  
  public int getDiceTwo(){
    return this.diceTwo;
  }
  
  public void setDiceNumber(int diceOne, int diceTwo){
    this.diceTwo=diceTwo;
    this.diceOne=diceOne;
  }
  
  public Client(){
    id=defaultStartingId;
    money=startMoney;
    position=startPosition;
    this.userName();
    
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
  
  public void userName(){
    System.out.print("Enter your username: ");
    Scanner scanner = new Scanner(System.in);
    String username = scanner.nextLine();
    while(username.equals("")){
      System.out.print("Enter your username: ");
      username = scanner.nextLine();
    }
    userName = username;
    System.out.println("Your username is " + username);
  }
  
  public String getUserName(){
    return this.userName;
  }
  
  public int getMoney(){
    return money;
  }
  
  public int[] getSitesOwned(){
    return sitesOwned;
  }
  
  public int getId(){
    return id;
  }
  
  public void setMoney(int amount){
    money=amount;
  }
  
  public void addMoney(int amount){
    money=money+amount;
  }
  
  public void subMoney(int amount){
    money=money-amount;
  }
  
  public void addSite(int idOfSite){ //server has array of all Sites that corrosponds with this id
    sitesOwned[siteArrayPointer]=idOfSite;
  }
  
  public void setId(int number){
    id=number;
  }
  
  public void addPosition(int number){
    position=position+number;
  }
  
  public int getPosition(){
    return position;
  }
  
  public void setPosition(int number){
    position=number;
  }
  
  public void firstContactServer(String messageType){
    try {
      JSONObject obj = this.sendMessageToServer(this.getId(), messageType, this.getUserName());
      String mess = String.valueOf(obj.get("id"));
      int newId = Integer.valueOf(mess);
      this.setId(newId); //gets ID
    } catch (Exception e) {
      System.out.println("Failed to get new ID: " + e);
    }
  }
    
  public void myTurn(){
    System.out.println(this.getUserName()+": Start turn.");
    if(jail_free>0 || inJail==true){ //you have a get out of jail free card
	boolean decision=true;//pop up window asking if user wants to use his get out of jail free card
	if(decision){
	   this.inJail=false;
	   jail_free--;
	   this.daysInJail=0;
        }
    }
    //roll dice
    diceOne=this.rollDice();
    diceTwo=this.rollDice();
    this.setDiceNumber(diceOne, diceTwo);
    
    System.out.println(this.getUserName()+": rolled = " + diceOne + " and "+ diceTwo);
    
    if(this.inJail==true && diceOne!=diceTwo){ //in jail and didnt roll double
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
      
      //HashMap<String, String> returnedMessage = new HashMap<String, String>();
      //returnedMessage.put("positionType", "chest");
     // returnedMessage.put("chestType", "jail");
     // returnedMessage.put("jailType", "out");
     // returnedMessage.put("rent", "1");
      
      System.out.println(this.getUserName()+": Landed on positionType "+returnedMessage.get("positionType")); 
      squareInfo = new HashMap<String, String>();
      squareInfo.put("positionType", String.valueOf(returnedMessage.get("positionType")));
      squareInfo.put("picture", String.valueOf(returnedMessage.get("picture")));
      if(returnedMessage.get("positionType")=="chest"){
	squareInfo.put("chestType", String.valueOf(returnedMessage.get("chestType")));
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
	squareInfo.put("chestType", String.valueOf(returnedMessage.get("chestType")));
	if(returnedMessage.get("chestType")=="jail"){
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
  
  public void build(Property property){
    if(property.getType()=="site"){
      if(this.getMoney()<property.getHouseCost()){
        System.out.println("You do not have enough money");
      }else if(coloursOwned.get(property.getColour())!=coloursTotal.get(property.getColour())){
        System.out.println("You do not own all of the colour "+property.getColour()+" sites.");
      }else if(property.getNumOfHouses()==5){ //you have aready built max amount
        System.out.println("You have built the maximum amount.");
      }else{//build hotel instead
        this.subMoney(property.incNumOfHouses());
      }
    }
  }
  
  public void goToJail(){
    this.setPosition(jailPosition);
    this.inJail=true;
  }
  
  public void moveToPosition(int diceOne, int diceTwo){
    int prevPosition=this.getPosition();
    this.addPosition(diceOne+diceTwo);
    if (this.getPosition()<prevPosition){ //passed Go
      this.addMoney(200);
    }
  }
  
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
  
  public int getCostOfProperty(int position){
    return properties.get(position).getPrice();
  }
  
  public int sellHouse(Property property){
    return property.decNumOfHouses();
  }
  
  public int sellSite(Property property){
    this.sendMessageToServer(this.getId(), "sell", String.valueOf(property.getId())); //sends the id of the property to the server
    return 0; //amount
  }
  
  //public void bid
  
  public void buyProperty(int property_ID, int cost){
    this.subMoney(cost);
    this.sendMessageToServer(this.getId(), "buy", String.valueOf(this.getPosition()));
  }
    
  public int rollDice(){
    Random randomGenerator = new Random();
    int randomInt = randomGenerator.nextInt(6);
    return randomInt+1;
  }
    
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
      while(br.ready()){
	String message = br.readLine();
	messageObj=decode(message);
      }
      System.out.println("Message received from the server : " +message);
      
      return messageObj; //answer
    }catch (IOException exception){
      exception.printStackTrace();
    }
    return messageObj; //answer
  }
  
  public JSONObject sendByeMessage(int id, String messageType, String sendMessage){
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
    return messageObj; //answer
  }
	  
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
  
  public void setNumberOfPlayers(int number){
    this.setNumberOfPlayers=number;
  }
  
  public int getNumberOfPlayers(){
    return this.setNumberOfPlayers;
  }
  
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
  
  public void updatePlayersPositions(){
    try {
      JSONObject obj=this.sendMessageToServer(this.getId(), "playersPositions", "playersPositions");
      for(int i=0;i<this.getNumberOfPlayers();i++){
        int playerPosition= Integer.valueOf(String.valueOf(obj.get(i)));
	playersPositions.put(i,playerPosition);
      }
      
    } catch (Exception e) {
      System.out.println("Failed to get updated player positions: " + e);
    }
  }
  
  public boolean checkWithServer(String equals, Socket socket){
    try{
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      //System.out.println("0");
      BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      //System.out.println("1");
      if(br.ready()){
	String message = br.readLine();
	System.out.println("message: "+message);
	System.out.println("2");
	JSONObject messageObj=decode(message);
	System.out.println("3");
	if(messageObj.get("messageType").equals("yourTurn")){
	  System.out.println("4");
	  return true;
	}else{
	  System.out.println("5");
	  return false;
	}
      }else{
	return false;
      }
    }catch(Exception e){
      return false;
    }
  }
  public static void main(String[] args)throws IOException{
    try{
      String host = "localhost";
      int port = portNumber;
      InetAddress address = InetAddress.getByName(host);
      socket = new Socket(address, port);
    }catch (Exception exception)
    {
      exception.printStackTrace();
    }
    Client client= new Client();
    
    String messageType="firstContact";
    System.out.println(client.getUserName()+" sending firstContact");
    client.firstContactServer(messageType);
    System.out.println(client.getUserName()+" sent firstContact");
    System.out.println("My new ID: "+client.getId());
    boolean forceStart=true; // button
    //while(client.checkWithServer("start", socket)){
    //  if(forceStart==true){ //forceStart button pressed
    //    messageType="start";  
    //  }
    //}
    System.out.println(client.getUserName()+" received 'game has started' from server");
    //client.makeListOfPlayers();
    while(!closed){
      //client.updatePlayersPositions();//get updated info of positions from server
      //display info on GUI
      if(client.checkWithServer("yourTurn", socket)){
	client.myTurn();
	client.sendByeMessage(client.getId(),"Bye","Bye");
      }
    }
  }
}
