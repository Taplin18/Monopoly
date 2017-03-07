package monopoly3;

import java.util.*;

class Square {

	protected Chest chest_cards;
	protected Chance chance_cards;
	protected Server_Property property_info;
	protected Transport transport_info;
	protected Utility utility_info;
	protected HashMap <Integer, Server_Property> properties = new HashMap <Integer, Server_Property>();
	protected HashMap <Integer, Transport> transports = new HashMap <Integer, Transport>();
	protected HashMap <Integer, Utility> utilities = new HashMap <Integer, Utility>();
	protected List <List <Object>> property_values = new ArrayList < List <Object>>();
	protected List <List <Object>> transport_values = new ArrayList < List <Object>>();
	protected List <List <Object>> utility_values = new ArrayList < List <Object>>();
	protected int[] transport_id = {5, 15, 25, 35};
	protected int[] utility_id = {12, 28};
	protected int[] property_ids = {1, 3, 6, 8, 
		9, 11, 13, 14, 
		16, 18, 19, 21, 
		23, 24, 26, 27, 
		29, 31, 32, 34, 
		37, 39};

	/**
	* Initilise the possible squares
	*/
	public Square() {
		chest_cards = new Chest();
		chance_cards = new Chance();
		Server_Property();
		transport();
		utility();
	}

	/**
	* Get the information of the chest/community chest card
	* @param card_type chance or a community chest card
	* @return the cards information
	*/
	public String get_card(String card_type) {
		if (card_type == "Chest") {
			return chest_cards.choose_card();
		} else if (card_type == "Chance") {
			return chance_cards.choose_card();
		}

		return "No card was returned";
	}

	/**
	* Get the information of the tax/corner positions
	* @param card_type Tax or Corner position
	* @param position the part of the board the sqaure is
	* @return the positiond information
	*/
	public String get_card(String card_type, int position){
		if (card_type == "Tax") {
			if (position == 4) {
				return "Tax - 200 - taxes.png";
			} else {
				return "Tax - 100 - taxes.png";
			}
		} else if (card_type == "Corners") {
			if (position == 0) {
				return "corner - Go";
			} else if (position == 10) {
				return "corner - Just Visiting / In Jail";
			} else if (position == 20) {
				return "corner - Free Parking";
			} else if (position == 30) {
				return "corner - Go to Jail";
			}
		}

		return "No card was returned";
	}

	/**
	* Buy the Server_Property and set owners id to players id
	* @param position the Server_Property id
	* @param playerID the players id
	*/
	public void buy_property(int position, int playerID) {
		Server_Property purchase = properties.get(position);
		purchase.buy_property(playerID);
	}

	/**
	* Get the owner ID
	* @param position the Server_Property id
	* @return the owners id
	*/
	public int prop_owned_by(int position) {
		property_info = properties.get(position);
		return property_info.owner();
	}

	/**
	* Get the name of the Server_Property
	* @param position the Server_Property id
	* @return the name of the Server_Property
	*/
	public String prop_name(int position) {
		property_info = properties.get(position);
		return property_info.name();
	}

	/**
	* Get the cost to build on the Server_Property
	* @param position the Server_Property id
	* @return the build cost
	*/
	public int prop_buildCost(int position) {
		property_info = properties.get(position);
		return property_info.buildCost();
	}

	/**
	* Get the price of the Server_Property
	* @param position the Server_Property id
	* @return the cost of the Server_Property
	*/
	public int prop_price(int position) {
		property_info = properties.get(position);
		return property_info.cost();
	}

	/**
	* Get the rent of the bought Server_Property
	* @param position the Server_Property id
	* @return the rent of the Server_Property
	*/
	public int prop_rent(int position) {
		property_info = properties.get(position);
		return property_info.rent();
	}

	/**
	* Get the colour of the Server_Property (for it's grouping)
	* @param position the Server_Property id
	* @return the colour of the Server_Property
	*/
	public String prop_colour(int position) {
		property_info = properties.get(position);
		return property_info.colour();
	}

	/**
	* Get the picutre name of the Server_Property
	* @param position the Server_Property id
	* @return the picture name of the Server_Property
	*/
	public String prop_picture(int position) {
		property_info = properties.get(position);
		return property_info.picture();
	}

	/**
	* Buy the transport and set owners id to players id
	* @param position the transport id
	* @param playerID the players id
	*/
	public void buy_transport(int position, int playerID) {
		Transport purchase = transports.get(position);
		purchase.buy_transport(playerID);
		boolean multiple = false;
		for (int i = 0; i < 4; i++) {
			Transport check_purchase = transports.get(transport_id[i]);
			if (check_purchase.owner() == position && transport_id[i] != position) {
				multiple = true;
				purchase.update_rent();
				check_purchase.update_rent();
			}
		}
	}

	/**
	* Get the owner ID
	* @param position the railroad id
	* @return the owners id
	*/
	public int trans_owned_by(int position) {
		transport_info = transports.get(position);
		return transport_info.owner();
	}

	/**
	* Get the price of the railroad
	* @param position the transport id
	* @return the cost of the railroad
	*/
	public int trans_price(int position) {
		transport_info = transports.get(position);
		return transport_info.cost();
	}

	/**
	* Get the name of the railroad
	* @param position the transport id
	* @return the name of the railroad
	*/
	public String trans_name(int position) {
		transport_info = transports.get(position);
		return transport_info.name();
	}

	/**
	* Get the rent of the bought railroad
	* @param position the transport id
	* @return the rent of the railroad
	*/
	public int trans_rent(int position) {
		transport_info = transports.get(position);
		return transport_info.rent();
	}

	/**
	* Get the picutre name of the transport
	* @param position the transport id
	* @return the picture name of the transport
	*/
	public String trans_picture(int position) {
		transport_info = transports.get(position);
		return transport_info.picture();
	}

	/**
	* Buy the utility and set owners id to players id
	* @param position the Server_Property id
	* @param playerID the players id
	*/
	public void buy_utility(int position, int playerID) {
		Utility purchase = utilities.get(position);
		purchase.buy_utility(playerID);
		boolean multiple = false;
		for (int i = 0; i < 2; i++) {
			Utility check_purchase = utilities.get(utility_id[i]);
			if (check_purchase.owner() == position && utility_id[i] != position) {
				multiple = true;
				purchase.update_rent();
				check_purchase.update_rent();
			}
		}
	}

	/**
	* Get the owner ID
	* @param position the utility id
	* @return the owners id
	*/
	public int util_owned_by(int position) {
		utility_info = utilities.get(position);
		return utility_info.owner();
	}

	/**
	* Get the price of the utility
	* @param position the utility id
	* @return the cost of the utility
	*/
	public int util_price(int position) {
		utility_info = utilities.get(position);
		return utility_info.cost();
	}

	/**
	* Get the name of the utility
	* @param position the utility id
	* @return the name of the utility
	*/
	public String util_name(int position) {
		utility_info = utilities.get(position);
		return utility_info.name();
	}

	/**
	* Get the rent of the bought utility
	* @param position the utility id
	* @return the rent of the utility
	*/
	public int util_rent(int position) {
		utility_info = utilities.get(position);
		return utility_info.rent();
	}

	/**
	* Get the picutre name of the Utility
	* @param position the utility id
	* @return the picture name of the utility
	*/
	public String util_picture(int position) {
		utility_info = utilities.get(position);
		return utility_info.picture();
	}

	//Initilise the Server_Property values
	private void Server_Property(){
		property_values.add(Arrays.asList("Mediterranean Avenue", 60, 10, "brown", 50, "src\\monopoly3\\med_avenue.jpg"));
		property_values.add(Arrays.asList("Baltic Avenue", 60, 20, "brown", 50, "src\\monopoly3\\baltic_avenue.jpg"));
		property_values.add(Arrays.asList("Oriental Avenue", 100, 30, "blue", 50, "src\\monopoly3\\oriental_avenue"));
		property_values.add(Arrays.asList("Vermont Avenue", 100, 30, "blue", 50, "src\\monopoly3\\vermont_avenue.jpg"));
		property_values.add(Arrays.asList("Connecticut Avenue", 120, 40, "blue", 50, "src\\monopoly3\\connecticut_avenue.jpg"));
		property_values.add(Arrays.asList("St. Charles Place", 140, 50, "pink", 100, "src\\monopoly3\\st_charles_place.jpg"));
		property_values.add(Arrays.asList("States Avenue", 140, 60, "pink", 100, "src\\monopoly3\\states_avenue.jpg"));
		property_values.add(Arrays.asList("Virginia Avenue", 160, 60, "pink", 100, "src\\monopoly3\\virginia_avenue.jpg"));
		property_values.add(Arrays.asList("St. James Place", 180, 70, "orange", 100, "src\\monopoly3\\st_james_place.jpg"));
		property_values.add(Arrays.asList("Tennessee Avenue", 180, 70, "orange", 100, "src\\monopoly3\\tennessee_avenue.jpg"));
		property_values.add(Arrays.asList("New York Avenue", 200, 80, "orange", 100, "src\\monopoly3\\new_york_avenue.jpg"));
		property_values.add(Arrays.asList("Kentucky Avenue", 220, 90, "red", 150, "src\\monopoly3\\kentucky_avenue.jpg"));
		property_values.add(Arrays.asList("Indiana Avenue", 220, 90, "red", 150, "src\\monopoly3\\indiana_avenue.jpg"));
		property_values.add(Arrays.asList("Illinois Avenue", 240, 100, "red", 150, "src\\monopoly3\\illinois_avenue.jpg"));
		property_values.add(Arrays.asList("Atlantic Avenue", 260, 110, "yellow", 150, "src\\monopoly3\\atlantic_avenue.jpg"));
		property_values.add(Arrays.asList("Ventnor Avenue", 260, 110, "yellow", 150, "src\\monopoly3\\ventnor_avenue.jpg"));
		property_values.add(Arrays.asList("Marvin Gardens", 280, 120, "yellow", 150, "src\\monopoly3\\marvin_avenue.jpg"));
		property_values.add(Arrays.asList("Pacific Avenue", 300, 130, "green", 200, "src\\monopoly3\\pacific_avenue.jpg"));
		property_values.add(Arrays.asList("North Carolina Avenue", 300, 130, "green", 200, "src\\monopoly3\\north_carolina_avenue.jpg"));
		property_values.add(Arrays.asList("Pennsylvania Avenue", 320, 150, "green", 200, "src\\monopoly3\\pennsylvania_avenue.jpg"));
		property_values.add(Arrays.asList("Park Place", 350, 175, "purple", 200, "src\\monopoly3\\park_place.jpg"));
		property_values.add(Arrays.asList("Boardwalk", 400, 200, "purple", 200, "src\\monopoly3\\boardwalk.jpg"));

		for (int i = 0; i < property_values.size(); i++) {
			List <Object> values = property_values.get(i);
			property_info = new Server_Property((String)values.get(0), (int)values.get(1), (int)values.get(2), (String)values.get(3), (int)values.get(4), (String)values.get(5));
			properties.put(property_ids[i], property_info);
		}
	}

	//Initilise the transport values
	private void transport(){
		transport_values.add(Arrays.asList("Reading Railroad", 200, 25, "src\\monopoly3\\transport.png"));
		transport_values.add(Arrays.asList("Pennsylvania Railroad", 200, 25, "src\\monopoly3\\transport.png"));
		transport_values.add(Arrays.asList("B. & O. Railroad", 200, 25, "src\\monopoly3\\transport.png"));
		transport_values.add(Arrays.asList("Short Line", 200, 25, "src\\monopoly3\\transport.png"));

		for (int i = 0; i < 4; i++) {
			List <Object> values = transport_values.get(i);
			transport_info = new Transport((String)values.get(0), (int)values.get(1), (int)values.get(2), (String)values.get(3));
			transports.put(transport_id[i], transport_info);
		}
	}

	//Initilise the utility values
	private void utility(){
		utility_values.add(Arrays.asList("Electric Company", 200, "src\\monopoly3\\utilities.png"));
		utility_values.add(Arrays.asList("Water Works", 200, "src\\monopoly3\\utilities.png"));

		for (int i = 0; i < 2; i++) {
			List <Object> values = utility_values.get(i);
			utility_info = new Utility((String)values.get(0), (int)values.get(1), (String)values.get(2));
			utilities.put(utility_id[i], utility_info);
		}
	}
}