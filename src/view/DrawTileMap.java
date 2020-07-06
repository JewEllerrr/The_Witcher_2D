package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import model.TileMap;

public class DrawTileMap {
	
	public BufferedImage[][] tiles;
	
	private BufferedImage tileset;
	private TileMap tm;

	public DrawTileMap(TileMap tm) {
		this.tm = tm;
	}

	public void loadTiles(String s) {
		try {
			tileset = ImageIO.read(new File(s));
			tm.setNumTilesAcross(tileset.getWidth() / tm.getTileSize());
			tiles = new BufferedImage[2][tm.getNumTilesAcross()];
			for (int col = 0; col < tm.getNumTilesAcross(); col++) {
				tiles[0][col] = tileset.getSubimage(col * tm.getTileSize(), 0, tm.getTileSize(), tm.getTileSize());
				tiles[1][col] = tileset.getSubimage(col * tm.getTileSize(), tm.getTileSize(), tm.getTileSize(),
						tm.getTileSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		
		for (int row = tm.getRowOffset(); row < tm.getRowOffset() + tm.getNumRowsToDraw(); row++) {
			if (row >= tm.getNumRows())
				break;
			for (int col = tm.getColOffset(); col < tm.getColOffset() + tm.getNumColsToDraw(); col++) {
				if (col >= tm.getNumCols())
					break;
				if (tm.getmap(row, col) == 0)
					continue;
				int rc = tm.getmap(row, col);
				int r = rc / tm.getNumTilesAcross();
				int c = rc % tm.getNumTilesAcross();
				g.drawImage(tiles[r][c], (int) tm.getx() + col * tm.getTileSize(),
						(int) tm.gety() + row * tm.getTileSize(), null);
			}
		}
	}
	
}
