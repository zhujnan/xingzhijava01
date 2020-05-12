package com.teris.teris;

public class S extends Tetromino{
	public S() {
		cells[0] = new Cell(1,4,Teris.S);
		cells[1] = new Cell(0,3,Teris.S);
		cells[2] = new Cell(0,4,Teris.S);
		cells[3] = new Cell(1,5,Teris.S);
		states = new State[2];
		states[0] = new State(0,0,0,-1,-1,0,-1,1);
		states[1] = new State(0,0,-1,0,0,1,1,1);
	}
}
