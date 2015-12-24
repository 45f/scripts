package omniapi.data;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.api.def.EntityDefinition;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.util.GraphicUtilities;
import org.osbot.rs07.event.CameraPitchEvent;
import org.osbot.rs07.event.CameraYawEvent;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;
import omniapi.interact.EntityInteractor;

public class Entity extends OmniScriptEmulator<OmniScript> implements PhysicalBase {

	private org.osbot.rs07.api.model.Entity child;

	public Entity(OmniScript script, org.osbot.rs07.api.model.Entity e) {
		super(script);
		child = e;
	}

	public org.osbot.rs07.api.model.Entity getRaw() {
		return child;
	}

	@Override
	public int getGridY() {
		if (!exists())
			return -1;
		return child.getGridY();
	}

	@Override
	public int getGridX() {
		if (!exists())
			return -1;
		return child.getGridX();
	}

	@Override
	public int getLocalX() {
		if (!exists())
			return -1;
		return child.getLocalX();
	}

	@Override
	public int getLocalY() {
		if (!exists())
			return -1;
		return child.getLocalY();
	}

	@Override
	public int getX() {
		if (!exists())
			return -1;
		return child.getX();
	}

	@Override
	public int getY() {
		if (!exists())
			return -1;
		return child.getY();
	}

	@Override
	public int getZ() {
		if (!exists())
			return -1;
		return child.getZ();
	}

	@Override
	public int getSizeX() {
		if (!exists())
			return -1;
		return child.getSizeX();
	}

	@Override
	public int getSizeY() {
		if (!exists())
			return -1;
		return child.getSizeY();
	}

	@Override
	public int getHeight() {
		if (!exists())
			return -1;
		return child.getHeight();
	}

	@Override
	public int getId() {
		if (!exists())
			return -1;
		return child.getId();
	}

	@Override
	public int[] getModelIds() {
		return child.getModelIds();
	}

	@Override
	public boolean isVisible() {
		if (!exists())
			return false;
		return child.isVisible();
	}

	@Override
	public boolean exists() {
		return (child != null && child.exists());
	}

	@Override
	public boolean examine() throws InterruptedException {
		return interact("Examine");
	}

	@Override
	public boolean hasAction(String... actions) {
		if (!exists())
			return false;
		return child.hasAction(actions);
	}

	public boolean interact(boolean sleep, String... interactions) throws InterruptedException {
		if (!exists())
			return false;
		if (!hasAction(interactions))
			return false;
		if (getMap().distance(getPosition()) > 12 && !getWebWalker().walkPath(getPosition()))
			return false;
		if (!isVisible())
			getCamera().toEntity(child);
		for (String interaction : interactions) {
			if (!new EntityInteractor(getScript()).setEntity(this).shrinkRectangle(8).interact(interaction, sleep, 5))
				return false;
		}
		return true;
	}

	@Override
	public boolean interact(String... interactions) throws InterruptedException {
		return interact(true, interactions);
	}

	/* Custom interaction methods */
	public boolean use() throws InterruptedException {
		return interact("Use");
	}

	public boolean hover(boolean reHover) throws InterruptedException {
		if (!exists())
			return false;
		if (!reHover && GraphicUtilities.getModelBoundingBox(getBot(), getGridX(), getGridY(), getZ(), getModel())
				.contains(getMouse().getPosition()))
			return true;

		while (!isVisible())
			toCamera();

		Point hoverPoint = getDistributedRandomPoint();
		if (hoverPoint.equals(new Point(-1, -1)))
			return false;

		debug(hoverPoint.getX() + ", " + hoverPoint.getY());
		return !getMouse().move((int) hoverPoint.getX(), (int) hoverPoint.getY())
				&& GraphicUtilities.getModelBoundingBox(getBot(), getGridX(), getGridY(), getZ(), getModel())
						.contains(getMouse().getPosition());
	}

	@Override
	public boolean hover() throws InterruptedException {
		if (!exists())
			return false;

		return hover(false);
	}

	@Override
	public String[] getActions() {
		return child.getActions();
	}

	@Override
	public String getName() {
		if (!exists())
			return "null";
		return child.getName();
	}

	@Override
	public Position getPosition() {
		return child.getPosition();
	}

	@Override
	public Model getModel() {
		return child.getModel();
	}

	@Override
	public Area getArea(int z) {
		return child.getArea(z);
	}

	@Override
	public EntityDefinition getDefinition() {
		return child.getDefinition();
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

	public Point getRandomPoint() {
		List<Point> suitablePoints = getSuitablePoints();
		if (suitablePoints == null)
			return new Point(-1, -1);

		return suitablePoints.get(getRandom(0, suitablePoints.size() - 1));
	}

	public Point getDistributedRandomPoint() {
		List<Point> suitablePoints = getSuitablePoints();
		if (suitablePoints == null)
			return new Point(-1, -1);

		int mouseX = (int) getMouse().getPosition().getX();
		int mouseY = (int) getMouse().getPosition().getY();

		Rectangle targetRect = getModel().getBoundingBox(getGridX(), getGridY(), getZ());

		int startX = (int) targetRect.getX() + 1;
		int startY = (int) targetRect.getY() + 1;
		int centreX = (int) (targetRect.getX() + (targetRect.getWidth() / 2));
		int centreY = (int) (targetRect.getY() + (targetRect.getHeight() / 2));
		int endX = (int) (targetRect.getX() + targetRect.getWidth()) - 1;
		int endY = (int) (targetRect.getY() + targetRect.getHeight()) - 1;

		int times = 0;
		int maxTimes = 1000;

		Point currentPoint = getDistributedRandom(mouseX, mouseY, startX, startY, endX, endY, centreX, centreY);
		while (!suitablePoints.contains(currentPoint) && times < maxTimes) {
			times++;
			currentPoint = getDistributedRandom(mouseX, mouseY, startX, startY, endX, endY, centreX, centreY);
		}

		if (times >= maxTimes)
			return getRandomPoint();
		else
			return currentPoint;
	}

	public ArrayList<Point> getSuitablePoints() {
		if (this instanceof DefaultEntity || !exists())
			return null; // Returning null because this should only be used
							// internally

		short[][] coords = GraphicUtilities.getScreenCoordinates(getBot(), getGridX(), getGridY(), getZ(), getModel());
		if (coords == null)
			return null; // OSBot method, could return null :^)

		ArrayList<Point> points = new ArrayList<Point>();

		for (int i = 0; i < coords.length; i++) {
			short[] currentCoords = coords[i];
			if (currentCoords != null && Constants.SCREEN_RECT.contains(currentCoords[0], currentCoords[1]))
				points.add(new Point(currentCoords[0], currentCoords[1]));
		}

		if (points.size() == 0)
			return null;

		return points;
	}

	@Override
	public boolean toCamera() throws InterruptedException {
		if (!exists())
			return false;
		if (isVisible())
			return true;

		int moveYaw = getCameraYaw();
		CameraYawEvent yawEvent = new CameraYawEvent(getBot(), moveYaw);
		yawEvent.setBlocking();
		yawEvent.execute();
		if (yawEvent.hasFinished()) {
			CameraPitchEvent pitchEvent = new CameraPitchEvent(getBot(), getCameraPitch());
			pitchEvent.setBlocking();
			pitchEvent.execute();
			if (pitchEvent.hasFinished())
				return true;
		}
		return false;
	}

	@Override
	public boolean walkTo() throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	private int getCameraYaw() {
		if (!exists())
			return 0;

		int deg = (int) Math.toDegrees(Math.atan2(getY() - myPosition().getY(), getX() - myPosition().getX())) - 90;
		if (deg < 0)
			deg += 360;
		return deg % 360;
	}

	private int getCameraPitch() {
		if (!exists())
			return 0;

		int height = getPosition().getTileHeight(getBot()) + getModel().getHeight();
		int plrHeight = myPosition().getTileHeight(getBot());

		int dist = myPosition().distance(getPosition()) * 512;
		double cos = (double) (dist + ((height + plrHeight > 0) ? -plrHeight : 0)) * 3.14E-4;
		if (cos > 1.0)
			cos = 1.0;

		cos = Math.cos(cos);

		return (int) Math.round(Math.toDegrees(cos));
	}
}
