package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

import model.Boss;
import model.ExtraHeart;
import model.FireBall;
import model.Level2State;
import model.Player;
import model.Stairs;

public class DrawLevel2State {

	Level2State lt;
	private Date date;
	private FileWriter writer;
	// background
	private Image bg;
	private Image bg2;

	// draw tilemap
	private DrawTileMap dtileMap;

	// HUD
	private HUD hud;

	// boss
	Boss boss;

	// player
	Player player;
	private Image geraltStay;
	private Image geraltRun;
	private Image geraltJump;
	private Image geraltFall;
	private Image geraltHit;

	// entity
	private ArrayList<ExtraHeart> eH;
	private ArrayList<Stairs> stairs;
	private ArrayList<FireBall> fireBalls;
	private Image stairsIcon;
	private Image heartsIcon;
	private Image bossIcon;
	private Image fbIcon;

	public DrawLevel2State(Level2State l) {
		
		lt = l;
		boss = l.getBoss();
		player = l.getPlayer();
		eH = l.getEH();
		stairs = l.getStairs();
		fireBalls = boss.getFireBalls();
		
		// HUD
		hud = new HUD(player);
		
		// entity
		stairsIcon = new ImageIcon("resources/tileSets/stairs.png").getImage();
		heartsIcon = new ImageIcon("resources/tileSets/pt1.png").getImage();
		bossIcon = new ImageIcon("resources/sprites/enemies/dragonboss.png").getImage();
		fbIcon = new ImageIcon("resources/sprites/enemies/fireball.gif").getImage();
		
		// load tilemap
		dtileMap = new DrawTileMap(l.getTileMap());
		dtileMap.loadTiles("resources/tileSets/tiles.png");
		
		// load bg sprite
		bg = new ImageIcon("resources/backgrounds/bg1.jpg").getImage();
		bg2 = new ImageIcon("resources/backgrounds/fog1.png").getImage();
		
		// load player sprite
		geraltStay = new ImageIcon("resources/sprites/player/geraltStay.png").getImage();
		geraltRun = new ImageIcon("resources/sprites/player/geraltRun.png").getImage();
		geraltJump = new ImageIcon("resources/sprites/player/geraltJump.png").getImage();
		geraltFall = new ImageIcon("resources/sprites/player/geraltFall.png").getImage();
		geraltHit = new ImageIcon("resources/sprites/player/geraltHit.png").getImage();
	}

	public void draw(Graphics2D g) {
		// draw bg
		g.drawImage(bg, 0, 0, 1366, 768, null);
		g.drawImage(bg2, 0, 0, 1366, 768, null);

		// draw tilemap
		dtileMap.draw(g);
		
		// if dead or win
		if (player.isDead()) {
			Font customFont = null;
			try {
				customFont = Font.createFont(Font.TRUETYPE_FONT, new File("4026.ttf")).deriveFont(70f);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.setFont(customFont);
			g.setColor(Color.RED);
			g.drawString("GAME OVER", 1366 / 2 - 200, 380);
		}
		if (boss.isDead()) {
			Font customFont = null;
			try {
				customFont = Font.createFont(Font.TRUETYPE_FONT, new File("4026.ttf")).deriveFont(70f);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.setFont(customFont);
			g.setColor(Color.RED);
			g.drawString("YOU WON", 1366 / 2 - 200, 380);
		}
		
		// draw extraHealth
		for (int i = 0; i < eH.size(); i++) {
			ExtraHeart e = eH.get(i);
			e.setMapPosition();
			g.drawImage(heartsIcon, (int) (e.getx() + e.getxmap() - e.getWidth() / 2),
					(int) (e.gety() + e.getymap() - e.getHeight() / 2), null);
		}

		// draw hud
		hud.draw(g);

		// draw stairs
		for (int i = 0; i < stairs.size(); i++) {
			Stairs s = stairs.get(i);
			s.setMapPosition();
			g.drawImage(stairsIcon, (int) (s.getx() + s.getxmap() - s.getWidth() / 2),
					(int) (s.gety() + s.getymap() - s.getHeight() / 2), null);
		}
		
		// draw BOSS
		boss.setMapPosition();
		for (int i = 0; i < fireBalls.size(); i++) {
			FireBall fb = fireBalls.get(i);
			;
			fb.setMapPosition();
			g.drawImage(fbIcon, (int) (fb.getx() + fb.getxmap() - fb.getWidth() / 2),
					(int) (fb.gety() + fb.getymap() - fb.getHeight() / 2), null);
		}
		if (boss.isFlinching()) {
			long elapsed = (System.nanoTime() - boss.getFlinchTimer()) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		if (boss.isRight()) {
			g.drawImage(bossIcon, (int) (boss.getx() + boss.getxmap() - boss.getWidth() / 2),
					(int) (boss.gety() + boss.getymap() - boss.getHeight() / 2), null);
		} else {

			g.drawImage(bossIcon, (int) (boss.getx() + boss.getxmap() - boss.getWidth() / 2 + boss.getWidth()),
					(int) (boss.gety() + boss.getymap() - boss.getHeight() / 2), -boss.getWidth(), boss.getHeight(),
					null);
		}

		// draw player
		if (player.isFlinching()) {
			long elapsed = (System.nanoTime() - player.getFlinchTimer()) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}

		// set player animation
		player.setMapPosition();
		if (player.getCurrAction() == Player.IDLE) {
			if (player.getFacingRight())
				g.drawImage(geraltStay, (int) (player.getx() + player.getxmap() - player.getWidth() / 2),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), null);
			else
				g.drawImage(geraltStay,
						(int) (player.getx() + player.getxmap() - player.getWidth() / 2 + player.getWidth()),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), -player.getWidth(),
						player.getHeight(), null);
		}
		if (player.getCurrAction() == Player.RUNNING) {
			if (player.getFacingRight())
				g.drawImage(geraltRun, (int) (player.getx() + player.getxmap() - player.getWidth() / 2),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), null);
			else
				g.drawImage(geraltRun,
						(int) (player.getx() + player.getxmap() - player.getWidth() / 2 + player.getWidth()),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), -player.getWidth(),
						player.getHeight(), null);
		}
		if (player.getCurrAction() == Player.JUMPING) {
			if (player.getFacingRight())
				g.drawImage(geraltJump, (int) (player.getx() + player.getxmap() - player.getWidth() / 2),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), null);
			else
				g.drawImage(geraltJump,
						(int) (player.getx() + player.getxmap() - player.getWidth() / 2 + player.getWidth()),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), -player.getWidth(),
						player.getHeight(), null);
		}
		if (player.getCurrAction() == Player.FALLING) {
			if (player.getFacingRight())
				g.drawImage(geraltFall, (int) (player.getx() + player.getxmap() - player.getWidth() / 2),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), null);
			else
				g.drawImage(geraltFall,
						(int) (player.getx() + player.getxmap() - player.getWidth() / 2 + player.getWidth()),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), -player.getWidth(),
						player.getHeight(), null);
		}
		if (player.getCurrAction() == Player.SCRATCHING) {
			if (player.getFacingRight())
				g.drawImage(geraltHit, (int) (player.getx() + player.getxmap() - player.getWidth() / 2),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), null);
			else
				g.drawImage(geraltHit,
						(int) (player.getx() + player.getxmap() - player.getWidth() / 2 + player.getWidth()),
						(int) (player.gety() + player.getymap() - player.getHeight() / 2), -player.getWidth(),
						player.getHeight(), null);
			player.setScratching(false);
		}
	}
	public void loadScore(long l) {
		try {
			writer = new FileWriter("records.txt", true);
			date = new Date();
			writer.write(date.toString());
			writer.write("               Score:");
			writer.write(Long.toString(l));
			writer.write("\n");
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
