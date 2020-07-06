package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JDialog;
import javax.swing.JPanel;

import model.GameStateManager;
import model.Level1State;
import model.Level2State;
import model.MenuState;
import model.LoadLevel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	// dimensions
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	
	JDialog dialog;

	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	// image
	private BufferedImage image;
	private Graphics2D g;
	private boolean flag;
	private boolean flag2;
	private boolean flag3;
	private boolean flag4;
	private long tStart;
	private long tEnd;

	DrawLevel1State lo;
	DrawLevel2State lt;
	DrawMenuState ms;
	DrawLoadLevel r;

	Level1State los;
	Level2State lts;
	MenuState mss;
	LoadLevel ll;
	// game state manager
	private GameStateManager gsm;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void init() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		running = true;
		gsm = new GameStateManager();

	}

	public void run() {

		init();

		long start;
		long elapsed;
		long wait;

		// game loop
		while (running) {

			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void update() {
		gsm.update();
		los = gsm.getStatelo();
		lts = gsm.getStatelt();
		mss = gsm.getMenu();
		if (gsm.isMenu())
			flag = flag2 = flag3 = flag4 = false;
		ll = gsm.getLoadLevel();
	}

	private void draw() {
		if (gsm.isLevel1() && gsm.getStatelo() != null) {
			if (!flag) {
				tStart = System.currentTimeMillis() / 1000;
				lo = new DrawLevel1State(gsm.getStatelo());
				flag = true;
			}
			lo.draw(g);
		}
		if (gsm.isLevel2() && gsm.getStatelt() != null) {
			if (!flag2) {
				lt = new DrawLevel2State(gsm.getStatelt());
				flag2 = true;
			}
			if (lts.getBoss().isDead() && flag) {
				tEnd = System.currentTimeMillis() / 1000;
				lt.loadScore(tEnd - tStart);
			}
			lt.draw(g);
		}
		if (gsm.isMenu() && gsm.getMenu() != null) {
			if (flag4)
				flag3 = false;
			if (!flag3) {
				ms = new DrawMenuState(gsm.getMenu());
				flag3 = true;
			}
			ms.draw(g);
		}
		if (gsm.isLoadState() && gsm.getLoadLevel() != null) {
			if (flag3)
				flag4 = false;
			if (!flag4) {
				r = new DrawLoadLevel(gsm.getLoadLevel());
				flag4 = true;
			}
			r.draw(g);
		}

	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key) {

	}

	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (gsm.getCurrentState() == 0) {
			if (k == KeyEvent.VK_ENTER) {
				mss.select();
			}
			if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
				mss.currentChoice--;
				if (mss.currentChoice == -1) {
					mss.currentChoice = 3 - 1;
				}
			}
			if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
				mss.currentChoice++;
				if (mss.currentChoice == 3) {
					mss.currentChoice = 0;
				}
			}
		}
		else if (gsm.getCurrentState() == 1) {
			if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A)
				los.getPlayer().setLeft(true);
			if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D)
				los.getPlayer().setRight(true);
			if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)
				los.getPlayer().setUp(true);
			if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)
				los.getPlayer().setDown(true);
			if (k == KeyEvent.VK_SPACE)
				los.getPlayer().setJumping(true);
			if (k == KeyEvent.VK_F)
				los.getPlayer().setScratching();
		}
		else if (gsm.getCurrentState() == 2) {
			if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A)
				lts.getPlayer().setLeft(true);
			if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D)
				lts.getPlayer().setRight(true);
			if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)
				lts.getPlayer().setUp(true);
			if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)
				lts.getPlayer().setDown(true);
			if (k == KeyEvent.VK_SPACE)
				lts.getPlayer().setJumping(true);
			if (k == KeyEvent.VK_F)
				lts.getPlayer().setScratching();
		}
		else if (gsm.getCurrentState() == 3) {
			if (k == KeyEvent.VK_ENTER) {
				ll.select();
			}
			if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
				ll.currentChoice--;
				if (ll.currentChoice == -1) {
					ll.currentChoice = 3 - 1;
				}
			}
			if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
				ll.currentChoice++;
				if (ll.currentChoice == 3) {
					ll.currentChoice = 0;
				}
			}
			if (k == KeyEvent.VK_ESCAPE) {
				gsm.setState(0);
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if (gsm.getCurrentState() == 1) {
			if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A)
				los.getPlayer().setLeft(false);
			if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D)
				los.getPlayer().setRight(false);
			if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)
				los.getPlayer().setUp(false);
			if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)
				los.getPlayer().setDown(false);
			if (k == KeyEvent.VK_SPACE)
				los.getPlayer().setJumping(false);
		}
		if (gsm.getCurrentState() == 2) {
			if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A)
				lts.getPlayer().setLeft(false);
			if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D)
				lts.getPlayer().setRight(false);
			if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)
				lts.getPlayer().setUp(false);
			if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)
				lts.getPlayer().setDown(false);
			if (k == KeyEvent.VK_SPACE)
				lts.getPlayer().setJumping(false);

		}
	}

}
