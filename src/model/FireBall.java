package model;

public class FireBall extends MapObject {
	
	private boolean hit, remove;

	public FireBall(TileMap tm, boolean right) {
		
		super(tm);
		moveSpeed = 3.8;
		if (right)
			dx = moveSpeed;
		else
			dx = -moveSpeed;
		
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;	
	}

	public void setHit() {
		if (hit)
			return;
		hit = true;
		dx = 0;
	}

	public boolean shouldRemove() {
		return remove;
	}

	public void update() {
		checkTileMapCollision();
		if (x <= 0 || x >= tileMap.getWidth() - width)
			setHit();
		setPosition(xtemp, ytemp);
		if (dx == 0 && !hit) {
			setHit();
		}
		if (hit) {
			remove = true;
		}
	}
}