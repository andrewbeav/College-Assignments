import javax.swing.*;
import java.awt.*;

public class SierpinskiPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();
		
		int sideLength;
		if (height < width) {
			sideLength = height;
		}
		else {
			sideLength = width;
		}

		draw(g, 0, 0, sideLength);
	}

	public void draw(Graphics g, int x, int y, int sideLength) {
		if (sideLength == 1) {
			g.drawRect(x, y, 1, 1);
		}
		else {
			draw(g, x, y+sideLength, sideLength/2);
			draw(g, x+sideLength, y+sideLength, sideLength/2);
			draw(g, x+(sideLength/2), y, sideLength/2);
		}
	}
}
