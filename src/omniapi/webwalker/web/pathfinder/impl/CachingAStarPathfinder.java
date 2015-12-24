package omniapi.webwalker.web.pathfinder.impl;

import java.util.ArrayList;

import omniapi.OmniScript;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.WebNode;

public class CachingAStarPathfinder extends AStarPathfinder {

	private ArrayList<ArrayList<WebNode>> paths = new ArrayList<>();

	public CachingAStarPathfinder(OmniScript context, Web web) {
		super(context, web);
	}

	@Override
	public ArrayList<WebNode> findPath(WebNode start, WebNode destination) {
		if (!paths.isEmpty()) {
			for (ArrayList<WebNode> path : paths) {
				if (path.contains(start) && path.contains(destination)) {
					return join(findPath(start, getClosest(path, start.construct())), path);
				}
			}
		}
		return addPath(super.findPath(start, destination));
	}

	private ArrayList<WebNode> addPath(ArrayList<WebNode> path) {
		if (!paths.contains(path))
			paths.add(path);
		return path;
	}

	private ArrayList<WebNode> join(ArrayList<WebNode> path1, ArrayList<WebNode> path2) {
		ArrayList<WebNode> path = new ArrayList<>();
		path.addAll(path1);
		path.addAll(path2);
		return path;
	}

}
