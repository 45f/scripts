package omniapi.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class DynamicLabel extends PaintComponent {

	private String text;
	private Color color = Color.WHITE;
	private Font font = null;

	public DynamicLabel(int xPos, int yPos, String Text) {
		super(xPos, yPos, 1, 1);
		text = Text;
	}

	public DynamicLabel(int xPos, int yPos, String Text, Font f) {
		super(xPos, yPos, 1, 1);
		text = Text;
		font = f;
	}

	public DynamicLabel(int xPos, int yPos, String Text, Color c) {
		super(xPos, yPos, 1, 1);
		text = Text;
		color = c;
	}

	public DynamicLabel(int xPos, int yPos, String Text, Color c, Font f) {
		super(xPos, yPos, 1, 1);
		text = Text;
		color = c;
		font = f;
	}

	@Override
	public void onDraw(Graphics2D g) {
		if (w == 1 || h == 1) {
			Rectangle rect = getStringBounds(g, text, x, y, font);
			w = rect.width;
			h = rect.height;
		}
		g.setFont(font);
		g.setColor(color);
		int tempY = y - (h + 4);
		for (String line : text.split("\n"))
			g.drawString(line, x, tempY += h + 4);
		g.setColor(Color.WHITE);

	}

	public void update(Paint parent) {
		if (enabled) {
			if (font == null) {
				font = parent.getButtonFont();
			}
			onUpdate(parent);
		}
	}

	public abstract void onUpdate(Paint parent);

	public void setText(String t) {
		text = t;
		w = h = 1;
	}
}
