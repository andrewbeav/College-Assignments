import java.io.*;

public class Square implements Serializable {
	// Type constants
	public static final char BOMB = '!';
	public static final char NOT_BOMB = '.';

	// Status constants
	public static final char NOT_CLICKED = '?';
	public static final char FLAGGED = 'F';
	public static final char CLICKED = 'C';
	public static final char CLICKED_ZERO = '0';

	// Object instance variables
	private char squareType; // Type of square (bomb/not bomb)
	private char squareStatus; // flagged/clicked/not clicked
	private int adjacentBombs; // # of adjacent bombs

	private GameBoard parentBoard;

	public Square(GameBoard parentBoard) {
		this.squareType = NOT_BOMB;
		this.squareStatus = NOT_CLICKED;
		this.parentBoard = parentBoard;
	}

	public void click() {
		this.squareStatus = CLICKED;
	}

	public boolean isClicked() {
		return (this.getStatus() == CLICKED);
	}

	public void flag() {
		this.squareStatus = FLAGGED;
		this.parentBoard.decrementRemainingBombs();
	}

	public void unFlag() {
		if (this.squareStatus == FLAGGED) this.squareStatus = NOT_CLICKED;
		this.parentBoard.incrementRemainingBombs();
	}

	public boolean isFlagged() {
		return (this.squareStatus == FLAGGED);
	}

	public void makeBomb() {
		this.squareType = BOMB;
	}	

	public boolean isBomb() {
		return (this.squareType == BOMB);
	}

	// Accessor/Mutator methods
	public void setType(char type) {
		this.squareType = type;
	}

	public void setAdjacentBombs(int n) {
		this.adjacentBombs = n;
	}

	public void setStatus(char status) {
		this.squareStatus = status;
	}

	public char getType() {
		return squareType;
	}

	public int getAdjacentBombs() {
		return adjacentBombs;
	}

	public char getStatus() {
		return squareStatus;
	}
}
