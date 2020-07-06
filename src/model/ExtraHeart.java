package model;

public class ExtraHeart extends MapObject {

	boolean heart;
	boolean dead;

	public ExtraHeart(TileMap tm) {
		
		super(tm);
		
		heart = true;
		
		width = 28;
		height = 28;
		cwidth = 40;
		cheight = 40;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean d) {
		this.dead = d;
	}
}
