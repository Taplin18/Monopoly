import java.util.*;

class Property {

	protected int rent;
	protected int price;
	protected int property_group;
	protected boolean bought = false;
	protected int ownerID;
	protected int houses = 0;
	protected int hotel = 0;
	
	/**
	* Initilise the property
	*/
	public Property(int price, int rent, int property_group) {
		this.rent = rent;
		this.price = price;
		this.property_group = property_group;
	}

	/**
	* Player buys the property
	* @param player_id the new owners id
	*/
	public void buy_property(int player_id) {
		bought = true;
		ownerID = player_id;
	}

	/**
	* Return the id of the owner
	* @return the id of owner
	*/
	public int owner() {
		return ownerID;
	}

	/**
	* Return the cost of the property
	* @return the price
	*/
	public int cost() {
		return price;
	}

	/**
	* Check if the property has been bought
	* @return true if bought, false otherwise
	*/
	public boolean available() {
		return bought;
	}

	/**
	* Build a house to increase the rent
	*/
	public void buildHouse() {
		houses++;
	}

	/**
	* Build a hotel to increase the rent
	*/
	public void buildHotel() {
		hotel++;
	}
}