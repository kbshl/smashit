package network;

import game.Finals;

import game.PlayerStats;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observer;
import java.util.Vector;

import manager.PictureManager;
import manager.SoundManager;
import map.Item;
import map.Map;
import map.Sprite;

public class FullPlayer extends Sprite implements KeyListener, Finals{
	//private int playerNumber;
	private String name;
	private final int leftKey, rightKey, jumpKey;
	private Map map;
	private Vector<FullPlayer> players;
	private boolean realPlayer;
	private ClientDataSender cPS;
	
	private boolean jumpLock = false, left = false, right = false,
	isDead = false;
private double moveTime = 0.007, pastMoveTime = 0, aniTime = 0.15,
	pastAniTime = 0, jumpTime = 0.005, pastJumpTime = 0, gravity = 0.1,
	jumpSpeed = 0, jumpStart = -4, pastItemTime = 0;
private int jumpCount = 0, jumpSkill = 2, lifes = 10, kills = 0,
	lostLifes = 0, playerNumber;//player f�gt bei 1 an = doof
	private Sprite collisionObject;
	
	private int oldFrame = 0, oldY;

	private Item item;
	
	private ServerDataSender sPS;

	public FullPlayer(String n, int playerNumber, Map m, Vector<FullPlayer> p, boolean realPlayer, ClientDataSender cPS, int lifes, ServerDataSender sPS) {
		super(0, 0, new String[] { "kirby_rosa_rechts_1.gif",
				"kirby_rosa_rechts_2.gif", "kirby_rosa_rechts_3.gif",
				"kirby_rosa_rechts_4.gif", "kirby_rosa_rechts_5.gif",
				"kirby_rosa_jump_rechts.gif", "kirby_rosa_fall_rechts.gif",

				"kirby_rosa_links_1.gif", "kirby_rosa_links_2.gif",
				"kirby_rosa_links_3.gif", "kirby_rosa_links_4.gif",
				"kirby_rosa_links_5.gif", "kirby_rosa_jump_links.gif",
				"kirby_rosa_fall_links.gif" }, false);
		this.realPlayer = realPlayer;
		this.playerNumber = playerNumber;
		this.lifes = lifes;
		this.sPS = sPS;
		name = n;
		map = m;
		players = p;
		this.cPS = cPS;
		
		leftKey = KeyEvent.VK_LEFT;
		rightKey = KeyEvent.VK_RIGHT;
		jumpKey = KeyEvent.VK_UP;
		
		switch (playerNumber) {
		case PLAYER1:
			playerNumber = 1;
//			leftKey = KeyEvent.VK_A;
//			rightKey = KeyEvent.VK_D;
//			jumpKey = KeyEvent.VK_W;
			images = new String[] { "kirby_rosa_rechts_1.gif",
					"kirby_rosa_rechts_2.gif", "kirby_rosa_rechts_3.gif",
					"kirby_rosa_rechts_4.gif", "kirby_rosa_rechts_5.gif",
					"kirby_rosa_jump_rechts.gif", "kirby_rosa_fall_rechts.gif",

					"kirby_rosa_links_1.gif", "kirby_rosa_links_2.gif",
					"kirby_rosa_links_3.gif", "kirby_rosa_links_4.gif",
					"kirby_rosa_links_5.gif", "kirby_rosa_jump_links.gif",
					"kirby_rosa_fall_links.gif" };
			break;
		case PLAYER2:
			playerNumber = 2;
//			leftKey = KeyEvent.VK_J;
//			rightKey = KeyEvent.VK_L;
//			jumpKey = KeyEvent.VK_I;
			images = new String[] { "kirby_blau_rechts_1.gif",
					"kirby_blau_rechts_2.gif", "kirby_blau_rechts_3.gif",
					"kirby_blau_rechts_4.gif", "kirby_blau_rechts_5.gif",
					"kirby_blau_jump_rechts.gif", "kirby_blau_fall_rechts.gif",

					"kirby_blau_links_1.gif", "kirby_blau_links_2.gif",
					"kirby_blau_links_3.gif", "kirby_blau_links_4.gif",
					"kirby_blau_links_5.gif", "kirby_blau_jump_links.gif",
					"kirby_blau_fall_links.gif" };
			break;
		case PLAYER3:
			playerNumber = 3;
//			leftKey = KeyEvent.VK_LEFT;
//			rightKey = KeyEvent.VK_RIGHT;
//			jumpKey = KeyEvent.VK_UP;
			images = new String[] { "kirby_gr�n_rechts_1.gif",
					"kirby_gr�n_rechts_2.gif", "kirby_gr�n_rechts_3.gif",
					"kirby_gr�n_rechts_4.gif", "kirby_gr�n_rechts_5.gif",
					"kirby_gr�n_jump_rechts.gif", "kirby_gr�n_fall_rechts.gif",

					"kirby_gr�n_links_1.gif", "kirby_gr�n_links_2.gif",
					"kirby_gr�n_links_3.gif", "kirby_gr�n_links_4.gif",
					"kirby_gr�n_links_5.gif", "kirby_gr�n_jump_links.gif",
					"kirby_gr�n_fall_links.gif" };
			break;
		case PLAYER4:
			playerNumber = 4;
//			leftKey = KeyEvent.VK_NUMPAD1;
//			rightKey = KeyEvent.VK_NUMPAD3;
//			jumpKey = KeyEvent.VK_NUMPAD5;
			images = new String[] { "kirby_rot_rechts_1.gif",
					"kirby_rot_rechts_2.gif", "kirby_rot_rechts_3.gif",
					"kirby_rot_rechts_4.gif", "kirby_rot_rechts_5.gif",
					"kirby_rot_jump_rechts.gif", "kirby_rot_fall_rechts.gif",

					"kirby_rot_links_1.gif", "kirby_rot_links_2.gif",
					"kirby_rot_links_3.gif", "kirby_rot_links_4.gif",
					"kirby_rot_links_5.gif", "kirby_rot_jump_links.gif",
					"kirby_rot_fall_links.gif" };
			break;
		default:
			playerNumber = 1;
//			leftKey = KeyEvent.VK_LEFT;
//			rightKey = KeyEvent.VK_RIGHT;
//			jumpKey = KeyEvent.VK_UP;
			images = new String[] { "kirby_blau_rechts_1.gif",
					"kirby_blau_rechts_2.gif", "kirby_blau_rechts_3.gif",
					"kirby_blau_rechts_4.gif", "kirby_blau_rechts_5.gif",
					"kirby_blau_jump_rechts.gif", "kirby_blau_fall_rechts.gif",

					"kirby_blau_links_1.gif", "kirby_blau_links_2.gif",
					"kirby_blau_links_3.gif", "kirby_blau_links_4.gif",
					"kirby_blau_links_5.gif", "kirby_blau_jump_links.gif",
					"kirby_blau_fall_links.gif" };
			break;
		}
		setNewPosition();

	}

	public void act(long delay) {
		pastAniTime += (delay / 1e9);

		if (item != null) {
			pastItemTime += (delay / 1e9);

			if (pastItemTime >= 20) {
				looseItem();
			}
		}

		if (left || right) {
			pastMoveTime += (delay / 1e9);

			if (pastMoveTime >= moveTime) {
				int i = (int) (pastMoveTime / moveTime);
				pastMoveTime -= i * moveTime;

				for (int k = 0; k < i; ++k) {
					if (left) {
						if (!checkCollision(-1, 0)) {
							x -= 1;
							

							if (pastAniTime >= 0) {
								currentFrame = 8;
							}
							if (pastAniTime >= 1 * aniTime) {
								currentFrame = 9;
							}
							if (pastAniTime >= 2 * aniTime) {
								currentFrame = 10;
							}
							if (pastAniTime >= 3 * aniTime) {
								currentFrame = 11;
							}
							if (pastAniTime >= 4 * aniTime) {
								currentFrame = 10;
							}
							if (pastAniTime >= 5 * aniTime) {
								currentFrame = 9;
								pastAniTime = 0;
							}
							
							/////
							if(sPS != null){
								sPS.sendPosition(x, y, (playerNumber-1));
								
							}
			
						} else {
							currentFrame = 7;
							pastAniTime = 0;
						}
						if(sPS != null){
							sPS.sendCurrentFrame(playerNumber-1, currentFrame);
						}
					}

					if (right) {
						if (!checkCollision(1, 0)) {
							x += 1;
							
							
							if (pastAniTime >= 0) {
								currentFrame = 1;
							}
							if (pastAniTime >= 1 * aniTime) {
								currentFrame = 2;
							}
							if (pastAniTime >= 2 * aniTime) {
								currentFrame = 3;
							}
							if (pastAniTime >= 3 * aniTime) {
								currentFrame = 4;
							}
							if (pastAniTime >= 4 * aniTime) {
								currentFrame = 3;
							}
							if (pastAniTime >= 5 * aniTime) {
								currentFrame = 2;
								pastAniTime = 0;
							}
							/////
							if(sPS != null){
								sPS.sendPosition(x, y, (playerNumber-1));
								
							}

						} else {
							currentFrame = 0;
							pastAniTime = 0;
						}
						if(sPS != null){
							sPS.sendCurrentFrame(playerNumber-1, currentFrame);
						}
					}
				}
			}
		}

		pastJumpTime += (delay / 1e9);
		if (pastJumpTime >= jumpTime) {
			int i = (int) (pastJumpTime / jumpTime);
			pastJumpTime -= i * jumpTime;

			for (int k = 0; k < i; ++k) {
				if (jumpSpeed >= 0) {

					if (jumpSpeed >= 1.2) {
						if (currentFrame < 7) {
							currentFrame = 6;
						} else {
							currentFrame = 13;
						}
					}

					if (!checkCollision(0, (int) jumpSpeed)) {
						y += (int) jumpSpeed;
						jumpSpeed += gravity;
						/////
						if(sPS != null){
							if(y != oldY){
								sPS.sendPosition(x, y, (playerNumber-1));
								oldY = y;
							}	
						}
					} else {
						if (jumpSpeed >= 1.2) {
							if (currentFrame < 7) {
								currentFrame = 0;
							} else {
								currentFrame = 7;
							}
						}
						y = collisionObject.getY() - height;
						/////
						if(sPS != null){
							if(y != oldY){
								sPS.sendPosition(x, y, (playerNumber-1));
								oldY = y;
							}	
						}
						jumpCount = 0;
						jumpSpeed = 0;
						if (collisionObject instanceof FullPlayer) {
							((FullPlayer) collisionObject).getKilled();
							kills++;
							if(sPS != null){
								sPS.sendAct("kill", playerNumber-1);
							}
						}
					}
					if(sPS != null){
						if(currentFrame != oldFrame){
							sPS.sendCurrentFrame(playerNumber-1, currentFrame);
							oldFrame = currentFrame;
						}
					}
				}

				if (jumpSpeed < 0) {

					if (currentFrame < 7) {
						currentFrame = 5;
					} else {
						currentFrame = 12;
					}
					if(sPS != null){
						if(currentFrame != oldFrame){
							sPS.sendCurrentFrame(playerNumber-1, currentFrame);
							oldFrame = currentFrame;
						}
					}

					if (!checkCollision(0, (int) jumpSpeed)) {
						y += (int) jumpSpeed;
						jumpSpeed += gravity;
						/////
						if(sPS != null){
							if(y != oldY){
								sPS.sendPosition(x, y, (playerNumber-1));
								oldY = y;
							}	
						}

					} else {
						y = collisionObject.getY()
								+ collisionObject.getHeight();
						jumpSpeed = 0;
						/////
						if(sPS != null){
							if(y != oldY){
								sPS.sendPosition(x, y, (playerNumber-1));
								oldY = y;
							}	
						}

					}
				}
			}
		}
		
		
	}
	
	
	
	public void keyPressed(KeyEvent e) {
		
		int i = e.getKeyCode();
		if(realPlayer){
			if (i == jumpKey) {
				if (!jumpLock) {
					jumpLock = true;
					if (jumpCount < jumpSkill) {
						SoundManager.getSoundManager().playSound("jump.wav");
						if(sPS != null){
							sPS.sendSound("jump");
						}
						jumpSpeed = jumpStart;
						jumpCount++;
					}
				}
			}
			if (i == leftKey) {
				left = true;
				right = false;
			}
			if (i == rightKey) {
				right = true;
				left = false;
			}
		}
		else{
			if (i == jumpKey) {
				cPS.sendPosition("Move", 3, 1);
			}
			if (i == leftKey) {
				cPS.sendPosition("Move", 1, 1);
			}
			if (i == rightKey) {
				cPS.sendPosition("Move", 2, 1);
			}
		}
		
		
	}
	
	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		if(realPlayer){
			if (i == jumpKey) {
				jumpLock = false;
			}
			if (i == leftKey) {
				left = false;
			}
			if (i == rightKey) {
				right = false;
			}
		}
		else{//Move:3:0
			if (i == jumpKey) {
				//cPS.sendPosition("Move:" + 3 + ":" + 0);
				cPS.sendPosition("Move", 3, 0);
			}
			if (i == leftKey) {
				//cPS.sendPosition("Move:" + 1 + ":" + 0);
				cPS.sendPosition("Move", 1, 0);
			}
			if (i == rightKey) {
				//cPS.sendPosition("Move:" + 2 + ":" + 0);
				cPS.sendPosition("Move", 2, 0);
			}
		}

	}
	
	//wird von ServerPositionReceiver aufgerufen
	//jump = 3 | left = 1 | right = 2;
	public void keyPressed(int i) {
		//int i = e.getKeyCode();
		if (i == 3) {
			if (!jumpLock) {
				jumpLock = true;
				if (jumpCount < jumpSkill) {
					SoundManager.getSoundManager().playSound("jump.wav");
					if(sPS != null){
						sPS.sendSound("jump");
					}
					jumpSpeed = jumpStart;
					jumpCount++;
				}
			}
		}
		if (i == 1) {
			left = true;
			right = false;
		}
		if (i == 2) {
			right = true;
			left = false;
		}
	}
	//jump = 3 | left = 1 | right = 2;
	public void keyReleased(int i) {
		//int i = e.getKeyCode();
		if (i == 3) {
			jumpLock = false;
		}
		if (i == 1) {
			left = false;
		}
		if (i == 2) {
			right = false;
		}
	}

	private boolean checkCollision(int nx, int ny) {
		boolean collision = false;
		Rectangle me = new Rectangle(x + nx, y + ny, width, height);
		for (Sprite s : map.getSprites()) {
			if (me.intersects(s.getBounds())) {
				if (!s.isWalkable()) {
					collision = true;
					collisionObject = s;
				} else {
					if (s instanceof Item) {
						getItem((Item) s);
					}
				}
			}
		}
		for (FullPlayer p : players) {
			if (p != this) {
				if (me.intersects(p.getBounds())) {
					collision = true;
					collisionObject = p;
				}
			}
		}
		return collision;
	}

	public void getKilled() {
		SoundManager.getSoundManager().playSound("dead.wav");
		if(sPS != null){
			sPS.sendAct("dead", this.playerNumber-1);
		}
		looseItem();
		if (lifes > 1) {
			lifes--;
			lostLifes++;
			setNewPosition();
		} else {
			lifes--;
			isDead = true;
			x = -100;
			y = 0;
		}
	}

	private void getItem(Item item) {
		SoundManager.getSoundManager().playSound("item.wav");
		if(sPS != null){
			sPS.sendSound("item");
			//nicht �ber send sound, sonder �ber collect idem
			
		}
		this.item = item;
		moveTime = 0.007;
		jumpSkill = 2;
		pastItemTime = 0;
		switch (item.getAbility()) {
		case JUMP_HIGH:
			jumpSkill = 3;
			break;
		case JUMP_LOW:
			jumpSkill = 1;
			break;
		case MOVE_FAST:
			moveTime = 0.003;
			aniTime = 0.1;
			break;
		case MOVE_SLOW:
			moveTime = 0.012;
			aniTime = 0.2;
			break;
		default:
			break;
		}
		
		if(sPS != null){
			//!!
			sPS.sendItem("Itemr",item.getX(),item.getY(), (playerNumber-1), item.getAbility());
			//sPS.sendItem("Itemr:" + item.getX() + ":" + item.getY() + ":" + (playerNumber-1) + ":" + item.getAbility());
		}
		item.collected();
	}

	private void setNewPosition() {
		boolean collision;
		y = 0;
		do {
			x = (int) (WINDOW_WIDTH * Math.random());
			y = (int) (GAME_HEIGHT * Math.random());
			collision = checkCollision(0, 0);
		} while (collision);
		
		oldY =y;
	}

	public void paint(Graphics g) {
		g.drawImage(PictureManager.getPictureManager().getImage(images[currentFrame]), x, y, null);

		if (isDead) {
			g.drawImage(PictureManager.getPictureManager().getImage("dead.gif"),
					35 + (playerNumber - 1) * 200, 562, null);
		} else {
			g.drawImage(PictureManager.getPictureManager().getImage(images[currentFrame]),
					35 + (playerNumber - 1) * 200, 562, null);
		}

		g.drawString(name + ": " + kills + "/" + lifes,
				65 + ((playerNumber - 1) * 200), 578);

		if (item != null) {
			g.drawImage(PictureManager.getPictureManager().getImage(item.getItemImage()),
					10 + (playerNumber - 1) * 200, 562, null);
		}

	}

	public PlayerStats getPlayerStats() {
		return new PlayerStats(name, kills, lifes);
	}

	public int getLifes() {
		return lifes;
	}

	public int getKills() {
		return kills;
	}

	public String getName() {
		return name;
	}

	public boolean isDead() {
		return isDead;
	}

	public void keyTyped(KeyEvent e) {
	}
	
	
	public void addKill(){
		this.kills = kills + 1;
	}
	
	public void looseItem(){
		pastItemTime = 0;
		item = null;
		moveTime = 0.007;
		jumpSkill = 2;
		aniTime = 0.15;
		
		if(sPS != null){
			sPS.sendAct("reIt", playerNumber-1);
		}
		
		//SPS act reIT
	}
	
	public void setCurrentFrame(int currentFrame){
		this.currentFrame = currentFrame;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
	
	
	
}