package omniapi;

import java.util.Random;

import omniapi.api.nodes.NodeHandler;
import omniapi.api.sleep.SleepCondition;
import omniapi.api.sleep.Sleeper;
import omniapi.data.Entities;
import omniapi.data.NPCs;
import omniapi.data.Widgets;
import omniapi.debug.DebugLogger;
import omniapi.debug.LogLevel;
import omniapi.finders.BankFinder;
import omniapi.finders.EntityFinder;
import omniapi.finders.InventoryFinder;
import omniapi.finders.NPCFinder;
import omniapi.finders.WidgetFinder;
import omniapi.interfaces.RSBank;
import omniapi.interfaces.RSGrandExchange;
import omniapi.webwalker.WebWalker;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.pathfinder.impl.AStarPathfinder;

import org.osbot.rs07.script.Script;

public abstract class OmniScript extends Script {

	private EntityFinder entityFinder = new EntityFinder(this);
	private NPCFinder npcFinder = new NPCFinder(this);
	private WidgetFinder widgetFinder = new WidgetFinder(this);
	private BankFinder bankFinder = new BankFinder(this);
	private InventoryFinder inventoryFinder = new InventoryFinder(this);

	private Entities entities = new Entities(this);
	private NPCs npcs = new NPCs(this);
	private Widgets widgets = new Widgets(this);
	private LogLevel debug = LogLevel.LOG;
	private DebugLogger logger;
	private Sleeper sleeper = new Sleeper(this, 25);
	private NodeHandler nodeHandler = new NodeHandler(this);

	/* Interfaces */
	private RSBank rsBank = new RSBank(this);
	private RSGrandExchange rsGrandExchange = new RSGrandExchange(this);

	// Valks webwalker :D
	private WebWalker webWalker;

	public OmniScript() {
		logger = new DebugLogger(this);
		// log("Thanks for using OmniAPI!");
		webWalker = new WebWalker(this);
	}

	/* Getters */
	public boolean isIdle() {
		return !myPlayer().isMoving() && !myPlayer().isAnimating() && !getCombat().isFighting()
				&& !getDialogues().inDialogue();
	}

	public WebWalker getWebWalker() {
		return webWalker;
	}

	public EntityFinder getEntityFinder() {
		return entityFinder;
	}

	public NPCFinder getNPCFinder() {
		return npcFinder;
	}

	public WidgetFinder getWidgetFinder() {
		return widgetFinder;
	}

	public BankFinder getBankFinder() {
		return bankFinder;
	}

	public InventoryFinder getInventoryFinder() {
		return inventoryFinder;
	}

	public Entities getEntities() {
		return entities;
	}

	public NPCs getNPCs() {
		return npcs;
	}

	public Widgets getRSWidgets() {
		return widgets;
	}

	public Script getRaw() {
		return (Script) this;
	}

	public void setLogLevel(LogLevel level) {
		debug = level;
	}

	public LogLevel getLogLevel() {
		return debug;
	}

	public DebugLogger getDebugLogger() {
		return logger;
	}

	public RSBank getRSBank() {
		return rsBank;
	}

	public RSGrandExchange getRSGrandExchange() {
		return rsGrandExchange;
	}

	public Sleeper getSleeper() {
		return sleeper;
	}

	/* Logging */

	public void log(Object item) {
		logger.log(item.toString());
	}

	public void warn(Object item) {
		logger.warn(item.toString());
	}

	public void debug(Object item) {
		logger.debug(item.toString());
	}

	/* Sleeping */
	public boolean sleepUntil(SleepCondition sc) {
		return sleeper.sleep(sc);
	}

	public boolean sleepUntil(SleepCondition sc, int maxTime) {
		return sleeper.sleep(sc, maxTime);
	}

	public NodeHandler getNodeHandler() {
		return nodeHandler;
	}

	/* Random functions */
	public int getRandom(int min, int max) {
		int range = (max - min) + 1;
		return (new Random(System.currentTimeMillis())).nextInt(range) + min;
	}

	public int rand(int min, int max) { // Edited slightly; in a normal
										// distribution, mean = mode = median =
										// midpoint of graph
		int n;
		int mean = (min + max) / 2;
		int std = (max - mean) / 3;
		Random r = new Random();
		do {
			double val = r.nextGaussian() * std + mean;
			n = (int) Math.round(val);
		} while (n < min || n > max);
		return n;
	}
}
