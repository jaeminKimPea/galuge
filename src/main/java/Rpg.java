import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Rpg extends JFrame implements KeyListener{

	JPanel gp = new JPanel() {
	@Override
	public void paint(Graphics g) {
		bufferImage = createImage(Main.SCREEN_WIDTH,Main.SCREEN_HEIGTH);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(bufferImage,0,0,null);
	}
	public void screenDraw(Graphics g) {
		
		if(isMainScreen) {
			g.drawImage(MainScreen, 0, 0, null);
			g.drawImage(ESC, 1100, 10, 82, 39, null);
		}
		if(isEnterScreen) {
			g.drawImage(EnterScreen, 0, 0, null);
			g.drawImage(ESC, 1100, 10, 82, 39, null);
		}
		if(isLoadingScreen) {
			g.drawImage(loadingScreen, 0, 0, null);
			g.drawImage(ESC, 1100, 10, 82, 39, null);
		}
		if(isGameScreen) {
			g.drawImage(GameScreen, 0, 0, null);
			Magician.gameDraw(g);
		}
		
		this.repaint();
		}
	};
	CardLayout card = new CardLayout();
	JPanel cardPanel = new JPanel(card);
	
	private Image bufferImage;
	private Graphics screenGraphic;
	
	private Image MainScreen = new ImageIcon("./mainStart.png").getImage();
	private Image EnterScreen = new ImageIcon("./mainLoading.png").getImage();
	private Image loadingScreen = new ImageIcon("./mainLoading2.png").getImage();
	private Image GameScreen = new ImageIcon("./Background.png").getImage();
	private Image ESC = new ImageIcon("img/btESC_hud.png").getImage();
	boolean isMainScreen, isEnterScreen, isLoadingScreen,isGameScreen;
	private boolean Released;
	boolean gameStart = false;
	private int Sel, SelLimit;
	JButton EscButton;
	String id = "";
	Login lp;
	signup sp;
	Ranking Rkp;
	private Audio backgroundMusic;
	//Thread MagicianStart = new Thread(this);
	public Magician Magician;
	
	public Rpg() {
		setframe();
	}
	public void setframe() {
		Rkp = new Ranking(this);
		lp = new Login(this,sp);
		sp = new signup(this);
		Magician = new Magician(this, Rkp);
		EscButton = new JButton("ESC");
		EscButton.setBounds(1100, 10, 82, 39);
		// 버튼 투명처리
		EscButton.setBorderPainted(false);
		EscButton.setFocusPainted(false);
		EscButton.setContentAreaFilled(false);
		
		cardPanel.add(lp, "Login");
		cardPanel.add(sp, "Register");
		cardPanel.add(gp, "game");
		cardPanel.add(Rkp, "Rank");
		add(cardPanel);
		setUndecorated(true); // 테두리없는 창
		setSize( Main.SCREEN_WIDTH, Main.SCREEN_HEIGTH);
		setResizable(false); // 창크기 조절 금지
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		Focus();
        setLayout(null);
        
		init();
	}
	private void init() {
		isMainScreen = false;
		isLoadingScreen = false;
		isGameScreen = false;
		
		backgroundMusic = new Audio("./Audio/menuBGM.wav", true);
		backgroundMusic.start();
		
		addKeyListener(this);
		
        addMouseListener(new MouseListener());
		add(EscButton);
		EscButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
	}
	
	private void gameStart() {
		isMainScreen = false;
		isEnterScreen = true;
		Timer EnteringTimer = new Timer();
		TimerTask EnteringTask = new TimerTask() {
			@Override
			public void run() {
				isEnterScreen = false;
				
				isLoadingScreen = true;
				Timer loadingTimer = new Timer();
				TimerTask loadingTask = new TimerTask() {
					@Override
					public void run() {
						backgroundMusic.stop();
						isLoadingScreen = false;
						isGameScreen = true;
						Magician.id = id;
						Magician.start();
					}
				};
				loadingTimer.schedule(loadingTask, 3000);
			}
		};
		EnteringTimer.schedule(EnteringTask, 3000);
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			Magician.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			Magician.setRight(true);
			break;
		case KeyEvent.VK_UP:
			Magician.setUp(true);
			break;
		case KeyEvent.VK_DOWN:
			Magician.setDown(true);
			break;
		case KeyEvent.VK_W:
			Magician.setUpshooting(true);
			break;
		case KeyEvent.VK_A:
			Magician.setLeftshooting(true);
			break;
		case KeyEvent.VK_S:
			Magician.setDownshooting(true);
			break;
		case KeyEvent.VK_D:
			Magician.setRightshooting(true);
			break;
		case KeyEvent.VK_R:
			if(Magician.isOver()) {
					Magician.reset();
					card.previous(cardPanel);
			}
			break;
		case KeyEvent.VK_P:
			if(!Magician.isOver()) {
				Magician.setOver(true);
			}
			else {
				Magician.setOver(false);
			}
			break;
		case KeyEvent.VK_CONTROL:
			Magician.Fixcommend(0);
			break;
		case KeyEvent.VK_ENTER:
			System.out.println("this game Focus");
			if(isMainScreen) {
				gameStart();
			}
			break;
		case KeyEvent.VK_ESCAPE:
			System.out.println("this game Focus");
			System.exit(0);
			break;
		
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			Magician.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
			Magician.setRight(false);
			break;
		case KeyEvent.VK_UP:
			Magician.setUp(false);
			break;
		case KeyEvent.VK_DOWN:
			Magician.setDown(false);
			break;
		case KeyEvent.VK_W:
			Magician.setUpshooting(false);
			break;
		case KeyEvent.VK_A:
			Magician.setLeftshooting(false);
			break;
		case KeyEvent.VK_S:
			Magician.setDownshooting(false);
			break;
		case KeyEvent.VK_D:
			Magician.setRightshooting(false);
			break;
		case KeyEvent.VK_CONTROL:
			Magician.Fixcommend(1);
			break;
		}
	}
	public void Focus() {
		this.setFocusable(true); // 키리스너 포커스
		this.requestFocus();
		System.out.println("game Focus");
	}
	class MouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Focus();
		}

	}
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_DB?serverTimezone=UTC", 
				"root","1234");

		return conn;
	}
	
}