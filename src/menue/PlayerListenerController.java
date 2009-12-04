package menue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
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
	private int connectCount, maxPlayer;
	
	public PlayerListenerController(CreateNetworkGameMenue cNGM, int maxPlayer){
		this.cNGM = cNGM;
		vtr_playerNames = new Vector();
		sockInOut = new Object[8][3];
		connectCount = 0;
		this.maxPlayer = maxPlayer-1;
		this.start();
		
	}
	
	public void run(){
		
		try{
			socket = new ServerSocket(7777);
		}
		catch(Exception e){
			System.out.println("Starten von Server fehlgeschlagen weil: " + e);
			
		}
		System.out.println("Server gestartet");
		try{
			while(!this.isInterrupted() || connectCount > maxPlayer){
				clientSocket = socket.accept();
				
				System.out.println("Client " + clientSocket.getInetAddress() + " hat sich angemeldet");
				
				sockOut = new PrintWriter(clientSocket.getOutputStream(), true);
			    sockIn =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			    
			    sockInOut[connectCount][0] = sockIn;
			    sockInOut[connectCount][1] = sockOut;
			    //new DatagramPacket (null, 0,0,clientSocket.getInetAddress(), 7777);
			    
			    sockInOut[connectCount][2] = new DatagramPacket(new byte[64],64, clientSocket.getInetAddress(), 80);
			    //sockInOut[connectCount][2] = clientSocket.getOutputStream();
			    
			    cNGM.setPlayerData(sockInOut);
			    
			    //namen empfangen
			    vtr_playerNames.add(sockIn.readLine());
				cNGM.setPlayerList(vtr_playerNames);
				connectCount++;
			}
		}
		catch(Exception e){
			System.out.println("Server wurde geschlossen");
		}
		
	}
	
	public void close(){
		try {
			
			
			
			socket.close();
			
			
		} catch (IOException e) {
			System.out.println("Socket konnte nicht geschlossen werden");
		}
	}

}
