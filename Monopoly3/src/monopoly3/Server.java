package monopoly3;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.io.*;

/**
* Creates the server that the game will run on
*/
public class Server {

	private static final int port = 10001;
	private static final int maxPlayers = 2;
	private static int currentPlayers = 0;
	private static Board board = new Board();

	public static void main(String[] args) {
		Socket playerSocket = null;
		ServerSocket server = null;
		System.out.println("Server starting...");
		try {
			server = new ServerSocket(port);
			InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Your current IP address : " + ip);
			System.out.println("Listening on port: " + port);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server Error");
		}

		while (true) {
			try {
				if (currentPlayers != maxPlayers+1) {
					playerSocket = server.accept();
					System.out.println("New player connected");
					ServerThread st = new ServerThread(playerSocket, board);
					st.start();
					currentPlayers++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Connection Error");
			}
		}
	}
}

class ServerThread extends Thread {

	private String line = null;
	private String playerName;
	private int playerID;
	private static int playerTurn = 0;
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	private PrintStream ps = null;
	private Socket sock = null;
	private Board board = null;
	private static HashMap <Integer, Integer> player_pos = new HashMap <Integer, Integer>();
	private static HashMap <Integer, Integer> rent_owed = new HashMap <Integer, Integer>();
	private static HashMap <Integer, String> player_usernames = new HashMap <Integer, String>();
	private static int ids = 0;
	private static boolean started = false;
	private static boolean game_over = false;
	private JSONParser parser = new JSONParser();
	private static JSONObject players = new JSONObject();
	private static final int maxPlayers = 2;
	private static String[] player_names = new String[maxPlayers];
	private static boolean turn_sent = false;
	private boolean position_sent = false;
	private boolean bye_sent = false;
	private boolean num_players_sent = false;
	private boolean startedMessage = false;
	private boolean rent_sent = false;
	private boolean again = false;

	/**
	* Creates a thread to deal with each player when they join the game
	*/
	public ServerThread(Socket sock, Board board){ //, ServerThread[] threads) {
		this.sock = sock;
		this.board = board;
		playerID = ids;
		player_pos.put(playerID, 0);
		rent_owed.put(playerID, 0);
		System.out.println(player_pos.values());
		ids++;
	}

	/**
	* Runs the thread and contains the players interactions with the server
	*/
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			System.out.println("IO error in server thread");
		}

		try {
			line = br.readLine();
			try{
				Object obj = parser.parse(line);
				JSONObject player_info = (JSONObject)obj;

				// Get the players username and send them their new ID
				if (playerName == null) {
					playerName = String.valueOf(player_info.get("message"));
					players.put(String.valueOf(playerID), playerName);
					player_names[playerID] = playerName;
					player_usernames.put(playerID, playerName);
					System.out.println("Name is: " + playerName);
					
					JSONObject new_player_id = new JSONObject();
					new_player_id.put("messageType", "firstContact");
					new_player_id.put("id", String.valueOf(playerID));
					
					StringWriter out = new StringWriter();
	         		new_player_id.writeJSONString(out);
	         		String jsonText = out.toString();
	         		System.out.println("first contect message sent: " + jsonText);
	         		bw.write(jsonText);
	         		bw.newLine();
	         		bw.flush();
				}

				// Check if the game can start
				if (!started) {
					if (player_pos.size() == maxPlayers) {
						started = true;
						System.out.println("Game can start");
					}
					if(player_info.get("messageType") == "start") {
						started = true;
					}
				}


				// Playing the game
				while (!game_over) {
					if (br.ready()) {
						line = br.readLine();
					}
					this.sleep(500);
					if (!startedMessage && started) {
						JSONObject numPlayers = new JSONObject();
						numPlayers.put("messageType", "start");
						
						StringWriter num = new StringWriter();
		         		numPlayers.writeJSONString(num);
		         		String num_players = num.toString();
		         		System.out.println("first contect message: " + num_players);
		         		bw.write(num_players);
		         		bw.newLine();
		         		bw.flush();
		         		startedMessage = true;
					}
					// Send the number of clients playing
					if (player_info.get("messageType").equals("noOfPlayers") && !num_players_sent) {
						JSONObject starting = new JSONObject();
						starting.put("number", String.valueOf(maxPlayers));
						starting.put("id", String.valueOf(playerID));
						
						StringWriter start_game = new StringWriter();
		         		starting.writeJSONString(start_game);
		         		String start_mon = start_game.toString();
		         		bw.write(start_mon);
		         		bw.newLine();
		         		bw.flush();
		         		num_players_sent = true;
					}

					// Send the position of the players to update the current players board
					if (player_info.get("messageType").equals("playersPositions")) {
						JSONObject the_players_pos = new JSONObject();
						for (int i = 0; i < maxPlayers; i++) {
							the_players_pos.put(String.valueOf(i), String.valueOf(player_pos.get(i)));
						}
						the_players_pos.put("messageType", "playersPositions");
						StringWriter sendPlayerPos = new StringWriter();
		         		the_players_pos.writeJSONString(sendPlayerPos);
		         		String play_pos = sendPlayerPos.toString();
		         		System.out.println("sendPlayerPos: " + play_pos);
		         		bw.write(play_pos);
						bw.newLine();
						bw.flush();
						System.out.println("Sending player positions");
					}

					// Let the client know that it their turn
					if(started && playerID == playerTurn){
						
						if (!turn_sent) {
							System.out.println("\nPlayer is: " + playerName);
							JSONObject turn = new JSONObject();
							turn.put("messageType", "yourTurn");
							turn.put("id", String.valueOf(playerID));
							StringWriter nextTurn = new StringWriter();
			         		turn.writeJSONString(nextTurn);
			         		String sendTurn = nextTurn.toString();
			         		System.out.println("sendTurn: " + sendTurn);
			         		bw.write(sendTurn);
							bw.newLine();
							bw.flush();
							System.out.println("Sending yourTurn");
							turn_sent = true;
							//bye_sent = false;
						}

						if (again) {
							System.out.println("\nPlayer is: " + playerName);
							JSONObject turn = new JSONObject();
							turn.put("messageType", "again");
							StringWriter nextTurn = new StringWriter();
			         		turn.writeJSONString(nextTurn);
			         		String sendTurn = nextTurn.toString();
			         		System.out.println("sendAgain: " + sendTurn);
			         		bw.write(sendTurn);
							bw.newLine();
							bw.flush();
							System.out.println("Sending Again");
							again = false;
						}

						obj = parser.parse(line);
						player_info = (JSONObject) obj;

						// Send the rent that is due to a client
						if (player_info.get("messageType").equals("rentDue") && !rent_sent) {
							JSONObject rent = new JSONObject();
							rent.put("messageType", "rent");
							rent.put("rent", String.valueOf(rent_owed.get(playerID)));
							rent.put("id", String.valueOf(playerID));
							StringWriter sendRent = new StringWriter();
			         		rent.writeJSONString(sendRent);
			         		String rent_amt = sendRent.toString();
			         		System.out.println("sendRent: " + rent_amt);
			         		bw.write(rent_amt);
							bw.newLine();
							bw.flush();
							System.out.println("Sending rent");
							rent_sent = true;
						}

						/*if (player_info.get("messageType").equals("Again")){
							System.out.println(playerName + " rolls again");
							position_sent = false;
						}*/

						// Send the rent that is due to a client
						if (player_info.get("messageType").equals("buy")) {
							JSONObject rent = new JSONObject();
							rent.put("messageType", "buy");
							StringWriter sendRent = new StringWriter();
			         		rent.writeJSONString(sendRent);
			         		String rent_amt = sendRent.toString();
			         		System.out.println("sendRent: " + rent_amt);
			         		bw.write(rent_amt);
							bw.newLine();
							bw.flush();
							System.out.println("Sending rent");
						}

						// Send the position of the players to update the current players board
						if (player_info.get("messageType").equals("playersPositions")) {
							JSONObject the_players_pos = new JSONObject();
							for (int i = 0; i < maxPlayers; i++) {
								the_players_pos.put(String.valueOf(i), String.valueOf(player_pos.get(i)));
							}
							StringWriter sendPlayerPos = new StringWriter();
			         		the_players_pos.writeJSONString(sendPlayerPos);
			         		String play_pos = sendPlayerPos.toString();
			         		System.out.println("sendPlayerPos: " + play_pos);
			         		bw.write(play_pos);
							bw.newLine();
							bw.flush();
							System.out.println("Sending player positions");
						}

						// Check the position the player has landed on and
						// send that positions information
						if (player_info.get("messageType").equals("position") && !position_sent){// && !position_sent) {
							System.out.println("Check position: " + player_info.get("message"));
							player_pos.put(playerID, Integer.valueOf(String.valueOf(player_info.get("message"))));
							String a = board.check_square(Integer.valueOf(String.valueOf(player_info.get("message"))));
							String[] answer = a.split(" - ");
							JSONObject position_info = new JSONObject();
							position_info.put("messageType", "position");
							
							if (answer[0].equals("chest")) {
								position_info.put("positionType", "chest");
								position_info.put("chestType", answer[1]);
								position_info.put("picture", answer[4]);
								if (answer[1].equals("jail")) {
									position_info.put("jailType", answer[2]);
									position_info.put("message", answer[3]);
								} else {
									position_info.put("chestAmount", answer[2]);
									position_info.put("message", answer[3]);
								}
							} else if (answer[0].equals("chance")) {
								position_info.put("positionType", "chance");
								position_info.put("chanceType", answer[1]);
								position_info.put("picture", answer[4]);
								if (answer[1].equals("jail")) {
									position_info.put("jailType", answer[2]);
									position_info.put("message", answer[3]);
								} else {
									if (answer[1].equals("back")) {
										String new_pos = String.valueOf(Integer.valueOf(String.valueOf(player_info.get("message"))) - 3);
										position_info.put("chancePosition", new_pos);
										position_info.put("message", answer[3]);
									} else {
										position_info.put("chancePosition", answer[2]);
										position_info.put("message", answer[3]);
									}
								}
							} else if (answer[0].equals("property")) {
								position_info.put("positionType", "property");
								position_info.put("ownership", answer[1]);
								if (answer[1].equals("owned")) {
									position_info.put("rent", answer[2]);
									position_info.put("username", player_usernames.get(Integer.valueOf(answer[3])));
									position_info.put("name", answer[4]);
									position_info.put("picture", answer[5]);
									int new_rent = rent_owed.get(Integer.valueOf(answer[3]) + Integer.valueOf(answer[2]));
									rent_owed.put(Integer.valueOf(answer[3]), new_rent);
								} else {
									position_info.put("name", answer[2]);
									position_info.put("colour", answer[3]);
									position_info.put("price", answer[4]);
									position_info.put("baseRent", answer[5]);
									position_info.put("buildCost", answer[6]);
									position_info.put("picture", answer[7]);
								}
							} else if (answer[0].equals("utilities")) {
								position_info.put("positionType", "utilities");
								position_info.put("ownership", answer[1]);
								position_info.put("picture", answer[5]);
								if (answer[1].equals("owned")) {
									position_info.put("rent", answer[2]);
									position_info.put("username", player_usernames.get(Integer.valueOf(answer[3])));
									position_info.put("name", answer[4]);
									int new_rent = rent_owed.get(Integer.valueOf(answer[3]) + Integer.valueOf(answer[2]));
									rent_owed.put(Integer.valueOf(answer[3]), new_rent);
								} else {
									position_info.put("name", answer[2]);
									position_info.put("price", answer[3]);
									position_info.put("baseRent", answer[4]);
								}
							} else if (answer[0].equals("transport")) {
								position_info.put("positionType", "transport");
								position_info.put("ownership", answer[1]);
								position_info.put("picture", answer[5]);
								if (answer[1].equals("owned")) {
									position_info.put("rent", answer[2]);
									position_info.put("username", player_usernames.get(Integer.valueOf(answer[3])));
									position_info.put("name", answer[4]);
									int new_rent = rent_owed.get(Integer.valueOf(answer[3]) + Integer.valueOf(answer[2]));
									rent_owed.put(Integer.valueOf(answer[3]), new_rent);
								} else {
									position_info.put("name", answer[2]);
									position_info.put("price", answer[3]);
									position_info.put("baseRent", answer[4]);
								}
							} else if (answer[0].equals("Tax")) {
								position_info.put("positionType", "taxes");
								position_info.put("taxAmount", answer[1]);
								position_info.put("picture", answer[2]);
							} else {
								position_info.put("positionType", "corner");
							}
							StringWriter play_info = new StringWriter();
			         		position_info.writeJSONString(play_info);
			         		String send_play_info = play_info.toString();
			         		System.out.println("send_play_info: " + send_play_info);
			         		bw.write(send_play_info);
							bw.newLine();
							bw.flush();
							position_sent = true;
						} 

						// End the current players turn
						if (player_info.get("messageType").equals("Bye")){ //&& !bye_sent){
							System.out.println(playerName + "'s turn is over");
							playerTurn++;
							if (playerTurn == maxPlayers) {
								playerTurn = 0;
							}
							//if (player_info.get("message").equals("Bye")) {
								turn_sent = false;
								position_sent = false;
								bye_sent = true;
								rent_sent = false;
							/*} else if (player_info.get("message").equals("again")) {
								again = true;
								//turn_sent = true;
								//position_sent = true;
								//rent_sent = true;
							}*/
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("JSON error: " + e);
			}
		} catch (IOException e) {
			line = this.getName();
			System.out.println("IO Error -> Player " + line + " terminated");
		} catch (NullPointerException e) {
			line = this.getName();
			System.out.println("Player " + line + " closed");
		} finally {
			try {
				System.out.println("Connection Closing..");
		        if (br!=null){
		            br.close(); 
		            System.out.println("Socket Input Stream Closed");
		        }

		        if(bw!=null){
		            bw.close();
		            System.out.println("Socket Out Closed");
		        }

		        if (sock!=null){
		        sock.close();
		        System.out.println("Socket Closed");
		        }
			} catch(IOException ie){
		        System.out.println("Socket Close Error");
		    }
		}
	}
}
