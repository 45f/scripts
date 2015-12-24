package omniapi.data;

import org.osbot.rs07.api.def.EntityDefinition;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Model;

public interface EntityBase {
	public int getGridY();

	public int getGridX();

	public int getLocalX();

	public int getLocalY();

	public int getX();

	public int getY();

	public int getZ();

	public int getSizeX();

	public int getSizeY();

	public int getHeight();

	public int getId();

	public int[] getModelIds();

	public boolean isVisible();

	public boolean exists();

	public boolean examine() throws InterruptedException;

	public boolean hasAction(String... actions);

	public boolean interact(String... interactions) throws InterruptedException;

	public boolean hover();

	public String[] getActions();

	public String getName();

	public Position getPosition();

	public Model getModel();

	public Area getArea(int z);

	public EntityDefinition getDefinition();
}
