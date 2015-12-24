package omniapi.data;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.api.ui.Option;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public class Widget extends OmniScriptEmulator<OmniScript> implements VirtualBase {

	private org.osbot.rs07.api.ui.RS2Widget child;

	public Widget(OmniScript script, org.osbot.rs07.api.ui.RS2Widget w) {
		super(script);
		child = w;
	}

	public org.osbot.rs07.api.ui.RS2Widget getRaw() {
		return child;
	}

	public int getAbsX() {
		if (!exists())
			return -1;
		return child.getAbsX();
	}

	public int getAbsY() {
		if (!exists())
			return -1;
		return child.getAbsY();
	}

	public int getActionType() {
		if (!exists())
			return -1;
		return child.getActionType();
	}

	public int getAlpha() {
		if (!exists())
			return -1;
		return child.getAlpha();
	}

	public Widget getChildWidget(int childId) {
		if (!exists())
			return new DefaultWidget(getScript());
		return new Widget(getScript(), child.getChildWidget(childId));
	}

	public int getContentType() {
		if (!exists())
			return -1;
		return child.getContentType();
	}

	public int getHeight() {
		if (!exists())
			return -1;
		return child.getHeight();
	}

	public int getWidth() {
		if (!exists())
			return -1;
		return child.getWidth();
	}

	public String[] getInteractActions() {
		if (!exists() || child.getInteractActions() == null)
			return new String[] { "null" };
		return child.getInteractActions();
	}

	@Override
	public boolean hasAction(String... actions) {
		if (!exists())
			return false;
		if (getInteractActions() == null || getInteractActions().length < 1)
			return false;
		List<String> actionList = Arrays.asList(getInteractActions());
		for (String action : actions) {
			if (!actionList.contains(action))
				return false;
		}
		return true;
	}

	public String[] getInteractOptions() {
		if (!exists() || child.getInteractOptions() == null)
			return new String[] { "null" };
		return child.getInteractOptions();
	}

	public String getMessage() {
		if (!exists() || child.getMessage() == null)
			return "null";
		return child.getMessage();
	}

	public Rectangle getRectangle() {
		if (!exists())
			return new Rectangle(0, 0, 0, 0);
		return child.getRectangle();
	}

	public Rectangle getBounds() {
		if (!exists())
			return new Rectangle(0, 0, 0, 0);
		return child.getBounds();
	}

	@Override
	public boolean exists() {
		return (child != null && child.isVisible());
	}

	@Override
	public boolean isVisible() {
		if (!exists())
			return false;
		return child.isVisible();
	}

	public int getSpriteIndex1() {
		if (!exists())
			return -1;
		return child.getSpriteIndex1();
	}

	public int getSpriteIndex2() {
		if (!exists())
			return -1;
		return child.getSpriteIndex2();
	}

	public int getRootId() {
		if (!exists())
			return -1;
		return child.getRootId();
	}

	public int getSecondLevelId() {
		if (!exists())
			return -1;
		return child.getSecondLevelId();
	}

	public int getThirdLevelId() {
		if (!exists())
			return -1;
		return child.getThirdLevelId();
	}

	@Override
	public boolean interact(String... interactions) throws InterruptedException {
		if (!exists())
			return false;

		if (interactions == null || interactions.length == 0)
			return interact(true, 5, "");
		return interact(true, 5, interactions);
	}

	public boolean interact(boolean sleep, int deviate, String... interactions) throws InterruptedException {
		if (interactions.length > 1)
			return false; // TODO
		String interaction = interactions[0];

		int tries = 0;
		while (!getBounds().contains(getMouse().getPosition())) {
			if (tries > 5)
				break;
			hover();
			debug("Hovering");
			tries++;
		}

		if (interaction == null || interaction == "")
			return getMouse().click(false);
		if (getMenuAPI().stripFormatting(getMenuAPI().getTooltip()).equalsIgnoreCase(interaction))
			return getMouse().click(false);

		if (!getMouse().click(true))
			return false;

		if (sleep)
			sleep(rand(25 * deviate, 40 * deviate));

		int index = -1;
		List<Option> options = getMenuAPI().getMenu();
		for (int i = 0; i < options.size(); i++) {
			Option o = options.get(i);
			String s = o.action;
			if (s.equalsIgnoreCase(interaction)) {
				index = i;
				break;
			}
		}

		if (index == -1) {
			getMouse().click(false); // Remove the menu API
			return false;
		}

		int mouseX = (int) getMouse().getPosition().getX();
		int mouseY = (int) getMouse().getPosition().getY();

		Rectangle menuRect = getMenuAPI().getOptionRectangle(index);

		int startX = (int) menuRect.getX() + 1;
		int startY = (int) menuRect.getY() + 1;
		int centreX = (int) (menuRect.getX() + (menuRect.getWidth() / 2));
		int centreY = (int) (menuRect.getY() + (menuRect.getHeight() / 2));
		int endX = (int) (menuRect.getX() + menuRect.getWidth()) - 1;
		int endY = (int) (menuRect.getY() + menuRect.getHeight()) - 1;

		/*
		 * We want to split our bounding box into the following conditions:
		 * Between start and finish -> skewed random point from the x/y within
		 * rect Before start -> skewed random point from start x (positive)
		 * After finish -> skewed random point from start x (negative)
		 */

		boolean north = (mouseY <= startY);
		boolean east = (mouseX >= endX);
		boolean south = (mouseY >= endY);
		boolean west = (mouseX <= startX);

		boolean betweenX = (mouseX > startX) && (mouseX < endX);
		boolean betweenY = (mouseY > startY) && (mouseY < endY);

		int finalX = 0;
		int finalY = 0;

		if (betweenX) {
			if (mouseX > centreX)
				finalX = negativeSkewedRandom(startX, mouseX);
			else
				finalX = positiveSkewedRandom(mouseX, endX);
		} else {
			if (west)
				finalX = positiveSkewedRandom(startX, endX);
			else
				finalX = negativeSkewedRandom(startX, endX);
		}

		if (betweenY) {
			if (mouseY > centreY)
				finalY = negativeSkewedRandom(startY, mouseY);
			else
				finalY = positiveSkewedRandom(mouseY, endY);
		} else {
			if (north)
				finalY = positiveSkewedRandom(startY, endY);
			else
				finalY = negativeSkewedRandom(startY, endY);
		}

		debug("INTERACT betweenx " + betweenX + "; betweeny " + betweenY);
		debug(finalX + ", " + finalY);

		if (getMouse().move(finalX, finalY))
			return false;
		if (sleep)
			sleep(rand(5 * deviate, 10 * deviate));
		return getMouse().click(false);
	}

	@Override
	public boolean hover() throws InterruptedException {
		if (!exists())
			return false;
		return hover(false);
	}

	public boolean hover(boolean reHover) throws InterruptedException {
		if (!exists())
			return false;
		if (reHover && getBounds().contains(getMouse().getPosition()))
			return true;

		int x = (int) getMouse().getPosition().getX();
		int y = (int) getMouse().getPosition().getY();
		int startX = getAbsX();
		int startY = getAbsY();
		int endX = (int) (startX + getBounds().getWidth());
		int endY = (int) (startY + getBounds().getHeight());
		int centreX = (int) (startX + (getBounds().getWidth() / 2));
		int centreY = (int) (startY + (getBounds().getHeight() / 2));

		Point hoverPoint = getDistributedRandom(x, y, startX, startY, endX, endY, centreX, centreY);

		debug(hoverPoint.getX() + ", " + hoverPoint.getY());
		return !getMouse().move((int) hoverPoint.getX(), (int) hoverPoint.getY())
				&& getBounds().contains(getMouse().getPosition());
	}

	public Point getDistributedRandom(int mouseX, int mouseY, int startX, int startY, int endX, int endY, int centreX,
			int centreY) {
		/*
		 * We want to split our bounding box into the following conditions:
		 * Between start and finish -> skewed random point from the x/y within
		 * rect Before start -> skewed random point from start x (positive)
		 * After finish -> skewed random point from start x (negative)
		 */

		boolean north = (mouseY <= startY);
		boolean east = (mouseX >= endX);
		boolean south = (mouseY >= endY);
		boolean west = (mouseX <= startX);

		boolean betweenX = (mouseX > startX) && (mouseX < endX);
		boolean betweenY = (mouseY > startY) && (mouseY < endY);

		int finalX = 0;
		int finalY = 0;

		if (betweenX) {
			if (mouseX > centreX)
				finalX = negativeSkewedRandom(startX, mouseX);
			else
				finalX = positiveSkewedRandom(mouseX, endX);
		} else {
			if (west)
				finalX = positiveSkewedRandom(startX, endX);
			else
				finalX = negativeSkewedRandom(startX, endX);
		}

		if (betweenY) {
			if (mouseY > centreY)
				finalY = negativeSkewedRandom(startY, mouseY);
			else
				finalY = positiveSkewedRandom(mouseY, endY);
		} else {
			if (north)
				finalY = positiveSkewedRandom(startY, endY);
			else
				finalY = negativeSkewedRandom(startY, endY);
		}

		return new Point(finalX, finalY);
	}
}
