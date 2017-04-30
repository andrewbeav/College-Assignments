import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SquareGui extends JButton {
	private int row, column;	

	// Board related stuff
	private GameBoard board;
	private int adjacentBombs;
	private MinesweeperPanel panel;

	private Toolkit tk;

	public SquareGui(MinesweeperPanel panel, GameBoard board, int row, int column) {
		this.row = row;
		this.column = column;
		this.board = board;
		this.panel = panel;

		this.makeNotClicked();

		this.addMouseListener(new MouseEventHandler(this));

		tk = Toolkit.getDefaultToolkit();
	}

	public void setAdjacentBombs(int adjacentBombs) {
		this.adjacentBombs = adjacentBombs;
	}

	public void update(int status) {
		if (status == Square.CLICKED) {
			this.makeClicked();
		}	
		else if (status == Square.CLICKED_ZERO) {
			this.panel.updateGrid();
		}
		else if (status == Square.FLAGGED) {
			this.flag();
		}
		else if (status == Square.NOT_CLICKED) {
			this.makeNotClicked();
		}
		else if (status == GameBoard.GAME_OVER) {
			this.panel.gameOver();
		}
		else if (status == GameBoard.GAME_WON) {
			this.panel.win(); // Calling on entire panel so each SquareGui will have win() called
		}
	}

	public void win() {
		this.setText(null);
		this.setBackground(new Color(35, 150, 22));
		Image smiley = tk.getImage("smiley_face.png");
		this.setIcon(new ImageIcon(smiley));
	}

	public void reveal() {
		this.adjacentBombs = board.getSquare(row, column).getAdjacentBombs();
		if (board.getSquare(row, column).isBomb()) this.makeBomb();
		else if (board.getSquare(row, column).isFlagged()) this.flag();
		else this.makeClicked();
	}

	public void makeBomb() {
		this.setText(null);

		// Image from: http://simpleicon.com/wp-content/uploads/bomb.png
		Image bomb = tk.getImage("bomb.png");
		this.setIcon(new ImageIcon(bomb));
		this.setBackground(new Color(188, 1, 1));
	}

	public void makeClicked() {
		this.adjacentBombs = board.getSquare(row, column).getAdjacentBombs();
		this.setIcon(null);
		this.setText(this.adjacentBombs + "");
		this.setBackground(new Color(100, 110, 127));
	}

	public boolean isClickedOnBoard() {
		return (this.board.getSquare(row, column).isClicked());
	}

	public void makeNotClicked() {
		this.setIcon(null);
		this.setBackground(new Color(83, 85, 89));
	}

	public void flag() {
		// Image from:
		//https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Flag_icon_darkblue.svg/250px-Flag_icon_darkblue.svg.png
		Image flag = tk.getImage("flag.png");
		this.setIcon(new ImageIcon(flag));
		this.setBackground(new Color(35, 150, 22));
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public GameBoard getBoard() {
		return this.board;
	}

	public MinesweeperPanel getPanel() {
		return this.panel;
	}
}
