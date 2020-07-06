package model;

import java.util.ArrayList;

public class Boss extends MapObject {

	private int health;
	private int damage;

	protected boolean flinching;
	protected long flinchTimer;

	private boolean dead;
	private int fire;
	private int maxFire;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;

	public Boss(TileMap tm) {

		super(tm);
		moveSpeed = 1;
		maxSpeed = 2;
		fallSpeed = 8.2;
		maxFallSpeed = 10.0;

		width = 174;
		height = 103;
		cwidth = 120;
		cheight = 70;

		health = 10;
		fire = maxFire = fireCost = 100;
		damage = 0;
		fireBallDamage = 1;
		fireBalls = new ArrayList<FireBall>();
		right = true;
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
			dx = 0;
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
		health = 0;
	}

	public void checkAttack(Player e) {
		// fireballs
		for (int j = 0; j < fireBalls.size(); j++) {
			if (fireBalls.get(j).intersects(e)) {
				e.hit(fireBallDamage);
				fireBalls.get(j).setHit();
				break;
			}
		}
	}

	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

		// check flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 400) {
				flinching = false;
			}
		}

		// fireball attack
		fire += 1;
		if (fire > maxFire)
			fire = maxFire;

		if (fire >= fireCost) {
			fire -= fireCost;
			FireBall fb = new FireBall(tileMap, right);
			fb.setPosition(x, y);
			fireBalls.add(fb);
		}
		
		// update fireballs
		for (int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if (fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}

		// if it hits a wall, go other direction
		if (right && dx == 0) {
			right = false;
			left = true;
			right = false;
		} else if (left && dx == 0) {
			right = true;
			left = false;
			right = true;
		}

		if (!bottomLeft) {
			left = false;
			right = true;
		}
		if (!bottomRight) {
			left = true;
			right = false;
		}
	}

	public boolean isBossDead() {
		return dead;
	}
	
	public boolean isRight() {
		return right;
	}

	public void setBossDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isFlinching() {
		return flinching;
	}

	public long getFlinchTimer() {
		return flinchTimer;
	}
	
	public ArrayList<FireBall> getFireBalls(){
		return fireBalls;
	}

	public int getHealth() {
		return health;
	}

}
