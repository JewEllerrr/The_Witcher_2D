package test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import io.LoadMap;
import model.*;

class PlayerTest {
	Player player;
	private TileMap tileMap = new TileMap(64);
	private LoadMap loadMap;
	private ArrayList<Mage> enemies;
	private ArrayList<ExtraHeart> eH;
	private ArrayList<Spikes> spikes;
	private Boss boss;

	@Test
	public void testCheckTarget() {
		boolean expected = true;
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level1.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		player = new Player(tileMap);
		player.setPosition(3000, 400);
		boolean actual = player.checkTarget(3000);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCheckEh() {
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level1.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		eH = new ArrayList<ExtraHeart>();
		ExtraHeart e = new ExtraHeart(tileMap);
		e.setPosition(100, 500);
		eH.add(e);
		player = new Player(tileMap);
		int expected = player.getMaxHealth();
		player.hit(2);
		player.setPosition(100, 500);
		player.checkEh(eH);
		int actual = player.getHealth();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCheckSpikes() {
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level1.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		player = new Player(tileMap);
		player.setPosition(100, 500);
		spikes = new ArrayList<Spikes>();
		Spikes s;
		s = new Spikes(tileMap);
		s.setPosition(100, 500);
		spikes.add(s);
		player.checkSpikes(spikes);
		boolean expected = true;
		boolean actual = player.isDead();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCheckAttack() {
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level1.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		player = new Player(tileMap);
		player.setPosition(300, 400);
		enemies = new ArrayList<Mage>();
		enemies = new ArrayList<Mage>();
		Mage s;
		s = new Mage(tileMap);
		s.setPosition(300, 400);
		enemies.add(s);
		player.checkAttack(enemies);
		int expected = player.getMaxHealth() - s.getDamage();
		int actual = player.getHealth();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCheckBoss() {
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level1.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		player = new Player(tileMap);
		boss = new Boss(tileMap);
		player.setPosition(100, 500);
		boss.setPosition(100, 500);
		player.checkBossAttack(boss);
		int expected = player.getMaxHealth() - boss.getDamage();
		int actual = player.getHealth();
		Assert.assertEquals(expected, actual);
	}

}
