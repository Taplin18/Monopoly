import java.util.*;


class BoardTester {

	public static void main(String[] args) {
		Board game = new Board();
		Random generator = new Random();
		int num_count = 10;

		while (num_count != 0) {
			int position = generator.nextInt(40);
			String info = game.check_square(position);
			System.out.println(info);
			num_count --;
		}

		game.buy(9, 0);
		System.out.println(game.check_square(9));
	}
}