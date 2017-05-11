import java.awt.event.*;
import java.io.*;

public class MouseEventHandler extends MouseAdapter implements Serializable {
	public static int RIGHT_CLICK_BUTTON = MouseEvent.BUTTON3;
	public static int LEFT_CLICK_BUTTON = MouseEvent.BUTTON1;

	private int squareRow, squareColumn;
	private boolean isGamePlaying = true;
	private int adjacentBombs;
	private SquareGui squareGui;

	public MouseEventHandler(SquareGui squareGui) {
		this.squareGui = squareGui;
		this.squareRow = squareGui.getRow();
		this.squareColumn = squareGui.getColumn();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		int button = event.getButton();

		if (button == LEFT_CLICK_BUTTON && !squareGui.getBoard().getSquare(squareRow, squareColumn).isFlagged()) {
			squareGui.getBoard().clickOnSquare(squareRow, squareColumn);
			if (!squareGui.getBoard().isGamePlaying()) {
				squareGui.update(GameBoard.GAME_OVER);
			}
			else if (squareGui.getBoard().getSquare(squareRow, squareColumn).getAdjacentBombs() == 0) {
				squareGui.update(Square.CLICKED_ZERO);
			}
			else {
				squareGui.setAdjacentBombs(squareGui.getBoard().getSquare(squareRow, squareColumn).getAdjacentBombs());
			}

			squareGui.update(Square.CLICKED);
		}
		else if (button == RIGHT_CLICK_BUTTON) {
			if (squareGui.getBoard().getSquare(squareRow, squareColumn).getStatus() != Square.FLAGGED) {
				squareGui.getBoard().flagSquare(squareRow, squareColumn);
				squareGui.update(Square.FLAGGED);
			}
			else {
				squareGui.getBoard().unFlagSquare(squareRow, squareColumn);
				squareGui.update(Square.NOT_CLICKED);
			}
			squareGui.getPanel().getFrame().updateRemainingBombs(squareGui.getBoard().getRemainingBombs());
		}

		if (squareGui.getBoard().checkForWin()) {
			squareGui.update(GameBoard.GAME_WON);
		}
	}

	public void gameOver() {
		this.isGamePlaying = false;
	}

	public boolean isGamePlaying() {
		return this.isGamePlaying;
	}
}
