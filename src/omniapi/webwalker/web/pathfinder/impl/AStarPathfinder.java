package omniapi.webwalker.web.pathfinder.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

import omniapi.OmniScript;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.WebNode;
import omniapi.webwalker.web.pathfinder.Pathfinder;

public class AStarPathfinder extends Pathfinder {
	public AStarPathfinder(OmniScript context, Web web) {
		super(context, web);
	}

	@Override
	public ArrayList<WebNode> findPath(WebNode start, WebNode destination) {
		if (start == null || destination == null || start.equals(destination))
			return null;
		ArrayDeque<WebNode> open = new ArrayDeque<>();
		ArrayDeque<WebNode> closed = new ArrayDeque<>();
		start.setFScore(calcManhattanDistance(start, destination));
		start.setPrevious(null);
		open.add(start);
		while (!open.isEmpty()) {
			WebNode u = open.getFirst();
			open.removeFirst();
			closed.add(u);
			if (u.equals(destination)) {
				ArrayList<WebNode> path = new ArrayList<>();
				while (u != null) {
					path.add(u);
					u = u.getPrevious();
				}
				Collections.reverse(path);
				return path;
			}
			for (WebNode neighbor : getAdjacent(u)) {
				int fScore = getFScore(start, neighbor, u);
				if (closed.contains(neighbor))
					continue;
				if (!open.contains(neighbor) || fScore < getGScore(start, neighbor)) {
					int neighbourFScore = getFScore(start, neighbor, destination);
					neighbor.setFScore(neighbourFScore);
					neighbor.setPrevious(u);
					if (!open.contains(neighbor)) {
						if (!open.isEmpty() && neighbourFScore < open.getFirst().getFScore())
							open.addFirst(neighbor);
						else
							open.addLast(neighbor);
					}
				}
			}
		}
		log("Could not find path to [" + destination.toString() + "]!");
		return null;
	}

	private int getFScore(WebNode start, WebNode neighbor, WebNode u) {
		return (int) (getGScore(start, u) + calcManhattanDistance(neighbor, u));
	}
}
