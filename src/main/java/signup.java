import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class signup extends JPanel {

	JTextField idTf;
	JPasswordField passTf;
	JPasswordField passReTf;
	JTextField nameTf;
	JTextField yearTf;
	JTextField phoneTf;
	//JPanel mainPanel;
	JPanel GPanel;
	JComboBox<String> monthComboBox;
	JComboBox<String> dayComboBox;
	JRadioButton menButton;
	JRadioButton girlButton;
	JButton registerButton;
	JButton EscButton;
	JButton beforeButton;
	Font font = new Font("회원가입", Font.BOLD, 40);
	
	private boolean vkctrl = false;
	private boolean vkz = false;
	
	String year = "", month = "", day = "";
	String id = "", pass = "", passRe = "", name = "", sex = "", phone = "";
	Rpg lp;

	public signup(Rpg lp) {

		this.lp = lp;
		
		GPanel GPanel = new GPanel();
		GPanel.setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGTH);
		//mainPanel = new JPanel();
		// 레이아웃 설정
        setBounds(0, 0, 1600, 900);
        setLayout(null);

		idTf = new JTextField(15);
		passTf = new JPasswordField(15);
		passReTf = new JPasswordField(15);
		nameTf = new JTextField(15);
		yearTf = new JTextField(4);
		phoneTf = new JTextField(11);
		//phoneTf.setBounds(570, 299, 280, 30);

		monthComboBox = new JComboBox<String>(
				new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" });
		
		dayComboBox = new JComboBox<String>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31" });
		
		menButton = new JRadioButton("남자");
		girlButton = new JRadioButton("여자");
		ButtonGroup sexGroup = new ButtonGroup();
		sexGroup.add(menButton);
		sexGroup.add(girlButton);

		add(idTf); // 아이디
		idTf.setBounds(550, 199, 280, 22);

		add(passTf); // pass
		passTf.setBounds(550, 239, 280, 22);
		
		add(new JLabel("특수문자 + 8자")); //보안설정

		add(passReTf); // password 재확인
		passReTf.setBounds(550, 279, 280, 22);

		add(nameTf); // 이름
		nameTf.setBounds(550, 319, 280, 22);

		add(yearTf);
		yearTf.setBounds(550, 359, 38, 22);

		add(monthComboBox);
		monthComboBox.setBounds(600, 359, 40, 22);

		add(dayComboBox);
		dayComboBox.setBounds(650, 359, 40, 22);

		add(menButton);
		menButton.setBorderPainted(false);
		menButton.setFocusPainted(false);
		menButton.setContentAreaFilled(false);
		menButton.setFont(new Font("sans serif", Font.BOLD, 15));
		menButton.setForeground(Color.WHITE);
		menButton.setBounds(550, 399, 60, 22);

		add(girlButton);
		girlButton.setBorderPainted(false);
		girlButton.setFocusPainted(false);
		girlButton.setContentAreaFilled(false);
		girlButton.setFont(new Font("sans serif", Font.BOLD, 15));
		girlButton.setForeground(Color.WHITE);
		girlButton.setBounds(605, 399, 60, 22);

		add(phoneTf);
		phoneTf.setBounds(550, 439, 90, 22);
		/*BPanel = new JPanel();
		BPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		BPanel.setLayout(new BoxLayout(BPanel, BoxLayout.Y_AXIS));
		JLabel signupLabel = new JLabel("회원가입 화면 ");
		signupLabel.setFont(font);
		signupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);*/

		registerButton = new JButton(new ImageIcon("img/btRegister_hud.png"));
		registerButton.setBounds(550, 489, 104, 48);
		registerButton.setBorderPainted(false);
		registerButton.setFocusPainted(false);
		registerButton.setContentAreaFilled(false);
		add(registerButton);
		
		EscButton = new JButton(new ImageIcon("img/btESC_hud.png"));
		EscButton.setBounds(1100, 10, 82, 39);
		// 버튼 투명처리
		EscButton.setBorderPainted(false);
		EscButton.setFocusPainted(false);
		EscButton.setContentAreaFilled(false);
		
		add(EscButton);
		
		beforeButton = new JButton(new ImageIcon("img/btbefore_hud.png"));
		beforeButton.setBounds(30, 10, 104, 48);
		// 버튼 투명처리
		beforeButton.setBorderPainted(false);
		beforeButton.setFocusPainted(false);
		beforeButton.setContentAreaFilled(false);
		
		add(beforeButton);
		
		add(GPanel);

		monthComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == monthComboBox) {
					JComboBox monthBox = (JComboBox) e.getSource();
					month = (String) monthBox.getSelectedItem();
					System.out.println(month);
				}

			}
		});
		dayComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == dayComboBox) {
					JComboBox dayBox = (JComboBox) e.getSource();
					day = (String) dayBox.getSelectedItem();
					System.out.println(month);
				}
			}
		});

		menButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sex = e.getActionCommand();
			}
		});

		girlButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sex = e.getActionCommand();
			}
		});
		registerButton.addActionListener(new ActionListener() {      //회원가입버튼

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				register();
				
			}
		});
		
		EscButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		beforeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lp.card.previous(lp.cardPanel);
			}
		});
		setVisible(true);
		Focus();
		addListener();
	}
	
	
	
	class MouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Focus();
		}

	}
class KeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_ENTER:
				register();
				break;
			case KeyEvent.VK_CONTROL:
				vkctrl = true;
				break;
			case KeyEvent.VK_Z:
				vkz = true;
				break;
			}
			if(vkctrl && vkz)
				lp.card.previous(lp.cardPanel);
		}
	}
	public void register() {
		id = idTf.getText();
		pass = new String(passTf.getPassword());
		passRe = new String(passReTf.getPassword());
		name = nameTf.getText();
		year = yearTf.getText();
		phone = phoneTf.getText();

		String sql = "insert into user_info(id, password, name, birthday, sex, phoneNumber) values (?,?,?,?,?,?)";

		Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"); //8자 영문+특문+숫자
		Matcher passMatcher = passPattern1.matcher(pass);

		if (!passMatcher.find()) {
			JOptionPane.showMessageDialog(null, "비밀번호는 영문+특수문자+숫자 8자로 구성되어야 합니다", "비밀번호 오류", 1);
		} else if (!pass.equals(passRe)) {
			JOptionPane.showMessageDialog(null, "비밀번호가 서로 맞지 않습니다", "비밀번호 오류", 1);

		} else {
			try {
				Connection conn = lp.getConnection();

				PreparedStatement pstmt = conn.prepareStatement(sql);

				String date = yearTf.getText() + "-" + month + "-" + day;

				pstmt.setString(1, idTf.getText());
				pstmt.setString(2, pass);
				pstmt.setString(3, nameTf.getText());
				pstmt.setString(4, date);
				pstmt.setString(5, sex);
				pstmt.setString(6, phoneTf.getText());

				int r = pstmt.executeUpdate();
				System.out.println("변경된 row " + r);
				JOptionPane.showMessageDialog(null, "회원 가입 완료!", "회원가입", 1);
				lp.card.previous(lp.cardPanel); // 다 완료되면 로그인 화면으로
			} catch (SQLException e1) {
				System.out.println("SQL error" + e1.getMessage());
				if (e1.getMessage().contains("PRIMARY")) {
					JOptionPane.showMessageDialog(null, "아이디 중복!", "아이디 중복 오류", 1);
				} else
					JOptionPane.showMessageDialog(null, "정보를 제대로 입력해주세요!", "오류", 1);
			} // try ,catch
		}
	}
	
	public void addListener() {
		idTf.addKeyListener(new KeyListener());
		passTf.addKeyListener(new KeyListener());
		passReTf.addKeyListener(new KeyListener());
		nameTf.addKeyListener(new KeyListener());
		yearTf.addKeyListener(new KeyListener());
		phoneTf.addKeyListener(new KeyListener());
		monthComboBox.addKeyListener(new KeyListener());
		dayComboBox.addKeyListener(new KeyListener());
		menButton.addKeyListener(new KeyListener());
		girlButton.addKeyListener(new KeyListener());
		addMouseListener(new MouseListener());
		addKeyListener(new KeyListener());
	}
	
	public void Focus() {
		this.setFocusable(true); // 키리스너 포커스
		this.requestFocus();
		System.out.println("signup Focus");
	}
	class GPanel extends JPanel{
		ImageIcon loginScreen = new ImageIcon("./login.png");
		Image img = loginScreen.getImage();
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img,0,0,getWidth(),getHeight(),this);
			g.setFont(new Font("문체부 궁체 흘림체", Font.BOLD, 45));
			g.setColor(new Color(0, 222, 233));
			g.drawString("회원가입", 524, 129);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 25));
			g.setColor(new Color(71, 99, 230));
			g.drawString("아이디", 445, 222);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(71, 99, 230));
			g.drawString(": ", 530, 222);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 25));
			g.setColor(new Color(71, 99, 230));
			g.drawString("비밀번호", 420, 262);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(71, 99, 230));
			g.drawString(": ", 530, 262);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 25));
			g.setColor(new Color(71, 99, 230));
			g.drawString("비밀번호 확인", 360, 302);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(71, 99, 230));
			g.drawString(": ", 530, 302);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 25));
			g.setColor(new Color(71, 99, 230));
			g.drawString("이 름", 455, 342);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(71, 99, 230));
			g.drawString(": ", 530, 342);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 25));
			g.setColor(new Color(71, 99, 230));
			g.drawString("생 일", 455, 382);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(71, 99, 230));
			g.drawString(": ", 530, 382);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 25));
			g.setColor(new Color(71, 99, 230));
			g.drawString("성 별", 455, 422);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(71, 99, 230));
			g.drawString(": ", 530, 422);
			g.setFont(new Font("문체부 훈민정음체", Font.BOLD, 25));
			g.setColor(new Color(71, 99, 230));
			g.drawString("전화번호", 420, 462);
			g.setFont(new Font("궁서", Font.BOLD, 30));
			g.setColor(new Color(71, 99, 230));
			g.drawString(": ", 530, 462);
			g.setFont(new Font("궁서", Font.BOLD, 17));
			g.setColor(Color.WHITE);
			g.drawString("Ctrl + Z", 130, 20);
		}
	}
}