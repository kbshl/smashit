package game;

public class PlayerStats implements Comparable<PlayerStats> {
	private String name;
	private int kills;
	private int lifes;
	private int points;

	public PlayerStats(String n, int k, int l) {
		name = n;
		lifes = l;
		kills = k;
		points = kills + lifes;
	}
	
	  public int compareTo(PlayerStats o) {
	        return o.getPoints()- points ;
	    }

	public String getName() {
		return name;
	}

	public int getKills() {
		return kills;
	}

	public int getLifes() {
		return lifes;
	}

	public int getPoints() {
		return points;
	}
}
