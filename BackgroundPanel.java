// BackgroundPanel.java
import java.awt.*;
import javax.swing.*;


public class BackgroundPanel extends JPanel {
    ImageIcon backgroundImage;

    public BackgroundPanel (LayoutManager layout, String image) {
		super(layout);
		backgroundImage = new ImageIcon(image);
	}

    protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
	}
}
