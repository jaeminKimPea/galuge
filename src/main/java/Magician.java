
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class Magician extends Thread  {
	private Image BufferImage;
	private Graphics Gc;
	
	private Image Charactor = Toolkit.getDefaultToolkit().getImage("./Main_Character_2.png");
	Image NowCharactor;
	private int Sel = 0, Sely = 0, firemove = 0;
	private int SelLimit = 3;
	//private boolean Released = false;
	
	private int delay = 20;
	private long pretime;
	private int cnt;
	private int Fixcnt = -1;
	private int maxcntcount = 0;
	private int Fixcntcount;
	private int score = 0;
	
	private int playerX, instantx;
	private int playerY, instanty;
	public static final int playerWidth = 51; // 48
	public static final int playerHeight = 69; // 66
	public static final int playerSpeed = 10;
	private int playerHp;
	private int FixCount = 0, Fix = 0;
	private int Fixpressed = 1;
	private int enemycount = 25;
	
	private boolean actSoon = false;
	private boolean up, down, left, right;
	private boolean upshooting, downshooting, leftshooting, rightshooting, shooting;
	private boolean reup, redown, releft, reright;
	private boolean rlcount = false, udcount = false;
	private boolean isOver;
	//private boolean pause= false;
	
	private ArrayList<PlayerAttack> PlayerAttackList = new ArrayList<PlayerAttack>();
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private ArrayList<EnemyMelee> enemyMeleeList = new ArrayList<EnemyMelee>();
	
	private PlayerAttack playerAttack;
	private EnemyAttack enemyAttack;
	private EnemyAttack enemyAttack3;
	private EnemyAttack enemyAttack0;
	private EnemyAttack enemyAttack1;
	private EnemyAttack enemyAttack2;
	private EnemyMeleeAttack enemyMeleeAttack;
	
	private Enemy enemy;
	private Enemy enemy1;
	private EnemyMelee enemyMelee;
	private EnemyMelee enemyMelee1;
	private EnemyMelee enemyMelee2;
	
	private Audio backgroundMusic;
	private Audio hitSound;
	
	String id = "";
	int ScorePre = 0;
	Rpg lp;
	Ranking Rkp;
	public Magician(Rpg lp,Ranking Rkp){
		this.lp = lp;
		this.Rkp = Rkp;
	}
	@Override
	public void run() {
		
		backgroundMusic = new Audio("./Audio/gameBGM.wav",true);
		hitSound = new Audio("./Audio/hitSound.wav", false);
		
		reset();
		
		while(true) {
			while(!isOver) {
				pretime = System.currentTimeMillis();
				if(System.currentTimeMillis() - pretime < delay) {
					try {
						//System.out.println("player: "+playerX + "," + playerY);
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						move();
						keyProcess();
						playerAttackProcess();
						Fixmmsec();
						enemyAppearProcess();
						enemyMoveProcess();
						enemyAttackProcess();
						enemyMeleeAppearProcess();
						enemyMeleeMoveProcess();
						enemyMeleeAttackProcess();
						if(cnt ==Integer.MAX_VALUE) {
							cnt = 0;
							maxcntcount++;
						}
						else
							cnt++;
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void reset() {
		isOver = false;
		cnt = 0;
		score = 0;
		playerHp = 50;
		playerX = (Main.SCREEN_WIDTH-playerWidth)/2;
		playerY = (Main.SCREEN_HEIGTH-playerHeight)/2;
		
		backgroundMusic.start();
		
		PlayerAttackList.clear();
		enemyList.clear();
		enemyMeleeList.clear();
		
	}
	
	
	private void move() {
			if(Sel<=(SelLimit-1)) {
				if(cnt%11==0 || actSoon) {
					if(up || down || left || right) {
					if(Sel != 2)
						Sel++;
					else
						Sel = 1;
				}
					actSoon = false;
			}
		}
			
	}
	
	private void keyProcess() {
		if(udcount) {
			if (up && playerY - playerSpeed>0) playerY -= playerSpeed;
		}
		else {
			if (down && playerY + playerHeight + playerSpeed<Main.SCREEN_HEIGTH) playerY += playerSpeed;
		}
		if(rlcount) {
			if (left && playerX - playerSpeed>0) playerX -= playerSpeed;
		}
		else {
			if (right && playerX + playerWidth + playerSpeed<Main.SCREEN_WIDTH) playerX += playerSpeed;
		}
		if (cnt % 10 == 0 && shooting) {
			Shootinginstant();
			playerAttack = new PlayerAttack(firemove, instantx, instanty);
			PlayerAttackList.add(playerAttack);
		}
	}
	
	private void playerAttackProcess() {
		for (int i = 0; i<PlayerAttackList.size();i++) {
			playerAttack = PlayerAttackList.get(i);
			FixfireMove(playerAttack);
			attackrecognition(playerAttack);
		}
	}
	
	public void enemyAppearProcess() {
		if(enemyList.size()<5 && enemycount>0) {
			if(cnt > 200 && cnt%60 == 0) {
				int move = (int)(Math.random()*3.8 - 0.4);
			
			if (move == 3)  // 위
				enemy = new Enemy(move, (int)(Math.random()*1120), 0);
			if (move == 0)  // 아래
				enemy = new Enemy(move, (int)(Math.random()*1120), 640);
			if (move == 1)  // 왼쪽
				enemy = new Enemy(move, 0, (int)(Math.random()*621));
			if (move == 2)  // 오른쪽
				enemy = new Enemy(move, 1120, (int)(Math.random()*621));
			enemyList.add(enemy);
			enemycount -= 1;
			}
		}else if(enemycount<=0) {
			if(cnt%240 == 0) {
				enemycount += 5;
			}
		}
	}
	
	public void enemyMoveProcess() {
		for(int i =  0;i<enemyList.size(); i++) {
			enemy = enemyList.get(i);
			enemy.move(cnt,playerX,playerY);
			for(int j = 0;j<enemyList.size() ; j++) {
				if(i!=j) {
				enemy1 = enemyList.get(j);
				if(enemy.x>enemy1.x-25 && enemy.x<enemy1.x + enemy1.width) {
					enemy.Xmove(cnt, enemy1);
				}
				if(enemy.y>enemy1.y-25 && enemy.y<enemy1.y + enemy1.height) {
					enemy.Ymove(cnt, enemy1);
				}
				}
			}
		}
	}
	
	public void enemyAttackProcess() {
		if(cnt > 210 && cnt%100==0) {
			for(int i =  0;i<enemyList.size(); i++) {
				enemy = enemyList.get(i);
				if(enemy.move == 1 && enemy.move == 2) {
					enemyAttack3 = new EnemyAttack(3, enemy.x + 25, enemy.y + 59);
					enemyAttack0 = new EnemyAttack(0, enemy.x + 25, enemy.y - 39);
					enemy.EnemyAttackList.add(enemyAttack3);
					enemy.EnemyAttackList.add(enemyAttack0);
				}
				else {
					enemyAttack1 = new EnemyAttack(1, enemy.x + 59, enemy.y + 25);
					enemyAttack2 = new EnemyAttack(2, enemy.x - 39, enemy.y + 25);
					enemy.EnemyAttackList.add(enemyAttack1);
					enemy.EnemyAttackList.add(enemyAttack2);
				}
				
				
			}
		}
		
		for(int i =  0;i<enemyList.size(); i++) {
			enemy = enemyList.get(i);
			for(int j =  0;j<enemy.EnemyAttackList.size(); j++) {
			enemyAttack = enemy.EnemyAttackList.get(j);
			enemyAttack.fire();
			if(enemyAttack.x>playerX && enemyAttack.x<playerX + playerWidth &&
					enemyAttack.y>playerY && enemyAttack.y<playerY + playerHeight) {
				hitSound.start();
				playerHp -= enemyAttack.Attack;
				enemy.EnemyAttackList.remove(enemyAttack);
				if(playerHp <= 0) {
					isOver = true;
					if(score != 0)
						Scoresave();
					lp.card.last(lp.cardPanel);
				}
			}
			}
		}
	}
	public void enemyMeleeAppearProcess() {
		if(enemyMeleeList.size()<2) {
			if(cnt > 200 && cnt%80 == 0) {
				int move = (int)(Math.random()*3.8 - 0.4);
			
			if (move == 3)  // 위
				enemyMelee = new EnemyMelee(move, (int)(Math.random()*1120), 0);
			if (move == 0)  // 아래
				enemyMelee = new EnemyMelee(move, (int)(Math.random()*1120), 640);
			if (move == 1)  // 왼쪽
				enemyMelee = new EnemyMelee(move, 0, (int)(Math.random()*621));
			if (move == 2)  // 오른쪽
				enemyMelee = new EnemyMelee(move, 1120, (int)(Math.random()*621));
			enemyMeleeList.add(enemyMelee);
			}
		}
	}
	public void enemyMeleeMoveProcess() {
		for(int i =  0;i<enemyMeleeList.size(); i++) {
			enemyMelee = enemyMeleeList.get(i);
			enemyMelee.move(cnt,playerX,playerY);
			/*for(int j = 0;j<enemyList.size() ; j++) {
				if(i!=j) {
				enemyMelee1 = enemyMeleeList.get(j);
				if(enemyMelee.x>enemyMelee1.x-25 && enemyMelee.x<enemyMelee1.x + enemyMelee1.width) {
					enemyMelee.Xmove(cnt, enemy1);
				}
				if(enemyMelee.y>enemyMelee1.y-25 && enemyMelee.y<enemyMelee1.y + enemyMelee1.height) {
					enemy.Ymove(cnt, enemy1);
				}
				}
			}*/
		}
	}
	
	public void enemyMeleeAttackProcess() {
		for(int i =  0;i<enemyMeleeList.size(); i++) {
			enemyMelee = enemyMeleeList.get(i);
			MeleeAttackcommend(enemyMelee);
			enemyMelee.Attackcount();
			
			
		}
		
	}
	public void MeleeAttackcommend(EnemyMelee enemyMelee) {
		if(enemyMelee.getAttackSel() == 11 && enemyMelee.Sel == 9) {
			int movex;
			if(enemyMelee.move == 1) {
				enemyMeleeAttack = new EnemyMeleeAttack(enemyMelee.move, enemyMelee.x - enemyMelee.width, enemyMelee.y - enemyMelee.height/4);
				movex = enemyMelee.x - enemyMeleeAttack.Awidth;
			}
			else {
				enemyMeleeAttack = new EnemyMeleeAttack(enemyMelee.move, enemyMelee.x - enemyMelee.width/4
						, enemyMelee.y - enemyMelee.height/4);
				movex = enemyMelee.x + enemyMelee.width + enemyMelee.widthplus + enemyMeleeAttack.Awidth;
			}
			enemyMelee.Attackadd(enemyMeleeAttack);
			if(enemyMelee.Attackcount == 1) {
			if(movex >playerX && movex <playerX + playerWidth &&
				enemyMelee.y + enemyMeleeAttack.Aheight>playerY && enemyMelee.y + enemyMeleeAttack.Aheight<playerY + playerHeight) {
				hitSound.start();
				playerHp -= enemyMeleeAttack.Attack;
				//System.out.println(enemyMelee.getAttackSel());
				//System.out.println(enemyMeleeAttack.Attack);
				enemyMelee.Attackcount = 0;
				if(playerHp <= 0) {
					isOver = true;
					if(score != 0)
						Scoresave();
					lp.card.last(lp.cardPanel);
				}
				}
			}
		}
	}
	
	public void attackrecognition(PlayerAttack playerAttack) {
		enemyattackrecognition(playerAttack);
		enemyattackMeleerecognition(playerAttack);
	}
	
	public void enemyattackrecognition(PlayerAttack playerAttack) {
		for(int j = 0;j<enemyList.size(); j++) {
			enemy1 = enemyList.get(j);
			if(playerAttack.x>enemy1.x && playerAttack.x<enemy1.x + enemy1.width &&
					playerAttack.y>enemy1.y && playerAttack.y<enemy1.y + enemy1.height) {
				enemy1.hp -= playerAttack.attack;
				PlayerAttackList.remove(playerAttack);
			}
			if(enemy1.hp <=0) {
				hitSound.start();
				enemyList.remove(enemy1);
				score += 1000;
			}
		}
	}
	
	public void enemyattackMeleerecognition(PlayerAttack playerAttack) {
		for(int j = 0;j<enemyMeleeList.size(); j++) {
			enemyMelee1 = enemyMeleeList.get(j);
			if(playerAttack.x + 25>enemyMelee1.x && playerAttack.x<enemyMelee1.x + enemyMelee1.width + enemyMelee1.widthplus &&
					playerAttack.y + 25>enemyMelee1.y && playerAttack.y<enemyMelee1.y + enemyMelee1.height + enemyMelee1.heightplus) {
				enemyMelee1.hp -= playerAttack.attack;
				PlayerAttackList.remove(playerAttack);
				enemyMelee1.setDamage(true);
				if(enemyMelee1.hp >=80) {
					if(!playerAttack.isFix()) {
						if(playerAttack.move == 3 || playerAttack.move == 2)
							enemyMelee1.setMove(1);
						else
							enemyMelee1.setMove(2);
					}
					else {
						if(Sely == 3 || Sely == 2)
							enemyMelee1.setMove(1);
						else
							enemyMelee1.setMove(2);
					}
					
						
				}
				else {
					if(!playerAttack.isFix()) {
						if(playerAttack.move == 3 || playerAttack.move == 2)
							enemyMelee1.setMove(0);
						else
							enemyMelee1.setMove(3);
					}
					else {
						if(Sely == 3 || Sely == 2)
							enemyMelee1.setMove(0);
						else
							enemyMelee1.setMove(3);
					}
				}
			}
			
			if(enemyMelee1.hp <=0) {
				hitSound.start();
				enemyMeleeList.remove(enemyMelee1);
				score += 1000;
			}
		}
	}
	
	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		infoDraw(g);
	}
	
	public void infoDraw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial",Font.BOLD, 40));
		g.drawString("SCORE : "+ score, 40, 80);
		if(isOver) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial",Font.BOLD, 80));
			g.drawString("Press R to restart", 295, 300);
		}
	}
	
	
	public void playerDraw(Graphics g) {
			g.drawImage(Charactor, playerX, playerY,
			playerX + playerWidth, playerY + playerHeight,
		    Sel * (96), Sely * 132,
		    Sel * (96) + (96),
		    Sely * (132) + (132), null);
			g.setColor(Color.RED);
			g.fillRect(playerX-5, playerY-30, playerHp *2, 10);
			for (int i = 0; i<PlayerAttackList.size();i++) {
				playerAttack = PlayerAttackList.get(i);
				if(!playerAttack.isFix())
					g.drawImage(playerAttack.Image, playerAttack.x, playerAttack.y, null);
				else
					g.drawImage(playerAttack.Image_, playerAttack.x, playerAttack.y, null);
				
			}
	}
	
	public void enemyDraw(Graphics g) {
		for(int i =  0;i<enemyList.size(); i++) {
			enemy = enemyList.get(i);
			g.drawImage(enemy.Image, enemy.x, enemy.y, null);
			g.setColor(Color.RED);
			g.fillRect(enemy.x+1, enemy.y-40, enemy.hp *6, 10);
			for(int j =  0;j<enemy.EnemyAttackList.size(); j++) {
			enemyAttack = enemy.EnemyAttackList.get(j);
			g.drawImage(enemyAttack.Image_0, enemyAttack.x, enemyAttack.y, null);
			}
			
		}
		for(int i =  0;i<enemyMeleeList.size(); i++) {
			enemyMelee = enemyMeleeList.get(i);
			g.drawImage(enemyMelee.Charactor, enemyMelee.x, enemyMelee.y,
					enemyMelee.x + enemyMelee.width + enemyMelee.heightplus, enemyMelee.y + enemyMelee.height + enemyMelee.heightplus,
					enemyMelee.Sel * enemyMelee.width, enemyMelee.move * enemyMelee.height,
					enemyMelee.Sel * enemyMelee.width + enemyMelee.width,
					enemyMelee.move * enemyMelee.height + enemyMelee.height, null);
			g.setColor(Color.RED);
			g.fillRect(enemyMelee.x-5, enemyMelee.y-30, enemyMelee.hp *2, 10);
			g.setColor(Color.RED);
			for(int j =  0;j<enemyMelee.AttackList().size(); j++) {
				enemyMeleeAttack = enemyMelee.AttackList().get(j);
				g.drawImage(enemyMeleeAttack.Image, enemyMeleeAttack.x, enemyMeleeAttack.y,
						enemyMeleeAttack.width, enemyMeleeAttack.height, null);
			}
		}
		
	}
	
	public boolean isOver() {
		return isOver;
	}
	
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	
	public void setUp(boolean up) {
		if(!up)
			actSoon = true;
		this.up = up;
		
		if(this.up){
			udcount = true;
			Sely = 3;
		}
		else {
			udcount = false;
			if (!down) {
				Sel = 0;
			}
			resetMotion();
		}
	}

	public void setDown(boolean down) {
		if(!down)
			actSoon = true;
		this.down = down;
		if(this.down){
			udcount = false;
			Sely = 0;
		}
		else {
			udcount = true;
			if (!up) {
				Sel = 0;
			}
			resetMotion();
		}
	}
	
	public void setLeft(boolean left) {
		if(!left)
			actSoon = true;
		
		this.left = left;
		if(this.left) {
			rlcount = true;
			Sely = 1;
		}
		else {
			rlcount = false;
			if (!right) {
				Sel = 0;
			}
			resetMotion();

		}
	}
	
	public void setRight(boolean right) {
		if(!right)
			actSoon = true;
		
		this.right = right;
		if(this.right){
			rlcount = false;
			Sely = 2; 
		}
		else {
			rlcount = true;
			if(!left){
				Sel = 0;
			}	
			resetMotion();
		}
	}
	
	public void resetMotion() {
		if(up)
			Sely = 3;
		if(down)     //  모션 전환
			Sely = 0;
		if(left)
			Sely = 1;
		if(right)
			Sely = 2;
		
	}
	
	public int getSelLimit() {
		return SelLimit;
	}

	private void Shootinginstant() {
		if(firemove == 3) {  // 위
			instantx = playerX + (playerWidth/8); instanty = playerY - 40;
		}
		if(firemove == 0) { // 아래
			instantx = playerX + (playerWidth/8); instanty = playerY + playerHeight;
		}
		if(firemove == 1) {// 왼쪽
			instantx = playerX - 20; instanty = playerY + (playerHeight/2) - 10;
		}
		if(firemove == 2) {// 오른쪽
			instantx = playerX + 20; instanty = playerY + (playerHeight/2) - 10;
		}

	}
	
	
	public void FixfireMove(PlayerAttack playerAttack){
		if(up)
			playerAttack.fire(3);
		else if(down)
			playerAttack.fire(0);
		else if(left)
			playerAttack.fire(1);
		else if(right)
			playerAttack.fire(2);
		else
			playerAttack.fire(-1);
	}
	
	public void Fixcommend(int fpressed) {
		if(Fixpressed==1) {
			Fixpressed = fpressed;
			setAllFix(true);
		}
		else
			Fixpressed = fpressed;
	}
	public void setAllFix(boolean fix) {
		if(PlayerAttackList.size() != 0) {
			FixCount++;
			if(Fixcnt == -1) {
				Fixcnt = cnt;
				Fixcntcount = maxcntcount;
				System.out.println(Fixcnt);
			}
		
			if(Fix < 4) {
				if(FixCount%2 != 0) {
					Fix++;
					for (int i = 0; i<PlayerAttackList.size();i++) {
						playerAttack = PlayerAttackList.get(i);
						playerAttack.setFix(fix);
					}
				}
				else if(FixCount%2 == 0 || Fixcnt == 0){
					for (int i = 0; i<PlayerAttackList.size();i++) {
						playerAttack = PlayerAttackList.get(i);
						playerAttack.setFix(!fix);
					}
					Fixcnt = -1;
				}
			}
		}
	}
	public void Fixmmsec() {
		if(Fixcnt != -1) {
			int re;
			if(Fixcntcount < maxcntcount && Fixcnt >= Integer.MAX_VALUE-1499 ) {
				Fixcnt -= Integer.MAX_VALUE;
			}
			if(Fixcnt < 0)
				re = -cnt + Fixcnt;
			else
				re = cnt - Fixcnt;
			System.out.println("re: "+re);
			if(re>=1500 || re<=-1500)
				Fixcnt = -1;
			if(Fixcnt == -1){
				for (int i = 0; i<PlayerAttackList.size();i++) {
					playerAttack = PlayerAttackList.get(i);
					playerAttack.setFix(false);
					Fixcntcount = maxcntcount;
				}
			}
		}
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public int getPlayerWidth() {
		return playerWidth;
	}

	public int getPlayerHeight() {
		return playerHeight;
	}

	public void setUpshooting(boolean upshooting) {
		this.upshooting = upshooting;
		if(this.upshooting) {
			firemove = 3;
		}
		if(!rightshooting && !downshooting && !leftshooting) {
			shooting = upshooting;
		}
	}

	public void setDownshooting(boolean downshooting) {
		this.downshooting = downshooting;
		if(this.downshooting) {
			firemove = 0;
		}
		if(!rightshooting && !upshooting && !leftshooting) {
			shooting = downshooting;
		}
	}

	public void setLeftshooting(boolean leftshooting) {
		this.leftshooting = leftshooting;
		if(this.leftshooting) {
			firemove = 1;
		}
		if(!rightshooting && !upshooting && !downshooting) {
			shooting = leftshooting;
		}
		
	}

	public void setRightshooting(boolean rightshooting) {
		this.rightshooting = rightshooting;
		if(this.rightshooting) {
			firemove = 2;
		}
		if(!leftshooting && !upshooting && !downshooting) {
			shooting = rightshooting;
		}
	}

	public void Scoresave() {
		try {

			String sql_scorequery = String.format("SELECT score FROM user_info WHERE id = '%s'",
					id);
			Connection conn = lp.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet scoreset = stmt.executeQuery(sql_scorequery);
			scoreset.next();
			ScorePre = Integer.parseInt(scoreset.getString(1));

			if (score > ScorePre) {
				System.out.println("score: "+score);
				update(score);
				lp.card.last(lp.cardPanel);
			}
			stmt.close();
			conn.close();

		} catch (SQLException ex) {
			System.out.println("SQLException" + ex);
		}
	}
	public void update(int score) {
		try {
			System.out.println("!!!new Score!!!");
			String sql_scorequery = String.format("UPDATE user_info SET score = %d WHERE id ='%s';",
					score, id);
			System.out.println("user: "+id);
			Connection conn = lp.getConnection();
			Statement stmt = conn.createStatement();
			int saveset = stmt.executeUpdate(sql_scorequery);
			if(saveset > 0) {
				System.out.println("Score 갱신");
				Rkp.Inforeset();
			}else {
				System.out.println("Score 갱신 실패");
			}
			stmt.close();
			conn.close();
			System.out.println("Current Score: " + score);

		} catch (SQLException ex) {
			System.out.println("SQLException" + ex);
		}
		
			
		
	}
	
	}
