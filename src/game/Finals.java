package game;

import java.awt.Font;

public interface Finals {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final int GAME_HEIGHT = 550;
	public static final int GAME_PAUSE = 10;
	public static final String PIC_PATH = "resource/pics/";
	public static final String SOUND_PATH = "resource/sounds/";
	public static final String MAP_PATH = "src/resource/maps/";
	
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
