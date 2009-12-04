// package network;
//
//import java.io.BufferedReader;
//
//public class ServerDataReceiver extends Thread{
//	
//	private BufferedReader sockIn;
//	private FullPlayer p;
//	private byte daten;
//	private int pfeil, aufAb;
//	
//	public ServerDataReceiver(FullPlayer p, BufferedReader sockIn){
//		this.p = p;
//		this.sockIn = sockIn;
//		
//		start();
//	}
//	
//	public void run(){
//	
//		while(true){
//			
//			try{
//				int i;
//				i =  sockIn.read();
//				System.out.println(Integer.toBinaryString(i));
//				
//				
//			}catch(Exception e){
//				System.out.println("fehler beim empfangen");
//			}
//			
//			pfeil = (daten >> 6) & 3;
//			aufAb = (daten >> 5) & 1;
//			
//			
//				
//			if(aufAb == 0){ //realeased
//				//System.out.println(inputParts[2] + "KeyReleased");
//				p.keyReleased(pfeil);
//			}
//			else{
//				//System.out.println(inputParts[2] + "Pressed");
//				p.keyPressed(pfeil);
//			}
//				
//			
//			
//			
//			
//			
//		}
//	}
//
//}

 package network;

import java.io.BufferedReader;

public class ServerDataReceiver extends Thread{
	
	private BufferedReader sockIn;
	private FullPlayer p;
	
	public ServerDataReceiver(FullPlayer p, BufferedReader sockIn){
		this.p = p;
		this.sockIn = sockIn;
		
		start();
	}
	
	public void run(){
		String input = "";
		String inputParts[];
		while(true){
			
			try{
				input = sockIn.readLine();
			}catch(Exception e){
				
			}
			//System.out.println("Beim Server kam an" + input);
			inputParts = input.split(":");
			
			if(inputParts[0].equals("Move")){
				
				if(Integer.parseInt(inputParts[2]) == 0){ //realeased
					//System.out.println(inputParts[2] + "KeyReleased");
					p.keyReleased(Integer.parseInt(inputParts[1]));
				}
				else{
					//System.out.println(inputParts[2] + "Pressed");
					p.keyPressed(Integer.parseInt(inputParts[1]));
				}
				
			}
			
			
			
			
		}
	}

}
