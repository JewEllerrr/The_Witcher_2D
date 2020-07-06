package view;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("The Witcher 2D");
		frame.setContentPane(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
}