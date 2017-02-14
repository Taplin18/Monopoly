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
	*/

	protected int num_properties = 40;
	protected int[] chest = {2, 17, 33};
	protected int[] chance = {7, 22, 36};
	protected int[] corners = {0, 10, 20, 30};
	protected int[] transport = {5, 15, 25, 35};
	protected int[] utilities = {12, 28};
	protected int[] taxes = {4, 38};
	protected HashMap <Integer, List <Integer>> properties = new HashMap <Integer, List <Integer>>();
	protected List <Integer> bought_properties = new ArrayList <Integer>();
	protected Square squares;

	public Board() {
		property_values();
		squares = new Square();
		//
		// initilise each square - corners, chest, chance, etc.
		// use for loop and add the number of players to the go square
		// can then add and remove players from squares.
	}

	public void buy(int property) {
		bought_properties.add(property);
	}

	public String check_square(int position) {
		// Chack the sqaure that the player has landed on
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
		for (int m = 0; m < taxes.length; m++) {
			if (position == taxes[m]) {
				return squares.get_card("Tax", position);
			}
		}
		for (int n = 0; n < corners.length; n++) {
			if (position == corners[n]) {
				return squares.get_card("Corners", position);
			}
		}

		for (int k = 0; k < transport.length; k++) {
			if (position == transport[k]) {
				// check if its in bought.
				// who owns it if it is
				return "Transport Thing";
			}
		}

		for (int l = 0; l < properties.size(); l++) {
			if (properties.containsKey(l)) {
				// chack if its been bought
				// who owns it
				return "Property Thing";
			}
		}

		for (int h = 0; h < utilities.length; h++) {
			if (position == utilities[h]) {
				return "Utilities";
			}
		}

		return "Not There!";
	}

	private void property_values() {
		// Adds the values to the HashMap
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