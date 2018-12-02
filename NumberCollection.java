package com.robertwraysmith.sudoku.domain;

import java.util.LinkedList;

/* There is one NumberCollection for each row, column, and 3x3 box on the Sudoku board.
 * This class has methods for checking whether a given position has a number or not, as well as
 * finding the index for that number.
 * 
 * A Number Collection only stores non-zero numbers.
 */
public class NumberCollection {
	
	LinkedList<Integer> numbers = new LinkedList<Integer>();
	boolean noRepeats = true;
	
	public boolean hasNumber( int n ) {
		return numbers.contains( n );
	}
	
	public void add( int n ) {
		
		if( n == 0 )
			return;
		
		if( hasNumber( n ) )
			noRepeats = false;
		numbers.add( n );
	}
}
