package omniapi.finders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import omniapi.OmniScript;
import omniapi.data.DefaultItem;
import omniapi.data.Item;

public class InventoryFinder extends VirtualFinder<Item> {

	public InventoryFinder(OmniScript script) {
		super(script);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Item find(FinderCondition<Item> condition) {
		return getInventoryStream().filter((item) -> (item.exists() && condition.meetsCondition(item))).findFirst()
				.orElse(new DefaultItem(getScript()));
	}

	public Item find(String name) {
		return find((item) -> (item.getName().equalsIgnoreCase(name)));
	}

	public Item find(String name, FinderCondition<Item> condition) {
		return find((item) -> (item.getName().equalsIgnoreCase(name) && condition.meetsCondition(item)));
	}

	public List<Item> findAll(FinderCondition<Item> condition) {
		return getInventoryStream().filter((item) -> (item.exists() && condition.meetsCondition(item)))
				.collect(Collectors.toList());
	}

	/* Private methods */
	private Stream<Item> getInventoryStream() {
		List<Item> invItems = new ArrayList<Item>();
		for (org.osbot.rs07.api.model.Item item : getInventory().getItems())
			invItems.add(new Item(getScript(), item));

		return invItems.stream();
	}
}
