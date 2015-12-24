package omniapi.interfaces;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;
import omniapi.data.DefaultEntity;
import omniapi.data.DefaultNPC;
import omniapi.data.Entity;
import omniapi.data.NPC;
import omniapi.data.Widget;

public class RSBank extends OmniScriptEmulator<OmniScript> implements RSInterface {

	public RSBank(OmniScript script) {
		super(script);
	}

	@Override
	public int getRootID() {
		return 12;
	}

	@Override
	public int getChildID() {
		return 3;
	}

	@Override
	public int getGrandchildID() {
		return 0;
	}

	@Override
	public Widget getWidget() {
		return getWidgetFinder().get(getRootID(), getChildID(), getGrandchildID());
	}

	@Override
	public boolean isOpen() {
		return getWidget().exists();
	}

	@Override
	public boolean open() throws InterruptedException {
		if (isOpen())
			return true;

		Entity bankEntity = getEntityFinder().find((entity) -> (entity.hasAction("Banking")));

		if (bankEntity instanceof DefaultEntity) {
			NPC bankNPC = getNPCFinder().find((npc) -> (npc.hasAction("Banking")));
			if (!bankNPC.exists() || !bankNPC.interact("Banking"))
				return false;
			return sleepUntil(() -> (isOpen()), 10000);
		} else {
			if (!bankEntity.interact("Banking"))
				return false;
			return sleepUntil(() -> (isOpen()), 10000);
		}

		// return false;
	}

	@Override
	public boolean close() throws InterruptedException {
		if (!isOpen())
			return true;
		Widget closeButton = getWidgetFinder().findFromAction("Close",
				(widget) -> (widget.getSpriteIndex1() == 535 && widget.getRootId() == getRootID()));
		return closeButton.interact();
	}

	public boolean depositInventory() throws InterruptedException {
		return getWidgetFinder().get(12, 27).interact();
	}
}
