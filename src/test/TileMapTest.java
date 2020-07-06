package test;

import org.junit.Assert;
import org.junit.Test;

import io.LoadMap;
import model.Player;
import model.TileMap;

class TileMapTest {

	private TileMap tileMap;
	private LoadMap loadMap;
	private Player player;

	@Test
	void testGetType() {
		tileMap = new TileMap(64);
		try {
			loadMap = new LoadMap(tileMap, "resources/maps/level1.map");
		} catch (Exception e) {
			e.printStackTrace();
		}
		player = new Player(tileMap);
		player.setPosition(100, 570);
		int expected = 1;
		int actual = tileMap.getType(0, 0);
		Assert.assertEquals(expected, actual);
	}

}
