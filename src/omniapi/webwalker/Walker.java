package omniapi.webwalker;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;
import omniapi.webwalker.web.WebNode;

import org.osbot.rs07.api.map.Position;

import omniapi.data.DefaultEntity;
import omniapi.data.Entity;

import org.osbot.rs07.utility.ConditionalSleep;

import java.util.ArrayList;

public class Walker extends OmniScriptEmulator<OmniScript> {

	private OmniScript context;

	public Walker(OmniScript context) {
		super(context);
		this.context = context;
	}

	public boolean walkPath(ArrayList<WebNode> path) {
		return walkPath(path, 2);
	}

	private boolean walkPath(ArrayList<WebNode> path, int distance) {
		/*
		 * Widget checking here Just going to try/catch because I cba to fix any
		 * throwing TODO
		 */
		try {
			getWidgetFinder().findFromText("Enter Wilderness").interact();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Position myPos = context.myPosition();
		WebNode last = path.get(path.size() - 1);
		WebNode next = nextPosition(path, 10);
		if (context.isIdle() || (!next.equals(last) && context.getMap().distance(next.construct()) <= distance)) {
			// context.setPaintedPath(path);
			debug("Checking if it's worth teleporting to [" + last.toString() + "]");
			if (!canTeleport(path, last)) {
				debug("Not worth teleporting, attempting to walk to suitable node");
				if (next != null) {
					debug("Found suitable node [" + next.toString() + "]");
					WebNode obstacleNode = noObstacleBlocking(next, path);
					if (obstacleNode == null && context.getMap().canReach(next.construct())
							&& context.myPosition().getZ() == next.getZ()) {
						debug("Next node is reachable, walking via LocalWalker");
						context.getLocalWalker().walk(next.construct());
					} else {
						debug("Next node is unreachable, checking for obstacles.");

						if (obstacleNode != null) {
							debug("Processed obstacle: [" + obstacleNode + "]");
						} else {
							debug("No valid obstacle found!"); // Yay!
						}
					}
					debug("Found no walkable nodes!");
					return false;
				} else {
					debug("Unable to determine next node!");
					return false;
				}
			}

			// Done something, wait until doing it
			new ConditionalSleep(600) {
				@Override
				public boolean condition() {
					return !context.isIdle();
				}
			}.sleep();

		}
		return myPos.distance(last.construct()) <= distance;
	}

	private WebNode nextPosition(ArrayList<WebNode> path, int skipDist) {
		// WebNode last = path.get(path.size() - 1);
		WebNode next = null;
		for (WebNode node : path) {
			if (context.getMap().canReach(node.construct())) {
				if (next == null || path.indexOf(next) < path.indexOf(node)) {
					next = node;
				}
			}
		}
		return next;
	}

	private boolean canTeleport(ArrayList<WebNode> path, WebNode destination) {
		// TODO: need teleport manager valk u nerd
		/*
		 * if (context.getTeleportManager().isEnabled()) { int myScore =
		 * path.size(); int score = Integer.MAX_VALUE; Teleport teleport = null;
		 * if (context.getTeleportManager().isEnabled()) { //debug(
		 * "Distance to destination = " + myDistance); for (Teleport tele :
		 * context.getTeleportManager().getLoadout().values()) { if
		 * (tele.hasItem(context)) { if
		 * (!tele.getDestination().isOnMiniMap(context.getBot())) { Position
		 * teleportDestination = tele.getDestination(); int teleScore =
		 * context.getWebWalker().realDistance(context.getWebWalker().
		 * getClosestNode(teleportDestination), destination); if (teleScore <
		 * myScore && teleScore < score) { //debug("Teleport [" + tele.getName()
		 * + "] score = " + teleScore); score = teleScore; teleport = tele; } }
		 * } } }
		 * 
		 * if (teleport != null) { debug("Teleporting to [" +
		 * destination.toString() + "] using " + teleport.getName()); if
		 * (context.getTeleportManager().perform(teleport))
		 * AntibanUtils.increaseFatigue(context); }
		 * 
		 * return teleport != null; }
		 */
		return false;
	}

	private WebNode noObstacleBlocking(WebNode next, ArrayList<WebNode> path) {
		Entity obstacleEntity = null;
		debug("Searching for obstacle nodes between [" + next.toString() + "]");
		WebNode obstacleNode = getNextObstacleIn(path, next);
		if (obstacleNode != null) {
			debug("Searching for obstacle entity for [" + obstacleNode.toString() + "]");
			obstacleEntity = getObstacleEntity(path, obstacleNode, next);
		}
		if (obstacleNode == null) {
			debug("No obstacle nodes found in path!");
			return null;
		}
		// Handle interaction
		if (!(obstacleEntity instanceof DefaultEntity) && !obstacleEntity.getName().equalsIgnoreCase("null")) {
			debug("Found obstacle [" + obstacleEntity.getName() + "], searching for action");
			// Get right-click option
			for (String action : obstacleEntity.getActions()) {
				if (action != null && !action.equals("null")) {
					debug("Found action [" + action + "] for [" + obstacleEntity.getName() + "], parsing " + "action");
					// TODO: fix
					if (getMap().distance(obstacleEntity.getPosition()) > 7)
						walkPath(getWebWalker().findPath(obstacleNode.getPrevious()));
					if (processObstacle(action, obstacleEntity, next))
						return obstacleNode;
				}
			}
		} else {
			warn("No valid obstacle entities found!");
			return null;
		}
		return null;
	}

	private Entity getObstacleEntity(ArrayList<WebNode> path, WebNode obstacleNode, WebNode next) {
		Entity obstacleEntity = null;
		for (Entity entity : context.getEntities().getAll()) {
			if (verify(path, next, obstacleEntity, entity)) {
				if (obstacleNode.getName().equalsIgnoreCase(entity.getName())
						&& context.getMap().canReach(entity.getPosition())) {
					obstacleEntity = entity;
				}
			}
		}
		if (obstacleEntity == null)
			obstacleEntity = context.getEntityFinder().findClosest(obstacleNode.getName());
		return obstacleEntity;
	}

	private boolean verify(ArrayList<WebNode> path, WebNode next, Entity currentEntity, Entity entity) {
		int currentEntityScore = obstacleScore(currentEntity, next);
		int entityScore = obstacleScore(entity, next);
		if (entityScore > 0 && (currentEntity == null || entityScore < currentEntityScore)) {
			WebNode currentEntityNode = currentEntity != null
					? context.getWebWalker().getClosestNode(path, currentEntity.getPosition()) : null;
			WebNode entityNode = context.getWebWalker().getClosestNode(path, entity.getPosition());
			return currentEntityNode == null || path.indexOf(currentEntityNode) < path.indexOf(entityNode);
		}
		return false;
	}

	private int obstacleScore(Entity entity, WebNode next) {
		if (entity == null || next == null)
			return -1;
		return next.distance(context.myPosition()) - next.distance(entity.getPosition());
	}

	private boolean processObstacle(String action, Entity obstacleEntity, WebNode next) {
		log(action + "name " + obstacleEntity.getName());
		if (action != null) {
			// Process stairs
			if (obstacleEntity.getName().equals("Staircase") || obstacleEntity.getName().equals("Stairs")
					|| obstacleEntity.getName().equals("Ladder") || obstacleEntity.getName().equals("Manhole")) {
				if (next.getZ() != context.myPosition().getZ()) {
					if (next.getZ() > context.myPosition().getZ()) {
						action = "Climb-up";
					} else if (next.getZ() < context.myPosition().getZ()) {
						action = "Climb-down";
					}
				}
			}
			// Avoid invalid obstacles
			if (action.equals("Close")) {
				debug("Invalid obstacle [" + obstacleEntity.getPosition().toString() + "]");
				return false;
			}
			// Paint obstacle
			// context.setPaintedEntity(obstacleEntity);
			if (context.isIdle() && obstacleEntity.getPosition().isOnMiniMap(context.getBot())) {
				debug("Interacting with obstacle obstructing [" + next.toString() + "]: ["
						+ obstacleEntity.getPosition().toString() + "] using action \"" + action + "\"");

				if (!obstacleEntity.isVisible())
					if (context.getCamera().toEntity(obstacleEntity.getRaw()))
						context.getLocalWalker().walk(obstacleEntity.getPosition());
				// Interact with obstacle
				try {
					if (obstacleEntity.interact(action)) {
						// AntibanUtils.increaseFatigue(context);
						debug("Interaction with obstacle [" + obstacleEntity.getPosition().toString()
								+ "] successful!");
						return true;
					} else {
						warn("Interaction with obstacle [" + obstacleEntity.getPosition().toString() + "] failed!");
						getCamera().toTop();
					}
				} catch (InterruptedException ex) {
					warn("Could not interact!");
				}
			}
		}
		return false;
	}

	public WebNode getNextObstacleIn(ArrayList<WebNode> path, WebNode next) {
		/*
		 * for (int i = path.indexOf(next) - 1; i >= 0; i--) { WebNode node =
		 * path.get(i); debug(i); if (node.getY() == context.myPosition().getY()
		 * && context.getWebWalker().getWeb().getObstacles().contains(node)) {
		 * return node; } }
		 */
		WebNode obstacleNode = null;
		WebNode lastReachable = null;
		for (WebNode node : path)
			if (context.getMap().canReach(node.construct()))
				lastReachable = node;
		if (lastReachable == null) {
			lastReachable = path.get(0);
		}
		if (context.getWebWalker().getWeb().getObstacles().contains(lastReachable)) {
			obstacleNode = lastReachable;
		} else {
			for (int i = path.indexOf(lastReachable); i < path.size(); i++) {
				WebNode pathNode = path.get(i);
				if (context.getWebWalker().getWeb().getObstacles().contains(pathNode)) {
					obstacleNode = pathNode;
					break;
				}
			}
		}
		return obstacleNode;

	}

}