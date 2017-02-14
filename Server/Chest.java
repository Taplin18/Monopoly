import java.util.*;

class Chest {

	protected List <String> cards = new ArrayList <String>();
	Random generator = new Random();

	public Chest() {
		make_chest_cards();
	}

	public String choose_card() {
		int index = generator.nextInt(16) + 1;
		return cards.get(index);
	}

	private void make_chest_cards() {
		cards.add("Advance to Go - Collect €200");
		cards.add("Bank error in your favour - Collect €200");
		cards.add("Doctor's fee - Pay €50");
		cards.add("From sale of stock you get €50");
		cards.add("Get Out of Jail Free");
		cards.add("Go to Jail");
		cards.add("Grand Opera Night - Collect €50");
		cards.add("Holiday Fund matures - Receive €50");
		cards.add("Income tax refund - Collect €20");
		cards.add("It's your birthday - Collect €10");
		cards.add("Life insurance matures - Collect €100");
		cards.add("Pay hospital fees of €100");
		cards.add("Pay school fees of €150");
		cards.add("Receive €25 consultancy fee");
		cards.add("You are assessed for street repairs - €40 per house - €115 per hotel");
		cards.add("You have won second place in a beauty contest - Collect €10");
		cards.add("You inherit €100");
	}
}