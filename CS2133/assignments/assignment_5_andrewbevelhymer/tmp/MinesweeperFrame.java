import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MinesweeperFrame extends JFrame implements Serializable {
	private MinesweeperPanel panel;
	private GameBoard board;

	private JLabel counterLabel;

	private JMenuItem newItem;
	private JMenuItem saveItem;
	private JMenuItem loadItem;
	private JMenuItem quitItem;

	public MinesweeperFrame() {
		this(15);
	}

	public MinesweeperFrame(int numOfBombs) {
		setTitle("Minesweeper");
		setSize(450, 450);

		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		JMenu fileMenu = new JMenu("File");
		mb.add(fileMenu);
		newItem = new JMenuItem("New");
		saveItem = new JMenuItem("Save");
		loadItem = new JMenuItem("Load");
		quitItem = new JMenuItem("Quit");
		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(loadItem);
		fileMenu.add(quitItem);
		MenuHandler menuHandler = new MenuHandler();
		newItem.addActionListener(menuHandler);
		saveItem.addActionListener(menuHandler);
		loadItem.addActionListener(menuHandler);
		quitItem.addActionListener(menuHandler);

		board = new GameBoard(numOfBombs);

		setLayout(new BorderLayout());

		panel = new MinesweeperPanel(this);
		add(panel, BorderLayout.CENTER);

		counterLabel = new JLabel("Bombs Still Not Flagged: " + numOfBombs);
		add(counterLabel, BorderLayout.PAGE_START);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public GameBoard getBoard() {
		return this.board;
	}

	public void updateRemainingBombs(int bombsLeft) {
		this.counterLabel.setText("Bombs Still Not Flagged: " + bombsLeft);
	}

	public void resetGame() {
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

		this.setVisible(false);
		new MinesweeperFrame(numOfBombs);
	}

	public void saveGame() {
		String filePath = "saved_minesweeper";
		JFileChooser fileChooser = new JFileChooser("/");
		if (fileChooser.showOpenDialog(saveItem) == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getPath();
		}
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadGame() {
		String filePath = "saved_minesweeper";
		JFileChooser fileChooser = new JFileChooser("/");
		if (fileChooser.showOpenDialog(loadItem) == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile().getPath();
		}
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
			MinesweeperFrame frame = (MinesweeperFrame)in.readObject();

			this.setVisible(false);
			frame.setVisible(true);
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(this, "Not a minesweeper file!", "Minesweeper", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (ClassNotFoundException e) {
			System.out.println("Not valid");
		}
	}

	private class MenuHandler implements ActionListener, Serializable {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == newItem) {
				resetGame();
			}
			else if (e.getSource() == saveItem) {
				saveGame();
			}
			else if (e.getSource() == loadItem) {
				loadGame();
			}
			else if (e.getSource() == quitItem) {
				System.exit(0);
			}
		}
	}
}
