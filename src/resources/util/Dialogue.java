package resources.util;

import org.osbot.rs07.script.API;

import static java.awt.event.KeyEvent.*;

/**
 * Created by mvpni on 1/20/2017.
 */
public class Dialogue extends API {

    public void handleLevelUp() {
        if (getDialogues().isPendingContinuation()) {
            getBot().getKeyEventHandler().generateBotKeyEvent(KEY_TYPED, System.currentTimeMillis(), 0, VK_UNDEFINED, (char) VK_SPACE);
        }
    }

    @Override
    public void initializeModule() {

    }
}


