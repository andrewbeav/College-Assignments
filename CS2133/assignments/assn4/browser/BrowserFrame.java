import javax.swing.*;

public class BrowserFrame extends JFrame {
	public BrowserFrame() {
		setTitle("Web Browser");
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new BrowserPanel(this));
	}
}
