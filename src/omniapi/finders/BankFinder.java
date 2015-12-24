package omniapi.finders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import omniapi.OmniScript;
import omniapi.data.DefaultItem;
import omniapi.data.Item;

public class BankFinder extends VirtualFinder<Item> {

	public BankFinder(OmniScript script) {
		super(script);
	}

	@Override
	public Item find(FinderCondition<Item> condition) {
		return (last = getBankStream().filter((item) -> (item.exists() && condition.meetsCondition(item))).findFirst()
				.orElse(new DefaultItem(getScript())));
	}

	public Item find(String name) {
		return find((item) -> (item.getName().equalsIgnoreCase(name)));
	}

	public Item find(String name, FinderCondition<Item> condition) {
		return (last = getBankStream().filter(
				(item) -> (item.exists() && item.getName().equalsIgnoreCase(name) && condition.meetsCondition(item)))
				.findFirst().orElse(new DefaultItem(getScript())));
	}

	public boolean canFind(String name) {
		return !(find(name) instanceof DefaultItem);
	}

	public boolean canFind(String name, FinderCondition<Item> condition) {
		return !(find(name, condition) instanceof DefaultItem);
	}

	public List<Item> findAll(FinderCondition<Item> condition) {
		return getBankStream().filter((item) -> (item.exists() && condition.meetsCondition(item)))
				.collect(Collectors.toList());
	}

	/* Private methods */
	private Stream<Item> getBankStream() {
		List<Item> bankItems = new ArrayList<Item>();
		for (org.osbot.rs07.api.model.Item item : getBank().getItems())
			bankItems.add(new Item(getScript(), item));

		return bankItems.stream();
	}
}
