import java.util.*;

class Square {

	//protected int position;
	protected Chest chest_cards;
	protected Chance chance_cards;
	protected Property property_info;
	protected Transport transport_info;
	protected Utility utility_info;
	protected HashMap <Integer, Property> properties = new HashMap <Integer, Property>();
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
		property();
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
				return "Tax - 200";
			} else {
				return "Tax - 100";
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
	* Buy the property and set owners id to players id
	* @param position the property id
	* @param playerID the players id
	*/
	public void buy_property(int position, int playerID) {
		Property purchase = properties.get(position);
		purchase.buy_property(playerID);
	}

	/**
	* Get the owner ID
	* @param position the property id
	* @return the owners id
	*/
	public int prop_owned_by(int position) {
		property_info = properties.get(position);
		return property_info.owner();
	}

	/**
	* Get the name of the property
	* @param position the property id
	* @return the name of the property
	*/
	public String prop_name(int position) {
		property_info = properties.get(position);
		return property_info.name();
	}

	/**
	* Get the cost to build on the property
	* @param position the property id
	* @return the build cost
	*/
	public int prop_buildCost(int position) {
		property_info = properties.get(position);
		return property_info.buildCost();
	}

	/**
	* Get the price of the property
	* @param position the property id
	* @return the cost of the property
	*/
	public int prop_price(int position) {
		property_info = properties.get(position);
		return property_info.cost();
	}

	/**
	* Get the rent of the bought property
	* @param position the property id
	* @return the rent of the property
	*/
	public int prop_rent(int position) {
		property_info = properties.get(position);
		return property_info.rent();
	}

	/**
	* Get the colour of the property (for it's grouping)
	* @param position the property id
	* @return the colour of the property
	*/
	public String prop_colour(int position) {
		property_info = properties.get(position);
		return property_info.colour();
	}

	/**
	* Buy the property and set owners id to players id
	* @param position the property id
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

	//Initilise the property values
	private void property(){
		property_values.add(Arrays.asList("Mediterranean Avenue", 60, 10, "brown", 50));
		property_values.add(Arrays.asList("Baltic Avenue", 60, 20, "brown", 50));
		property_values.add(Arrays.asList("Oriental Avenue", 100, 30, "blue", 50));
		property_values.add(Arrays.asList("Vermont Avenue", 100, 30, "blue", 50));
		property_values.add(Arrays.asList("Connecticut Avenue", 120, 40, "blue", 50));
		property_values.add(Arrays.asList("St. Charles Place", 140, 50, "pink", 100));
		property_values.add(Arrays.asList("States Avenue", 140, 60, "pink", 100));
		property_values.add(Arrays.asList("Virginia Avenue", 160, 60, "pink", 100));
		property_values.add(Arrays.asList("St. James Place", 180, 70, "orange", 100));
		property_values.add(Arrays.asList("Tennessee Avenue", 180, 70, "orange", 100));
		property_values.add(Arrays.asList("New York Avenue", 200, 80, "orange", 100));
		property_values.add(Arrays.asList("Kentucky Avenue", 220, 90, "red", 150));
		property_values.add(Arrays.asList("Indiana Avenue", 220, 90, "red", 150));
		property_values.add(Arrays.asList("Illinois Avenue", 240, 100, "red", 150));
		property_values.add(Arrays.asList("Atlantic Avenue", 260, 110, "yellow", 150));
		property_values.add(Arrays.asList("Ventnor Avenue", 260, 110, "yellow", 150));
		property_values.add(Arrays.asList("Marvin Gardens", 280, 120, "yellow", 150));
		property_values.add(Arrays.asList("Pacific Avenue", 300, 130, "green", 200));
		property_values.add(Arrays.asList("North Carolina Avenue", 300, 130, "green", 200));
		property_values.add(Arrays.asList("Pennsylvania Avenue", 320, 150, "green", 200));
		property_values.add(Arrays.asList("Park Place", 350, 175, "purple", 200));
		property_values.add(Arrays.asList("Boardwalk", 400, 200, "purple", 200));

		for (int i = 0; i < property_values.size(); i++) {
			List <Object> values = property_values.get(i);
			property_info = new Property((String)values.get(0), (int)values.get(1), (int)values.get(2), (String)values.get(3), (int)values.get(4));
			properties.put(property_ids[i], property_info);
		}
	}

	//Initilise the transport values
	private void transport(){
		transport_values.add(Arrays.asList("Reading Railroad", 200, 25));
		transport_values.add(Arrays.asList("Pennsylvania Railroad", 200, 25));
		transport_values.add(Arrays.asList("B. & O. Railroad", 200, 25));
		transport_values.add(Arrays.asList("Short Line", 200, 25));

		for (int i = 0; i < 4; i++) {
			List <Object> values = transport_values.get(i);
			transport_info = new Transport((String)values.get(0), (int)values.get(1), (int)values.get(2));
			transports.put(transport_id[i], transport_info);
		}
	}

	//Initilise the utility values
	private void utility(){
		utility_values.add(Arrays.asList("Electric Company", 200));
		utility_values.add(Arrays.asList("Water Works", 200));

		for (int i = 0; i < 2; i++) {
			List <Object> values = utility_values.get(i);
			utility_info = new Utility((String)values.get(0), (int)values.get(1));
			utilities.put(utility_id[i], utility_info);
		}
	}
}