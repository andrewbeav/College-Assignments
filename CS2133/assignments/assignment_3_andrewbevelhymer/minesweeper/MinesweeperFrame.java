import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperFrame extends JFrame {
	private MinesweeperPanel panel;
	private GameBoard board;

	private JLabel counterLabel;

	public MinesweeperFrame() {
		setTitle("Minesweeper");
		setSize(450, 450);

		String[] options = {"Easy", "Medium", "Hard", "Extreme"};
		int numOfBombs = JOptionPane.showOptionDialog(this, "Select Difficulty", "Minesweeper", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (numOfBombs == -1) System.exit(0);

		switch(numOfBombs) {
			case 0:
				numOfBombs = GameBoard.EASY;
				break;
			case 1:
				numOfBombs = GameBoard.MEDIUM;
				break;
			case 2:
				numOfBombs = GameBoard.HARD;
				break;
			case 3:
				numOfBombs = GameBoard.EXTREME;
		}

		board = new GameBoard(numOfBombs);

		setLayout(new BorderLayout());

		panel = new MinesweeperPanel(this, board);
		add(panel, BorderLayout.CENTER);

		counterLabel = new JLabel("Bombs Still Not Flagged: " + numOfBombs);
		add(counterLabel, BorderLayout.PAGE_START);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void updateRemainingBombs(int bombsLeft) {
		this.counterLabel.setText("Bombs Still Not Flagged: " + bombsLeft);
	}
}
