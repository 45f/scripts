package omniapi.paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DynamicOrb extends PaintComponent {

	private DynamicLabel label;
	private Picture picture;
	private boolean fixedDimensions = false;

	public DynamicOrb(int xPos, int yPos, DynamicLabel l, Picture p) {
		super(xPos, yPos, 1, 1);
		picture = p;
		label = l;
		label.x = picture.x;
		label.y = picture.y;
		label.x += picture.getWidth() + 6;
	}

	public DynamicOrb(int xPos, int yPos, DynamicLabel l, BufferedImage image, int imageWidth, int imageHeight) {
		super(xPos, yPos, 1, 1);
		picture = new Picture(xPos, yPos, imageWidth, imageHeight, image);
		label = l;
		label.x = picture.x;
		label.y = picture.y;
		label.x += imageWidth + 6;
	}

	@Override
	public void onDraw(Graphics2D g) {
		picture.draw(g);
		label.draw(g);
		if (!fixedDimensions) {
			label.y = label.getY() + picture.getHeight() - (picture.getHeight() / 2) + (label.getHeight() / 2);
			fixedDimensions = true;

			h = picture.getHeight();
			w = picture.getWidth() + 6 + label.getWidth();
		}
	}

	@Override
	public void onUpdate(Paint parent) {
		picture.update(parent);
		label.update(parent);

	}
}
