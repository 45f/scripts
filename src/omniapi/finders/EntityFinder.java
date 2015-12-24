package omniapi.finders;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import omniapi.OmniScript;
import omniapi.data.DefaultEntity;
import omniapi.data.Entity;

public class EntityFinder extends PhysicalFinder<Entity> {

	public EntityFinder(OmniScript script) {
		super(script);
	}

	@Override
	public Entity find(String name, FinderDistance distance) {
		return findThatMeetsCondition(
				(target) -> (target != null && target.exists() && target.getName().equalsIgnoreCase(name)), distance);
	}

	@Override
	public Entity findThatMeetsCondition(String name, FinderCondition<Entity> condition, FinderDistance distance) {
		Stream<Entity> entities = getEntities().getAll().stream().filter(target -> (target != null && target.exists()
				&& target.getName().equalsIgnoreCase(name) && condition.meetsCondition(target)));
		Optional<Entity> entity = ((distance.equals(FinderDistance.FURTHEST)
				|| distance.equals(FinderDistance.FURTHEST_GAMETILES))
						? entities.max(getComparatorForDistance(distance))
						: entities.min(getComparatorForDistance(distance)));
		return (last = entity.orElse(new DefaultEntity(getScript())));
	}

	@Override
	public Entity findThatMeetsCondition(FinderCondition<Entity> condition, FinderDistance distance) {
		Stream<Entity> entities = getEntities().getAll().stream()
				.filter(target -> (target != null && target.exists() && condition.meetsCondition(target)));
		Optional<Entity> entity = ((distance.equals(FinderDistance.FURTHEST)
				|| distance.equals(FinderDistance.FURTHEST_GAMETILES))
						? entities.max(getComparatorForDistance(distance))
						: entities.min(getComparatorForDistance(distance)));
		return (last = entity.orElse(new DefaultEntity(getScript())));
	}

	@Override
	public Comparator<Entity> getComparatorForDistance(FinderDistance distance) {
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
