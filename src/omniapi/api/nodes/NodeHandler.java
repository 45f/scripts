package omniapi.api.nodes;

import java.util.ArrayList;
import java.util.List;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;

public class NodeHandler extends OmniScriptEmulator<OmniScript> {

	private List<AbstractNode> nodeList = new ArrayList<AbstractNode>();

	public NodeHandler(OmniScript script) {
		super(script);
	}

	public int execute() throws InterruptedException {
		for (AbstractNode abs : nodeList)
			if (abs.canExecute())
				return abs.execute();
		return Constants.TICK / 4;
	}

	public void addNode(FunctionalNode fn) {
		nodeList.add(new WrapperNode(getScript(), fn));
	}

	public void addNode(AbstractNode abs) {
		nodeList.add(abs);
	}
}
