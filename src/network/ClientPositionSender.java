package network;

import java.io.PrintWriter;


public class ClientPositionSender {
	
	private PrintWriter sockOut;
	
	public ClientPositionSender(PrintWriter sockOut){
		this.sockOut = sockOut;
	}
	
	
	
	public void sendPosition(String s){
		
		sockOut.println(s);
	}

}
