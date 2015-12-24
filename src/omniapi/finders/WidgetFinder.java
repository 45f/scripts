package omniapi.finders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import omniapi.OmniScript;
import omniapi.data.DefaultWidget;
import omniapi.data.Widget;

public class WidgetFinder extends VirtualFinder<Widget> {

	public WidgetFinder(OmniScript script) {
		super(script);
	}

	@Override
	public Widget find(FinderCondition<Widget> condition) {
		return (last = getRSWidgets().getAll().stream()
				.filter((widget) -> (widget != null && widget.isVisible() && condition.meetsCondition(widget)))
				.findFirst().orElse(new DefaultWidget(getScript())));
	}

	public Widget findFromText(String text) {
		return (last = getRSWidgets().getAll().stream()
				.filter((widget) -> (widget != null && widget.isVisible()
						&& widget.getMessage().toLowerCase().contains(text.toLowerCase())))
				.findFirst().orElse(new DefaultWidget(getScript())));
	}

	public List<Widget> findAllFromText(String text) {
		return getRSWidgets().getAll().stream()
				.filter((widget) -> (widget != null && widget.isVisible()
						&& widget.getMessage().toLowerCase().contains(text.toLowerCase())))
				.collect(Collectors.toList());
	}

	public Widget findFromText(String text, FinderCondition<Widget> condition) {
		return (last = getRSWidgets().getAll().stream()
				.filter((widget) -> (widget != null && widget.isVisible()
						&& widget.getMessage().toLowerCase().contains(text.toLowerCase())
						&& condition.meetsCondition(widget)))
				.findFirst().orElse(new DefaultWidget(getScript())));
	}

	public List<Widget> findAllFromText(String text, FinderCondition<Widget> condition) {
		return getRSWidgets().getAll().stream()
				.filter((widget) -> (widget != null && widget.isVisible()
						&& widget.getMessage().toLowerCase().contains(text.toLowerCase())
						&& condition.meetsCondition(widget)))
				.collect(Collectors.toList());
	}

	public Widget findFromAction(String action) {
		return (last = getRSWidgets().getAll().stream()
				.filter((widget) -> (widget.exists() && widget.hasAction(action))).findAny()
				.orElse(new DefaultWidget(getScript())));
	}

	public List<Widget> findAllFromAction(String action) {
		return getRSWidgets().getAll().stream().filter((widget) -> (widget.exists() && widget.hasAction(action)))
				.collect(Collectors.toList());
	}

	public Widget findFromAction(String action, FinderCondition<Widget> condition) {
		return (last = getRSWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible()
				&& widget.hasAction(action) && condition.meetsCondition(widget))).findAny()
				.orElse(new DefaultWidget(getScript())));
	}

	public List<Widget> findAllFromAction(String action, FinderCondition<Widget> condition) {
		return getRSWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible()
				&& widget.hasAction(action) && condition.meetsCondition(widget))).collect(Collectors.toList());
	}

	public Widget get(int root, int child) {
		return (last = new Widget(getScript(), getWidgets().get(root, child)));
	}

	public Widget get(int root, int child, int grandchild) {
		return (last = new Widget(getScript(), getWidgets().get(root, child, grandchild)));
	}

	/* Private methods */
	public boolean widgetHasMethod(Widget widget, String action) {
		if (widget == null)
			return false;
		if (widget.getInteractActions() == null || widget.getInteractActions().length <= 1)
			return false;
		List<String> actions = Arrays.asList(widget.getInteractActions());
		log(widget.getSpriteIndex1() == 535);
		return actions.stream().filter(str -> (str != null && str.equalsIgnoreCase(action))).findAny()
				.orElse(null) != null;
	}
}
