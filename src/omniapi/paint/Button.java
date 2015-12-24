package omniapi.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Button extends PaintComponent {

	private Point mousePosition;
	private boolean hover, clicked;
	private boolean textOnly;

	private Color Basic = Color.WHITE;
	private Color Hover = Color.BLUE;
	private Font font;
	private String text;
	public boolean toggle = true;
	public boolean toggleState = false;

	private Picture background = null;

	/*
	 * public Button(int xPos, int yPos, int width, int height) { super(xPos,
	 * yPos, width, height); text = ""; }
	 */

	public Button(int xPos, int yPos, String Text) {
		super(xPos, yPos, 1, 1);
		text = Text;
		textOnly = true;
	}

	public Button(int xPos, int yPos, String Text, Font f) {
		super(xPos, yPos, 1, 1);
		text = Text;
		textOnly = true;
		font = f;
	}

	public Button(int xPos, int yPos, String Text, Picture bg) {
		super(xPos, yPos, bg.getWidth(), bg.getHeight());
		text = Text;
		background = bg;
	}

	public Button(int xPos, int yPos, Picture bg) {
		super(xPos, yPos, bg.getWidth(), bg.getHeight());
		background = bg;
	}

	@Override
	public void onDraw(Graphics2D g) {
		g.setFont(font);
		if (textOnly && (w == 1 || h == 1)) {
			Rectangle rect = getStringBounds(g, text, x, y, font);
			h = rect.height;
			w = rect.width;
			r = rect;
		}
		if ((!textOnly || text.isEmpty()) && background != null) { // draw only
																	// a button
			background.draw(g);
		} else if (textOnly) {
			if (!hover && !clicked) {
				g.setColor(Basic);
				g.drawString(text, x, y);
				g.setColor(Color.WHITE);
			} else {
				if (clicked) {
					g.setColor(Color.BLACK);
					g.drawString(text, x + 1, y + 1);
					g.setColor(Color.WHITE);
				}
				if (hover) {
					g.setColor(Hover);
					g.drawString(text, x, y);
					g.setColor(Color.WHITE);
				}
			}
		} else { // draw text on top of button
			background.draw(g);
			if (!hover && !clicked) {
				g.setColor(Basic);
				g.drawString(text, x, y);
				g.setColor(Color.WHITE);
			} else {
				if (clicked) {
					g.setColor(Color.BLACK);
					g.drawString(text, x + 1, y + 1);
					g.setColor(Color.WHITE);
				}
				if (hover) {
					g.setColor(Hover);
					g.drawString(text, x, y);
					g.setColor(Color.WHITE);
				}
			}
		}
	}

	@Override
	public void onUpdate(Paint parent) {
		mousePosition = parent.getMousePosition();
		hover = contains(mousePosition);
		clicked = contains(parent.getClickPosition());
		// parent.script.log(clicked);
		if (clicked)
			click(parent);
		if (font == null)
			font = parent.getButtonFont();
	}

	public abstract void onClick(Paint parent);

	public void click(Paint parent) {
		if (toggle && !toggleState || !toggle) {
			onClick(parent);
			if (toggle)
				toggleState = true;
		}
	}

	public boolean isClicked() {
		return clicked;
	}

}
