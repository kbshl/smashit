package network;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientPositionSender {
	
	private PrintWriter sockOut;
	
	public ClientPositionSender(PrintWriter sockOut){
		this.sockOut = sockOut;
	}
	
	
	
	public void sendPosition(String s){
		
		sockOut.println(s);
	}

}
