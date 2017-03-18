package dependencies.util;

import org.osbot.rs07.api.ui.RS2Widget;

/**
 * Created by RAIJIN on 3/12/2017.
 */
public class CachedWidget {

    private Integer parentID;
    private Integer childID;
    private Integer child2ID;
    private String text;
    private RS2Widget widget;

    public CachedWidget(final int parentID, final int childID, final int child2ID) {
        this.parentID = parentID;
        this.childID = childID;
        this.child2ID = child2ID;
    }

    public CachedWidget(final int parentID, final int childID) {
        this.parentID = parentID;
        this.childID = childID;
    }

    public CachedWidget(final String text) {
        this.text = text;
    }

    public RS2Widget getWidget(final org.osbot.rs07.api.Widgets widgets) {
        if (widget == null)
            cacheWidget(widgets);
        return widget;
    }

    private void cacheWidget(final org.osbot.rs07.api.Widgets widgets) {
        RS2Widget widget;
        if (text != null)
            widget = widgets.getWidgetContainingText(text);
        else if (child2ID != null)
            widget = widgets.get(parentID, childID, child2ID);
        else
            widget = widgets.get(parentID, childID);
        this.widget = widget;
    }
}

