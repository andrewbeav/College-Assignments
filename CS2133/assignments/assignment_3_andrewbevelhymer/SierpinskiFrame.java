import javax.swing.*;
import java.awt.*;

public class SierpinskiFrame extends JFrame {
	SierpinskiFrame() {
		setTitle("Sierpinski Triangles");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setSize(screenSize.width/2, screenSize.height/2);

		SierpinskiPanel panel = new SierpinskiPanel();
		add(panel);
	}
}
