package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import model.LoadLevel;

public class DrawLoadLevel {

	private Image bg;
	private LoadLevel l;

	public DrawLoadLevel(LoadLevel s) {
		l = s;
		bg = new ImageIcon("resources/backgrounds/bg1.png").getImage();
	}

	public void draw(Graphics2D g) {
		
		g.drawImage(bg, 0, 0, 1366, 768, null);
		Font customFont = null;
		Font customFont2 = null;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("4026.ttf")).deriveFont(70f);
			customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("4026.ttf")).deriveFont(40f);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
		g.setFont(customFont);
		g.setColor(Color.RED);
		for (int i = 0; i < 3; i++) {
			if (i == l.getCurrentChoice()) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.WHITE);
			}
			g.setFont(customFont);
			if (i == 0)
				g.drawString(l.getOptions(i), GamePanel.WIDTH / 2 - 100, 340 + i * 80);
			else if (i == 1) {
				g.drawString(l.getOptions(i), GamePanel.WIDTH / 2 - 100, 340 + i * 80);
			} else if (i == 2) {
				g.setFont(customFont2);
				g.drawString(l.getOptions(i), GamePanel.WIDTH / 2 - 118, 340 + i * 150);
			}
		}

	}

}
