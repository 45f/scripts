package omniapi.webwalker.web.loader.impl;

import omniapi.OmniScript;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.WebNode;
import omniapi.webwalker.web.loader.AbstractWebLoader;
import omniapi.webwalker.web.loader.LoaderSettings;

import org.osbot.rs07.api.map.Position;

import java.util.ArrayList;

public class ShittyWebLoader extends AbstractWebLoader {

	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;
	public static final int NAME = 3;
	public static final int OPTIONS = 4;

	public ShittyWebLoader(OmniScript context, Web web) {
		super(context, web);
	}

	@Override
	public final Web load() {
		long startTime = System.currentTimeMillis();
		getNodes();
		log("Loaded " + getWeb().getNodes().size() + " nodes");
		log("Loaded " + getWeb().getObstacles().size() + " obstacles");
		log("Loaded web in " + (System.currentTimeMillis() - startTime) + "ms");
		return getWeb();
	}

	@Override
	public String parseObject(WebNode node) {
		StringBuilder sb = new StringBuilder();
		sb.append(node.getX() + LoaderSettings.SEPARATOR);
		sb.append(node.getY() + LoaderSettings.SEPARATOR);
		sb.append(node.getZ() + LoaderSettings.SEPARATOR);
		sb.append(node.getName() + LoaderSettings.SEPARATOR);
		for (String s : node.getActions())
			if (s != null && !s.equals("null"))
				sb.append(s + LoaderSettings.SEPARATOR);
		return sb.toString();
	}

	@Override
	public WebNode parseLine(String line) {
		WebNode a = null;
		WebNode b = null;
		String[] pair = line.split(LoaderSettings.EDGE_SEPARATOR);
		WebNode node = null;
		for (String edge : pair) {
			edge = edge.replace(LoaderSettings.EDGE_SEPARATOR, "");
			String[] nodeString = edge.split(LoaderSettings.SEPARATOR);
			WebNode parsedNode = parseNode(nodeString, getWeb());
			if (parsedNode != null) {
				if (a == null) {
					a = parsedNode;
				} else {
					b = parsedNode;
				}
			}
			if (a != null && b != null) {
				node = getWeb().addNew(a);
				if (node != null) {
					node.addEdge(getWeb().addNew(b));
				}
			}
		}
		return node;
	}

	private WebNode parseNode(String[] nodeString, Web web) {
		try {
			int x = 0, y = 0, z = 0;
			String name = "";
			ArrayList<String> options = new ArrayList<>();
			for (int i = 0; i < nodeString.length; i++) {
				if (i == X)
					x = Integer.valueOf(nodeString[i]);
				if (i == Y)
					y = Integer.valueOf(nodeString[i]);
				if (i == Z)
					z = Integer.valueOf(nodeString[i]);
				if (i == NAME)
					name = nodeString[i];
				if (name.equals("null"))
					return null;
				if (i >= OPTIONS)
					if (!nodeString[i].equals("null"))
						options.add(nodeString[i]);
			}
			WebNode n = new WebNode(new Position(x, y, z), name, options.toArray(new String[options.size()]));
			return n;
		} catch (NumberFormatException e) {
			return null;
		}
	}
}