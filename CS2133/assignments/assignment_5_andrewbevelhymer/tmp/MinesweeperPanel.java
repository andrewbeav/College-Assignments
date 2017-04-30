import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class MinesweeperPanel extends JPanel implements Serializable {
	private SquareGui[][] squareGrid = new SquareGui[GameBoard.BOARD_SIZE][GameBoard.BOARD_SIZE];
	private int squareListSize = (int)Math.pow(GameBoard.BOARD_SIZE, 2);

	private MinesweeperFrame frame;

	public MinesweeperPanel(MinesweeperFrame frame) {
		setLayout(new GridLayout(GameBoard.BOARD_SIZE, GameBoard.BOARD_SIZE));

		this.frame = frame;

		makeSquareGrid();
		makeGridGui();
	}

	public void gameOver() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				//squareGrid[r][c].makeBomb();
				squareGrid[r][c].reveal();
			}
		}
	}

	public void win() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				squareGrid[r][c].win();
			}
		}
	}

	public SquareGui[][] getSquareGrid() {
		return this.squareGrid;
	}
	
	private void makeSquareGrid() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				squareGrid[r][c] = new SquareGui(this, r, c);
			}
		}
	}

	public void updateGrid() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				if (squareGrid[r][c].isClickedOnBoard()) squareGrid[r][c].makeClicked();
			}
		}
	}

	private void makeGridGui() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				this.add(squareGrid[r][c]);
			}
		}
	}

	public MinesweeperFrame getFrame() {
		return this.frame;
	}
}
