import java.net.ServerSocket;
import java.net.Socket;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.io.*;

public class Server {

	private static final int port = 2222;
	private static final int maxPlayers = 4;
	private static int currentPlayers = 0;

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
				if (currentPlayers != maxPlayers) {
					playerSocket = server.accept();
					System.out.println("New player connected");
					currentPlayers++;
					ServerThread st = new ServerThread(playerSocket);
					st.start();
				} else {
					System.out.println("Too many players connected");
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
	private int playerTurn = 0;
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	private PrintStream ps = null;
	private Socket sock = null;
	private static int ids = 0;
	private static String[] player_names;
	private static boolean started = false;
	private static boolean game_over = false;
	private JSONParser parser = new JSONParser();
	private static JSONObject players = new JSONObject();
	//private Board board;

	public ServerThread(Socket sock) {
		this.sock = sock;
		playerID = ids;
		ids++;
	}

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
				while (!started) {
					if (playerName == null) {
						playerName = (String)player_info.get("message");
						players.put(String.valueOf(playerID), playerName);
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
						System.out.println("Waiting for game to start...");
					}
					while (!started) {
						if(player_info.get("messageType") == "start") {
							System.out.println("Monopoly beginning now...");
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
				}
				/*while(!game_over) {
					if(playerID == playerTurn){
	
					}

				}*/
			} catch (Exception e) {
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