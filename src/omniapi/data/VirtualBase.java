package omniapi.data;

/*
 * The VirtualBase interface allows the representation of an object that is in a virtual (2D) space.
 * */
public interface VirtualBase {
	public boolean hover() throws InterruptedException;

	public boolean hasAction(String... actions);

	public boolean interact(String... interactions) throws InterruptedException;

	public boolean isVisible();

	public boolean exists();
}
