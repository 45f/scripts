package omniapi.webwalker.web.pathfinder;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.WebNode;

import org.osbot.rs07.api.map.Position;

import java.util.ArrayList;

public abstract class Pathfinder extends OmniScriptEmulator<OmniScript> implements IPathfinder {

	private OmniScript context;
	private Web web;

	public Pathfinder(OmniScript context, Web web) {
		super(context);
		this.context = context;
		this.web = web;
	}

	protected ArrayList<WebNode> findPath(WebNode destination) {
		return findPath(getClosest(web.getNodes(), context.myPosition()));
	}

	protected static WebNode getClosest(ArrayList<WebNode> set, Position p) {
		WebNode v = null;
		for (WebNode vtx : set)
			if (p != null && vtx != null && vtx.getZ() == p.getZ())
				if (v == null || vtx.distance(p) < v.distance(p)) {
					v = vtx;
				}
		return v;
	}

	protected static ArrayList<WebNode> getAdjacent(WebNode node) {
		return node.getEdges();
	}

	protected static double getGScore(WebNode start, WebNode pos) {
		if (pos != null && start != null) {
			double dx = start.getX() - pos.getX();
			double dy = start.getY() - pos.getY();
			return Math.sqrt((dx * dx) + (dy * dy));
		}
		return Double.MAX_VALUE;
	}

	protected static int calcManhattanDistance(WebNode current, WebNode target) {
		int dx = Math.abs(target.getX() - current.getX());
		int dy = Math.abs(target.getY() - current.getY());
		return dx + dy;
	}

	protected Web getWeb() {
		return web;
	}

}
