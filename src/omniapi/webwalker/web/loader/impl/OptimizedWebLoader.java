package omniapi.webwalker.web.loader.impl;

import omniapi.OmniScript;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.WebNode;
import omniapi.webwalker.web.loader.AbstractWebLoader;
import omniapi.webwalker.web.loader.LoaderSettings;

import org.osbot.rs07.api.map.Position;

import java.util.ArrayList;

public class OptimizedWebLoader extends AbstractWebLoader {

	public static final int ID = 0;
	public static final int X = 1;
	public static final int Y = 2;
	public static final int Z = 3;
	public static final int NAME = 4;
	public static final int OPTIONS = 5;

	public OptimizedWebLoader(OmniScript context, Web web) {
		super(context, web);
	}

	@Override
	public WebNode parseLine(String line) {
		String[] nodeArray = line.split(LoaderSettings.EDGE_SEPARATOR);
		WebNode node = parseNodeString(nodeArray[0]);
		for (int i = 1; i < nodeArray.length; i++) {
			if (node != null) {
				node.addId(Integer.parseInt(nodeArray[i]));
			}
		}
		return node;
	}

	@Override
	public String parseObject(WebNode node) {
		StringBuilder sb = new StringBuilder();
		sb.append(node.getId()).append(LoaderSettings.SEPARATOR);
		sb.append(node.getX()).append(LoaderSettings.SEPARATOR);
		sb.append(node.getY()).append(LoaderSettings.SEPARATOR);
		sb.append(node.getZ()).append(LoaderSettings.SEPARATOR);
		sb.append(node.getName()).append(LoaderSettings.SEPARATOR);
		for (String s : node.getActions())
			if (s != null && !s.equals("null"))
				sb.append(s).append(LoaderSettings.SEPARATOR);
		ArrayList<WebNode> edges = node.getEdges();
		for (WebNode edge : edges) {
			sb.append(LoaderSettings.EDGE_SEPARATOR).append(edge.getId());
		}
		sb.append(LoaderSettings.LINE_BREAK);
		return sb.toString();
	}

	@Override
	public final Web load() {
		long startTime = System.currentTimeMillis();
		ArrayList<WebNode> nodes = getNodes();
		// Hacky shit to link nodes
		for (WebNode node : nodes) {
			for (int id : node.getIds()) {
				WebNode edge = getWeb().getNodeMap().get(id);
				if (edge != null) {
					node.addEdge(edge);
					edge.addEdge(node);
				}
			}
		}
		getWeb().setLoaded(true);
		// log("Loaded " + getWeb().getNodes().size() + " nodes");
		// log("Loaded " + getWeb().getObstacles().size() + " obstacles");
		// log("Loaded web in " + (System.currentTimeMillis() - startTime) +
		// "ms");
		return getWeb();
	}

	private WebNode parseNodeString(String nodeString) {
		String[] nodeArray = nodeString.split(LoaderSettings.SEPARATOR);
		try {
			int id = 0, x = 0, y = 0, z = 0;
			String name = "";
			ArrayList<String> options = new ArrayList<>();
			for (int i = 0; i < nodeArray.length; i++) {
				if (i == ID)
					id = Integer.valueOf(nodeArray[i]);
				if (i == X)
					x = Integer.valueOf(nodeArray[i]);
				if (i == Y)
					y = Integer.valueOf(nodeArray[i]);
				if (i == Z)
					z = Integer.valueOf(nodeArray[i]);
				if (i == NAME)
					name = nodeArray[i];
				if (i >= OPTIONS)
					options.add(nodeArray[i]);
			}
			return getWeb()
					.addNew(new WebNode(id, new Position(x, y, z), name, options.toArray(new String[options.size()])));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
