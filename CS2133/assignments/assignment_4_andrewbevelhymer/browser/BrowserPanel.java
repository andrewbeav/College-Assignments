import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrowserPanel extends JPanel {

	JTextField addressBar;
	JTextArea contentArea;
	JFrame parentFrame;

	public BrowserPanel(JFrame parentFrame) {
		this.parentFrame = parentFrame;

		setLayout(new BorderLayout());

		addressBar = new JTextField();
		addressBar.addKeyListener(new KeyHandler());
		add(addressBar, BorderLayout.NORTH);

		contentArea = new JTextArea();
		contentArea.setEditable(false);
		add(contentArea, BorderLayout.CENTER);

		JScrollPane scroll = new JScrollPane(contentArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll);
	}

	private class KeyHandler extends KeyAdapter {
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				String urlString = addressBar.getText();
				NetworkReader networkReader = new NetworkReader(urlString);
				Webpage webpage = networkReader.getWebpage();
				parentFrame.setTitle(webpage.getTitle());
				contentArea.setText(webpage.getBody());
			}
		}
	}
}
