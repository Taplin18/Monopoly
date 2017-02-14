import java.util.*;

class Player {

	/**
	* 
	* This class represents the player.
	*
	* player_ID - the ID of the player.
	* money - current amount the player has.
	* position - player's position on the board.
	* properties - property player owns.
	* jail_free - the number of get out of jail free cards.
	*
	*/

	private static int player_ID = 0;
	private int money = 2000;
	private int position = 0;
	private Set <Integer> properties = new HashSet<Integer>();
	private int jail_free = 0;

	/**
	* Create new Player.
	*/
	public Player() {
		player_ID += 1;
	}

	/**
	* Buy a piece of property.
	* @param property_ID the id of the property being bought.
	* @param cost the cost of the property being bought.
 	*/
	public void buyPropery(int property_ID, int cost) {
		properties.add(property_ID);
		money -= cost;
	}

	/**
	* Sell a piece of property.
	* @param property_ID the id of the property being sold.
	* @param cost the cost of the property being sold.
	*/
	public void sellProperty(int property_ID, int cost) {
		properties.remove(property_ID);
		money += cost;
	}

	/**
	* Store a Get Out Of Jail Free card.
	*/
	public void addJailFreeCard() {
		jail_free += 1;
	}

	/**
	* Use a Get Out Of Jail Free card.
	*/
	public void useJailFreeCard() {
		jail_free -= 1;
	}

	public String toString() {
		return "Player " + player_ID + "\n";
	}
}