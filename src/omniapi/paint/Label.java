package omniapi.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Label extends PaintComponent {

	private String text;
	private Color color = Color.WHITE;
	private Font font = null;
	private boolean rightAlign = false;

	public Label(int xPos, int yPos, String Text) {
		super(xPos, yPos, 1, 1);
		text = Text;
	}

	public Label(int xPos, int yPos, String Text, Font f) {
		super(xPos, yPos, 1, 1);
		text = Text;
		font = f;
	}

	public Label(int xPos, int yPos, String Text, Color c) {
		super(xPos, yPos, 1, 1);
		text = Text;
		color = c;
	}

	public Label(int xPos, int yPos, String Text, Color c, Font f) {
		super(xPos, yPos, 1, 1);
		text = Text;
		color = c;
		font = f;
	}

	public Label(int xPos, int yPos, String Text, Color c, Font f, boolean alignRight) {
		super(xPos, yPos, 1, 1);
		text = Text;
		color = c;
		font = f;
		rightAlign = alignRight;
	}

	@Override
	public void onDraw(Graphics2D g) {
		if (w == 1 || h == 1) {
			Rectangle rect = getStringBounds(g, text, x, y, font);
			w = rect.width;
			h = rect.height;
			if (rightAlign) {
				x -= w;
			}
		}
		g.setFont(font);
		g.setColor(color);
		int tempY = y;
		for (String line : text.split("\n"))
			g.drawString(line, x, tempY += h + 4);
		// g.drawString(text, x, y);
		g.setColor(Color.WHITE);

	}

	@Override
	public void onUpdate(Paint parent) {
		if (font == null) {
			font = parent.getButtonFont();
		}
	}

	public void setText(String t) {
		text = t;
		w = h = 1;
	}

}
