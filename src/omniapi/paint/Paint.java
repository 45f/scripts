package omniapi.paint;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.osbot.rs07.script.Script;

public class Paint implements MouseListener, MouseMotionListener {

	public Script script;
	private Point mousePos = new Point(-1, -1);
	private Point clickPos = new Point(-1, -1);
	private List<PaintComponent> list = new ArrayList<PaintComponent>();
	public boolean enabled = true;

	private Font font = null;

	private Button toggleButton;

	public Paint(Script m) {
		script = m;
		try {
			toggleButton = new Button(494, 347,
					new Picture(494, 347, ImageIO.read(new URL("http://i.imgur.com/MKbDjOK.png")))) {

				@Override
				public void onClick(Paint parent) {
					parent.enabled = !parent.enabled;
					// script.log("Clicked");
				}

			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Point getMousePosition() {
		return mousePos;
	}

	public Font getButtonFont() {
		return font;
	}

	public void setDefaultFont(Font f) {
		font = f;
	}

	public Point getClickPosition() {
		return clickPos;
	}

	public void update() {
		toggleButton.update(this);
		if (enabled) {
			for (PaintComponent pc : list) {
				pc.update(this);
				if (pc instanceof Button && ((Button) pc).isClicked()) {
					// script.log("buton");
					((Button) pc).click(this);
				}
			}
		}
	}

	public void unToggleButtons() {
		if (toggleButton.enabled && toggleButton.toggle && toggleButton.toggleState)
			toggleButton.toggleState = false;
		for (PaintComponent pc : list) {
			if (pc instanceof Button) {
				Button b = (Button) pc;
				if (b.enabled && b.toggle && b.toggleState)
					b.toggleState = false;
			}
		}
	}

	public void draw(Graphics2D g) {

		if (enabled) {
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			for (PaintComponent pc : list) {
				pc.draw(g);
			}
		}
		toggleButton.draw(g);
	}

	public void add(PaintComponent p) {
		list.add(p);
	}

	public BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		clickPos = e.getPoint();
		// script.log(clickPos);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clickPos = null;
		unToggleButtons();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos = e.getPoint();
	}

}
