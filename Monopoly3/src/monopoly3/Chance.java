package monopoly3;

import java.util.*;

class Chance {

	protected List <String> cards = new ArrayList <String>();
	Random generator = new Random();

	/**
	* Initilise the chance cards
	*/
	public Chance() {
		make_chance_cards();
	}

	/**
	* Randomly choose a chance card
	* @return chance card information
	*/
	public String choose_card() {
		int index = generator.nextInt(8);
		return cards.get(index);
	}

	/**
	* The possible chance cards
	*/
	private void make_chance_cards() {
		cards.add("chance - move - 0 - Advance to Go -> Collect \u20AC200 - chance");
		cards.add("chance - move - 24 - Advance to Illinois Avenue -> If you pass Go, collect \u20AC200 - chance");
		cards.add("chance - move - 11 - Advance to St. Charles Place -> If you pass Go, collect \u20AC200 - chance");
		cards.add("chance - jail - out - Get Out of Jail Free - chance");
		cards.add("chance - jail - in - Go to Jail - chance");
		cards.add("chance - back - 3 - Go Back 3 Spaces - chance");
		cards.add("chance - move - 5 - Take a trip to Reading Railroad Railroad –> If you pass Go, collect \u20AC200 - chance");
		cards.add("chance - move - 39 - Advance to Boardwalk - chance");
	}
}