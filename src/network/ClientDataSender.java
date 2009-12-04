//package network;
//
//import java.io.PrintWriter;
//
//
//public class ClientDataSender {
//	
//	
//	private byte btemp, daten;
//	private int i;
//	private PrintWriter sockOut;
//	
//	public ClientDataSender(PrintWriter sockOut){
//		this.sockOut = sockOut;
//	
//	}
//	
//	public void sendPosition(String s, int pfeil, int aufAb){
//		
//		daten = (byte) (pfeil << 6);
//		btemp = (byte) (aufAb << 5);
//		daten = (byte) ((daten | btemp) );
//		
//		
//		sockOut.write(daten); //write
//		sockOut.flush();
//		
//		System.out.println("gesendet wurde" + Integer.toBinaryString(daten));
//		
//	}
//	
//	
//
//}



package network;

import java.io.PrintWriter;


public class ClientDataSender {
	
	private PrintWriter sockOut;
	
	public ClientDataSender(PrintWriter sockOut){
		this.sockOut = sockOut;
	}
	
	public void sendPosition(String s, int pfeil, int aufAb){
		
		
		
		sockOut.println(s+":" + pfeil + ":"+ aufAb);
	}
	
	

}
