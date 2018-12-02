package com.robertwraysmith.sudoku.domain;

public class Move {
	
	int[] move = new int[ 3 ];
	
	public Move( int row, int col, int val ) {
		move[ 0 ] = row;
		move[ 1 ] = col;
		move[ 2 ] = val;
	}
}
