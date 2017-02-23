//create property object for each property owned with all details
//keeping track of everyone's position

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Scanner;

import java.util.Random;

import org.json.simple.JSONObject;

public class Client implement Runnable{
  
  private int money;
  private int id;
  private int[] sitesOwned;
  private int position;
  
  private static int startMoney=2000;
  private static int noOfSites=20;
  
  private int siteArrayPointer=0;
  
  private int defaultStartingId=100;
  
  private static int portNumber=2222;
  private static int positionPortNumber=0;
  
  private static startPosition=0;
  
  private Set <Integer> properties = new HashSet<Integer>();
  private int jail_free = 0;
  
  private int turnsInJail=0;
  private boolean inJail=false;
  private int daysInJail=0;
  private int posOfJail=20;
  
  private boolean prevInJail=false; //at the beginning of the turn client was in jail (nulls second turn on double)
  
  private static int goAmount=2000;
  
  private String userName;
  
  private int noOfDoubles=0;
  
  public Client(){
    id=defaultStartingId;
    sitesOwned=[noOfSites];
    money=startMoney;
    position=startPosition;
    userName=this.userName();
  }
  
  public String userName(){
    System.out.println("Enter your username: ");
    Scanner scanner = new Scanner(System.in);
    String username = scanner.nextLine();
    while(username==""){
      System.out.println("Enter your username: ");
      Scanner scanner = new Scanner(System.in);
      String username = scanner.nextLine();
    }
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
  
  public void firstContactServer(){
    int newId=Integer.parseInt(this.sendMessageToServer(this.getId(), "firstContact", this.getUserName()));
    this.setId(newId); //gets ID
  }
    
  public void myTurn(){
    //roll dice
    int diceOne=this.rollDice();
    int diceTwo=this.rollDice();
    
    if(inJail==true && diceOne!=diceTwo){ //in jail and didnt roll double
      daysInJail++;
      if(InJail==3){
	this.inJail=false;
	this.daysInJail=0;
      }
    }else{ //not in jail or in jail but has rolled double
      if(inJail==true && diceOne==diceTwo){ //in jail and rolled double
	this.inJail=false;
	this.daysInJail=0;
	this.prevInJail=true
      }
      this.moveToPosition(diceOne, diceTwo);//move to position
      //check with server of position
      JSONArray returnedMessage=this.sendMessageToServer(this.getId(), "position", this.getPosition());
      if(returnedMessage.get(positionType)=="chest"){
	if(returnedMessage.get(chestType)="jail"){
	  if(returnedMessage.get(jailType)=="out"){
	    this.jail_free++;
	  }else{
	    this.setPosition(jailPosition);
	  }
	}else{ //money
	  if(returnedMessage.get(chestType)=="add"){
	    this.addMoney(returnedMessage.get(chestAmount));
	  }else{
	    this.pay(returnedMessage.get(chestAmount));
	  }
	}
      }else if(returnedMessage.get(positionType)=="property"){
	if(returnedMessage.get(ownership)=="owned"){
	  int rent= returnedMessage.get(rent);//get rent amount from JSON
	  this.pay(rent);
	}else{ //vacant
	  Property property=new Property(returnedMessage.get(name), returnedMessage.get(colour), returnedMessage.get(price), returnedMessage.get(rentArray), returnedMessage.get(houseCost), returnedMessage.get(hotelCost));
	  this.optionToBuy(property);
	}
      }else if(returnedMessage.get(positionType)=="transport"){
	if(returnedMessage.get(ownership)=="owned"){
	  int rent= returnedMessage.get(rent);//GET RENT FROM JSON
	  this.pay(rent);
	}else{
	  int cost = returnedMessage.get(price);
	  this.optionToBuy(//PROPERTY OBJECT);
	}
      }else if(returnedMessage.get(positionType)=="utilities"){
	if(returnedMessage.get(ownership)=="owned"){
	  int rent= returnedMessage.get(rent);//GET RENT FROM JSON 
	  this.pay(rent);
	}else{
	  int cost = returnedMessage.get(price);
	  this.optionToBuy(//PROPERTY OBJECT);
	}
      }else if(returnedMessage.get(positionType)=="taxes"){
	this.pay(returnMessage.get(taxAmount));
      }else if(returnedMessage.get(positionType)=="chance"){
	if(returnedMessage.get(chestType)="jail"){
	  if(returnedMessage.get(jailType)=="out"){
	    jail_free++;
	  }else{
	    this.setPosition(jailPosition);
	  }
	}else{ //go to position...
	  int prevPosition=this.getPosition();
	  this.setPosition(returnedMessage.get(chancePosition));
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
  
  public void goToJail(){
    this.setPosition(posOfJail);
    this.inJail=true;
  }
  
  public void moveToPosition(int diceOne, int diceTwo){
    int prevPosition=this.getPosition();
    this.addPosition(diceOne+diceTwo);
    if (this.getPosition<prevPosition){ //passed Go
      this.addMoney(200);
    }
  }
  
  public void optionToBuy(Property property){
    //DISPLAY POP UP WINDOW OF CARD DETAILS
    if("yes"){
      if(this.getMoney()>property.getPrice()){//you can buy
	this.buyProperty(this.getPosition(), property.getprice());
	//STORE PROPERTY OBJECT IN HASHMAP
      }
    }
  }
  
  public void pay(int amount){
    this.subMoney(amount);
    if(this.getMoney()<0){
      this.sellProperties();
    }
  }
  
  public int getCostOfProperty(int position){
    return cost;
  }
  
  public void sellProperties(){
    while(this.getMoney()<0){
      //sell a house
      //contact server
    }
  }
  
  public void bid
  
  public void buyProperty(int property_ID, int cost) {
    properties.add(property_ID);
    this.subMoney(cost);
    //send to server
  }
    
  public int rollDice(){
    Random randomGenerator = new Random();
    int randomInt = randomGenerator.nextInt(6);
    return randomInt+1;
  }
    
  public JSONArray sendMessageToServer(int id, String messageType, int sendMessage){
    private static Socket socket;
    try {
      JSONObject jsonMessage = new JSONObject();
      jsonMessage.put("id",new Integer(id));
      jsonMessage.put("messageType",messageType);
      jsonMessage.put("message",new Integer(message));
    
      String host = "localhost";
      int port = portNumber;
      InetAddress address = InetAddress.getByName(host);
      socket = new Socket(address, port);
  
      //Send the message to the server
      OutputStream os = socket.getOutputStream();
      OutputStreamWriter osw = new OutputStreamWriter(os);
      BufferedWriter bw = new BufferedWriter(osw);
      
      StringWriter out = new StringWriter();
      jsonMessage.writeJSONString(out);
      String jsonText = out.toString();
      
      bw.write(jsonText);
      bw.flush();
      System.out.println("Message sent to the server : "+jsonMessage);
      
      new Thread(new Client()).start();
      while(!closed){
	inputline.readLine().trim();
      }
      
      //Get the return message from the server
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String message = br.readLine();
      System.out.println("Message received from the server : " +message);
      
      return message; //answer
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
    finally
    {
    //Closing the socket
      try
      {
	socket.close();
      }
      catch(Exception e)
      {
	e.printStackTrace();
      }
    }
  }
  
  public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
    String responseLine;
    try {
      while ((responseLine = br.readLine()) != null) {
        System.out.println(responseLine);
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
  
  public static main(String[] args){
    new Thread(new Client()).start();
      while(!closed){
	client.firstContactServer();
	client.myTurn();
      }
  }
}