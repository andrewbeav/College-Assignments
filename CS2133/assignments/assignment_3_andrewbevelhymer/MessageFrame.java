import javax.swing.*;
import java.awt.*;

public class MessageFrame extends JFrame {
	MessagePanel messagePanel;

	public MessageFrame() {
		setSize(400, 700);
		setTitle("Message in a Bottle");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messagePanel = new MessagePanel();
		add(messagePanel);
	}
}
