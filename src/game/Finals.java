package game;

import java.awt.Font;

public interface Finals {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int GAME_HEIGHT = 550;
	public static final int GAME_PAUSE = 8;
	
	public static final String PIC_PATH = "resource/pics/";
	public static final String SOUND_PATH = "resource/sounds/";
	public static final String MAP_PATH_INTERN = "resource/maps/";
	public static final String MAP_PATH_EXTERN = "maps/";
	
	public static final String DB_SERVER = "localhost";
	public static final String DB_NAME = "smashit";
	public static final String DB_USER = "kostja";
	public static final String DB_PASSWORD = "vologda";
	public static final String HIGHSCORE_URL = "http://localhost/SWT-Projektseite/web/highscore.php";
	
	public static final String JUMP_SOUND = "jump.wav";
	public static final String DEAD_SOUND = "dead.wav";
	public static final String ITEM_SOUND = "item.wav";
	public static final String BACKGROUND_SOUND = "music.mid";
	
	public static final Font GAME_FONT = new Font("Monospaced", Font.BOLD, 12);
	
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	public static final int PLAYER3 = 3;
	public static final int PLAYER4 = 4;
	
	
	public static final int JUMP_LOW = 0;
	public static final int JUMP_HIGH = 1;
	public static final int MOVE_FAST = 2;
	public static final int MOVE_SLOW = 3;
}
