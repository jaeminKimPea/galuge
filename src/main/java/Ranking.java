import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import java.util.ArrayList;

public class Ranking extends JPanel {

	JTextField idTextField;
	JPasswordField passTextField;
	Object o;
	
	String userMode = "일반";
	Rpg lp;
	String admin = "admin";
	
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> score = new ArrayList<String>();
	MouseListener ML;
	static int rangLocationX = 450;
	static int rangLocationY = 160;
	final static int Firrang = 1;
	final static int endrang = 16;
	int rangFir = Firrang;
	int rangLas = endrang;
	int y;
	int ey;
	int res = 0;
	int scroll = 47;
	boolean vkctrl = false;
	boolean vkz = false;
	JButton EscButton;
	JButton LoginButton;
	public Ranking(Rpg lp) {
		this.lp = lp;
		
		setLayout(null);
		
		Inforeset();
		JP GPanel = new JP();
		GPanel.setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGTH);
		
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGTH);
		setVisible(true); // 필수
		Focus();
		addKeyListener(new KeyListener());
		LoginButton = new JButton(new ImageIcon("img/btLogin_hud.png"));
		LoginButton.setBounds(990, 5, 104, 48);
		// 버튼 투명처리
		LoginButton.setBorderPainted(false);
		LoginButton.setFocusPainted(false);
		LoginButton.setContentAreaFilled(false);
		
		EscButton = new JButton(new ImageIcon("img/btESC_hud.png"));
		EscButton.setBounds(1100, 10, 82, 39);
		// 버튼 투명처리
		EscButton.setBorderPainted(false);
		EscButton.setFocusPainted(false);
		EscButton.setContentAreaFilled(false);
		EscButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		LoginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lp.card.first(lp.cardPanel);
			}
		});
		ML = new MouseListener();
		addMouseListener(ML);
		add(LoginButton);
		add(EscButton);
		add(GPanel);
	}
	
	class JP extends JPanel{
		
		ImageIcon loginScreen = new ImageIcon("./login.png");
		Image img = loginScreen.getImage();
		
		public void paint(Graphics g) {
			super.paint(g);
			
			g.setColor(Color.WHITE);
			g.drawImage(img,0,0,getWidth(),getHeight(),this);
			g.setFont(new Font("휴먼엑스포", Font.PLAIN, 30));
			g.drawString("GalafaGame RANK", rangLocationX + 10, rangLocationY/2);
			g.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
			g.setColor(Color.red);
			g.drawString("RANK", rangLocationX, rangLocationY);
			g.drawString("USER", rangLocationX + 120, rangLocationY);
			g.drawString("SCORE", rangLocationX + 260, rangLocationY);
			
			g.setFont(new Font("휴먼엑스포", Font.PLAIN, 17));
			g.setColor(Color.WHITE);
			int y=rangLocationY;
			int rank = 0;
			while(name.size() != rank){ 
				System.out.println(rank);
				
				rank++;
				
				if(rangFir <=rank && rank <= rangLas) {
					y+=30;
					g.drawString(rank+"", rangLocationX, y); //첫번째 토큰(등수)는 x좌표가 60 y좌표가 y인 곳에 출력
					g.drawString(name.get(rank-1), rangLocationX + 120, y);// 유저이름 출력
					g.drawString(score.get(rank-1), rangLocationX + 260, y);// 점수 출력
				}
			}
		}
	}
	public void Focus() {
		setFocusable(true); // 키리스너 포커스
		requestFocus();
		System.out.println("Lank Focus");
	}
	
	public void Inforeset() {
		try {
			name.clear();
			score.clear();
			int k = 1;
			String sql_name = String.format("SELECT name FROM login_db.user_info ORDER BY score DESC");
			String sql_score = String.format("SELECT score FROM login_db.user_info ORDER BY score DESC");
			Connection conn = lp.getConnection();
			Statement stmt = conn.createStatement();
			Statement stmt1 = conn.createStatement();
			ResultSet rname = stmt.executeQuery(sql_name);
			ResultSet rscore = stmt1.executeQuery(sql_score);
			rname.next();
			rscore.next();
			while(true) {
			
			String name = rname.getString(1);
			String score = rscore.getString(1);
			this.name.add(name);
			this.score.add(score);
			System.out.print(k + " ");
			System.out.print(name + " ");
			System.out.print(score);
			rname.next();
			rscore.next();
			k++;
			}
		} catch (SQLException ex) {
			System.out.println("정보조회완료");
		}
	}
	
	class KeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_CONTROL:
				vkctrl = true;
				break;
			case KeyEvent.VK_Z:
				vkz = true;
				break;
			}
			if(vkctrl && vkz)
				lp.card.first(lp.cardPanel);
		}
	}
	
	class MouseListener extends MouseAdapter {
		int y;
		int ey;
		int res = 0;
		public void mousePressed(MouseEvent e) {
			y = e.getY();
			Focus();
		}
		/*public void mouseDragged(MouseEvent e) {
			ey = e.getY();
			System.out.println(2);
			res += y-ey/47;
			repaint();
		}*/
		public void mouseReleased(MouseEvent e) {
			ey = e.getY();
			res = (ey-y)/scroll;
			System.out.println("y "+y);
			System.out.println("ey "+ey);
			System.out.println("res "+res);
			if(res >0) {
				if(rangFir - res > Firrang - 1) {
					rangFir -= res;
					if(rangLas - res <= endrang)
						rangLas = endrang;
					else
						rangLas -= res;
				}
				else {
					rangFir = Firrang;
					rangLas = endrang;
				}
			}
			else if(res <0) {
				if(rangLas - res < name.size()) {
					rangFir -= res;
					rangLas -= res;
				}
				else {
					rangFir = name.size()- endrang + 1;
					rangLas = name.size();
				}
			}
			
			repaint();
		}
	}
}
