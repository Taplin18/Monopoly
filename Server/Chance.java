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
		cards.add("chance - move - 0 - Advance to Go -> Collect  \u20AC200");
		cards.add("chance - move - 24 - Advance to somewhere -> If you pass Go, collect  \u20AC200");
		cards.add("chance - move - 11 - Advance to somewhere -> If you pass Go, collect  \u20AC200");
		cards.add("chance - jail - out - Get Out of Jail Free");
		cards.add("chance - jail - in - Go to Jail");
		cards.add("chance - back - 3 - Go Back 3 Spaces");
		cards.add("chance - move - 5 - Take a trip to somewhere Railroad –> If you pass Go, collect  \u20AC200");
		cards.add("chance - move - 39 - Advance to somewhere");
	}
}