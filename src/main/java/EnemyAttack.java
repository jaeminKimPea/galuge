import java.awt.*;
import javax.swing.*;

public class EnemyAttack {
	Image Image_0;
	Image[] Image = {
			new ImageIcon("./fire0.png").getImage(),
			new ImageIcon("./fire1.png").getImage(),
			new ImageIcon("./fire2.png").getImage(),
			new ImageIcon("./fire3.png").getImage()
	};
	
	int x, y;
	int width;
	int height;
	int Attack = 5;
	int move;
	
	public EnemyAttack(int move, int x, int y) {
		this.x = x;
		this.y = y;
		this.move = move;
		Image_0 = Image[this.move];
		width = Image_0.getWidth(null);
		height = Image_0.getHeight(null);
	}
	
	public void fire() {
		if(move == 3)
			this.y += 5;
		if(move == 0)
			this.y -= 5;
		if(move == 1)
			this.x += 9;
		if(move == 2)
			this.x -= 9;
	}
}
