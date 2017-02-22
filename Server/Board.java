import java.util.*;

class Board {

	/**
	*
	* num_properties - the number of squares on the board.
	* chest - the positions of the community chest squares.
	* chance - the positions of the chance squares.
	* corners - the positions of the corner squares.
	* transport - the positions of the transportation squares.
	* utilities - the position of the untility squares.
	* taxes - the position of the tax squares.
	* properties - the positions as keys and values [cost, rent, grouping]
	* bought_properties - property id as key and owner id as value
	*
	*/

	protected int num_properties = 40;
	protected int[] chest = {2, 17, 33};
	protected int[] chance = {7, 22, 36};
	protected int[] corners = {0, 10, 20, 30};
	protected int[] transport = {5, 15, 25, 35};
	protected int[] utilities = {12, 28};
	protected int[] taxes = {4, 38};
	protected int[] properties = {1, 3, 6, 8, 
		9, 11, 13, 14, 
		16, 18, 19, 21, 
		23, 24, 26, 27, 
		29, 31, 32, 34, 
		37, 39};
	protected List <Integer> bought_properties = new ArrayList <Integer>();
	protected Square squares;


	/**
	* Initilise the board
	*/
	public Board() {
		squares = new Square();
	}

	/**
	* Buy the property
	* @param property the id of the property to be bought
	* @param player_id the id of the player who is buying it
	*/
	public void buy(int property, int player_id) {
		bought_properties.add(property);
		//squares.buy_property(property, player_id);
	}

	/**
	* Check what type of sqaure the player is on
	* @param position the players position on the board
	* @return card information
	*/
	public String check_square(int position) {
		for (int i = 0; i < chest.length; i++) {
			if (position == chest[i]) {
				return squares.get_card("Chest");
			}
		}

		for (int i = 0; i < chance.length; i++) {
			if (position == chance[i]) {
				return squares.get_card("Chance");
			}
		}

		for (int i = 0; i < transport.length; i++) {
			if (position == transport[i]) {
				if (check_if_bought(position)) {
					return String.format("Transport {%s} owned by {%s}", position, bought_properties.get(position));
				} else {
					return String.format("Transport {%s} costs €200", position);
				}
			}
		}

		for (int i = 0; i < utilities.length; i++) {
			if (position == utilities[i]) {
				if (check_if_bought(position)) {
					return String.format("Utiliity {%s} owned by {%s}", position, bought_properties.get(position));
				} else {
					return String.format("Utiliity {%s} costs €150", position);
				}
			}
		}

		for (int i = 0; i < corners.length; i++) {
			if (position == corners[i]) {
				return squares.get_card("Corners", position);
			}
		}

		for (int i = 0; i < taxes.length; i++) {
			if (position == taxes[i]) {
				return squares.get_card("Tax", position);
			}
		}

		for (int i = 0; i < properties.length; i++) {
			if (position == properties[i]) {
				if (check_if_bought(position)) {
					return String.format("Property {%s} owned by {%s}", position, squares.owned_by(position));
				} else {
					return String.format("Property {%s} costs €%s", position, squares.price(position));
				}
			}
		}

		return "Can't check this position";
	}

	/**
	* Check if the property has been bought
	* @param position the id of the sqaure to be checked
	* @return true if bought, false otherwise
	*/
	private boolean check_if_bought(int position) {
		return bought_properties.contains(position);
	}
	
}