package omniapi.data;

import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.api.ui.RS2Widget;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;

public class Widgets extends OmniScriptEmulator<OmniScript> {

	List<Widget> cache;
	private long lastCacheTime;

	public Widgets(OmniScript script) {
		super(script);
		cache = new ArrayList<Widget>();
		lastCacheTime = 0;
	}

	public List<Widget> getAll() {
		if (System.currentTimeMillis() > (lastCacheTime + (Constants.TICK / 2))) {
			cache.clear();
			for (RS2Widget o : getWidgets().getAll()) {
				cache.add(new Widget(getScript(), o));
			}
			lastCacheTime = System.currentTimeMillis();
		}
		return cache;
	}
}
