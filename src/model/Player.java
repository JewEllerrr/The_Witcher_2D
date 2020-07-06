package model;

import java.util.ArrayList;

public class Player extends MapObject {

	// player stuff
	private int health;
	private int maxHealth;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	private int damage;

	// scratch
	private boolean scratching;
	private boolean facingRight;
	private int scratchDamage;
	private int scratchRange;
	private int currentAction;


	// animation actions
	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;
	public static final int SCRATCHING = 4;

	public Player(TileMap tm) {

		super(tm);

		width = 64;
		height = 102;
		cwidth = 54;
		cheight = 102;

		moveSpeed = 6.8;
		maxSpeed = 8;
		stopSpeed = 0.4;
		fallSpeed = 0.64;
		maxFallSpeed = 3.0;
		jumpStart = -10;
		stopJumpSpeed = 0.3;
		damage = 1;
		health = maxHealth = 4;
		scratchDamage = 2;
		scratchRange = 100;
	}

	public void checkEh(ArrayList<ExtraHeart> eh) {
		// loop through objects
		for (int i = 0; i < eh.size(); i++) {
			ExtraHeart o = eh.get(i);
			// check object collision
			if (intersects(o) && health != maxHealth) {
				o.setDead(true);
				health = maxHealth;
			}
		}
	}

	public void checkSpikes(ArrayList<Spikes> spikes) {
		for (int i = 0; i < spikes.size(); i++) {
			Spikes o = spikes.get(i);
			// check object collision
			if (intersects(o)) {
				dead = true;
			}
		}
	}

	public void checkStairs(ArrayList<Stairs> stairs) {
		for (int i = 0; i < stairs.size(); i++) {
			Stairs o = stairs.get(i);
			// check object collision
			if (intersects(o) && this.getUp()) {
				dy -= fallSpeed;
			}
			if (intersects(o) && this.getDown()) {
				dy += fallSpeed;
			}
		}
	}

	public boolean checkTarget(int x) {
		if (this.getx() >= x) {
			return true;
		}
		return false;
	}

	public void checkAttack(ArrayList<Mage> enemies) {
		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {
			Mage e = enemies.get(i);
			// scratch attack
			if (scratching) {
				if (e.getx() > x && e.getx() < x + scratchRange && e.gety() > y - height / 2
						&& e.gety() < y + height / 2) {
					e.hit(scratchDamage);
				} else if (e.getx() < x && e.getx() > x - scratchRange && e.gety() > y - height / 2
						&& e.gety() < y + height / 2) {
					e.hit(scratchDamage);
				}
			}
			// check enemy collision
			if (intersects(e)) {
				hit(e.getDamage());
			}
		}
	}

	public void checkBossAttack(Boss e) {
		// loop through enemies
		if (scratching) {
			if (e.getx() > x && e.getx() < x + scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2) {
				e.hit(scratchDamage);

			} else if (e.getx() < x && e.getx() > x - scratchRange && e.gety() > y - height / 2
					&& e.gety() < y + height / 2) {
				e.hit(scratchDamage);
			}
		}
		// check enemy collision
		if (intersects(e)) {
			hit(e.getDamage());
		}
	}

	public void hit(int damage) {
		if (flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			setDead(true);
		flinching = true;
		flinchTimer = System.nanoTime();
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
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0) {
					dx = 0;
				}
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if ((currentAction == SCRATCHING) && !(jumping || falling)) {
			dx = 0;
		}
		
		// jumping
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		// falling
		if (falling) {

			if (dy > 0)
				dy += fallSpeed * 0.7;
			else
				dy += fallSpeed;

			if (dy > 0)
				jumping = false;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;

			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		}
	}

	public void update() {
		if (scratching) {
			if (currentAction != SCRATCHING) {
				currentAction = SCRATCHING;
				width = 128;
			}
		} else if (dy > 0) {
			if (currentAction != FALLING) {
				currentAction = FALLING;
				width = 64;
			}
		} else if (dy < 0) {
			if (currentAction != JUMPING) {
				currentAction = JUMPING;
				width = 64;
			}
		} else if (left || right) {
			if (currentAction != RUNNING) {
				currentAction = RUNNING;
				width = 64;
			}
		} else {
			if (currentAction != IDLE) {
				currentAction = IDLE;
				width = 64;
			}
		}
		if (currentAction != SCRATCHING) {
			if (right)
				facingRight = true;
			if (left)
				facingRight = false;
		}
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setHealth(int h) {
		this.health = h;
	}

	public void kill() {
		dead = true;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setScratching() {
		scratching = true;
	}

	public int getDamage() {
		return damage;
	}

	public boolean isFlinching() {
		return flinching;
	}

	public long getFlinchTimer() {
		return flinchTimer;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isLeft() {
		return left;
	}

	public double getdy() {
		return dy;
	}

	public boolean isScratching() {
		return scratching;
	}

	public void setScratching(boolean b) {
		scratching = b;
	}

	public void setWidth(int i) {
		width = i;
	}

	public int getCurrAction() {
		return currentAction;
	}

	public boolean getFacingRight() {
		return facingRight;
	}

}
