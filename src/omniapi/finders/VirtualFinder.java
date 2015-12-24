package omniapi.finders;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

/*
 * The VirtualFinder class is a generic that suits any finding operation. PhysicalFinder extends VirtualFinder, however
 * this only means that VirtualFinder should be used for things in a virtual (2D) space.
 * */
public abstract class VirtualFinder<T> extends OmniScriptEmulator<OmniScript> {

	protected T last;

	public VirtualFinder(OmniScript script) {
		super(script);
	}

	public abstract T find(FinderCondition<T> condition);

	public boolean canFind(FinderCondition<T> condition) {
		return (find(condition) != null);
	}

	public T getLastFound() {
		return last;
	}
}
