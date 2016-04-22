package zeah.core.piscarilius;

import omniapi.OmniScript;
import omniapi.api.nodes.AbstractNode;

/**
 * Created  on 4/22/2016.
 * This file is part of zeah.core.piscarilius.
 */
public class DeliverFishTask extends AbstractNode {

    public DeliverFishTask(OmniScript script) {
        super(script);
    }

    @Override
    public boolean canExecute() {
        return false;
    }

    @Override
    public int execute() throws InterruptedException {
        return 0;
    }
}
