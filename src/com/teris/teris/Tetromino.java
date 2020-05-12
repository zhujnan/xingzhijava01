package com.teris.teris;

import java.util.Random;

/**
 * ����˹������
 * 
 * 		�߱�I��S��J��O�͵ȷ���Ĺ�ͬ���Ժ���Ϊ
 * 
 * 		��ͬ�����ԣ�
 * 					1��ÿ������˹���鶼��4���������
 * 					      ��һ������Cell���ͳ���Ϊ4��
 * 					      ��������ʾ
 * 					2��ÿ������˹��������Ϸ�����
 * 					      ���в�ͬ��״̬��������״̬��Ķ���
 * 					      �ǲ��������ⱻ��������ģ�����������Ҫ��״
 * 						̬��д����������ڲ�
 * 		��ͬ����Ϊ��
 * 					1���������һ������˹����
 * 					2���������ƶ�
 * 					3���������ת���ѵ㣩	
 * @author admin
 *
 */
public class Tetromino {
	protected Cell[] cells = new Cell[4];
	/**
	 * ÿ�ֶ���˹���鶼��״̬
	 */
	protected State[] states ; //״̬������Ķ���˹����
	
	protected int index = 10000;//����һ������
	/**
	 * ÿ������˹���飬����һ����ͬ�������Ǿ���״̬���еķ���������״̬
	 *�еķ����Ǿ�ֹ״̬��
	 *��������Ҫ������״̬��Ķ����ǲ��������ⱻ����
	 *����ģ�����������Ҫ��״̬��д����������ڲ����ڲ��ࣩ��
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
	 * �������7�ַ���֮һ���ǹ�������
	 * 	��������������������������һ������ķ�������װ�˸��ӵĴ�������ʹ�÷��㡣
	 *  ��;���������һ������Ķ���˹���飬L T O I S 
	 */
	public static Tetromino randomOne() {
		Random random = new Random(); //����һ�������
		int type = random.nextInt(7); //ÿִ��һ�θ��д���
									 //����һ�� 0-7֮�����,������7
		switch(type) {
		case 0: return new T(); //����T�ͷ��������
		case 1: return new I(); //����I�ͷ��������
		case 2: return new S();
		case 3: return new Z();
		case 4: return new J();
		case 5: return new L();
		case 6: return new O();
		}
		return null;
	}
	/**
	 * ��ӷ��������ƶ�����
	 */
	public void moveLeft() {
		cells[0].moveLeft();
		cells[1].moveLeft();
		cells[2].moveLeft();
		cells[3].moveLeft();
	}
	/**
	 * ��ӷ��������ƶ�����
	 */
	public void moveRight() {
		for(int i=0;i<cells.length;i++) {
			this.cells[i].moveRight();
		}
	}
	//��ӷ������䷽��
	public void softDrop() {
		for(int i=0;i<cells.length;i++) {
			cells[i].softDrop();
		}
	}
	/**
	 * ������ת�㷨
	 * 	�����÷��������µ�λ����ת
	 */
	public void rotateRight() {
		/**
		 * ��ȡSn(4������0��1��2��3)
		 * ��ȡ��ǰ��
		 * ����0���᲻��
		 * ����1�����б�Ϊ����+sn[1]
		 * ����2�����б�Ϊ����+sn[2]
		 * ����3�����б�Ϊ����+sn[3]
		 */
		index++;
		State s = states[index % states.length];
		Cell o = cells[0];
		int row = o.getRow();
		int col = o.getCol();
		
		cells[1].setRow(row + s.row1);
		cells[1].setCol(col + s.col1);
		/**
		 * T1 = T0�� + S
		 */
		cells[2].setRow(row + s.row2);
		cells[2].setCol(col + s.col2);
		/**
		 * T2 = T1�� + S
		 */
		cells[3].setRow(row + s.row3);
		cells[3].setCol(col + s.col3);
		/**
		 * T3 = T2.�� + S
		 */
	}
}
