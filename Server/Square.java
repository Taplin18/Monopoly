import java.util.*;

class Square {

	protected int position;
	protected Chest chest_cards;
	protected Chance chance_cards;

	public Square() {
		chest_cards = new Chest();
		chance_cards = new Chance();
	}

	public String get_card(String card_type) {
		if (card_type == "Chest") {
			return chest_cards.choose_card();
		} else if (card_type == "Chance") {
			return chance_cards.choose_card();
		}

		return "Nope!";
	}

	public String get_card(String card_type, int position){
		if (card_type == "Tax") {
			if (position == 4) {
				return "Tax - €200";
			} else {
				return "Tax - €100";
			}
		} else if (card_type == "Corners") {
			if (position == 0) {
				return "Go - Collect €200";
			} else if (position == 10) {
				return "Just Visiting / In Jail";
			} else if (position == 20) {
				return "Free Parking";
			} else if (position == 30) {
				return "Go to Jail";
			}
		}

		return "Extra Nope!";
	}
}