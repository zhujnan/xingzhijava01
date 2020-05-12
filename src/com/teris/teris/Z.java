package com.teris.teris;

public class Z extends Tetromino{
	public Z() {
		cells[0] = new Cell(1,4,Teris.Z);
		cells[1] = new Cell(0,3,Teris.Z);
		cells[2] = new Cell(0,4,Teris.Z);
		cells[3] = new Cell(1,5,Teris.Z);
		states = new State[2];
		states[0] = new State(0,0,-1,-1,-1,0,0,1);
		states[1] = new State(0,0,-1,1,0,1,1,0);
	}
}
