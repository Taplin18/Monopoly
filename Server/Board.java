import java.util.*;

class Board {

	/**
	* num_properties - the number of squares on the board.
	* chest - the positions of the community chest squares.
	* chance - the positions of the chance squares.
	* corners - the positions of the corner squares.
	* transport - the positions of the transportation squares.
	* utilities - the position of the untility squares.
	* taxes - the position of the tax squares.
	* properties - the positions as keys and values [cost, rent, grouping]
	* bought_properties - property id as key and owner id as value
	*/

	protected int num_properties = 40;
	protected int[] chest = {2, 17, 33};
	protected int[] chance = {7, 22, 36};
	protected int[] corners = {0, 10, 20, 30};
	protected int[] transport = {5, 15, 25, 35};
	protected int[] utilities = {12, 28};
	protected int[] taxes = {4, 38};
	protected HashMap <Integer, List <Integer>> properties = new HashMap <Integer, List <Integer>>();
	protected HashMap <Integer, Integer> bought_properties = new HashMap <Integer, Integer>();
	protected Square squares;

	public Board() {
		property_values();
		squares = new Square();
		//
		// initilise each square - corners, chest, chance, etc.
		// use for loop and add the number of players to the go square
		// can then add and remove players from squares.
	}

	public void buy(int property, int player_id) {
		// add property id and player id to bought_properties
		bought_properties.put(property, player_id);
	}

	public String check_square(int position) {
		// Check the sqaure that the player has landed on
		for (int i = 0; i < chest.length; i++) {
			if (position == chest[i]) {
				return squares.get_card("Chest");
			}
		}

		for (int j = 0; j < chance.length; j++) {
			if (position == chance[j]) {
				return squares.get_card("Chance");
			}
		}

		for (int k = 0; k < transport.length; k++) {
			if (position == transport[k]) {
				if (check_if_bought(position)) {
					return String.format("Transport {%s} owned by {%s}", position, bought_properties.get(position));
				} else {
					return String.format("Transport {%s} costs €200", position);
				}
			}
		}

		for (int h = 0; h < utilities.length; h++) {
			if (position == utilities[h]) {
				if (check_if_bought(position)) {
					return String.format("Utiliity {%s} owned by {%s}", position, bought_properties.get(position));
				} else {
					return String.format("Utiliity {%s} costs €150", position);
				}
			}
		}

		for (int g = 0; g < corners.length; g++) {
			if (position == corners[g]) {
				return squares.get_card("Corners", position);
			}
		}

		for (int m = 0; m < taxes.length; m++) {
			if (position == taxes[m]) {
				return squares.get_card("Tax", position);
			}
		}

		if (properties.containsKey(position)) {
			if (check_if_bought(position)) {
				return String.format("Property {%s} owned by {%s}", position, bought_properties.get(position));
			} else {
				List <Integer> prop_values = properties.get(position);
				int price = prop_values.get(0);
				return String.format("Property {%s} costs €%s", position, price);
			}
		}

		return "Can't check this position";
	}

	private boolean check_if_bought(int position) {
		// return true if bought, false otherwise
		return bought_properties.containsKey(position);
	}

	private void property_values() {
		// Adds the values to the HashMap properties
		properties.put(1, Arrays.asList(60, 10, 0));
		properties.put(3, Arrays.asList(60, 20, 0));
		properties.put(6, Arrays.asList(100, 30, 1));
		properties.put(8, Arrays.asList(100, 30, 1));
		properties.put(9, Arrays.asList(120, 40, 1));
		properties.put(11, Arrays.asList(140, 50, 2));
		properties.put(13, Arrays.asList(140, 60, 2));
	 	properties.put(14, Arrays.asList(160, 60, 2));
        properties.put(16, Arrays.asList(180, 70, 3));
        properties.put(18, Arrays.asList(180, 70, 3));
        properties.put(19, Arrays.asList(200, 80, 3));
        properties.put(21, Arrays.asList(220, 90, 4));
        properties.put(23, Arrays.asList(220, 90, 4));
        properties.put(24, Arrays.asList(240, 100, 4));
        properties.put(26, Arrays.asList(260, 110, 5));
        properties.put(27, Arrays.asList(260, 110, 5));
        properties.put(29, Arrays.asList(280, 120, 5));
        properties.put(31, Arrays.asList(300, 130, 6));
        properties.put(32, Arrays.asList(300, 130, 6));
        properties.put(34, Arrays.asList(320, 150, 6));
        properties.put(37, Arrays.asList(350, 175, 7));
        properties.put(39, Arrays.asList(400, 200, 7));
	}
	
}