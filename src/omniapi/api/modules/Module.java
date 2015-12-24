package omniapi.api.modules;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

/*
 * Modules are similar to OSBot randoms in the idea that they are extended for various functionality that should be handled
 * in the API level (eg dismissing randoms). The reason these are here is to allow more consistency vs the OSBot API.
 * */
public abstract class Module extends OmniScriptEmulator<OmniScript> {

	protected String name, version;
	protected boolean enabled = false;

	public Module(OmniScript script, String n, String v) {
		super(script);
		name = n;
		version = v;
	}

	public abstract boolean shouldActivate();

	public abstract int activate() throws InterruptedException;

	public void setEnabled(boolean b) {
		enabled = b;
	}
}
