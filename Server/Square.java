import java.util.*;

class Square {

	//protected int position;
	protected Chest chest_cards;
	protected Chance chance_cards;
	protected Property property_info;
	protected HashMap <Integer, Property> properties = new HashMap <Integer, Property>();
	protected List <List <Integer>> property_values = new ArrayList < List <Integer>>();
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
				return "Tax - €200";
			} else {
				return "Tax - €100";
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
	* Get the owner ID
	* @param position the property id
	* @return the owners id
	*/
	public int prop_owned_by(int position) {
		property_info = properties.get(position);
		return property_info.owner();
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
	* Initilise the property values
	*/
	private void property(){
		property_values.add(Arrays.asList(60, 10, 0));
		property_values.add(Arrays.asList(60, 20, 0));
		property_values.add(Arrays.asList(100, 30, 1));
		property_values.add(Arrays.asList(100, 30, 1));
		property_values.add(Arrays.asList(120, 40, 1));
		property_values.add(Arrays.asList(140, 50, 2));
		property_values.add(Arrays.asList(140, 60, 2));
		property_values.add(Arrays.asList(160, 60, 2));
		property_values.add(Arrays.asList(180, 70, 3));
		property_values.add(Arrays.asList(180, 70, 3));
		property_values.add(Arrays.asList(200, 80, 3));
		property_values.add(Arrays.asList(220, 90, 4));
		property_values.add(Arrays.asList(220, 90, 4));
		property_values.add(Arrays.asList(240, 100, 4));
		property_values.add(Arrays.asList(260, 110, 5));
		property_values.add(Arrays.asList(260, 110, 5));
		property_values.add(Arrays.asList(280, 120, 5));
		property_values.add(Arrays.asList(300, 130, 6));
		property_values.add(Arrays.asList(300, 130, 6));
		property_values.add(Arrays.asList(320, 150, 6));
		property_values.add(Arrays.asList(350, 175, 7));
		property_values.add(Arrays.asList(400, 200, 7));

		for (int i = 0; i < property_values.size(); i++) {
			List <Integer> values = property_values.get(i);
			property_info = new Property(values.get(0), values.get(1), values.get(2));
			properties.put(property_ids[i], property_info);
		}
	}
}