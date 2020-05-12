package com.teris.teris;

/**
 * T型方块
 * @author admin
 *
 */
public class T extends Tetromino {
	/**
	 * 	执行顺序：静态代码块>普通代码块>构造代码块 
	 */
	public T() {
		cells[0] = new Cell(0,4,Teris.T);
		cells[1] = new Cell(0,3,Teris.T);
		cells[2] = new Cell(0,5,Teris.T);
		cells[3] = new Cell(1,4,Teris.T);
		states = new State[4];
		states[0] = new State(0,0,0,-1,0,1,1,0); //状态1：s0头向右
		states[1] = new State(0,0,-1,0,1,0,0,-1);//状态2：s1头向下
		states[2] = new State(0,0,0,1,0,-1,-1,0);//状态3：s2头向左
		states[3] = new State(0,0,1,0,-1,0,0,1); //状态4：s3头向上
	}
}
