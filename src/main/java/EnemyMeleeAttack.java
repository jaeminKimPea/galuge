import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class EnemyMeleeAttack {
	Image Image = new ImageIcon("./bloodImpact_.png").getImage();
	
	int x, y;
	int width = Image.getWidth(null) - 25;
	int height = Image.getHeight(null) - 25;
	int Awidth = 29; // 14
	int Aheight = 22;
	int Attack = 5;
	int move;
	
	boolean AttackAct = true;
	int AttackCount = 0;
	
	public EnemyMeleeAttack(int move, int x, int y) {
		this.x = x;
		this.y = y;
		this.move = move;
	}
	
	public void fire() {
		if(move == 1)
			this.x += 9;
		if(move == 2)
			this.x -= 9;
	}
}
