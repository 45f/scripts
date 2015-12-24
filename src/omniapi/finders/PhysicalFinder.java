package omniapi.finders;

import java.util.Comparator;

import org.osbot.rs07.api.map.Position;

import omniapi.OmniScript;

/*
 * PhysicalFinder is an extension of VirtualFinder which has additional methods to find objects within a physical space.
 * This should only be used when you are finding things within a physical space; use VirtualFinder otherwise.
 * */
public abstract class PhysicalFinder<T> extends VirtualFinder<T> {

	public PhysicalFinder(OmniScript script) {
		super(script);
	}

	public T findClosest(String name) {
		return find(name, FinderDistance.CLOSEST);
	}

	public boolean canFind(String name) {
		return (findClosest(name) != null);
	}

	public T findClosest(String name, FinderCondition<T> condition) {
		return findThatMeetsCondition(name, condition, FinderDistance.CLOSEST);
	}

	@Override
	public T find(FinderCondition<T> condition) {
		return findClosestThatMeetsCondition(condition);
	}

	public T findClosestThatMeetsCondition(FinderCondition<T> condition) {
		return findThatMeetsCondition(condition, FinderDistance.CLOSEST);
	}

	public abstract T find(String name, FinderDistance distance);

	public abstract T findThatMeetsCondition(String name, FinderCondition<T> condition, FinderDistance distance);

	public abstract T findThatMeetsCondition(FinderCondition<T> condition, FinderDistance distance);

	public abstract Comparator<T> getComparatorForDistance(FinderDistance distance);

	/*
	 * This method estimates the distance between two points in a physical
	 * space, which is more effective than game tiles
	 * 
	 * @return The estimated distance (via Octile Heuristic) from A to B
	 */
	public int getDistance(Position a, Position b) {
		int horizontalCost = 10;
		int diagonalCost = 14;

		int horizontal = Math.abs(a.getX() - b.getX());
		int vertical = Math.abs(a.getY() - b.getY());

		if (horizontal == 0 || vertical == 0)
			return 0;

		return Math.max(horizontal, vertical) + (diagonalCost - horizontalCost) * Math.min(horizontal, vertical);
	}
}
