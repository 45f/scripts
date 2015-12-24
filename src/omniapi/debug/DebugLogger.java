package omniapi.debug;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public class DebugLogger extends OmniScriptEmulator<OmniScript> {

	public DebugLogger(OmniScript script) {
		super(script);
	}

	public void warn(String item) {
		if (getLogLevel().getLevel() > 1)
			getScript().getRaw().log("[WARN] " + item);
	}

	public void log(String item) {
		getScript().getRaw().log("[LOG] " + item);
	}

	public void debug(String item) {
		if (getLogLevel().getLevel() > 0)
			getScript().getRaw().log("[DEBUG] " + item);
	}
}
