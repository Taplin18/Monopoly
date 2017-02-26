import java.util.*;

class Transport {

	protected String name;
	protected int rent;
	protected int price;
	protected boolean bought = false;
	protected int ownerID = -1;
	
	/**
	* Initilise the railroad
	*/
	public Transport(String name, int price, int rent) {
		this.name = name;
		this.rent = rent;
		this.price = price;
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