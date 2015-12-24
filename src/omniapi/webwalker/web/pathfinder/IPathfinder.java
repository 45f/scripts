package omniapi.webwalker.web.pathfinder;

import java.util.ArrayList;

import omniapi.webwalker.web.WebNode;

public interface IPathfinder {

	ArrayList<WebNode> findPath(WebNode start, WebNode destination);
}
