import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Two vertical lines
		g.drawLine(100, 150, 100, 600);
		g.drawLine(300, 150, 300, 600);

		// Base
		g.drawLine(100, 600, 300, 600);

		// Neck
		g.drawLine(175, 100, 100, 150);
		g.drawLine(225, 100, 300, 150);

		// Two vertical lines above neck
		g.drawLine(175, 50, 175, 100);
		g.drawLine(225, 50, 225, 100);

		// Cap
		g.drawOval(175, 45, 50, 10);

		// Message
		g.drawString("This is a message", 150, 325);
	}
}
