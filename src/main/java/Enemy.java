import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Enemy {
	Image Image = new ImageIcon("./alien2.png").getImage();
	
	int x, y;
	int mX = Main.SCREEN_WIDTH;
	int mY = Main.SCREEN_HEIGTH;
	
	int width = Image.getWidth(null);
	int height = Image.getHeight(null);
	int move;
	int hp = 10;
	int Speed = 4;
	int halSpeed = 2;
	int px = Speed, py = Speed;
	int mx = halSpeed, my = halSpeed;
	ArrayList<EnemyAttack> EnemyAttackList = new ArrayList<EnemyAttack>();
	
	public Enemy(int move, int x, int y) {
		this.x = x;
		this.y = y;
		this.move = move;
	}
	public boolean checkCollision(Enemy other) {
		Rectangle myRect = new Rectangle();
		Rectangle otherRect = new Rectangle();
		myRect.setBounds(x, y, width, height);
		otherRect.setBounds(other.x, other.y, other.width,other.height);
		return myRect.intersects(otherRect);
	}
	public void move(int cnt, int px, int py) {
		if(px-x>0)
			this.px = Speed;
		else if(px-x<0)
			this.px = -Speed;
		if(py-y>0)
			this.py = Speed;
		else if(py-y<0)
			this.py = -Speed;
		
		if(0<=cnt%65 && cnt%65<=20) {
			if(px-x>width || px-x<-width)
					this.x += this.px;
			if(py-y>height || py-y<-height)
					this.y += this.py;
		}
	}
	
	public void Xmove(int cnt, Enemy enemy1) {
		if(enemy1.x-x>0)
			enemy1.mx = halSpeed;
		else if(enemy1.x-x<0)
			enemy1.mx = -halSpeed;
		if(0<=cnt%65 && cnt%65<=20) {
			if(px-x>width || px-x<-width)
				enemy1.x += enemy1.mx;
		}
	}
	public void Ymove(int cnt, Enemy enemy1) {
		if(enemy1.y-y>0)
			enemy1.my = halSpeed;
		else if(enemy1.y-y<0)
			enemy1.my = -halSpeed;
		if(0<=cnt%65 && cnt%65<=20) {
			if(py-y>height || py-y<-height)
				enemy1.y += enemy1.my;
		}
	}
	
	public EnemyAttack EnemyAttack(int i) {
		if(i<EnemyAttackList.size()) {
			return EnemyAttackList.get(i);
		}
		return null;
	}
	
}