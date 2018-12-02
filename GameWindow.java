package com.robertwraysmith.sudoku.domain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameWindow extends Application {

	public final int WINDOW_WIDTH = 600;
	public final int WINDOW_HEIGHT = 600;
	
	Grid displayGrid = new Grid();
	GamePlayer game;
	//Initialized in launch()
	GridPane gridPane;
	Label message;
	
	Object[][]  objectContainer = new Object[ 9 ][ 9 ];
	
	public GameWindow() {
		Grid g = new Grid( "C:\\Users\\Gabriel\\Desktop\\Numbers.txt" );
		game = new GamePlayer( g );
		displayGrid = game.gameGrid;
	}
	
	@Override
	public void start( Stage primaryStage ) {
		
		gridPane = new GridPane(); 
		gridPane.setPadding( new Insets( 10, 10, 10, 10));
		gridPane.setVgap( 10 );
		gridPane.setHgap( 20 );
		gridPane.setAlignment( Pos.CENTER );
		
		Canvas canvass = new Canvas();
		
		for( int i = 0; i < 9; i++ ) {
			for( int j = 0; j < 9; j++ ) {
				int val = displayGrid.grid[ j ][ i ].val;
				if( val != 0 ) {
					Label number = new Label( Integer.toString( val ) );
					number.setFont( new Font( "Consolus", 32));
					//number.setTextFill( Color.RED );
					GridPane.setConstraints( number, i, j );
					gridPane.getChildren().add( number );
					objectContainer[ j ][ i ] = number;
				} else {
					TextField tf = new TextField();
					tf.setOnAction( e -> textFieldHandler( tf, gridPane, game ) );
					GridPane.setConstraints( tf, i, j );
					gridPane.getChildren().add( tf );
					objectContainer[ j ][ i ] = tf;
				}
			}
		}
		
		Button b = new Button( "Confirm Guesses" );
		GridPane.setConstraints( b, 0, 9 );
		gridPane.getChildren().add( b );
		
		b = new Button( "Undo" );
		GridPane.setConstraints( b, 2, 9 );
		gridPane.getChildren().add( b );
		b.setOnAction( e -> undo() );
		
		b = new Button( "Redo" );
		GridPane.setConstraints( b, 3, 9 );
		gridPane.getChildren().add( b );
		b.setOnAction( e -> redo() );
		
		message = new Label( "" );
		GridPane.setConstraints( message, 1, 9 );
		gridPane.getChildren().add( message );
		
		primaryStage.setTitle( "Sudoku" );
		Scene scene = new Scene( gridPane , WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
	
	public void undo() {
		Move u = game.undo();
		if( u != null ) {
			int row = u.move[ 0 ];
			int col = u.move[ 1 ];
			int val = u.move[ 2 ];
			Object o = objectContainer[ row ][ col ];
			gridPane.getChildren().remove( o );
			
			if( val == 0 ) {
				TextField tf = new TextField();
				tf.setOnAction( e -> textFieldHandler( tf, gridPane, game ) );
				GridPane.setConstraints( tf, col, row );
				gridPane.getChildren().add( tf );
				objectContainer[ row ][ col ] = tf;
				
			} else {
				Label number = new Label( Integer.toString( val ) );
				number.setFont( new Font( "Consolus", 32));
				GridPane.setConstraints( number, col, row );
				gridPane.getChildren().add( number );
				objectContainer[ row ][ col ] = number;
			}
		} else 
			message.setText( "No undo Available" );
			
		
	}
	
	public void redo() {
		Move r = game.redo();
		if( r != null ) {
			int row = r.move[ 0 ];
			int col = r.move[ 1 ];
			int val = r.move[ 2 ];
			Object o = objectContainer[ row ][ col ];
			gridPane.getChildren().remove( o );
			
			if( val == 0 ) {
				TextField tf = new TextField();
				tf.setOnAction( e -> textFieldHandler( tf, gridPane, game ) );
				GridPane.setConstraints( tf, col, row );
				gridPane.getChildren().add( tf );
				objectContainer[ row ][ col ] = tf;
				message.setText( "No redo Available" );
				
			} else {
				Label number = new Label( Integer.toString( val ) );
				number.setFont( new Font( "Consolus", 32));
				GridPane.setConstraints( number, col, row );
				gridPane.getChildren().add( number );
				objectContainer[ row ][ col ] = number;
			}
		} else 
			message.setText( "No redo Available" );
		
	}
	
	public void textFieldHandler( TextField tf, GridPane grid, GamePlayer g ) {
		
		String s = tf.getText();
		try {
			int val = Integer.parseInt( s );
			if( val <= 9 && val >= 1 ) {
				//First change what the window displays
				int row = GridPane.getRowIndex( tf );
				int col = GridPane.getColumnIndex( tf );
				Label number = new Label( Integer.toString( val ) );
				number.setFont( new Font( "Consolus", 32));
				GridPane.setConstraints( number, col, row);
				grid.getChildren().remove( tf );
				grid.getChildren().add( number );
				objectContainer[ row ][ col ] = number;
				//Then change games's gameGrid
				Move m = new Move( row, col, val );
				g.set( m );
				if( g.isLegalMove( m ) ) {
					
				}
			} else 
				message.setText( "Invalid input" );
			
		} catch( NumberFormatException e ) {
			message.setText( "Invalid input" );
		}
	}
	
	public static void main( String[] args ) {
		launch( args );
	}
}
