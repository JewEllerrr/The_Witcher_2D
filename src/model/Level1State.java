package model;

import java.util.ArrayList;

import io.LoadMap;

public class Level1State extends GameState {

	private TileMap tileMap;
	private Player player;

	private ArrayList<Mage> enemies;
	private ArrayList<ExtraHeart> eH;
	private ArrayList<Spikes> spikes;
	private ArrayList<Stairs> stairs;
	private LoadMap loadMap;

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {

		tileMap = new TileMap(64);
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level1.map");
			// tileMap.loadMap("resources/maps/level1.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		player = new Player(tileMap);
		player.setPosition(100, 400);
		populateEnemies();
		createExtraHeart(1687, 300);
		createSpikes();
		createStairs();
	}

	private void createSpikes() {
		spikes = new ArrayList<Spikes>();
		Spikes s;
		s = new Spikes(tileMap);
		s.setPosition(740, 625);
		spikes.add(s);
		s = new Spikes(tileMap);
		s.setPosition(2128, 625);
		spikes.add(s);
	}

	private void createExtraHeart(int x, int y) {
		eH = new ArrayList<ExtraHeart>();
		ExtraHeart e = new ExtraHeart(tileMap);
		e.setPosition(x, y);
		eH.add(e);
	}

	private void createStairs() {
		stairs = new ArrayList<Stairs>();
		Stairs s;
		int x = 1820;
		int y = 358;
		for (int i = 0; i < 4; i++) {
			s = new Stairs(tileMap);
			s.setPosition(x, y);
			stairs.add(s);
			y += 64;
		}
	}

	private void populateEnemies() {
		enemies = new ArrayList<Mage>();
		Mage s;
		s = new Mage(tileMap);
		s.setPosition(300, 400);
		enemies.add(s);
		s = new Mage(tileMap);
		s.setPosition(860, 400);
		enemies.add(s);
		s = new Mage(tileMap);
		s.setPosition(1680, 400);
		enemies.add(s);
		s = new Mage(tileMap);
		s.setPosition(2700, 400);
		enemies.add(s);
	}

	public void update() {

		if (player.checkTarget(3000)) {
			gsm.setState(GameStateManager.LEVEL2STATE);
		}

		if (player.isDead()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gsm.setState(GameStateManager.MENUSTATE);
		}
		// if fall the map
		if (player.gety() >= tileMap.getHeight() - player.getHeight()) {
			player.kill();
		}
		// update player
		player.update();
		tileMap.setPosition(1366 / 2 - player.getx(), 768 / 2 - player.gety());
		// attack enemies
		player.checkAttack(enemies);
		// check extraHeart
		player.checkEh(eH);
		// check spikes
		player.checkSpikes(spikes);
		// check stairs
		player.checkStairs(stairs);
		// update all enemies
		for (int i = 0; i < enemies.size(); i++) {
			Mage e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				enemies.remove(i);
				i--;
			}
		}
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

	public TileMap getTileMap() {
		return tileMap;
	}

	public ArrayList<Stairs> getStairs() {
		return stairs;
	}

	public ArrayList<Spikes> getSpikes() {
		return spikes;
	}

	public ArrayList<ExtraHeart> getEH() {
		return eH;
	}

	public ArrayList<Mage> getEnemies() {
		return enemies;
	}

}
