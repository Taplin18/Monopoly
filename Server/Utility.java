import java.util.*;

class Utility {

	protected String name;
	protected int rent;
	protected int price;
	protected boolean bought = false;
	protected int ownerID = -1;
	
	/**
	* Initilise the railroad
	*/
	public Utility(String name, int price) {
		this.name = name;
		this.price = price;
	}

	/**
	* Player buys the utility
	* @param player_id the new owners id
	*/
	public void buy_utility(int player_id) {
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
	* Return the cost of the utility
	* @return the price
	*/
	public int cost() {
		return price;
	}

	/**
	* Return the name of the utility
	* @return the name
	*/
	public String name() {
		return name;
	}

	/**
	* Return the rent of the utility
	* @return the rent
	*/
	public int rent() {
		return rent;
	}

	/**
	* Check if the utility has been bought
	* @return true if bought, false otherwise
	*/
	public boolean available() {
		return bought;
	}
}