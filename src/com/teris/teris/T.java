package com.teris.teris;

/**
 * T�ͷ���
 * @author admin
 *
 */
public class T extends Tetromino {
	/**
	 * 	ִ��˳�򣺾�̬�����>��ͨ�����>�������� 
	 */
	public T() {
		cells[0] = new Cell(0,4,Teris.T);
		cells[1] = new Cell(0,3,Teris.T);
		cells[2] = new Cell(0,5,Teris.T);
		cells[3] = new Cell(1,4,Teris.T);
		states = new State[4];
		states[0] = new State(0,0,0,-1,0,1,1,0); //״̬1��s0ͷ����
		states[1] = new State(0,0,-1,0,1,0,0,-1);//״̬2��s1ͷ����
		states[2] = new State(0,0,0,1,0,-1,-1,0);//״̬3��s2ͷ����
		states[3] = new State(0,0,1,0,-1,0,0,1); //״̬4��s3ͷ����
	}
}
