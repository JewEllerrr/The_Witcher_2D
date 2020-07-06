package model;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public abstract void init() throws Exception;
	public abstract void update();
}