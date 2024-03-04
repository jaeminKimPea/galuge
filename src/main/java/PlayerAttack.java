import javax.swing.*;
import java.awt.*;


public class PlayerAttack {
	Image Image;
	Image Image_;
	Image[] Image0 = {
			new ImageIcon("./bullet0.png").getImage(), // 아래
			new ImageIcon("./bullet1.png").getImage(), // 왼쪽
			new ImageIcon("./bullet2.png").getImage(),  // 오른쪽
			new ImageIcon("./bullet3.png").getImage() // 위
	};
	Image[] Image_0 = {
			new ImageIcon("./bullet_0.png").getImage(), // 아래
			new ImageIcon("./bullet_1.png").getImage(), // 왼쪽
			new ImageIcon("./bullet_2.png").getImage(),  // 오른쪽
			new ImageIcon("./bullet_3.png").getImage() // 위
	};
	
	int x;
	int y;
	int move;
	int width;
	int height;
	int attack = 5;
	private boolean fix;
	Magician Magician;
	public PlayerAttack(int move, int x,int y) {
		this.move = move;
		this.x = x;
		this.y = y;
		fix = false;
		Image = Image0[this.move];
		Image_ = Image_0[this.move];
		width = Image.getWidth(null)-5;
		height = Image.getHeight(null)-5;
	}
	
	public void fire(int Sely) {
		if(!fix) {
		if(this.move == 3)  // 위
			this.y -= 15;
		if(this.move == 0)  // 아래
			this.y += 15;
		if(this.move == 1) // 왼쪽
			this.x -= 15;
		if(this.move == 2) // 오른쪽
			this.x += 15;
		}
		else {
			   // 위
			if(Sely == 3)
				this.y -= Magician.playerSpeed;
			  // 아래
			if(Sely == 0)
				this.y += Magician.playerSpeed;
			  // 왼쪽
			if(Sely == 1)
				this.x -= Magician.playerSpeed;
			  // 오른쪽
			if(Sely == 2)
				this.x += Magician.playerSpeed;
		}
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

	public boolean isFix() {
		return fix;
	}
	
}
