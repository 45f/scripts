package omniapi.paint;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public abstract class PaintComponent {

	protected int x, y, w, h;
	protected boolean enabled;
	protected Rectangle r;

	public PaintComponent(int xPos, int yPos, int width, int height) {
		x = xPos;
		y = yPos;
		w = width;
		h = height;
		enabled = true;
		r = new Rectangle(x, y, w, h);
	}

	public Rectangle getStringBounds(Graphics2D g, String str, int x, int y, Font font) {
		FontRenderContext frc = g.getFontRenderContext();
		GlyphVector gv = font.createGlyphVector(frc, str);
		return gv.getPixelBounds(null, x, y);
	}

	public void draw(Graphics2D g) {
		if (enabled) {
			onDraw(g);
		}
	}

	public void update(Paint parent) {
		if (enabled) {
			onUpdate(parent);
		}
	}

	public boolean contains(Point p) {
		if (p == null)
			return false;
		if (p.getX() == -1 || p.getY() == -1)
			return false;
		return r.contains(p);
	}

	public boolean contains(int X, int Y) {
		Point p = new Point(X, Y);
		return contains(p);
	}

	public abstract void onDraw(Graphics2D g);

	public abstract void onUpdate(Paint parent);

	public Rectangle getRect() {
		return r;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}
}
