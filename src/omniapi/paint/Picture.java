package omniapi.paint;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Picture extends PaintComponent {

	private BufferedImage img;
	public int internalX, internalY;

	public Picture(int xPos, int yPos, BufferedImage image) {
		super(xPos, yPos, image.getWidth(), image.getHeight());
		internalX = x;
		internalY = y;
		img = image;
	}

	public Picture(int xPos, int yPos, int width, int height, BufferedImage image) {
		super(xPos, yPos, width, height);

		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		if (width > imageWidth) { // defined area bigger than image area
			internalX = x + ((width - imageWidth) / 2);
		}
		if (height > imageHeight) {
			internalY = y + ((height - imageHeight) / 2);
		}
		img = image;
	}

	@Override
	public void onDraw(Graphics2D g) {
		g.drawImage((Image) img, internalX, internalY, null);
	}

	@Override
	public void onUpdate(Paint parent) {

	}

}
