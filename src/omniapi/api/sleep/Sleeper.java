package omniapi.api.sleep;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public class Sleeper extends OmniScriptEmulator<OmniScript> {

	private int step;

	public Sleeper(OmniScript script, int stepTime) {
		super(script);
		step = stepTime;
	}

	public boolean sleep(SleepCondition sc) {
		return sleep(sc, rand(5000, 5500));
	}

	public boolean sleep(SleepCondition sc, int maxTime) {
		try {
			long timeNow = System.currentTimeMillis();
			while (!sc.meetsCondition()) {
				Thread.sleep(step);

				if (System.currentTimeMillis() < (timeNow + maxTime))
					continue;
				debug("Sleep finished, ran out of time");
				return false;
			}
			debug("Sleep finished regularly");
			return true;
		} catch (InterruptedException ie) {
			return false;
		}
	}
}
