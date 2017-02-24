public class Property{

  private String name; 
  private String colour;
  private int baseRent;
  private int rent;
  private int houseCost;
  private int hotelCost;
  private int numOfHouses;
  //private boolean hotelPresent;
  private int price;
  private int id;

  public Property(String name, String colour, int price, int baseRent, int houseCost, int hotelCost){ //rent[0]: zero houses, rent[1]: one house... rent[4]: four houses, rent[5]: hotel
    this.name=name;
    this.colour=colour;
    this.price=price;
    this.baseRent=baseRent;
    this.rent=baseRent;
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
    this.setRent(this.getNumOfHouses());
    if(this.getNumOfHouses()==5){
      return hotelCost;
    }else{
      return houseCost;
    }
  }
  
  public int decNumOfHouses(){ //returns half cost to buy house/hotel as sold price
    this.numOfHouses=this.numOfHouses-1;
    this.setRent(this.getNumOfHouses());
    if(this.getNumOfHouses()==4){
      return hotelCost/2;
    }else{
      return houseCost/2;
    }
  }
  
  public void setNumOfHouse(int number){
    this.numOfHouses=0;
  }
  
  //public void setHotelPresent(boolean present){
  //  this.hotelPresent=present;
  //}
  
  //public boolean getHotelPresent(){
  //  return hotelPresent;
  //}

  public void setRent(int numOfHouses){
    this.rent=(baseRent*this.getNumOfHouses())+baseRent;
  }
  
  public int getRent(){
    return this.rent;
  }
  
  public String getColour(){
    return this.colour;
  }
  
  public String getName(){
    return this.name;
  }
  
  public int getPrice(){
    return this.price;
  }
  
  public int getHouseCost(){
    return houseCost;
  }
  
}