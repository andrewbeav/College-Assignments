import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SquareGui extends JButton implements Serializable {
	private int row, column;	

	// Board related stuff
	private int adjacentBombs;
	private MinesweeperPanel panel;

	public SquareGui(MinesweeperPanel panel, int row, int column) {
		this.row = row;
		this.column = column;
		this.panel = panel;

		this.makeNotClicked();

		this.addMouseListener(new MouseEventHandler(this));
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
		Image smiley = Toolkit.getDefaultToolkit().getImage("smiley_face.png");
		this.setIcon(new ImageIcon(smiley));
	}

	public void reveal() {
		this.adjacentBombs = panel.getFrame().getBoard().getSquare(row, column).getAdjacentBombs();
		if (panel.getFrame().getBoard().getSquare(row, column).isBomb()) this.makeBomb();
		else if (panel.getFrame().getBoard().getSquare(row, column).isFlagged()) this.flag();
		else this.makeClicked();
	}

	public void makeBomb() {
		this.setText(null);

		// Image from: http://simpleicon.com/wp-content/uploads/bomb.png
		Image bomb = Toolkit.getDefaultToolkit().getImage("bomb.png");
		this.setIcon(new ImageIcon(bomb));
		this.setBackground(new Color(188, 1, 1));
	}

	public void makeClicked() {
		this.adjacentBombs = panel.getFrame().getBoard().getSquare(row, column).getAdjacentBombs();
		this.setIcon(null);
		this.setText(this.adjacentBombs + "");
		this.setBackground(new Color(100, 110, 127));
	}

	public boolean isClickedOnBoard() {
		return (this.panel.getFrame().getBoard().getSquare(row, column).isClicked());
	}

	public void makeNotClicked() {
		this.setIcon(null);
		this.setBackground(new Color(83, 85, 89));
	}

	public void flag() {
		// Image from:
		//https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Flag_icon_darkblue.svg/250px-Flag_icon_darkblue.svg.png
		Image flag = Toolkit.getDefaultToolkit().getImage("flag.png");
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
		return this.panel.getFrame().getBoard();
	}

	public MinesweeperPanel getPanel() {
		return this.panel;
	}
}
