import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

class Login extends JPanel {

	
	JTextField idTextField;
	JPasswordField passTextField;
	Object o;
	
	String userMode = "일반";
	Rpg lp;
	Signup sp;
	String admin = "admin";
	JButton loginButton = new JButton(new ImageIcon("img/btLogin_hud.png"));
	JButton signupButton = new JButton(new ImageIcon("img/btSighnup_hud.png"));
	JButton EscButton = new JButton(new ImageIcon("img/btESC_hud.png"));
	JButton RankButton = new JButton(new ImageIcon("img/btRank_hud.png"));
	
	public Login(Rpg lp, Signup sp) {
		this.lp = lp;
		this.sp = sp;
		GPanel GPanel = new GPanel();
		GPanel.setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGTH);
		
		// 레이아웃 설정
		setLayout(null);
        
		idTextField = new JTextField(15);
		idTextField.setBounds(570, 299, 280, 30);
		add(idTextField);
        idTextField.setOpaque(false);
        idTextField.setFont(new Font("sans serif", Font.BOLD, 20));
        idTextField.setForeground(Color.WHITE);
        idTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
        passTextField = new JPasswordField(15);
        passTextField.setBounds(570, 399, 280, 30);
        passTextField.setOpaque(false);
        passTextField.setFont(new Font("sans serif", Font.BOLD, 20));
        passTextField.setForeground(Color.WHITE);
        passTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        add(passTextField);
        Button();
        add(GPanel);
        setVisible(true);
        Focus();
        addListener();
        
	}
	class GPanel extends JPanel{
		ImageIcon loginScreen = new ImageIcon("./login.png");
		Image img = loginScreen.getImage();
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img,0,0,getWidth(),getHeight(),this);
			g.setFont(new Font("문체부 궁체 흘림체", Font.BOLD, 45));
			g.setColor(new Color(0, 222, 233));
			g.drawString("로그인", 554, 229);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 30));
			g.setColor(new Color(33, 146, 183));
			g.drawString("아이디", 440, 329);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(33, 146, 183));
			g.drawString(": ", 540, 329);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 30));
			g.setColor(new Color(33, 146, 183));
			g.drawString("비밀번호", 410, 429);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(33, 146, 183));
			g.drawString(": ", 540, 429);
		}
	}
	
	public void Button() {
		
        loginButton.setBounds(514, 479, 104, 48);
        // 버튼 투명처리
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
 
        add(loginButton);
        
        signupButton.setBounds(634, 479, 104, 48);
		// 버튼 투명처리
		signupButton.setBorderPainted(false);
		signupButton.setFocusPainted(false);
		signupButton.setContentAreaFilled(false);
		
		add(signupButton);
        
		EscButton.setBounds(1100, 10, 82, 39);
		// 버튼 투명처리
		EscButton.setBorderPainted(false);
		EscButton.setFocusPainted(false);
		EscButton.setContentAreaFilled(false);
		
		add(EscButton);
		
		RankButton.setBounds(574, 539, 104, 48);
		// 버튼 투명처리
		RankButton.setBorderPainted(false);
		RankButton.setFocusPainted(false);
		RankButton.setContentAreaFilled(false);
		
		add(RankButton);
		
		
	}
	

	
	public void AcLogin(String id, String pass) {
		try {

			String sql_query = String.format("SELECT password FROM user_info WHERE id = '%s' AND password ='%s'",
					id, pass);
			Connection conn = lp.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rset = stmt.executeQuery(sql_query);
			rset.next();

			if (pass.equals(rset.getString(1))) {
				JOptionPane.showMessageDialog(this, "Login Success", "로그인 성공", 1);
				lp.id = id;
				lp.isMainScreen = true;
				if(rset != null) rset.close();
				lp.card.show(lp.cardPanel, "game");
			} else {
				JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 1);
				if(rset != null) rset.close();
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 1);
			System.out.println("SQLException" + ex);
		}
	}
	class KeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.out.println("ESCAPE");
				System.exit(0);
				break;
			case KeyEvent.VK_ENTER:
				System.out.println("ENTER");
				String id = idTextField.getText();
				String pass = passTextField.getText();

				AcLogin(id, pass);
				break;
			
			}
		}
	}
	public void Focus() {
		setFocusable(true); // 키리스너 포커스
		requestFocus();
		System.out.println("Login Focus");
	}
	class MouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			Focus();
		}

	}
	public void addListener() {
		idTextField.addKeyListener(new KeyListener());
		passTextField.addKeyListener(new KeyListener());
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				switch (e.getActionCommand()) {

				case "일반":
					userMode = "일반";
					break;

				case "관리자":
					userMode = "관리자";
					break;

				}
				String id = idTextField.getText();
				String pass = passTextField.getText();

				AcLogin(id, pass);
			}
		});

		signupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lp.card.next(lp.cardPanel);
			}
		});
		
		EscButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		RankButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lp.card.last(lp.cardPanel);
			}
		});
		
	}
	
}// class LoginPanel
