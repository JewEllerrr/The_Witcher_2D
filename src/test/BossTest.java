package test;

import org.junit.Assert;
import org.junit.Test;

import io.LoadMap;
import model.*;

class BossTest {

	private Boss boss;
	private TileMap tileMap;
	private Player player;
	private LoadMap loadMap;

	@Test
	public void hit() {
		tileMap = new TileMap(64);
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level2.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		boss = new Boss(tileMap);
		player = new Player(tileMap);
		int expected = boss.getHealth() - player.getDamage();
		boss.hit(player.getDamage());
		int actual = boss.getHealth();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void isDead() {
		tileMap = new TileMap(64);
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level2.map");
			// tileMap.loadMap("resources/maps/level2.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		boss = new Boss(tileMap);
		int expected = 0;
		boss.kill();
		int actual = boss.getHealth();
		Assert.assertEquals(expected, actual);
	}
}