package com.robertwraysmith.sudoku.domain;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Grid {
	
	//Each BoardPosition has 3 NumberCollections associated with it
	BoardPosition[][] grid = new BoardPosition[ 9 ][ 9 ];
	
	//This constructor makes a Grid from a text file with a sudoku board prewritten
	public Grid( String filename ) {
		try {
			Scanner diskScanner = new Scanner( new File( filename ) );
			for ( int rowNum = 0; rowNum < 9; rowNum++ ) {
				for ( int colNum = 0; colNum < 9; colNum++ ) { 
					int val = diskScanner.nextInt();
					///Initialize Board
					grid[ rowNum ][ colNum ] = new BoardPosition( rowNum, colNum, val );
				}
			}
			assignNumberCollections();
			diskScanner.close();
		} catch (Exception e) {
			System.out.println("Exception from Scanner with file " + filename);
		}
		
	}
	
	public Grid() {
		
	}
	//This constructor makes a copy of another grid
	public Grid( Grid g ) {
		
		for( int i = 0; i < 9; i++ ) {
			for( int j = 0; j < 9; j++ ) {
				
				grid[ i ][ j ] = new BoardPosition( i, j, g.grid[ i ][ j ].val );
				
				BoardPosition b = g.grid[ i ][ j ];
				LinkedList< Integer > row = b.rowCollection.numbers;
				LinkedList< Integer > column = b.columnCollection.numbers;
				LinkedList< Integer > box = b.boxCollection.numbers;
				
				grid[ i ][ j ].rowCollection = new NumberCollection();
				grid[ i ][ j ].columnCollection = new NumberCollection();
				grid[ i ][ j ].boxCollection = new NumberCollection();
				
				//Copy the numberCollections of g into grid.
				for( int k = 0; k < row.size(); k++ ) 
					grid[ i ][ j ].rowCollection.add( row.get( k ) );
				for( int k = 0; k < column.size(); k++ ) 
					grid[ i ][ j ].columnCollection.add( column.get( k ) );
				for( int k = 0; k < box.size(); k++ ) 
					grid[ i ][ j ].boxCollection.add( box.get( k ) );
			}
		}
	}
	
	public void set( int row, int col, int val ) throws ArrayIndexOutOfBoundsException {
		try {
			grid[ row ][ col ].val = val;
		} catch ( Exception e ) {
			//throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	public int get( int row, int col ) {
		return grid[ row ][ col ].val;
	}
	
	public void assignNumberCollections() {
		//Rows
		NumberCollection numCol;
		for( int i = 0; i < 9 ; i++ ) {
			numCol = new NumberCollection();
			for( int j = 0; j < 9; j++ ) {
				BoardPosition b = grid[ i ][ j ];
				b.rowCollection = numCol;
				numCol.add( grid[ i ][ j ].val );
			}
		}
		//Columns
		for( int j = 0; j < 9 ; j++ ) {
			numCol = new NumberCollection();
			for( int i = 0; i < 9; i++ ) {
				BoardPosition b = grid[ i ][ j ];
				b.columnCollection = numCol;
				numCol.add( grid[ i ][ j ].val );
			}
		}
		//Boxes
		for( int i = 0; i < 9 ; i += 3 ) {
			for( int j = 0; j < 9; j += 3 ) {
				numCol = new NumberCollection();
				for( int k = i; k < i + 3; k++ ) {
					for( int l = j; l < j + 3; l++ ) {
						BoardPosition b = grid[ k ][ l ];
						b.boxCollection = numCol;
						numCol.add( grid[ k ][ l ].val );
					}
				}
			}
		}
	}
	
}
