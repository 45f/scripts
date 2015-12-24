package omniapi.api.modules;

import java.util.Arrays;
import java.util.List;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.data.NPC;

/*
 * This module is used simply as a better way to interact with randoms.
 * This module will use the OmniAPI interact methods (vs default OSBot) to keep consistency with any interactions.
 * TODO: Genie lamps, rewards, choose which randoms to ignore etc
 * */
public class RemoveRandoms extends Module {

	private final List<String> RANDOM_LIST = Arrays.asList(new String[] { "Genie", "Rick Turpentine", "Drunken Dwarf",
			"Mysterious Old Man", "Dr Jekyll", "Security Guard", "Cap'n Hand", "Bee Keeper", "Drill Demon", "Evil Bob",
			"Freaky Forester", "Leo", "Miles", "Giles", "Niles", "Mime", "Molly", "Prison Pete", "Sandwich Lady",
			"Tilt", "Flippa", "Dunce", "Quiz Master", "Postie Pete" });
	private NPC dismissableNPC;

	public RemoveRandoms(OmniScript script) {
		super(script, "Random Remover", "v1.0");
	}

	@Override
	public boolean shouldActivate() {
		return (dismissableNPC = getNPCFinder().findClosestThatMeetsCondition(
				(npc) -> (RANDOM_LIST.contains(npc.getName()) && getMap().canReach(npc.getPosition())
						&& npc.getRaw().hasMentionedPhrase(myPlayer().getName(), 35000)))).exists();
	}

	@Override
	public int activate() throws InterruptedException {
		String tmpNPCName = dismissableNPC.getName();
		if (!dismissableNPC.interact("Dismiss"))
			return Constants.TICK;
		if (!sleepUntil(() -> (!dismissableNPC.exists())))
			return Constants.TICK;
		debug("Dismissed NPC " + tmpNPCName + " thanks to RemoveRandoms module!");
		return 100;
	}

}
