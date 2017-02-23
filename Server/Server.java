import java.net.ServerSocket;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.io.*;

public class Server {

	// The sockets
	private static ServerSocket serverSocket = null;
	private static Socket playerSocket = null;

	// The max number of players allowed
	private static final int maxPlayers = 4;
	private static final playerThread[] threads = new playerThread[maxPlayers];

	public static void main(String[] args) {
		int portNumber = 2222;
		if (args.length < 1) {
			System.out.println("Server is starting\n"
				+ "Listening to the port: " + portNumber);
		} else {
			portNumber = Integer.valueOf(args[0]).intValue();
		}

		// Open server socket on portNumber
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}

		// Create a player socket for each connection
		while(true) {
			try {
				playerSocket = serverSocket.accept();
				int id = 0;
				for (id = 0; id < maxPlayers; id++) {
					if (threads[id] == null) {
						(threads[id] = new playerThread(playerSocket, threads)).start();
						break;
					}
				}
				if (id == maxPlayers) {
					String returnMessage = "Too many players, wait for a new game";
					OutputStream os = playerSocket.getOutputStream();
	                OutputStreamWriter osw = new OutputStreamWriter(os);
	                BufferedWriter bw = new BufferedWriter(osw);
	                bw.write(returnMessage);
	                System.out.println("Message sent to the client is "+returnMessage);
	                bw.flush();
	                playerSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}


class playerThread extends Thread {
	private String playerName;
	private int playerID;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	private OutputStream os;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	private PrintStream ps;
	private Socket playerSocket = null;
	private int maxPlayers;
	private final playerThread[] threads;
	private JSONParser parser = new JSONParser();
	private JSONObject players = new JSONObject();
	private int playerTurn = 0;
	private Board board;

	public playerThread(Socket playerSocket, playerThread[] threads) {
		this.playerSocket = playerSocket;
		this.threads = threads;
		this.maxPlayers = threads.length;
	}

	public void run() {
		board = new Board();
		try {

			is = playerSocket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			os = playerSocket.getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
			ps = new PrintStream(os);

			while (true) {
				String info = br.readLine();
				try {
					Object obj = parser.parse(info);
					JSONObject player_info = (JSONObject)obj;
	         		playerName = (String) player_info.get("username");
	         		playerID = (int) player_info.get("id");
	         		break;
				} catch (Exception e) {
					System.out.println(e);
				}
			}

			synchronized (this) {
				for (int i = 0; i < maxPlayers; i++) {
					// You joining the game
					if (threads[i] != null && threads[i] == this) {
						threads[i].ps.println(playerName + " has joined the game");
						players.put(String.valueOf(playerID), playerName);
						break;
					}
				}
				for (int i = 0; i < maxPlayers; i++) {
					// Other player join the game
					if (threads[i] != null && threads[i] != this) {
						threads[i].ps.println("Player " + playerID + " has joined the game");
						StringWriter out = new StringWriter();
      					players.writeJSONString(out);
      					String jsonText = out.toString();
						bw.write(jsonText);
						bw.flush();
					}
		        }
			}

			// start listening to 
			while (true) {
				String game_info = br.readLine();
				try {
					Object obj = parser.parse(game_info);
					JSONObject game_update = (JSONObject)obj;
					// To quit the game
			        if (game_update.get("status").equals("quit") && playerTurn == playerID) {
			          break;
			        }

			        // players turn over, change playerTurn
			        if (game_update.get("status").equals("over") && playerTurn == playerID) {
			        	playerTurn++;
	    				if (playerTurn == maxPlayers) {
			        		playerTurn = 0;
			        	}
			        	synchronized (this) {
			        		for (int i = 0; i < maxPlayers; i++) {
			        			if (threads[i] != null && threads[i] != this
			        				&& threads[i].playerID == playerTurn) {
			        				threads[i].ps.println("Your Turn!");
			        				this.ps.println("Turn Over - Confirmed");
			        				break;
			        			}
			        		}
			        	}
			        } else {
			        	// check players request
			        	if (playerTurn == playerID) {
			        		if (game_update.get("status").equals("check")) {
			        			String sqaure_info = board.check_square((int)game_update.get("position"));
			        		} else if (game_update.get("status").equals("buy")) {
			        			board.buy((int)game_update.get("position"), playerID);
			        		}
			        	}
			        }
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			ps.println(playerName + " has quit the game");
			synchronized (this) {
				for (int i = 0; i < maxPlayers; i++) {
					if (threads[i] == this) {
						threads[i] = null;
					}
				}
			}
			br.close();
			os.close();
			playerSocket.close();
		} catch (IOException e){
			System.out.println(e);
		}
	}
}