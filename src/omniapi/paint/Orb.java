package omniapi.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Orb extends PaintComponent {

	private Label label;
	private Picture picture;

	public Orb(int xPos, int yPos, int width, int height, Label l, Picture p) {
		super(xPos, yPos, width, height);
		label = l;
		picture = p;
	}

	public Orb(int xPos, int yPos, int width, int height, String text, BufferedImage image) {
		super(xPos, yPos, width, height);
		picture = new Picture(xPos, yPos, image);
		label = new Label(xPos + image.getWidth() + 6, yPos, text);

	}

	public Orb(int xPos, int yPos, int width, int height, String text, BufferedImage image, int imageWidth,
			int imageHeight) {
		super(xPos, yPos, width, height);
		picture = new Picture(xPos, yPos, imageWidth, imageHeight, image);
		label = new Label(xPos + image.getWidth() + 6, yPos, text);
	}

	@Override
	public void onDraw(Graphics2D g) {
		picture.draw(g);
		int addValue = ((picture.getHeight() - label.getHeight()) / 2);
		if (addValue > 0)
			label.y = label.getY() + addValue;
		label.draw(g);
	}

	@Override
	public void onUpdate(Paint parent) {
		picture.update(parent);
		label.update(parent);
	}

}
