package omniapi.api;

import java.util.Random;

import org.osbot.rs07.script.Script;

import omniapi.OmniScript;
import omniapi.api.sleep.SleepCondition;
import omniapi.api.sleep.Sleeper;
import omniapi.data.Entities;
import omniapi.data.NPCs;
import omniapi.data.Widgets;
import omniapi.debug.LogLevel;
import omniapi.finders.EntityFinder;
import omniapi.finders.NPCFinder;
import omniapi.finders.WidgetFinder;
import omniapi.interfaces.RSBank;
import omniapi.webwalker.WebWalker;
import omniapi.webwalker.web.pathfinder.impl.AStarPathfinder;

public class OmniScriptEmulator<S extends OmniScript> extends ScriptEmulator<S> {

	public OmniScriptEmulator(S script) {
		super(script);
	}

	@Override
	public S getScript() {
		return script;
	}

	public WebWalker getWebWalker() {
		return script.getWebWalker();
	}

	public EntityFinder getEntityFinder() {
		return script.getEntityFinder();
	}

	public NPCFinder getNPCFinder() {
		return script.getNPCFinder();
	}

	public WidgetFinder getWidgetFinder() {
		return script.getWidgetFinder();
	}

	public Entities getEntities() {
		return script.getEntities();
	}

	public NPCs getNPCs() {
		return script.getNPCs();
	}

	public Script getRawScript() {
		return script.getRaw();
	}

	public Widgets getRSWidgets() {
		return script.getRSWidgets();
	}

	public RSBank getRSBank() {
		return script.getRSBank();
	}

	public LogLevel getLogLevel() {
		return script.getLogLevel();
	}

	public Sleeper getSleeper() {
		return script.getSleeper();
	}

	/* Sleeping */
	public boolean sleepUntil(SleepCondition sc) {
		return getSleeper().sleep(sc);
	}

	public boolean sleepUntil(SleepCondition sc, int maxTime) {
		return getSleeper().sleep(sc, maxTime);
	}

	public void log(Object o) {
		getScript().log(o);
	}

	public void debug(Object o) {
		getScript().debug(o);
	}

	public void warn(Object o) {
		getScript().warn(o);
	}

	public void sleep(int time) throws InterruptedException {
		debug("Sleeping for " + time);
		script.sleep(time);
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

	public int positiveSkewedRandom(int min, int max) {
		return skewedRandom((double) min, (double) max, 2.55, -1.68);
	}

	public int negativeSkewedRandom(int min, int max) {
		return skewedRandom((double) min, (double) max, 2.55, 1.68);
	}

	// http://stackoverflow.com/a/13548135
	public int skewedRandom(double min, double max, double skew, double bias) {
		Random r = new Random(System.currentTimeMillis());
		double range = max - min;
		double mid = min + range / 2.0;
		double unitGaussian = r.nextGaussian();
		double biasFactor = Math.exp(bias);
		double retval = mid + (range * (biasFactor / (biasFactor + Math.exp(-unitGaussian / skew)) - 0.5));
		return (int) retval;
	}
}
