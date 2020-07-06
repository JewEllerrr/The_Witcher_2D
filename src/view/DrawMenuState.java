package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import model.MenuState;

public class DrawMenuState {
	
	private MenuState m;
	private Image bg;
	
	public DrawMenuState (MenuState ms) {
		m = ms;
		bg = new ImageIcon("resources/backgrounds/bg1.png").getImage();
	}
	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, 1366, 768, null);
		Font customFont = null;
		Font customFont2 = null;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("4026.ttf")).deriveFont(100f);
			customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("4026.ttf")).deriveFont(70f);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}

		g.setFont(customFont);
		g.setColor(Color.RED);
		g.drawString("The Witcher 2D", 300, 160);
		for (int i = 0; i < 3; i++) {
			if (i == m.getCurrentChoice()) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.WHITE);
			}
			g.setFont(customFont2);
			if (i == 0)
				g.drawString(m.getOptions(i), GamePanel.WIDTH / 2 - 180, 340 + i * 80);
			else if (i == 1) {
				g.drawString(m.getOptions(i), GamePanel.WIDTH / 2 - 110, 340 + i * 80);
			}
			else if (i == 2) {
				g.drawString(m.getOptions(i), GamePanel.WIDTH / 2 - 92, 340 + i * 80);
			}
		}

	}
}
