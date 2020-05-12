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
 * 游戏面板类
 * @author zhujn
 *
 */
public class Teris extends JPanel{
	/**
	 * 游戏的当前状态：running 运行状态
	 * 				  pause 暂停状态
	 * 				  game_Over 结束状态
	 * 
	 */
	 private int state;
	 public static final int RUNNIG = 0;
	 public static final int PAUSE = 1;
	 public static final int GAME_OVER = 2;
	 //速度
	 private int speed;
	 private int level;
	 private int index;
	 //绘制墙
	 public static final int ROWS = 20;
	 public static final int COLS = 10;
	 private Cell[][] wall = new Cell[ROWS][COLS];
	
	 private Tetromino tetromino; //正在下落的方块
	 private Tetromino nextOne;	  //下一个进场的方块
	 private int lines; //销毁行数
	 private int score; //得分
	 private Timer timer; //定时器
	/**
	 * 在Teris类中添加，背景图片的引用
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
	 * 静态代码块在类加载的时候执行
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
	  //类名.class 获取Class类的对象 ，
	  //通过Class类对象来调用其中的getResource方法
	}
	public void paint(Graphics g) {//绘制容器
		g.drawImage(backgroud,0,0,null); //绘制背景图片
		g.translate(15, 15); // 将图形上下文的原点平移到当前坐标系中的点（x,y）;
		paintWall(g);//画格子墙
		paintTetromino(g);//画方块
		paintNextOne(g);//画下一个出场方块
		paintScore(g);//画得分 
		paintState(g);//绘制游戏的状态
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
		g.drawString("分数："+score, x, y);
		y+=56;
		g.drawString("行数："+lines,x,y);
		y+=56;
		g.drawString("等级："+level, x, y);
		x=290;
		y=160;
		g.setColor(new Color(0x667799));
		g.drawString("分数："+score, x, y);
		y+=56;
		g.drawString("行数："+lines,x,y);
		y+=56;
		g.drawString("等级："+level, x, y);
	}
	private void paintNextOne(Graphics g) {
		//绘制下一个出现的俄罗斯方块
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
		 * 绘制网格墙
		 */
		for(int row = 0;row<wall.length;row++) {
			for(int col=0;col<wall[row].length;col++) {
				int x = col * CELL_SIZE;
				int y = row * CELL_SIZE;
				Cell cell = wall[row][col];
				if(cell == null) {
					g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
					//绘制指定矩形边框
					g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
				}else {
					g.drawImage(cell.getImage(),x,y,null);
			//绘制指定图像中当前可用的图像
				}
			}
		}
	}
	public static void main(String[] args) {
		JFrame fram = new JFrame("Tetris");
		Teris tetris = new Teris();
		fram.add(tetris);
		fram.setSize(525,550);
		fram.setAlwaysOnTop(true);//总在最上
		fram.setUndecorated(true);//去掉边框
		fram.setLocationRelativeTo(null);//
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出
		fram.setVisible(true);//调用paint()方法
		tetris.action();//在main方法中调用action
	}
	private void action() {
		tetromino = Tetromino.randomOne();
		nextOne = Tetromino.randomOne();
		state = RUNNIG;
		this.repaint();//重绘面板，尽快调用paint绘制墙
		this.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							int key = e.getKeyCode();
							switch (state) {
							case GAME_OVER:
								processGameOverKey(key);//游戏结束需要执行的函数
								break;
							case PAUSE:
								processPauseKey(key); //游戏暂停需要执行的函数
								break;
							case RUNNIG:
								processRunningKey(key);//游戏恢复运行执行的函数
								break;
							default:
								break;
							}
							repaint();
						}
					});
		this.setFocusable(true);
		this.requestFocus();
		timer = new Timer(); //定时器对象
		timer.schedule(new TimerTask() {	
			//10毫秒之后执行，每间隔10毫秒执行一次
			@Override
			public void run() {
				speed = 40 -(lines/100);
				speed = speed<1?1:speed;
				level = 41 - speed;
				if(state == RUNNIG && index % speed == 0) {
					softDropAction();//软下落
				}
				index++;
				repaint(); //重绘页面，每间隔10秒刷新一次
			}
		}, 10, 10); 
		//	private void processRunningKey(int key) {}
		//	private void processPauseKey(int key) {}
			//添加键盘监听事件	创建匿名内部类对象
		//	private void processGameOverKey(int key) {}
			
	}
	protected void processRunningKey(int key) {
		/**
		 * 游戏在运行状态下，按下相关按键所触发的事件
		 */
		switch (key) {
		case KeyEvent.VK_Q: //按Q键 ，游戏退出
				System.out.println(0);
		case KeyEvent.VK_RIGHT: //按->键 向右移动键
			Teris.this.moveRightAction(); //执行向右的方法
			break;
		case KeyEvent.VK_LEFT: //按<-键 向左移动键
			Teris.this.moveLeftAction(); //执行向左移动方法
			break;
		case KeyEvent.VK_DOWN: //执行向下键
			Teris.this.softDropAction(); //软下落
			break;
		case KeyEvent.VK_SPACE: // 按空格键，硬下落，一落到底
			Teris.this.hardDropAction(); //软下落
			break;
		case KeyEvent.VK_P: //按P键 ，暂停
			state = PAUSE;
			break;
		case KeyEvent.VK_UP: //按P键 ，暂停
			rotateRightAction(); //执行方块的旋转方法
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
			//游戏重新开始
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
	//得分表 
	private int[] scoreTable = {0,1,10,100,500};
	protected void softDropAction() {
		/**
		 * 软下落：
		 * 1.如果能够下落就下落
		 * 2.如果不能下落就着陆到墙里面
		 * 3.销毁已经满的行
		 * 4.如果没有结束，就产生下一个方块
		 */
		if(canDrop()) {
			tetromino.softDrop();
		}else {
			landIntoWall();
			int lines = destroyLines(); //销毁的行数
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
	//判断游戏是否结束
	private boolean isGameOver() {
		/**
		 * 如果下一个方块没有出场位置了，则游戏结束
		 * 下一个出场方块每个格子行列对应的墙上位置如果有格子，游戏结束
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
	//销毁已满的行，返回销毁行数
	private int destroyLines() {
		//0-19行逐行查找，如果找到满行就删除这行
		int lines = 0;
		for(int row=0;row< ROWS;row++) {
			if(isFullCells(row)) {
				deleteRow(row);
				lines++;
			}
		}
		return lines;
	}
	//删除一行，row是行号
	private void deleteRow(int row) {
		for(int i=row;i>=1;i--) {
			//复制： wall[i-1]  ->   wall[i]
			System.arraycopy(wall[i-1],0,wall[i],0,COLS);
		}
		Arrays.fill(wall[0], null); //fill填充
	}
	//检查row这行是否都是格子
	private boolean isFullCells(int row) {
		Cell[] line = wall[row];
		for (Cell cell : line) {
			if(cell==null) {
				return false;
			}
		}
		return true;
	}
	//着陆到墙里方法
	private void landIntoWall() {
		/**
		 * 根据每个格子的位置，进入到墙上对应的位置
		 */
		
		Cell[] cells = tetromino.cells;
		for (Cell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			wall[row][col] = cell;
		}
	}
	/*
	 * 检测当前方块是否能下落
	 */
	private boolean canDrop() {
		//1.方块的某个格子行到达19就不能下落了
		//2.方块的某个格子对应墙上的下方出现格子就不能下落了
		Cell[] cells = tetromino.cells;
		for(int i=0;i<cells.length;i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			if(row == ROWS -1) {
				return false;//不能下落了
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
		tetromino.moveRight();  //正在下落的方块右移
		/**
		 * outOfBoutd() 方法，判断下落得方块是否超出边界。
		 * coincide()方法，判断下落的方块是否与其它方块重合。
		 */
		if(outOfBounds()||coincide()) {
			//如果正在下落的方块，超出边界下落的方块向左移动
			tetromino.moveLeft();
		}
	}
	//检查正在下落的方块是否跃出边界
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
	//检查是否重合
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
