package omniapi.interfaces;

import omniapi.data.Widget;

/*
 * This interface allows a simple base for an interface, which can be defined as a set of widgets that contain useful information
 * (eg Banking, Grand Exchange, Poll Booth)
 * */
public interface RSInterface {
	public int getRootID();

	public int getChildID();

	public int getGrandchildID();

	public Widget getWidget();

	public boolean isOpen();

	public boolean open() throws InterruptedException;

	public boolean close() throws InterruptedException;
}
