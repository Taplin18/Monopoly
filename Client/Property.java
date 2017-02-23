public class Property{

  private String name; 
  private String colour;
  private int[] rentArray;
  private int rent;
  private int houseCost;
  private int hotelCost;
  private int numOfHouses;
  
  private int id;

  public Property(String name, String colour, int price, int[] rentArray, int houseCost, int hotelCost){ //rent[0]: zero houses, rent[1]: one house... rent[4]: four houses, rent[5]: hotel
    this.name=name;
    this.colour=colour;
    this.price=price;
    this.rentArray=rentArray;
    this.rent=rentArray[0];
    this.houseCost=houseCost;
    this.hotelCost=hotelCost;
  }
  
  public int getNumOfHouses(){
    return this.numOfHouses;
  }
  
  public void setId(int position){
    this.id=position;
  }
  
  public int getId(){
    return this.id;
  }
  
  public int incNumOfHouses(){ //returns cost
    this.numOfHouses=this.numOfHouses+1;
    this.setRent(this.getNumOfHouses);
    if(this.getNumOfHouses()==5){
      return hotelCost;
    }else{
      return houseCost;
    }
  }
  
  public int decNumOfHouses(){ //returns half cost to buy house/hotel as sold price
    this.numOfHOuses=this.numOfHouses-1;
    this.setRent(this.getNumOfHouses);
    if(this.getNumOfHouses()==4){
      return hotelCost/2;
    }else{
      return houseCost/2;
    }
  }
  
  public void setRent(int numOfHouses){
    this.rent=rentArray[numOfHouses];
  }
  
  public int getRent(){
    return this.rent;
  }
  
  public int getColour(){
    return this.colour;
  }
  
  public int getName(){
    return this.name;
  }
  
  public int getPrice(){
    return this.price;
  }
  
}