package com.teris.teris;

import java.util.Random;

/**
 * 俄罗斯方块类
 * 
 * 		具备I型S型J型O型等方块的共同属性和行为
 * 
 * 		共同的属性：
 * 					1、每个俄罗斯方块都由4个方格组成
 * 					      用一个数组Cell类型长度为4的
 * 					      数组来表示
 * 					2、每个俄罗斯方块在游戏面板中
 * 					      都有不同的状态，构建的状态类的对象
 * 					      是不可以随意被创建对象的，所以我们需要把状
 * 						态类写到方块类的内部
 * 		共同的行为：
 * 					1、随机生成一个俄罗斯方块
 * 					2、左右下移动
 * 					3、方块的旋转（难点）	
 * @author admin
 *
 */
public class Tetromino {
	protected Cell[] cells = new Cell[4];
	/**
	 * 每种俄罗斯方块都有状态
	 */
	protected State[] states ; //状态，下落的俄罗斯方块
	
	protected int index = 10000;//声明一个变量
	/**
	 * 每个俄罗斯方块，都有一个共同的属性那就是状态，有的方块是下落状态
	 *有的方块是静止状态。
	 *而我们需要构建的状态类的对象是不可以随意被创建
	 *对象的，所以我们需要把状态类写到方块类的内部（内部类）。
	 */
	protected class State{
		int row0,col0,row1,col1,
			row2,col2,row3,col3;
		public State(int row0, int col0, 
				int row1, int col1,
				int row2, int col2, 
				int row3, int col3) {
			super();
			this.row0 = row0;
			this.col0 = col0;
			this.row1 = row1;
			this.col1 = col1;
			this.row2 = row2;
			this.col2 = col2;
			this.row3 = row3;
			this.col3 = col3;
		}
	}
	/**
	 * 随机生成7种方块之一，是公共方法
	 * 	工厂方法：用于生产（创建）一个对象的方法，封装了复杂的创建过程使用方便。
	 *  用途：随机生成一个下落的俄罗斯方块，L T O I S 
	 */
	public static Tetromino randomOne() {
		Random random = new Random(); //产生一个随机数
		int type = random.nextInt(7); //每执行一次该行代码
									 //产生一个 0-7之间的数,不包括7
		switch(type) {
		case 0: return new T(); //创建T型方块类对象
		case 1: return new I(); //创建I型方块类对象
		case 2: return new S();
		case 3: return new Z();
		case 4: return new J();
		case 5: return new L();
		case 6: return new O();
		}
		return null;
	}
	/**
	 * 添加方块向左移动方法
	 */
	public void moveLeft() {
		cells[0].moveLeft();
		cells[1].moveLeft();
		cells[2].moveLeft();
		cells[3].moveLeft();
	}
	/**
	 * 添加方块向右移动方法
	 */
	public void moveRight() {
		for(int i=0;i<cells.length;i++) {
			this.cells[i].moveRight();
		}
	}
	//添加方块下落方法
	public void softDrop() {
		for(int i=0;i<cells.length;i++) {
			cells[i].softDrop();
		}
	}
	/**
	 * 方块旋转算法
	 * 	触发该方法，向新的位置旋转
	 */
	public void rotateRight() {
		/**
		 * 获取Sn(4组数据0，1，2，3)
		 * 获取当前轴
		 * 格子0是轴不变
		 * 格子1的行列变为：轴+sn[1]
		 * 格子2的行列变为：轴+sn[2]
		 * 格子3的行列变为：轴+sn[3]
		 */
		index++;
		State s = states[index % states.length];
		Cell o = cells[0];
		int row = o.getRow();
		int col = o.getCol();
		
		cells[1].setRow(row + s.row1);
		cells[1].setCol(col + s.col1);
		/**
		 * T1 = T0轴 + S
		 */
		cells[2].setRow(row + s.row2);
		cells[2].setCol(col + s.col2);
		/**
		 * T2 = T1轴 + S
		 */
		cells[3].setRow(row + s.row3);
		cells[3].setCol(col + s.col3);
		/**
		 * T3 = T2.轴 + S
		 */
	}
}
