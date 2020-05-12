package com.teris.teris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * ��Ϸ�����
 * @author zhujn
 *
 */
public class Teris extends JPanel{
	/**
	 * ��Ϸ�ĵ�ǰ״̬��running ����״̬
	 * 				  pause ��ͣ״̬
	 * 				  game_Over ����״̬
	 * 
	 */
	 private int state;
	 public static final int RUNNIG = 0;
	 public static final int PAUSE = 1;
	 public static final int GAME_OVER = 2;
	 //�ٶ�
	 private int speed;
	 private int level;
	 private int index;
	 //����ǽ
	 public static final int ROWS = 20;
	 public static final int COLS = 10;
	 private Cell[][] wall = new Cell[ROWS][COLS];
	
	 private Tetromino tetromino; //��������ķ���
	 private Tetromino nextOne;	  //��һ�������ķ���
	 private int lines; //��������
	 private int score; //�÷�
	 private Timer timer; //��ʱ��
	/**
	 * ��Teris������ӣ�����ͼƬ������
	 */
	public static BufferedImage backgroud;
	public static BufferedImage gameOver;
	public static BufferedImage pause;
	public static BufferedImage T;
	public static BufferedImage I;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage J;
	public static BufferedImage L;
	public static BufferedImage O;

	/**
	 * ��̬�����������ص�ʱ��ִ��
	 */
	static {
		try {
			backgroud = ImageIO.read(Teris.class.getResource("TETRIS.png"));
			gameOver = ImageIO.read(Teris.class.getResource("game-over.png"));
			pause = ImageIO.read(Teris.class.getResource("pause.png"));
			T = ImageIO.read(Teris.class.getResource("T.png"));
			S = ImageIO.read(Teris.class.getResource("S.png"));
			Z = ImageIO.read(Teris.class.getResource("Z.png"));
			J = ImageIO.read(Teris.class.getResource("J.png"));
			L = ImageIO.read(Teris.class.getResource("L.png"));
			I = ImageIO.read(Teris.class.getResource("I.png"));
			O = ImageIO.read(Teris.class.getResource("O.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	  //����.class ��ȡClass��Ķ��� ��
	  //ͨ��Class��������������е�getResource����
	}
	public void paint(Graphics g) {//��������
		g.drawImage(backgroud,0,0,null); //���Ʊ���ͼƬ
		g.translate(15, 15); // ��ͼ�������ĵ�ԭ��ƽ�Ƶ���ǰ����ϵ�еĵ㣨x,y��;
		paintWall(g);//������ǽ
		paintTetromino(g);//������
		paintNextOne(g);//����һ����������
		paintScore(g);//���÷� 
		paintState(g);//������Ϸ��״̬
	}
	private void paintState(Graphics g) {
		switch(state) {
		case PAUSE:g.drawImage(pause,-15,-15,null);
			break;
		case GAME_OVER:
			g.drawImage(pause,-15,-15,null);
			break;
		}
	}
	private void paintScore(Graphics g) {
		int x = 292;
		int y = 162;
		g.setColor(new Color(0xffffff));
		Font font = new Font(Font.MONOSPACED,Font.BOLD,28);
		g.setFont(font);
		g.drawString("������"+score, x, y);
		y+=56;
		g.drawString("������"+lines,x,y);
		y+=56;
		g.drawString("�ȼ���"+level, x, y);
		x=290;
		y=160;
		g.setColor(new Color(0x667799));
		g.drawString("������"+score, x, y);
		y+=56;
		g.drawString("������"+lines,x,y);
		y+=56;
		g.drawString("�ȼ���"+level, x, y);
	}
	private void paintNextOne(Graphics g) {
		//������һ�����ֵĶ���˹����
		if(nextOne ==null) {
			return;
		}
		Cell[] cells = nextOne.cells;
		for(int i = 0;i<cells.length;i++) {
			Cell cell = cells[i];
			int x = (cell.getCol() +10)*CELL_SIZE;
			int y = (cell.getRow() + 1)*CELL_SIZE;
			g.drawImage(cell.getImage(),x,y,null);
		}
	}
	private void paintTetromino(Graphics g) {
		if(tetromino == null) {
			return;
		}
		Cell[] cells = tetromino.cells;
		for(int i = 0;i<cells.length;i++) {
			Cell cell = cells[i];
			int x = cell.getCol()*CELL_SIZE;
			int y = cell.getRow()*CELL_SIZE;
			g.drawImage(cell.getImage(),x,y,null);
		}
	}
	public static final int CELL_SIZE = 26;
	private void paintWall(Graphics g) {
		/**
		 * ��������ǽ
		 */
		for(int row = 0;row<wall.length;row++) {
			for(int col=0;col<wall[row].length;col++) {
				int x = col * CELL_SIZE;
				int y = row * CELL_SIZE;
				Cell cell = wall[row][col];
				if(cell == null) {
					g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
					//����ָ�����α߿�
					g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
				}else {
					g.drawImage(cell.getImage(),x,y,null);
			//����ָ��ͼ���е�ǰ���õ�ͼ��
				}
			}
		}
	}
	public static void main(String[] args) {
		JFrame fram = new JFrame("Tetris");
		Teris tetris = new Teris();
		fram.add(tetris);
		fram.setSize(525,550);
		fram.setAlwaysOnTop(true);//��������
		fram.setUndecorated(true);//ȥ���߿�
		fram.setLocationRelativeTo(null);//
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�˳�
		fram.setVisible(true);//����paint()����
		tetris.action();//��main�����е���action
	}
	private void action() {
		tetromino = Tetromino.randomOne();
		nextOne = Tetromino.randomOne();
		state = RUNNIG;
		this.repaint();//�ػ���壬�������paint����ǽ
		this.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							int key = e.getKeyCode();
							switch (state) {
							case GAME_OVER:
								processGameOverKey(key);//��Ϸ������Ҫִ�еĺ���
								break;
							case PAUSE:
								processPauseKey(key); //��Ϸ��ͣ��Ҫִ�еĺ���
								break;
							case RUNNIG:
								processRunningKey(key);//��Ϸ�ָ�����ִ�еĺ���
								break;
							default:
								break;
							}
							repaint();
						}
					});
		this.setFocusable(true);
		this.requestFocus();
		timer = new Timer(); //��ʱ������
		timer.schedule(new TimerTask() {	
			//10����֮��ִ�У�ÿ���10����ִ��һ��
			@Override
			public void run() {
				speed = 40 -(lines/100);
				speed = speed<1?1:speed;
				level = 41 - speed;
				if(state == RUNNIG && index % speed == 0) {
					softDropAction();//������
				}
				index++;
				repaint(); //�ػ�ҳ�棬ÿ���10��ˢ��һ��
			}
		}, 10, 10); 
		//	private void processRunningKey(int key) {}
		//	private void processPauseKey(int key) {}
			//��Ӽ��̼����¼�	���������ڲ������
		//	private void processGameOverKey(int key) {}
			
	}
	protected void processRunningKey(int key) {
		/**
		 * ��Ϸ������״̬�£�������ذ������������¼�
		 */
		switch (key) {
		case KeyEvent.VK_Q: //��Q�� ����Ϸ�˳�
				System.out.println(0);
		case KeyEvent.VK_RIGHT: //��->�� �����ƶ���
			Teris.this.moveRightAction(); //ִ�����ҵķ���
			break;
		case KeyEvent.VK_LEFT: //��<-�� �����ƶ���
			Teris.this.moveLeftAction(); //ִ�������ƶ�����
			break;
		case KeyEvent.VK_DOWN: //ִ�����¼�
			Teris.this.softDropAction(); //������
			break;
		case KeyEvent.VK_SPACE: // ���ո����Ӳ���䣬һ�䵽��
			Teris.this.hardDropAction(); //������
			break;
		case KeyEvent.VK_P: //��P�� ����ͣ
			state = PAUSE;
			break;
		case KeyEvent.VK_UP: //��P�� ����ͣ
			rotateRightAction(); //ִ�з������ת����
			break;
		}
	
	}
	private void rotateRightAction() {
		tetromino.rotateRight();
		if(outOfBounds()||coincide()) {
			//tetromino.rotateLeft();
		}
	}
	protected void processPauseKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_C:
			index = 0;
			state = RUNNIG;
			break;
		}
	
	}
	protected void processGameOverKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
				System.exit(0);
			break;
		case KeyEvent.VK_S:
			//��Ϸ���¿�ʼ
			lines = 0;
			score = 0;
			wall = new Cell[ROWS][COLS];
			tetromino = Tetromino.randomOne();
			nextOne = Tetromino.randomOne();
			state = RUNNIG;
			index = 0;
		break;
		}
	
	}
	protected void hardDropAction() {
		
	}
	//�÷ֱ� 
	private int[] scoreTable = {0,1,10,100,500};
	protected void softDropAction() {
		/**
		 * �����䣺
		 * 1.����ܹ����������
		 * 2.��������������½��ǽ����
		 * 3.�����Ѿ�������
		 * 4.���û�н������Ͳ�����һ������
		 */
		if(canDrop()) {
			tetromino.softDrop();
		}else {
			landIntoWall();
			int lines = destroyLines(); //���ٵ�����
			this.lines += lines; // lines = 0,1,2,3,4
			this.score += scoreTable[lines]; //{0,1,10,100,500}
			if(isGameOver()) {
				state = GAME_OVER;
			}else {
				tetromino = nextOne;
				nextOne = Tetromino.randomOne();
			}
		}
	}
	//�ж���Ϸ�Ƿ����
	private boolean isGameOver() {
		/**
		 * �����һ������û�г���λ���ˣ�����Ϸ����
		 * ��һ����������ÿ���������ж�Ӧ��ǽ��λ������и��ӣ���Ϸ����
		 */
		Cell[] cells = nextOne.cells;
		for (Cell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			if(wall[row][col] !=null) {
				return true;
			}
		}
		return false;
	}
	//�����������У�������������
	private int destroyLines() {
		//0-19�����в��ң�����ҵ����о�ɾ������
		int lines = 0;
		for(int row=0;row< ROWS;row++) {
			if(isFullCells(row)) {
				deleteRow(row);
				lines++;
			}
		}
		return lines;
	}
	//ɾ��һ�У�row���к�
	private void deleteRow(int row) {
		for(int i=row;i>=1;i--) {
			//���ƣ� wall[i-1]  ->   wall[i]
			System.arraycopy(wall[i-1],0,wall[i],0,COLS);
		}
		Arrays.fill(wall[0], null); //fill���
	}
	//���row�����Ƿ��Ǹ���
	private boolean isFullCells(int row) {
		Cell[] line = wall[row];
		for (Cell cell : line) {
			if(cell==null) {
				return false;
			}
		}
		return true;
	}
	//��½��ǽ�﷽��
	private void landIntoWall() {
		/**
		 * ����ÿ�����ӵ�λ�ã����뵽ǽ�϶�Ӧ��λ��
		 */
		
		Cell[] cells = tetromino.cells;
		for (Cell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			wall[row][col] = cell;
		}
	}
	/*
	 * ��⵱ǰ�����Ƿ�������
	 */
	private boolean canDrop() {
		//1.�����ĳ�������е���19�Ͳ���������
		//2.�����ĳ�����Ӷ�Ӧǽ�ϵ��·����ָ��ӾͲ���������
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			if(row == ROWS -1) {
				return false;//����������
			}
		}
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if(wall[row+1][col] != null) {
				return false;
			}
		}
		return true;
	}
	protected void moveLeftAction() {
		tetromino.moveLeft();
		if(outOfBounds()||coincide()) {
			tetromino.moveRight();
		}
	}
	protected void moveRightAction() {
		tetromino.moveRight();  //��������ķ�������
		/**
		 * outOfBoutd() �������ж�����÷����Ƿ񳬳��߽硣
		 * coincide()�������ж�����ķ����Ƿ������������غϡ�
		 */
		if(outOfBounds()||coincide()) {
			//�����������ķ��飬�����߽�����ķ��������ƶ�
			tetromino.moveLeft();
		}
	}
	//�����������ķ����Ƿ�Ծ���߽�
	private boolean outOfBounds() {
		Cell[] cells = tetromino.cells;
		for (int i =0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if(row<0||row>=ROWS||col<0||col>=COLS) {
				return true;
			}
		}
		return false;
	}
	//����Ƿ��غ�
	private boolean coincide() {
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if(wall[row][col] !=null) {
				return true;
			}
		}
		return false;
	}
}
