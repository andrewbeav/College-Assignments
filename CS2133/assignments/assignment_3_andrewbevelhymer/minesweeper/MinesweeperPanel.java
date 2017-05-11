import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MinesweeperPanel extends JPanel {
	private SquareGui[][] squareGrid = new SquareGui[GameBoard.BOARD_SIZE][GameBoard.BOARD_SIZE];
	private int squareListSize = (int)Math.pow(GameBoard.BOARD_SIZE, 2);

	private MinesweeperFrame parentFrame;

	private GameBoard board;

	public MinesweeperPanel(MinesweeperFrame parentFrame, GameBoard board) {
		setLayout(new GridLayout(GameBoard.BOARD_SIZE, GameBoard.BOARD_SIZE));

		this.parentFrame = parentFrame;
		this.board = board;

		makeSquareGrid();
		makeGridGui();
	}

	public void gameOver() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				squareGrid[r][c].reveal();
			}
		}

		JOptionPane.showMessageDialog(this, "You lose!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	public void win() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				squareGrid[r][c].win();
			}
		}

		JOptionPane.showMessageDialog(this, "You Win!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
	private void makeSquareGrid() {
		for (int r = 0; r < squareGrid.length; r++) {
			for (int c = 0; c < squareGrid[r].length; c++) {
				squareGrid[r][c] = new SquareGui(this, board, r, c);
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

	public MinesweeperFrame getParentFrame() {
		return this.parentFrame;
	}

}
