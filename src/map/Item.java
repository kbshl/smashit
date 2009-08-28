package map;

import game.Finals;

public class Item extends Sprite implements Finals {
	
	private int ability;
	private boolean removeable;
	
	public Item(int x, int y){
		super(x, y, new String[] { "item.gif" }, true);
		ability = JUMP_HIGH;
	}

	public int getAbility(){
		return ability;
	}
	
	public boolean isRemoveable(){
		return removeable;
	}
	
	public void collected(){
		removeable = true;
		y = 540;
	}
}
