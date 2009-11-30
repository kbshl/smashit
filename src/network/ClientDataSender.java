package network;

import java.io.PrintWriter;


public class ClientDataSender {
	
	private PrintWriter sockOut;
	
	public ClientDataSender(PrintWriter sockOut){
		this.sockOut = sockOut;
	}
	
	
	
	public void sendPosition(String s){
		
		sockOut.println(s);
	}

}
