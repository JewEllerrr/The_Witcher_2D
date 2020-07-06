package model;

public class Mage extends MapObject {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean flinching;
	protected long flinchTimer;

	public Mage(TileMap tm) {

		super(tm);

		moveSpeed = 0.3;
		maxSpeed = 0.1;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;

		width = 86;
		height = 94;
		cwidth = 80;
		cheight = 84;

		health = maxHealth = 4;
		damage = 1;

	}

	private void getNextPosition() {
		// movement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		// falling
		if (falling) {
			dy += fallSpeed;
		}
	}
	
	public boolean isDead() {
		return dead;
	}

	public int getDamage() {
		return damage;
	}

	public void hit(int damage) {
		if (dead || flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void kill() {
		dead = true;
	}

	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 200) {
				flinching = false;
			}
		}
		
		// if it hits a wall, go other direction
		if (right && dx == 0) {
			right = false;
			left = true;
		} else if (left && dx == 0) {
			right = true;
			left = false;
		}
		if (!bottomLeft) {
			left = false;
		}
		if (!bottomRight) {
			left = true;
		}
	}

	public boolean isFlinching() {
		return flinching;
	}

	public long getFlinchTimer() {
		return flinchTimer;
	}

}
