package omniapi.api.nodes;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public abstract class AbstractNode extends OmniScriptEmulator<OmniScript> implements FunctionalNode {

	public AbstractNode(OmniScript script) {
		super(script);
	}

	public abstract boolean canExecute();

}
