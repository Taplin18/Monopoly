import java.util.*;

class Transport {

	protected String name;
	protected int rent;
	protected int price;
	protected boolean bought = false;
	protected int ownerID = -1;
	protected String picture;
	
	/**
	* Initilise the railroad
	* @param name The name of the transport
	* @param price The cost of the transport to buy
	* @param rent The rent charged to players when they don't own it
	*/
	public Transport(String name, int price, int rent, String picture) {
		this.name = name;
		this.rent = rent;
		this.price = price;
		this.picture = picture;
	}

	/**
	* Player buys the railroad
	* @param player_id the new owners id
	*/
	public void buy_transport(int player_id) {
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
	* Return the cost of the railroad
	* @return the price
	*/
	public int cost() {
		return price;
	}

	/**
	* Return the name of the railroad
	* @return the name
	*/
	public String name() {
		return name;
	}

	/**
	* Double the rent when player owns multiple railroads
	*/
	public void update_rent() {
		rent *= 2;
	}

	/**
	* Return the name of the transport's picture
	* @return the name of the picture
	*/
	public String picture() {
		return picture;
	}

	/**
	* Return the rent of the railroad
	* @return the rent
	*/
	public int rent() {
		return rent;
	}

	/**
	* Check if the railroad has been bought
	* @return true if bought, false otherwise
	*/
	public boolean available() {
		return bought;
	}
}