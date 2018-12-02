package com.robertwraysmith.sudoku.domain;

import java.util.Stack;

public class MoveLog {
	Stack< Move > undoLog = new Stack< Move >();
	Stack< Move > redoLog = new Stack< Move >();
	
	public Move undo() {
		if( undoLog.isEmpty() )
			return null;
		Move undo = undoLog.pop();
		return undo;
	}
	
	public Move redo() {
		if( redoLog.isEmpty() )
			return null;
		Move redo = redoLog.pop();
		return redo;
	}
	
	public void add( Move m ) {
		undoLog.add( m );
		redoLog = new Stack< Move >();
	}
	
	public int size() {
		return undoLog.size();
	}
	
	public Move get( int index ) {
		return undoLog.get(index );
	}
	
	public Move getLast() {
		if( !undoLog.isEmpty() ) {
			return undoLog.peek();
		} else {
			return null;
		}
	}
}
