package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import model.TileMap;

public class LoadMap {
	
	private BufferedReader br;
	private int[][] map;
	
	public LoadMap(TileMap t, String s) throws Exception {

		File file = new File(s);
		FileReader fileReader = new FileReader(file);
		br = new BufferedReader(fileReader);
		t.setNumCols(Integer.parseInt(br.readLine()));
		t.setNumRows(Integer.parseInt(br.readLine()));
		map = new int[t.getNumRows()][t.getNumCols()];
		t.map(map);
		t.setWidth(t.getNumCols() * t.getTileSize());
		t.setHeight(t.getNumRows() * t.getTileSize());

		t.setXmin(1366 - t.getWidth());
		t.setXmax(0);
		t.setYmin(768 - t.getHeight());
		t.setYmax(0);

		String delims = "\\s+";
		for (int row = 0; row < t.getNumRows(); row++) {
			String line = br.readLine();
			String[] tokens = line.split(delims);
			for (int col = 0; col < t.getNumCols(); col++) {
				t.setmap(row, col, Integer.parseInt(tokens[col]));
			}
		}

	}
}
