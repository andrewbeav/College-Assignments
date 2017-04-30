import java.util.*;
import java.io.*;

public class GameBoard implements Serializable {
	public static final int BOARD_SIZE = 10;
	public static final int GAME_OVER = -1;
	public static final int GAME_WON = 1;

	// Difficulty Constants (May not need)
	public static final int EASY = 5;
	public static final int MEDIUM = 10;
	public static final int HARD = 15;
	public static final int EXTREME = 20;

	private Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];
	private int numOfBombs = MEDIUM; // Number of bombs. May change
	private int remainingBombs;

	private boolean isGamePlaying = true;

	public GameBoard() {
		initBoard();
		placeBombs();
	}

	public GameBoard(int numOfBombs) {
		this.numOfBombs = numOfBombs;
		this.remainingBombs = numOfBombs;
		initBoard();
		placeBombs();
	}

	public void initBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				board[r][c] = new Square(this);
			}
		}
	}

	public int calculateAdjacentBombs(int r, int c) {
		int bombs = 0;
		if (r > 0 && c > 0 && board[r-1][c-1].isBomb()) bombs++;
		if (r > 0 && board[r-1][c].isBomb()) bombs++;
		if (r > 0 && c < BOARD_SIZE-1 && board[r-1][c+1].isBomb()) bombs++;
		if (c > 0 && board[r][c-1].isBomb()) bombs++;
		if (c < BOARD_SIZE-1 && board[r][c+1].isBomb()) bombs++;
		if (r < BOARD_SIZE-1 && c > 0 && board[r+1][c-1].isBomb()) bombs++;
		if (r < BOARD_SIZE-1 && board[r+1][c].isBomb()) bombs++;
		if (r < BOARD_SIZE-1 && c < BOARD_SIZE-1 && board[r+1][c+1].isBomb()) bombs++;
		return bombs;
	}

	public Square getSquare(int r, int c) {
		return board[r][c];
	}

	public void clickOnSquare(int r, int c) {
		if (!board[c][c].isFlagged())board[r][c].click();

		if (board[r][c].isBomb()) gameOver();

		board[r][c].setAdjacentBombs(calculateAdjacentBombs(r, c));
		if (board[r][c].getAdjacentBombs() == 0) clickAdjacentSquares(r, c);
	}
	
	public void clickAdjacentSquares(int r, int c) {
		if (board[r][c].getAdjacentBombs() == 0) { // Just to be sure
			if (r > 0 && c > 0 && !board[r-1][c-1].isClicked()) clickOnSquare(r-1, c-1); 
			if (r > 0 && !board[r-1][c].isClicked()) clickOnSquare(r-1, c);
			if (r > 0 && c < BOARD_SIZE-1 && !board[r-1][c+1].isClicked()) clickOnSquare(r-1, c+1);
			if (c > 0 && !board[r][c-1].isClicked()) clickOnSquare(r, c-1);
			if (c < BOARD_SIZE-1 && !board[r][c+1].isClicked()) clickOnSquare(r, c+1);
			if (r < BOARD_SIZE-1 && c > 0 && !board[r+1][c-1].isClicked()) clickOnSquare(r+1, c-1);
			if (r < BOARD_SIZE-1 && !board[r+1][c].isClicked()) clickOnSquare(r+1, c);
			if (r < BOARD_SIZE-1 && c < BOARD_SIZE-1 && !board[r+1][c+1].isClicked()) clickOnSquare(r+1, c+1);
		}
	}

	public void flagSquare(int r, int c) {
		board[r][c].flag();
	}

	public void unFlagSquare(int r, int c) {
		board[r][c].unFlag();
	}

	public void placeBombs() {
		for (int i = 0; i < numOfBombs; i++) {
			int r = (int)(Math.random()*(BOARD_SIZE));
			int c = (int)(Math.random()*(BOARD_SIZE));

			if (!board[r][c].isBomb()) board[r][c].makeBomb(); 
			else i--; // if what we tried to make a bomb was already a bomb, just try again with the same i value
		}
	}

	public void decrementRemainingBombs() {
		this.remainingBombs --;
	}

	public void incrementRemainingBombs() {
		this.remainingBombs ++;
	}

	public int getRemainingBombs() {
		return this.remainingBombs;
	}

	public boolean isGamePlaying() {
		return this.isGamePlaying;
	}

	public void gameOver() {
		this.isGamePlaying = false;
	}

	// Method that checks to see if all the bombs have been flagged
	public boolean checkForWin() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				if (!(board[r][c].isBomb()) && board[r][c].isFlagged()) return false;
				else if (board[r][c].isBomb() && !board[r][c].isFlagged()) return false;
			}
		}
		return true;
	}

	public void printBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				// Adding "" in the middle so java prints out the char and not the int value of the char
				System.out.print(board[r][c].getStatus() + "" + board[r][c].getType() + " ");
			}
			System.out.println();
		}
		System.out.println("----------------------------------------------------------------------------");
	}

	// Just for testing 
	public static void main(String[] args) {
		GameBoard board = new GameBoard();
		board.printBoard();
	}
}
