package monopoly3;

import java.util.*;

class Chest {

	protected List <String> cards = new ArrayList <String>();
	Random generator = new Random();

	/**
	* Initilise the community chest cards
	*/
	public Chest() {
		make_chest_cards();
	}

	/**
	* Randomly choose a community chest card
	* @return community chest card information
	*/
	public String choose_card() {
		int index = generator.nextInt(15);
		return cards.get(index);
	}

	/**
	* The possible community chest cards
	*/
	private void make_chest_cards() {
		cards.add("chest - add - 200 - Bank error in your favour -> Collect \u20AC200 - chest.png");
		cards.add("chest - pay - -50 - Doctor's fee -> Pay \u20AC50 - chest.png");
		cards.add("chest - add - 50 - From sale of stock you get \u20AC50 - chest.png");
		cards.add("chest - jail - out - Get Out of Jail Free - chest.png");
		cards.add("chest - jail - in - Go to Jail - chest.png");
		cards.add("chest - add - 50 - Grand Opera Night -> Collect \u20AC50 - chest.png");
		cards.add("chest - add - 50 - Holiday Fund matures -> Receive \u20AC50 - chest.png");
		cards.add("chest - add - 20 - Income tax refund -> Collect \u20AC20 - chest.png");
		cards.add("chest - add - 10 - It's your birthday -> Collect \u20AC10 - chest.png");
		cards.add("chest - add - 100 - Life insurance matures -> Collect \u20AC100 - chest.png");
		cards.add("chest - pay - -100 - Pay hospital fees of \u20AC100 - chest.png");
		cards.add("chest - pay - -150 - Pay school fees of \u20AC150 - chest.png");
		cards.add("chest - add - 25 - Receive \u20AC25 consultancy fee - chest.png");
		cards.add("chest - add - 10 - You have won second place in a beauty contest -> Collect \u20AC10 - chest.png");
		cards.add("chest - add - 100 - You inherit \u20AC100 - chest.png");
	}
}