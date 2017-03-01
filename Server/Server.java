import java.net.ServerSocket;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.io.*;

/**
* Creates the server that the game will run on
*/
public class Server {

	private static final int port = 2222;
	private static final int maxPlayers = 2;
	private static int currentPlayers = 0;
	private static Board board = new Board();

	public static void main(String[] args) {
		Socket playerSocket = null;
		ServerSocket server = null;
		System.out.println("Server starting...");
		try {
			server = new ServerSocket(port);
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
	private static int ids = 0;
	private static boolean started = false;
	private static boolean game_over = false;
	private JSONParser parser = new JSONParser();
	private static JSONObject players = new JSONObject();
	private static final int maxPlayers = 2;
	private static String[] player_names = new String[maxPlayers];
	private static boolean turn_sent = false;
	private static boolean message_sent = false;
	private static boolean bye_sent = false;

	/**
	* Creates a thread to deal with each player when they join the game
	*/
	public ServerThread(Socket sock, Board board){ //, ServerThread[] threads) {
		this.sock = sock;
		this.board = board;
		//this.threads = threads;
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
				if (playerName == null) {
					playerName = String.valueOf(player_info.get("message"));
					players.put(String.valueOf(playerID), playerName);
					player_names[playerID] = playerName;
					System.out.println("Name is: " + playerName);
					JSONObject new_player_id = new JSONObject();
					new_player_id.put("messageType", "firstContact");
					new_player_id.put("id", String.valueOf(playerID));
					StringWriter out = new StringWriter();
	         		new_player_id.writeJSONString(out);
	         		String jsonText = out.toString();
	         		bw.write(jsonText);
	         		bw.newLine();
	         		bw.flush();
				}
				if (!started) {
					if (player_pos.size() == maxPlayers) {
						started = true;
						System.out.println("Game can start");
					}
					if(player_info.get("messageType") == "start") {
						started = true;
					} else if (player_info.get("messageType") == "check") {
						String returnMess = "Current Players:\n";
						for (int i = 0; i < player_names.length; i++) {
							returnMess += player_names[i] + "\n";
						}
						bw.write(returnMess);
						bw.newLine();
						bw.flush();
					}
				}
				while (!game_over) {	
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
						}

						obj = parser.parse(line);
						player_info = (JSONObject) obj;
						if (player_info.get("messageType").equals("rentDue")) {
							JSONObject rent = new JSONObject();
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
						}
						if (player_info.get("messageType").equals("position") && !message_sent) {
							System.out.println("Check position: " + player_info.get("message"));
							String a = board.check_square(Integer.valueOf(String.valueOf(player_info.get("message"))));
							String[] answer = a.split(" - ");
							JSONObject position_info = new JSONObject();
							
							if (answer[0].equals("chest")) {
								position_info.put("positionType", "chest");
								position_info.put("chestType", answer[1]);
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
									position_info.put("name", answer[4]);
									position_info.put("picture", answer[5]);
									rent_owed.put(Integer.valueOf(answer[3]), Integer.valueOf(answer[2]));
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
								if (answer[1].equals("owned")) {
									position_info.put("rent", answer[2]);
									position_info.put("name", answer[4]);
									rent_owed.put(Integer.valueOf(answer[3]), Integer.valueOf(answer[2]));
								} else {
									position_info.put("name", answer[2]);
									position_info.put("price", answer[3]);
									position_info.put("baseRent", answer[4]);
								}
							} else if (answer[0].equals("transport")) {
								position_info.put("positionType", "transport");
								position_info.put("ownership", answer[1]);
								if (answer[1].equals("owned")) {
									position_info.put("rent", answer[2]);
									position_info.put("name", answer[4]);
									rent_owed.put(Integer.valueOf(answer[3]), Integer.valueOf(answer[2]));
								} else {
									position_info.put("name", answer[2]);
									position_info.put("price", answer[3]);
									position_info.put("baseRent", answer[4]);
								}
							} else if (answer[0].equals("Tax")) {
								position_info.put("positionType", "taxes");
								position_info.put("taxAmount", answer[1]);
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
							message_sent = true;
						} 

						if (player_info.get("messageType").equals("Bye")){
							System.out.println(playerName + "'s turn is over");
							playerTurn++;
							if (playerTurn == maxPlayers) {
								playerTurn = 0;
							}
							turn_sent = false;
							message_sent = false;
						}
					} 
					if (br.ready()) {
						line = br.readLine();
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
