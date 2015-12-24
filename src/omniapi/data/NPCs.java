package omniapi.data;

import java.util.ArrayList;
import java.util.List;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;

public class NPCs extends OmniScriptEmulator<OmniScript> {

	private List<NPC> cache;
	private long lastCacheTime;

	public NPCs(OmniScript script) {
		super(script);
		cache = new ArrayList<NPC>();
		lastCacheTime = 0;
	}

	public List<NPC> getAll() {
		if (System.currentTimeMillis() > lastCacheTime + (Constants.TICK / 2)) {
			cache.clear();
			for (org.osbot.rs07.api.model.NPC n : getRawScript().getNpcs().getAll()) {
				cache.add(new NPC(getScript(), n));
			}
			lastCacheTime = System.currentTimeMillis();
		}
		return cache;
	}
}
