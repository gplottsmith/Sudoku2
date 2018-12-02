package com.robertwraysmith.sudoku.domain;

public class BoardPosition {
	
	int row, col, val;
	NumberCollection rowCollection, columnCollection, boxCollection;
	
	public BoardPosition( int r, int c, int v ) {
		row = r;
		col = c;
		val = v;
	}
	
}
