package menue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class PlayerListenerController extends Thread{
	
	private CreateNetworkGameMenue cNGM;
	private ServerSocket socket;
	private Socket clientSocket;
	private BufferedReader sockIn = null;
	private PrintWriter sockOut = null;
	private Vector<String> vtr_playerNames;
	
	private Object[][] sockInOut;
	private int connectCount;
	
	public PlayerListenerController(CreateNetworkGameMenue cNGM){
		this.cNGM = cNGM;
		vtr_playerNames = new Vector();
		sockInOut = new Object[8][3];
		connectCount = 0;
		
		this.start();
		
	}
	
	public void run(){
		
		try{
			socket = new ServerSocket(cNGM.getPort());
		}
		catch(Exception e){
			System.out.println("Starten von Server fehlgeschlagen weil: " + e);
			
		}
		System.out.println("Server gestartet");
		try{
			while(!this.isInterrupted() || connectCount > 7){
				clientSocket = socket.accept();
				
				System.out.println("Client " + clientSocket.getInetAddress() + " hat sich angemeldet");
				
				sockOut = new PrintWriter(clientSocket.getOutputStream(), true);
			    sockIn =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			    
			    sockInOut[connectCount][0] = sockIn;
			    sockInOut[connectCount][1] = sockOut;
			    sockInOut[connectCount][2] = clientSocket.getOutputStream();
			    
			    cNGM.setPlayerData(sockInOut);
			    
			    //namen empfangen
			    vtr_playerNames.add(sockIn.readLine());
				cNGM.setPlayerList(vtr_playerNames);
				connectCount++;
			}
		}
		catch(Exception e){
			System.out.println("Fehler" +e);
		}
		
	}

}
