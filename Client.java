import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.util.Random;

import org.json.simple.JSONObject;

public class Client{
  
  private int money;
  private int id;
  private int[] sitesOwned;
  private int position;
  
  private static int startMoney=2000;
  private static int noOfSites=20;
  
  private int siteArrayPointer=0;
  
  private int defaultStartingId=100;
  
  private static int portNumber=25000;
  private static int positionPortNumber=0;
  
  private static startPosition=0;
  
  public Client(){
    id=defaultStartingId;
    sitesOwned=[noOfSites];
    money=startMoney;
    position=startPosition
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
  
  public void addMOney(int amount){
    money=money+amount;
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
  
  public void firstContactServer(){
    int newId=Integer.parseInt(this.sendMessageToServer(this.getId(), "firstContact", 0));
    this.setId(newId); //gets ID
  }
    
  public void myTurn(){
    //roll dice
    int diceOne=this.rollDice();
    int diceTwo=this.rollDice();
    //add number to position
    int prevPosition=this.getPosition;
    this.addPosition(diceOne+diceTwo);
    if (this.getPosition<prevPosition){ //passed G
      this.addMoney(200);
    }
      //check with server of position
    JSONArray returnedMessage=this.sendMessageToServer(this.getId(), "position", this.getPosition());
    if(returnedMessage.get(positionType)=="chest"){
    
    }else if(returnedMessage.get(positionType)=="property"){
    
    }else if(returnedMessage.get(positionType)=="transport"){
    
    }else if(returnedMessage.get(positionType)=="utilities"){
    
    }else if(returnedMessage.get(positionType)=="taxes"){
    
    }else if(returnedMessage.get(positionType)=="chance"){
    
    }
      //else if
      //else if
      
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
      bw.write(jsonMessage);
      bw.flush();
      System.out.println("Message sent to the server : "+jsonMessage);
  
      //Get the return message from the server
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String message = br.readLine();
      System.out.println("Message received from the server : " +message);
      
      return message;
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
}