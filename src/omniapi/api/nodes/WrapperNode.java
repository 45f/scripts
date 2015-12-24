package omniapi.api.nodes;

import omniapi.OmniScript;

public class WrapperNode extends AbstractNode {

	private FunctionalNode node;

	public WrapperNode(OmniScript script, FunctionalNode n) {
		super(script);
		node = n;
	}

	@Override
	public int execute() throws InterruptedException {
		return node.execute();
	}

	@Override
	public boolean canExecute() {
		return true;
	}

}
