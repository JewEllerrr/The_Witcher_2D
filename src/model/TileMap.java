package model;

public class TileMap {

	// position
	private double x;
	private double y;

	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	private double tween;

	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// tileset
	private int numTilesAcross;
	private int[][] tiles;

	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		setNumRowsToDraw(768 / tileSize + 2);
		setNumColsToDraw(1366 / tileSize + 2);
		tween = 0.07;
		numTilesAcross = 7;
		tiles = new int[2][numTilesAcross];
		for (int i = 0; i < numTilesAcross; i++)
			tiles[1][i] = 1;
	}

	public int getTileSize() {
		return tileSize;
	}

	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getType(int row, int col) {
		if (numTilesAcross == 0)
			return 0;
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c];
	}

	public void setTween(double d) {
		tween = d;
	}

	public void setPosition(double x, double y) {

		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;

		fixBounds();

		setColOffset((int) - this.x / tileSize);
		setRowOffset((int) - this.y / tileSize);
	}

	private void fixBounds() {
		if (x < xmin)
			x = xmin;
		if (y < ymin)
			y = ymin;
		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getColOffset() {
		return colOffset;
	}

	public void setColOffset(int colOffset) {
		this.colOffset = colOffset;
	}

	public int getNumRowsToDraw() {
		return numRowsToDraw;
	}

	public void setNumRowsToDraw(int numRowsToDraw) {
		this.numRowsToDraw = numRowsToDraw;
	}

	public int getNumColsToDraw() {
		return numColsToDraw;
	}

	public int getmap(int x, int y) {
		return map[x][y];
	}

	public void setmap(int x, int y, int d) {
		map[x][y] = d;
	}

	public void setNumColsToDraw(int numColsToDraw) {
		this.numColsToDraw = numColsToDraw;
	}

	public void setNumTilesAcross(int i) {
		numTilesAcross = i;
	}

	public int getNumTilesAcross() {
		return numTilesAcross;
	}

	public int getNumCols() {
		return numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumCols(int p) {
		numCols = p;
	}
	public void setNumRows(int p) {
		numRows = p;
	}
	
	public void setHeight(int i) {
		height = i;
	}

	public void setWidth(int i) {
		width = i;
	}

	public void setXmin(int i) {
		xmin = i;
	}
	public void setYmin(int i) {
		ymin = i;
	}
	public void setXmax(int i) {
		xmax = i;
	}
	public void setYmax(int i) {
		ymax = i;
	}

	public void map(int[][] map2) {
		map = map2;
	}

}
