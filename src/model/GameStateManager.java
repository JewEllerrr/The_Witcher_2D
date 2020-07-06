package model;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;

	public static final int NUMGAMESTATES = 4;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int LEVEL2STATE = 2;
	public static final int LOADLEVELSTATE = 3;

	public GameStateManager() {

		gameStates = new GameState[NUMGAMESTATES];
		currentState = MENUSTATE;
		loadState(currentState);

	}

	public void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if (state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if (state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		if (state == LOADLEVELSTATE)
			gameStates[state] = new LoadLevel(this);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void update() {
		try {
			if (gameStates[currentState] != null)
				gameStates[currentState].update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Level1State getStatelo() {
		return (Level1State) gameStates[LEVEL1STATE];
	}

	public Level2State getStatelt() {
		return (Level2State) gameStates[LEVEL2STATE];
	}

	public MenuState getMenu() {
		return (MenuState) gameStates[MENUSTATE];
	}

	public LoadLevel getLoadLevel() {
		return (LoadLevel) gameStates[LOADLEVELSTATE];
	}

	public int getCurrentState() {
		return currentState;
	}

	public boolean isLevel1() {
		if (currentState == LEVEL1STATE)
			return true;
		else
			return false;
	}

	public boolean isLevel2() {
		if (currentState == LEVEL2STATE)
			return true;
		else
			return false;
	}

	public boolean isLoadState() {
		if (currentState == LOADLEVELSTATE)
			return true;
		else
			return false;
	}

	public boolean isMenu() {
		if (currentState == MENUSTATE)
			return true;
		else
			return false;
	}

}
