package map;

import game.Finals;

public class Item extends Sprite implements Finals {
	
	private int ability;
	private boolean removeable;
	private double pastAniTime = 0, aniTime = 0.25;
	
	public Item(int x, int y){
		super(x, y, new String[] { "item1.gif", "item2.gif","item3.gif","item4.gif" }, true);
		
		ability = (int) (Math.random()*4);
	}
	
	public void act(long delay) {
		pastAniTime += (delay / 1e9);
		if(pastAniTime >= aniTime){
			currentFrame = 1;
		}
		if(pastAniTime >= 2*aniTime){
			currentFrame = 2;
		}
		if(pastAniTime >= 3*aniTime){
			currentFrame = 3;
		}
		if(pastAniTime >= 4*aniTime){
			currentFrame = 2;
		}
		if(pastAniTime >= 5*aniTime){
			currentFrame = 1;
		}
		if(pastAniTime >= 6*aniTime){
			pastAniTime = 0;
			currentFrame = 0;
		}
		
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
