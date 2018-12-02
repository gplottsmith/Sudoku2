package com.robertwraysmith.sudoku.domain;


public class GamePlayer {
	
	Grid gameGrid;
	Grid startGrid;
	Grid endGrid;
	MoveLog log = new MoveLog();
	
	public GamePlayer( Grid start ) {
		
		gameGrid = new Grid( start );
		this.startGrid = start;
	}
	
	public void set( Move m ) {
		int row = m.move[ 0 ];
		int col = m.move[ 1 ];
		int val = m.move[ 2 ];
		if( startGrid.grid[ row ][ col ].val == 0 ) {
			//Add old value to the moveLog
			int oldVal = gameGrid.grid[ row ][ col ].val;
			Move u = new Move( row, col, oldVal );
			log.add( u );
			
			gameGrid.set( row, col, val );
		} else {
			//print error message
		}
	}
	
	//The Move passed to this function shouldn't throw an arrayIndexOutOfBounds,
	//i.e. it's a valid Move
	public boolean isLegalMove( Move m ) {
		
		int r = m.move[ 0 ], c = m.move[ 1 ], val = m.move[ 2 ];
		BoardPosition b = gameGrid.grid[ r ][ c ];
		NumberCollection row = b.rowCollection, column = b.columnCollection, 
				box = b.boxCollection;
		if( row.hasNumber( val ) || column.hasNumber( val ) || box.hasNumber( val ) ) 
			return false;
		else
			return true;
		
	}
	
	public Move undo() {
		Move u = log.undo();
		if( u != null ) {
			int row = u.move[ 0 ];
			int col = u.move[ 1 ];
			int val = u.move[ 2 ];
			int oldVal = gameGrid.grid[ row ][ col ].val;
			
			Move r = new Move( row, col, oldVal );
			log.redoLog.add( r );
			
			gameGrid.set( row,  col,  val );
		} else {
			//print error message
			System.out.println( "No undo Available" );
		}
		return u;
	}
	
	public Move redo() {
		Move r = log.redo();
		if( r != null ) {
			int row = r.move[ 0 ];
			int col = r.move[ 1 ];
			int val = r.move[ 2 ];
			int oldVal = gameGrid.grid[ row ][ col ].val;
			
			Move u = new Move( row, col, oldVal );
			log.undoLog.add( u );
			
			gameGrid.set( row,  col,  val );
			
		} else {
			//print error message
			System.out.println( "No redo Available" );
		}
		return r;
	}
}
