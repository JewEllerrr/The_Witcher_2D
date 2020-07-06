package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import model.Player;

public class HUD {

	private Player player;

	private BufferedImage image;
	private BufferedImage one;
	private BufferedImage two;
	private BufferedImage three;
	private BufferedImage four;
	private BufferedImage five;
	
	private Font font;

	public HUD(Player p) {
		player = p;
		try {
			image = ImageIO.read(new File("resources/HUD/hudd.png"));
			one = ImageIO.read(new File("resources/HUD/one.png"));
			two = ImageIO.read(new File("resources/HUD/two.png"));
			three = ImageIO.read(new File("resources/HUD/three.png"));
			four = ImageIO.read(new File("resources/HUD/four.png"));
			five = ImageIO.read(new File("resources/HUD/five.png"));
			font = new Font("TimesNewRoman", Font.PLAIN, 30);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g) {
		
		g.drawImage(image, 0, 40, null);
		
		if (player.getHealth() == 4)
			g.drawImage(one, 60, 51, null);
		if (player.getHealth() == 3)
			g.drawImage(two, 60, 51, null);
		if (player.getHealth() == 2)
			g.drawImage(three, 60, 51, null);
		if (player.getHealth() == 1)
			g.drawImage(four, 60, 51, null);
		if (player.isDead())
			g.drawImage(five, 60, 51, null);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.setColor(Color.BLACK);

	}


}
