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
		int index = generator.nextInt(15);
		return cards.get(index);
	}

	/**
	* The possible chance cards
	*/
	private void make_chance_cards() {
		cards.add("Advance to Go - Collect €200");
		cards.add("Advance to _____ - If you pass Go, collect €200");
		cards.add("Advance to _____ - If you pass Go, collect €200");
		cards.add("Advance token to nearest Utility. If owned, throw dice and pay owner a total ten times the amount thrown");
		cards.add("Get Out of Jail Free");
		cards.add("Go to Jail");
		cards.add("Advance to nearest Railroad and pay owner twice the rental to which they are otherwise entitled");
		cards.add("Bank pays you dividend of €50");
		cards.add("Go Back 3 Spaces");
		cards.add("Make general repairs on all your property – €25 per house – €100 per hotel");
		cards.add("Pay poor tax of €15");
		cards.add("Take a trip to _____ Railroad – If you pass Go, collect €200");
		cards.add("Advance to (last sqaure before Go)");
		cards.add("You have been elected Chairman of the Board – Pay each player €50");
		cards.add("Your loan matures – Collect €150");
	}
}