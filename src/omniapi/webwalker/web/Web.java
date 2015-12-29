package omniapi.webwalker.web;

import org.osbot.rs07.api.Map;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Web { // TODO: extend OmniScriptEmulator<OmniScript>

	public static final int DISTANCE = 4;
	private static Web instance;

	private final java.util.Map<Integer, WebNode> nodeMap = new HashMap<>();
	private final ArrayList<WebNode> nodes = new ArrayList<>();
	private final ArrayList<WebNode> banks = new ArrayList<>();
	private final ArrayList<WebNode> obstacles = new ArrayList<>();
	private final ArrayList<WebNode> resources = new ArrayList<>();
	private final ArrayList<WebNode> npcs = new ArrayList<>();
	private boolean loaded;

	public final java.util.Map<Integer, WebNode> getNodeMap() {
		return nodeMap;
	}

	public final ArrayList<WebNode> getNodes() {
		return nodes;
	}

	public final ArrayList<WebNode> getBanks() {
		return banks;
	}

	public final ArrayList<WebNode> getObstacles() {
		return obstacles;
	}

	public final ArrayList<WebNode> getResources() {
		return resources;
	}

	public final ArrayList<WebNode> getNpcs() {
		return npcs;
	}

	public WebNode addNode(WebNode node) {
		if (node == null)
			return null;
		int topId = 0;
		for (int mapId : nodeMap.keySet()) {
			if (topId <= mapId)
				topId = mapId + 1;
			WebNode mapNode = nodeMap.get(mapId);
			if (mapNode.equals(node)) {
				return mapNode;
			}
		}
		if (node.getId() != 0)
			topId = node.getId();
		node.setId(topId);
		nodeMap.put(topId, node);
		nodes.add(node);
		return node;
	}

	public WebNode add(Position p, Entity e, Map map) {
		if (e != null) {
			return addNew(new WebNode(p, e.getName(), e.getActions()));
		}
		if (!isPositionBlocked(p, map)) {
			return addNew(new WebNode(nodes.size(), p, ""));
		}
		return null;
	}

	public boolean isPositionBlocked(Position p, Map map) {
		int[][] flags = map.getRegion().getClippingPlanes()[map.myPosition().getZ()].getTileFlags();
		int x = p.getX() - map.getBaseX();
		int y = p.getY() - map.getBaseY();
		if (x < flags.length && y < flags.length && x >= 0 && y >= 0)
			return (flags[x][y] & 0x1280100) != 0;
		return true;
	}

	public synchronized WebNode addNew(WebNode node) {
		ArrayList<WebNode> targetSet = nodes;
		if (node.getActions().size() > 0) {
			if (node.hasAction("Mine", "Chop down", "Chop", "Net", "Lure", "Bait", "Cage", "Harpoon", "Pick", "Use",
					"Search for traps", "Steal", "Pick Lock") || node.getName().equalsIgnoreCase("Anvil")
					|| node.getName().equalsIgnoreCase("Allotment") || node.getName().equalsIgnoreCase("Tree patch")
					|| node.getName().equalsIgnoreCase("Mysterious ruins") || node.getName().equalsIgnoreCase("Stove")
					|| node.getName().equalsIgnoreCase("Range"))
				targetSet = resources;
			if (node.hasAction("Banking", "Deposit"))
				targetSet = banks;
			if (node.hasAction("Open", "Close", "Use", "Enter", "Climb", "Climb-up", "Climb-down", "Climb-into",
					"Cross", "Jump-over", "Slash", "Pass-through"))
				targetSet = obstacles;
			if (node.hasAction("Talk-to", "AttackMonk"))
				targetSet = npcs;
		}
		node = addNode(node);
		if (node != null && targetSet != null && !targetSet.contains(node))
			targetSet.add(node);
		return node;

	}

	public static WebNode closestNode(ArrayList<WebNode> set, Position p) {
		WebNode v = null;
		for (WebNode vtx : set)
			if (p != null && vtx != null && p.getZ() == vtx.getZ())
				if (v == null || vtx.distance(p) < v.distance(p)) {
					v = vtx;
				}
		return v;
	}

	public static Web getInstance() {
		return instance == null ? (instance = new Web()) : instance;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
}
