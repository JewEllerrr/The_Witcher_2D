package model;

public class MenuState extends GameState {
	
	private String[] options = { "New Game", "Levels", "Quit" };
	public int currentChoice = 0;

	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void update() {
		
	}

	public void select() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if (currentChoice == 1) {
			gsm.setState(GameStateManager.LOADLEVELSTATE);
		}
		if (currentChoice == 2) {
			System.exit(0);
		}
	}

	public void init() {
		
	}

	public int getCurrentChoice() {
		return currentChoice;
	}

	public String getOptions(int i) {
		return options[i];
	}
}