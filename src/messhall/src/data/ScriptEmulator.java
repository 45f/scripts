package messhall.src.data;

import dependencies.handlers.InventoryHandler;
import dependencies.util.CSleep;
import messhall.src.MessHall;
import messhall.src.gui.MessHallGUI;
import org.osbot.rs07.Bot;
import org.osbot.rs07.api.*;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.util.ExperienceTracker;
import org.osbot.rs07.event.Event;
import org.osbot.rs07.script.MethodProvider;

import java.util.Random;

/**
 * Created by RAIJIN on 3/17/2017.
 */

@SuppressWarnings("hiding")
public class ScriptEmulator<Script extends MessHall> {

    protected MessHall script;

    public ScriptEmulator() {
    }

    public MessHall getScript() {
        return script;
    }

    public void setScript(MessHall mainScript) {
        this.script = mainScript;
    }

    protected int random(int a, int b) {
        int max = Math.max(a, b), min = Math.min(a, b);

        if (max == min)
            return max;

        return min + ((new Random()).nextInt(max - min));
    }

    // Sleep functions
    protected void sleep(int a) {
        try {
            MethodProvider.sleep(a);
        } catch (InterruptedException e) {

        }
    }

    protected void sleep(int a, int b) {
        try {
            MethodProvider.sleep(random(a, b));
        } catch (InterruptedException e) {

        }
    }

    // Log Functions

    protected void log(String s) {
        script.log(s);
    }

    protected void log(int s) {
        script.log(s);
    }

    protected void log(boolean s) {
        script.log(s);
    }

    protected void stop() {
        getScript().stop();
    }

    protected void stop(boolean logout) {
        getScript().stop(logout);
    }

    protected void execute(Event e) {
        script.execute(e);
    }

    // Give access to script stuffs

    protected String getAuthor() {
        return script.getAuthor();
    }

    public Bot getBot() {
        return script.getBot();
    }

    protected Bank getBank() {
        return script.getBank();
    }

    protected Camera getCamera() {
        return script.getCamera();
    }

    protected Chatbox getChatbox() {
        return script.getChatbox();
    }

    public Client getClient() {
        return script.getClient();
    }

    protected ColorPicker getColorPicker() {
        return script.getColorPicker();
    }

    protected Combat getCombat() {
        return script.getCombat();
    }

    protected Configs getConfigs() {
        return script.getConfigs();
    }

    protected DepositBox getDepositBox() {
        return script.getDepositBox();
    }

    protected Dialogues getDialogues() {
        return script.getDialogues();
    }

    protected DoorHandler getDoorHandler() {
        return script.getDoorHandler();
    }

    protected Equipment getEquipment() {
        return script.getEquipment();
    }

    protected ExperienceTracker getExperienceTracker() {
        return script.getExperienceTracker();
    }

    protected GrandExchange getGrandExchange() {
        return script.getGrandExchange();
    }

    protected HintArrow getHintArrow() {
        return script.getHintArrow();
    }

    protected Inventory getInventory() {
        return script.getInventory();
    }

    protected InventoryHandler getInventoryHandler() {
        return getInventoryHandler();
    }

    protected Keyboard getKeyboard() {
        return script.getKeyboard();
    }

    protected LogoutTab getLogoutTab() {
        return script.getLogoutTab();
    }

    protected Magic getMagic() {
        return script.getMagic();
    }

    protected Map getMap() {
        return script.getMap();
    }

    protected Menu getMenuAPI() {
        return script.getMenuAPI();
    }

    protected Mouse getMouse() {
        return script.getMouse();
    }

    protected NPCS getNpcs() {
        return script.getNpcs();
    }

    protected Objects getObjects() {
        return script.getObjects();
    }

    protected Prayer getPrayer() {
        return script.getPrayer();
    }

    protected Quests getQuests() {
        return script.getQuests();
    }

    protected Settings getSettings() {
        return script.getSettings();
    }

    protected Skills getSkills() {
        return script.getSkills();
    }

    protected Store getStore() {
        return script.getStore();
    }

    protected Tabs getTabs() {
        return script.getTabs();
    }

    protected Trade getTrade() {
        return script.getTrade();
    }

    protected double getVersion() {
        return script.getVersion();
    }

    protected Walking getWalking() {
        return script.getWalking();
    }

    protected Widgets getWidgets() {
        return script.getWidgets();
    }

    protected Worlds getWorlds() {
        return script.getWorlds();
    }

    protected Player myPlayer() {
        return script.myPlayer();
    }

    protected Position myPosition() {
        return script.myPosition();
    }

    protected MessHallGUI getGUI() {
        return script.getGUI();
    }

    protected void openBank() throws InterruptedException {
        if (getBank().open())
            new CSleep(() -> getBank().isOpen(), 5_000).sleep();
    }

    protected boolean isItemSelected(final String item) {
        String selectedName = getInventory().getSelectedItemName();
        return selectedName != null && selectedName.equals(item);
    }

    protected boolean mustContinue() {
        return getDialogues().isPendingContinuation();
    }
}