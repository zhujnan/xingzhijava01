package com.teris.teris;

import java.awt.image.BufferedImage;

public class Cell {
	private int row;
	private int col; 
	private BufferedImage image;//每个方块都有一 个贴图
	public Cell(int row, int col, BufferedImage image) {
		super();
		this.row = row;
		this.col = col;
		this.image = image;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public void moveRight() {
		col++;
	}
	public void moveLeft() {
		col--;
	}
	public void softDrop() {
		row++;
	}
}
