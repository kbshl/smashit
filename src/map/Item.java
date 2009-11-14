package map;

import game.Finals;

public class Item extends Sprite implements Finals {

	private int ability;
	private boolean removeable;
	private double pastAniTime = 0, aniTime = 0.15;
	private String itemImage;

	public Item(int x, int y) {
		super(x, y, new String[] { "item1.gif", "item2.gif", "item3.gif",
				"item4.gif" }, true);

		ability = (int) (Math.random() * 4);

		switch (ability) {
		case JUMP_LOW:
			itemImage = "item_jumplow.gif";
			break;
		case JUMP_HIGH:
			itemImage = "item_jumphigh.gif";
			break;
		case MOVE_FAST:
			itemImage = "item_movefast.gif";
			break;
		case MOVE_SLOW:
			itemImage = "item_moveslow.gif";
			break;
		default:
			break;

		}
	}
	//brauch ich fürs netzwerk
	public Item(int x, int y, int ability) {
		super(x, y, new String[] { "item1.gif", "item2.gif", "item3.gif",
				"item4.gif" }, true);

		this.ability = ability;

		switch (ability) {
		case JUMP_LOW:
			itemImage = "item_jumplow.gif";
			break;
		case JUMP_HIGH:
			itemImage = "item_jumphigh.gif";
			break;
		case MOVE_FAST:
			itemImage = "item_movefast.gif";
			break;
		case MOVE_SLOW:
			itemImage = "item_moveslow.gif";
			break;
		default:
			break;

		}
	}

	public void act(long delay) {
		pastAniTime += (delay / 1e9);
		
		if (pastAniTime >= aniTime) {
			currentFrame = 1;
		}
		if (pastAniTime >= 2 * aniTime) {
			currentFrame = 2;
		}
		if (pastAniTime >= 3 * aniTime) {
			currentFrame = 3;
		}
		if (pastAniTime >= 4 * aniTime) {
			currentFrame = 2;
		}
		if (pastAniTime >= 5 * aniTime) {
			currentFrame = 1;
		}
		if (pastAniTime >= 6 * aniTime) {
			pastAniTime = 0;
			currentFrame = 0;
		}

	}

	public int getAbility() {
		return ability;
	}

	public boolean isRemoveable() {
		return removeable;
	}

	public void collected() {
		removeable = true;
		y = 650;
	}
	
	public String getItemImage(){
		return itemImage;
	}

}
