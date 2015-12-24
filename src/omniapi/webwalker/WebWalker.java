package omniapi.webwalker;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.WebNode;
import omniapi.webwalker.web.loader.AbstractWebLoader;
import omniapi.webwalker.web.loader.impl.OptimizedWebLoader;
import omniapi.webwalker.web.pathfinder.Pathfinder;
import omniapi.webwalker.web.pathfinder.impl.AStarPathfinder;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.map.Position;

import omniapi.data.Entity;
import omniapi.data.NPC;
import omniapi.finders.FinderCondition;
import omniapi.finders.FinderDistance;

import java.util.ArrayList;

public class WebWalker extends OmniScriptEmulator<OmniScript> {
	private final AbstractWebLoader loader;
	private OmniScript context;

	private Web web;
	private Pathfinder pathfinder;
	private Walker walker;

	public WebWalker(OmniScript context) {
		this(context, Web.getInstance());
	}

	public WebWalker(OmniScript context, Web web) {
		super(context);
		this.context = context;
		this.web = web;
		this.loader = new OptimizedWebLoader(context, web);
		if (!web.isLoaded())
			loader.load();
		this.pathfinder = new AStarPathfinder(context, web);
		this.walker = new Walker(context);

	}

	public Entity getEntityAt(Position p) {
		for (Entity e : context.getEntities().getAll())
			if (e != null && e.exists())
				if (e.getPosition().equals(p))
					return e;
		return null;
	}

	public WebNode getClosest(String... names) {
		return getClosestNode(context.myPosition(), names);
	}

	// Emulate Valkyr's getNextEntity(Filter, Position) function
	public Entity getNextEntity(FinderCondition<Entity> condition, Position p) {
		FinderCondition<Entity> positionCondition = (e) -> (e.getPosition().equals(p) && condition.meetsCondition(e));
		return getEntityFinder().findThatMeetsCondition(positionCondition, FinderDistance.CLOSEST);
	}

	// Emulate Valkyr's waitInteractionDelay() function
	public void waitInteractionDelay() {
		try {
			sleep((Constants.TICK * 2) + rand(Constants.TICK / 4, Constants.TICK));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public boolean interactWith(WebNode location, String option, FinderCondition<Entity> filter) {
		if (location != null) {
			if (context.getBank().isOpen()) {
				context.getBank().close();
				debug("Closing bank.");
			}
			debug("Searching for entity at " + location);
			Entity targetEntity = getNextEntity(filter, location.construct());
			if (targetEntity != null && context.getMap().canReach(targetEntity.getPosition())) {
				// context.setPaintedEntity(targetEntity);
				if (context.myPlayer().getInteracting() == null) {
					try {
						if (targetEntity.interact(option)) {
							// AntibanUtils.increaseFatigue(context);
							debug("Successfully interacted with [" + targetEntity.getName() + "]");
							waitInteractionDelay();
							return true;
						} else {
							debug("Failed to interact with [" + targetEntity.getName() + "]");
							if (!targetEntity.isVisible()) {
								debug("Entity not visible, walking to " + targetEntity.getPosition());
								context.getLocalWalker().walk(targetEntity.getPosition());
							}
						}
					} catch (InterruptedException ex) {
						debug("Failed to interact!");
					}
				} else {
					/*
					 * if (context.getHoverNextEntity() &&
					 * AntibanUtils.BOOL_TRACKER.HOVER_NEXT.get()) {
					 * targetEntity.hover(); }
					 */
					debug("Not interacting; player is busy!");
					return false;
				}
			} else {
				WebNode dest = targetEntity != null ? getClosestNode(targetEntity.getPosition()) : location;
				debug("Could not locate entity, using WebWalker to reach [" + dest.toString() + "]");
				walk(dest);
			}

		} else {
			debug("Invalid target/destination! target=[" + location + "] destination=[" + location + "]");
		}
		return false;
	}

	public WebNode getClosestWithOption(String... options) {
		return getClosestWithOption(getWeb().getNodes(), context.myPosition(), options);
	}

	public WebNode getClosestWithOption(ArrayList<WebNode> set, Position p, String... options) {
		WebNode v = null;
		for (WebNode vtx : set)
			if (p != null && vtx != null) {
				if (v == null || vtx.distance(p) < v.distance(p)) {
					if (vtx.hasAllActions(options)) {
						v = vtx;
					}
				}
			}
		return v;
	}

	public WebNode getClosestNode(Position p, String... names) {
		WebNode v = null;
		for (WebNode vtx : web.getNodes())
			if (p != null && vtx != null) {
				if (v == null || vtx.distance(p) < v.distance(p)) {
					for (String name : names) {
						if (vtx.getName().equals(name)) {
							v = vtx;
						}
					}
				}
			}
		return v;
	}

	public WebNode getClosestNode(Position p) {
		return getClosestNode(web.getNodes(), p);
	}

	public WebNode getClosestNode(ArrayList<WebNode> set, Position p) {
		WebNode v = null;
		for (WebNode vtx : set)
			if (p != null && vtx != null && p.getZ() == vtx.getZ()) {
				if (v == null || vtx.distance(p) < v.distance(p)) {
					v = vtx;
				}
			}
		return v;
	}

	public boolean walk(WebNode v) {
		if (v != null) {
			debug("Finding path to [" + v.toString() + "]");
			ArrayList<WebNode> p1 = findPath(v);
			walkPath(p1);
		}
		return false;
	}

	public boolean walkPath(Position start, Position finish) {
		if (getMap().distance(finish) <= 4)
			return true;
		ArrayList<WebNode> path = findPath(getClosestNode(start), getClosestNode(finish));
		return walkPath(path);
	}

	public boolean walkPath(Position finish) {
		if (getMap().distance(finish) <= 4)
			return true;
		ArrayList<WebNode> path = findPath(getClosestNode(myPosition()), getClosestNode(finish));
		return walkPath(path);
	}

	public boolean walkPath(ArrayList<WebNode> path) {
		return path != null && !path.isEmpty() && walker.walkPath(path);
	}

	public ArrayList<WebNode> findPath(WebNode finish) {
		return pathfinder.findPath(getClosestNode(context.myPosition()), finish);
	}

	public ArrayList<WebNode> findPath(WebNode start, WebNode finish) {
		return pathfinder.findPath(start, finish);
	}

	public Walker getWalker() {
		return walker;
	}

	public Web getWeb() {
		return web;
	}

	public int realDistance(WebNode node, WebNode destination) {
		ArrayList<WebNode> path = pathfinder.findPath(node, destination);
		return path != null ? path.size() : Integer.MAX_VALUE;
	}

	public AbstractWebLoader getLoader() {
		return loader;
	}
}
