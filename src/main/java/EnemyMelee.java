import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EnemyMelee {
	int x, y;
	int mX = Main.SCREEN_WIDTH;
	int mY = Main.SCREEN_HEIGTH;
	Image Charactor = Toolkit.getDefaultToolkit().getImage("./skeleton_.png");
	
	Image NowCharactor;
	int Sel = 0;
	private int  nowMove = 0;
	private int Defencemotion = 6;
	private static final int DefenceLimit = 8;
	private int DamageCount = 2;
	private static final int DamageLimit = 11;
	private int movecount = 0;
	int Attackcount = 1;
	private int Attackmove = 1;
	private int AttackSel = 9;
	private int dcount = 0;
	private int Abnomalcount = 0;
	private int Abnomal = -1;
	private static final int SelLimit = 2;
	private boolean Damage = false;
	private boolean Move = true;
	private boolean Attack = false;
	
	int width = 32;
	int widthplus = 15;
	int height = 47;
	int heightplus = 15;
	int move;
	int hp = 100;
	int diagonalSpeed = 2;
	int px = diagonalSpeed, py = diagonalSpeed;
	int mx = diagonalSpeed, my = diagonalSpeed;
	
	boolean udTrue = true, rlTrue = false;
	
	ArrayList<EnemyMeleeAttack> enemyMeleeAttackList = new ArrayList<EnemyMeleeAttack>();
	EnemyMeleeAttack MeleeAttack;
	
	
	public EnemyMelee(int move, int x, int y) {
		this.x = x;
		this.y = y;
		this.move = move;
	}
	public void move(int cnt, int px, int py) {
		if(!Damage) {
			if(!Attack) {
				if(Move) {
					changemovedirection(px, py); // 플레이어 쪽으로 움직이도록 
												// this.px this.py 변경
					if(cnt%52<=35) {
				
			
						movedirection(px, py); // 실제 좌표 변경 밑 모션 방향 전환
											// 공격 활성화 결정함
						if(!Attack) {
						if(cnt%52%5==0) {
							movecount += 3; // 0 5 10 15 20 25 30 35에 같이 카운트
							// System.out.println("Move: "+ movecount);
							if(movecount >108 )	// 결과적으로 5번 움직일 때 108이상이 된다.
								Move = false;
					
							moveMotion();
						}
						}
					}
					else
						Sel = 1;
				}
				else {
					restmovement(); // 일정이상 움직였을 때(Move == false)
				}					// 휴식
			}
			else {
				Attackmovement();
			}
		}
		else {
			AbnomalStatus(px, py);  // 대미지가 들어왔을 때(Damage == true)
		}					 // 상태이상
	}
	
	public void moveMotion() {
		if(udTrue || rlTrue) {
			if(Sel != SelLimit)
				Sel++;
			else
				Sel = 0;
			}
			else
				Sel = 1;
	}
	
	public void movedirection(int px, int py) {
		if(px-x>width || px-x<-width) {
			this.x += this.px;
			rlTrue = true;
		}
		else {
			rlTrue = false;
		}
		if(py-y>height || py-y<-height) {
			this.y += this.py;
			udTrue = true;
		}
		else {
			udTrue = false;
		}
		if(!udTrue && !rlTrue)
			Attack = true;
		
		if(udTrue && !rlTrue) {
			if(py-y>0)
				move = 0;
			else if(py-y<0)
				move = 3;
		}
		else {
			if(px-x>0)
				move = 2;
			else if(px-x<0)
				move = 1;
		}
	}
	
	public void direction(int px, int py) {
		if(py-y>0)
			move = 0;
		else if(py-y<0)
			move = 3;
		if(px-x>0)
			move = 2;
		else if(px-x<0)
			move = 1;
	}
	
	public void restmovement() {
		movecount--;
		
		if(Sel < 3 || (Sel > 4 && movecount%20 == 0)) {
			if(move == 0)
				move = 1;
			if(move == 3)
				move = 2;
			Sel = 3;
		}
		else  if(movecount%20 == 0){
			Sel++;
		}
		if(movecount <=0) {
			movecount =0;
			Move = true;
			Sel = 1;
		}
	}
	
	public void Attackmovement() {
		Attackmove++;
		if(Attackmove == 1) { // 1 
			//System.out.println(Attackmove);
			Sel = 9;
			if(move == 0)
				move = 1;
			else if(move == 3)
				move = 2;
		}
		if(AttackSel < 11) {
			if(Attackmove%8 == 0){ // 8 16
				//System.out.println(Attackmove);
				Sel = ++AttackSel;
			}
		}
		else {
			if(Sel > 9) {
				if(Attackmove%5 == 0){ //  20 25
					//System.out.println(Attackmove);
					Sel--;
				}
			}
			else {
				if(Attackmove/14 == 2) { // 28
					//System.out.println(Attackmove);
					AttackSel = 9;
					Attackmove =0;
					Attackcount = 1;
					Attack = false;
					Sel = 1;
				}
			}
		}
		
	}
	
	public void AbnomalStatus(int px, int py) {
		if(hp > 80) {
			DefenceMotion(px, py);
		}
		else {
			DamageMotion(px, py);
		}
		Attack = false;
	}
	
	public void DefenceMotion(int px, int py) {
		if(Abnomalcount < dcount) { // 이 조건문 전에 공격을 맞은 방향으로 move 바뀌는 상호작용
			Defencemotion = 6;   // 방어 자세
			Sel = Defencemotion;
			
			Abnomal = -1;     // 카운트 초기화
			Abnomalcount = dcount; // 대미지로 인한 방어 모션 전환 false
		}
		Abnomal++;
		if(Sel < DefenceLimit) {
			if(Abnomal %100 == 50) {
				System.out.println("Abnomal: "+Abnomal);
				Sel++;
			}
		}
		else { // 대미지를 더 안받고 모션이 끝났을 경우
			Abnomal = -1;
			Damage = false;
			direction(px, py); // 플레이어 방향으로 전환
			Sel = 1; // 걷지 않는 자세
		}
		if(Sel == DefenceLimit - 1)
			if(Abnomal % 25 == 0) {
				changedirectionControl(px, py, 9);
				movedirection(px, py);
			}
	}
	
	public void DamageMotion(int px, int py) {
		if(Abnomalcount < dcount) { // 대미지에 의한 모션 전환
			// 이 전에 공격과 상호작용으로 대미지를 받은 방향으로 모션 전환
			if(DamageCount < DamageLimit)
				DamageCount++;
			Sel = DamageCount;
			Abnomal = -1;    // 대미지 받을 경우 카운트 초기화
			Abnomalcount = dcount;
		}
		Abnomal++;
		if(DamageCount == DamageLimit && Abnomal%25 == 24)
			hp += 2;
		if(Abnomal > 300) {  //카운트가 지나갈 때 까지 현재 모션 고정
			Abnomal = -1;
			Damage = false;
			direction(px, py); // 플레이어 방향으로 전환
			Sel = 1; // 걷지 않는 자세
		}
	}
	
	public void setDamage(boolean damage) {
		Damage = damage;
		dcount++;
	}
	
	public void changemovedirection(int px, int py) {
		if(px-x>0)
			this.px = diagonalSpeed;
		else if(px-x<0)
			this.px = -diagonalSpeed;
		if(py-y>0)
			this.py = diagonalSpeed;
		else if(py-y<0)
			this.py = -diagonalSpeed;
	}
	public void changedirectionControl(int px, int py, int diagonalSpeed) {
		if(px-x>0)
			this.px = diagonalSpeed;
		else if(px-x<0)
			this.px = -diagonalSpeed;
		if(py-y>0)
			this.py = diagonalSpeed;
		else if(py-y<0)
			this.py = -diagonalSpeed;
	}
	
	public void XYmove(int px, int py) {
		if(px-x>width || px-x<-width)
			x += this.px/2;
		if(py-y>height || py-y<-height)
			y += this.py/2;
	}
	
	public void Xmove(int cnt, EnemyMelee enemyMelee1) {
		if(enemyMelee1.x-x>0)
			enemyMelee1.mx = diagonalSpeed;
		else if(enemyMelee1.x-x<0)
			enemyMelee1.mx = -diagonalSpeed;
		if(cnt%65<=20) {
			if(px-x>width || px-x<-width)
				enemyMelee1.x += enemyMelee1.mx;
		}
	}
	public void Ymove(int cnt, EnemyMelee enemyMelee1) {
		if(enemyMelee1.y-y>0)
			enemyMelee1.my = diagonalSpeed;
		else if(enemyMelee1.y-y<0)
			enemyMelee1.my = -diagonalSpeed;
		if(cnt%65<=20) {
			if(py-y>height || py-y<-height)
				enemyMelee1.y += enemyMelee1.my;
		}
	}
	public void setMove(int move) {
		this.move = move;
	}
	public int getAttackSel() {
		return AttackSel;
	}
	
	public void Attackadd(EnemyMeleeAttack Attack) {
		if(Attack.AttackAct) {
		enemyMeleeAttackList.add(Attack);
		Attack.AttackAct = false;
		}
	}
	
	public void Attackcount() {
		for(int i = 0; i<enemyMeleeAttackList.size();i++) {
			MeleeAttack = enemyMeleeAttackList.get(i);
			if(!MeleeAttack.AttackAct) {
				MeleeAttack.AttackCount++;
				if(MeleeAttack.AttackCount == 300) {
					enemyMeleeAttackList.remove(MeleeAttack);
					MeleeAttack.AttackAct = true;
					MeleeAttack.AttackCount = 0;
				}
			}
		}
	}
	
	public ArrayList<EnemyMeleeAttack> AttackList() {
			return enemyMeleeAttackList;
	}
}
