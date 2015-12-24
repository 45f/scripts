package omniapi.finders;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import omniapi.OmniScript;
import omniapi.data.DefaultNPC;
import omniapi.data.NPC;

public class NPCFinder extends PhysicalFinder<NPC> {

	public NPCFinder(OmniScript script) {
		super(script);
	}

	@Override
	public NPC find(String name, FinderDistance distance) {
		return findThatMeetsCondition(
				(target) -> (target != null && target.exists() && target.getName().equalsIgnoreCase(name)), distance);
	}

	@Override
	public NPC findThatMeetsCondition(String name, FinderCondition<NPC> condition, FinderDistance distance) {
		Stream<NPC> npcs = getNPCs().getAll().stream().filter(target -> (target != null && target.exists()
				&& target.getName().equalsIgnoreCase(name) && condition.meetsCondition(target)));
		Optional<NPC> npc = ((distance.equals(FinderDistance.FURTHEST)
				|| distance.equals(FinderDistance.FURTHEST_GAMETILES)) ? npcs.max(getComparatorForDistance(distance))
						: npcs.min(getComparatorForDistance(distance)));

		return (last = npc.orElse(new DefaultNPC(getScript())));
	}

	@Override
	public NPC findThatMeetsCondition(FinderCondition<NPC> condition, FinderDistance distance) {
		Stream<NPC> npcs = getNPCs().getAll().stream()
				.filter(target -> (target != null && target.exists() && condition.meetsCondition(target)));
		Optional<NPC> npc = ((distance.equals(FinderDistance.FURTHEST)
				|| distance.equals(FinderDistance.FURTHEST_GAMETILES)) ? npcs.max(getComparatorForDistance(distance))
						: npcs.min(getComparatorForDistance(distance)));

		return (last = npc.orElse(new DefaultNPC(getScript())));
	}

	@Override
	public Comparator<NPC> getComparatorForDistance(FinderDistance distance) {
		switch (distance) { // We handle FURTHEST/FURTHEST_GAMETILES in our find
							// conditions, so we don't need to worry about what
							// the comparators are
		default:
		case CLOSEST:
		case FURTHEST:
			return ((one, two) -> Integer.compare(getDistance(myPosition(), one.getPosition()),
					getDistance(myPosition(), two.getPosition())));
		case CLOSEST_GAMETILES:
		case FURTHEST_GAMETILES:
			return ((one, two) -> Integer.compare(getMap().distance(one.getPosition()),
					getMap().distance(two.getPosition())));
		}
	}

}
