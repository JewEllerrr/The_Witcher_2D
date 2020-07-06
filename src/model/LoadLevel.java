package model;

public class LoadLevel extends GameState {
	
	public int currentChoice = 0;
	private String[] options = { "Level 1", "Level 2", "Back to Menu" };

	public LoadLevel(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void init() {
		
	}

	public void update() {
		
	}

	public void select() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if (currentChoice == 1) {
			gsm.setState(GameStateManager.LEVEL2STATE);
		}
		if (currentChoice == 2) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
	}

	public int getCurrentChoice() {
		return currentChoice;
	}

	public String getOptions(int i) {
		return options[i];
	}

}
