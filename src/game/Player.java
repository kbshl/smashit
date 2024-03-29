package game;

import java.awt.event.KeyEvent;

import manager.PictureManager;
import manager.SoundManager;
import map.Sprite;
import map.Map;
import map.Item;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class Player extends Sprite implements KeyListener, Finals {

	private String name;
	private final int leftKey, rightKey, jumpKey;
	private Map map;
	private Vector<Player> players;

	private boolean jumpLock = false, left = false, right = false,
			isDead = false;
	private double moveTime = 0.007, pastMoveTime = 0, aniTime = 0.15,
			pastAniTime = 0, jumpTime = 0.005, pastJumpTime = 0, gravity = 0.1,
			jumpSpeed = 0, jumpStart = -4, pastItemTime = 0;
	private int jumpCount = 0, jumpSkill = 2, lifes = 10, kills = 0, playerNumber;
	private Sprite collisionObject;
	private Item item;

	public Player(String n, int playerNum, Map m, Vector<Player> p, int l) {
		super(0, 0, new String[] { "kirby_rosa_rechts_1.gif",
				"kirby_rosa_rechts_2.gif", "kirby_rosa_rechts_3.gif",
				"kirby_rosa_rechts_4.gif", "kirby_rosa_rechts_5.gif",
				"kirby_rosa_jump_rechts.gif", "kirby_rosa_fall_rechts.gif",

				"kirby_rosa_links_1.gif", "kirby_rosa_links_2.gif",
				"kirby_rosa_links_3.gif", "kirby_rosa_links_4.gif",
				"kirby_rosa_links_5.gif", "kirby_rosa_jump_links.gif",
				"kirby_rosa_fall_links.gif" }, false);
		name = n;
		map = m;
		players = p;
		lifes = l;

		switch (playerNum) {
		case PLAYER1:
			playerNumber = 1;
			leftKey = KeyEvent.VK_A;
			rightKey = KeyEvent.VK_D;
			jumpKey = KeyEvent.VK_W;
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
			leftKey = KeyEvent.VK_J;
			rightKey = KeyEvent.VK_L;
			jumpKey = KeyEvent.VK_I;
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
			leftKey = KeyEvent.VK_LEFT;
			rightKey = KeyEvent.VK_RIGHT;
			jumpKey = KeyEvent.VK_UP;
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
			leftKey = KeyEvent.VK_NUMPAD1;
			rightKey = KeyEvent.VK_NUMPAD3;
			jumpKey = KeyEvent.VK_NUMPAD5;
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
			leftKey = KeyEvent.VK_LEFT;
			rightKey = KeyEvent.VK_RIGHT;
			jumpKey = KeyEvent.VK_UP;
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

						} else {
							currentFrame = 7;
							pastAniTime = 0;
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

						} else {
							currentFrame = 0;
							pastAniTime = 0;
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
					} else {
						if (jumpSpeed >= 1.2) {
							if (currentFrame < 7) {
								currentFrame = 0;
							} else {
								currentFrame = 7;
							}
						}
						y = collisionObject.getY() - height;
						jumpCount = 0;
						jumpSpeed = 0;
						if (collisionObject instanceof Player) {
							((Player) collisionObject).getKilled();
							kills++;
						}
					}
				}

				if (jumpSpeed < 0) {

					if (currentFrame < 7) {
						currentFrame = 5;
					} else {
						currentFrame = 12;
					}

					if (!checkCollision(0, (int) jumpSpeed)) {
						y += (int) jumpSpeed;
						jumpSpeed += gravity;

					} else {
						y = collisionObject.getY()
								+ collisionObject.getHeight();
						jumpSpeed = 0;

					}
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == jumpKey) {
			if (!jumpLock) {
				jumpLock = true;

				if (jumpCount < jumpSkill) {
					SoundManager.getSoundManager().playSound(Finals.JUMP_SOUND);
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

	public void keyReleased(KeyEvent e) {
		int i = e.getKeyCode();
		if (i == jumpKey) {
			jumpLock = false;
		}
		if (i == leftKey) {
			left = false;
			currentFrame = 7;
		}
		if (i == rightKey) {
			right = false;
			currentFrame = 0;
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
		for (Player p : players) {
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
		SoundManager.getSoundManager().playSound(Finals.DEAD_SOUND);
		looseItem();

		if (lifes > 1) {
			lifes--;
			setNewPosition();
		} else {
			lifes--;
			isDead = true;
			x = -100;
			y = 0;
		}
	}

	private void getItem(Item item) {
		SoundManager.getSoundManager().playSound(Finals.ITEM_SOUND);
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
	
	private void looseItem(){
		pastItemTime = 0;
		item = null;
		moveTime = 0.007;
		jumpSkill = 2;
		aniTime = 0.15;
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

}
