// CustomJTextField.java
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.geom.RoundRectangle2D;

public class CustomTextField extends JTextField implements FocusListener {

	private Insets fieldInsets;

	/**
	 * The shape of the text field.
	 */
	private Shape fieldShape;

	public CustomTextField (int size) {
		super(size);
		setForeground(new Color(162, 154, 154)); // FIX
		setPreferredSize(new Dimension(size * 10, 50));
		setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 25));
		Border b = UIManager.getBorder("TextField.border");
		JTextField field = new JTextField(size);
		fieldInsets = b.getBorderInsets(field);
		setOpaque(false);
		addFocusListener(this);
	}

	protected void paintComponent (Graphics g) {
		g.setColor(new Color(0, 0, 0)); // FIX
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
		super.paintComponent(g);
		int textX = 2;
		if (symbol != null) {
			int symbolWidth = symbol.getIconWidth();
			int symbolHeight = symbol.getIconHeight();
			int x = fieldInsets.left + 5;
			textX = x + symbolWidth + 2;
			int y = (getHeight() - symbolHeight) / 2;
			symbol.paintIcon(this, g, x, y);
		}
		setMargin(new Insets(4, textX, 2, 2));
	}

	/**
	 * This method changes the colour of the border of the text field.
	 * @param g refers to the graphics of the text field.
	 */
	protected void paintBorder (Graphics g) {
		if (hasFocus()) {
			g.setColor(new Color(79, 193, 0));
			g.drawRoundRect(0, 0, getWidth() - 2, getHeight() - 1, 5, 5);
			g.drawRoundRect(1, 0, getWidth() - 2, getHeight() - 1, 5, 5);
			g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 5, 5);
		} else {
			g.setColor(new Color(53, 50, 48));
			g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
		}
    }

	/**
	 * This method tells the frame what the container size of the object is.
	 * @param x the length of the container size
	 * @param y the the height of the container size.
	 */
	public boolean contains (int x, int y) {
        if (fieldShape == null || !fieldShape.getBounds().equals(getBounds())) {
            fieldShape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        }
        return fieldShape.contains(x, y);
    }

	/**
	 * Checks to see if the interactive object is no longer in focus,and changes
	 * the colour and default text depending on whether of not the object is in focus.
	 * @param e refers to a FocusEvent object and identifies it as a permanent change in focus.
	 */
	public void focusGained (FocusEvent e) {
		symbol = symbol2;
		repaint();
		if (getText().equals(defaultString)){
			setForeground(new Color(53, 50, 48));
			setText("");
		}
	}

	/**
	 * Checks to see if the interactive object is no longer in focus,and changes
	 * the colour and default text depending on whether of not the object is in focus.
	 * @param e refers to a FocusEvent object and identifies it as a permanent change in focus.
	 */
	public void focusLost (FocusEvent e) {
		symbol = symbol1;
		repaint();
		if (getText().equals("")){
			setForeground(new Color(142, 132, 127));
			setText(defaultString);
		}
	}
}
