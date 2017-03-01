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
		cards.add("chest - add - 200 - Bank error in your favour -> Collect €200");
		cards.add("chest - pay - -50 - Doctor's fee -> Pay €50");
		cards.add("chest - add - 50 - From sale of stock you get €50");
		cards.add("chest - jail - out - Get Out of Jail Free");
		cards.add("chest - jail - in - Go to Jail");
		cards.add("chest - add - 50 - Grand Opera Night -> Collect €50");
		cards.add("chest - add - 50 - Holiday Fund matures -> Receive €50");
		cards.add("chest - add - 20 - Income tax refund -> Collect €20");
		cards.add("chest - add - 10 - It's your birthday -> Collect €10");
		cards.add("chest - add - 100 - Life insurance matures -> Collect €100");
		cards.add("chest - pay - -100 - Pay hospital fees of €100");
		cards.add("chest - pay - -150 - Pay school fees of €150");
		cards.add("chest - add - 25 - Receive €25 consultancy fee");
		cards.add("chest - add - 10 - You have won second place in a beauty contest -> Collect €10");
		cards.add("chest - add - 100 - You inherit €100");
	}
}