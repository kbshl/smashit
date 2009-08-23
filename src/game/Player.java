package game;

public class Player {
	
	private String name;
	
	public Player(String name){
		this.name = name;
	}
	
	public Position getPosition(){
		return new Position(1,2);
	}

}
