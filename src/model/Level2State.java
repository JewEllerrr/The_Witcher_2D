package model;

import java.util.ArrayList;

import io.LoadMap;

public class Level2State extends GameState {

	private TileMap tileMap;

	private Player player;
	private Boss boss;

	private ArrayList<ExtraHeart> eH;
	private ArrayList<Stairs> stairs;

	private LoadMap loadMap;

	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {

		tileMap = new TileMap(64);
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level2.map");
			// tileMap.loadMap("resources/maps/level2.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		player = new Player(tileMap);
		player.setPosition(100, 400);
		boss = new Boss(tileMap);
		boss.setPosition(900, 450);
		createExtraHeart();
		createStairs();
	}

	private void createExtraHeart() {
		eH = new ArrayList<ExtraHeart>();
		ExtraHeart s;
		s = new ExtraHeart(tileMap);
		s.setPosition(650, 365);
		eH.add(s);
		s = new ExtraHeart(tileMap);
		s.setPosition(890, 300);
		eH.add(s);

	}

	private void createStairs() {
		stairs = new ArrayList<Stairs>();
		Stairs s;
		int x = 486;
		int y = 422;
		for (int i = 0; i < 3; i++) {
			s = new Stairs(tileMap);
			s.setPosition(x, y);
			stairs.add(s);
			y += 64;
		}
	}

	public void update() {

		if (boss.isDead()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gsm.loadState(GameStateManager.MENUSTATE);
			gsm.setState(GameStateManager.MENUSTATE);
			gsm.update();
		}
		if (player.isDead()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gsm.loadState(GameStateManager.MENUSTATE);
			gsm.setState(GameStateManager.MENUSTATE);
			gsm.update();
		}

		// if fall the map
		if (player.gety() >= tileMap.getHeight() - player.getHeight()) {
			player.kill();
		}
		tileMap.setPosition(1366 / 2 - player.getx(), 768 / 2 - player.gety());

		// update player
		player.update();

		// attack enemies
		player.checkBossAttack(boss);

		// check extraHeart
		player.checkEh(eH);

		// check stairs
		player.checkStairs(stairs);
		// update all enemies
		boss.update();
		if (boss.gety() >= tileMap.getHeight() - boss.getHeight()) {
			boss.kill();
		}
		if (boss.isDead()) {
			boss.kill();

		} else
			boss.checkAttack(player);

		// update hearts
		for (int i = 0; i < eH.size(); i++) {
			ExtraHeart o = eH.get(i);
			if (o.isDead()) {
				eH.remove(i);
				i--;
				if (player.getHealth() < player.getMaxHealth())
					player.setHealth(player.getMaxHealth());
			}
		}

	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<ExtraHeart> getEH() {
		return eH;
	}

	public ArrayList<Stairs> getStairs() {
		return stairs;
	}

	public TileMap getTileMap() {
		return tileMap;
	}

	public Boss getBoss() {
		return boss;
	}

}
