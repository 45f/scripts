package omniapi.data;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public class Item extends OmniScriptEmulator<OmniScript> implements VirtualBase {

	private org.osbot.rs07.api.model.Item child;

	public Item(OmniScript script, org.osbot.rs07.api.model.Item item) {
		super(script);
		child = item;
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
		return child.hover();
	}

	@Override
	public boolean hasAction(String... actions) {
		if (!exists())
			return false;
		return child.hasAction(actions);
	}

	@Override
	public boolean interact(String... interactions) throws InterruptedException {
		if (!exists())
			return false;
		return child.interact(interactions);
	}

	@Override
	public boolean isVisible() {
		// TODO: actual isVisible()
		return exists();
	}

	@Override
	public boolean exists() {
		return (child != null);
	}

	public int getAmount() {
		if (!exists())
			return 0;
		return child.getAmount();
	}

	public org.osbot.rs07.api.model.Item getRaw() {
		return child;
	}

	public int getId() {
		if (!exists())
			return -1;
		return child.getId();
	}

	public String getName() {
		if (!exists())
			return "null";
		return child.getName();
	}

	public int getNonNotedId() {
		if (!exists())
			return -1;
		return child.getNonNotedId();
	}

	public Widget getOwner() {
		if (!exists())
			return new DefaultWidget(getScript());
		return new Widget(getScript(), child.getOwner());
	}

	public boolean isNote() {
		if (!exists())
			return false;
		return child.isNote();
	}

}
