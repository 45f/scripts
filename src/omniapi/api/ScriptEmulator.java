package omniapi.api;

import org.osbot.rs07.Bot;
import org.osbot.rs07.antiban.AntiBan;
import org.osbot.rs07.api.Bank;
import org.osbot.rs07.api.Camera;
import org.osbot.rs07.api.Chatbox;
import org.osbot.rs07.api.Client;
import org.osbot.rs07.api.ColorPicker;
import org.osbot.rs07.api.Combat;
import org.osbot.rs07.api.Configs;
import org.osbot.rs07.api.DepositBox;
import org.osbot.rs07.api.Dialogues;
import org.osbot.rs07.api.DoorHandler;
import org.osbot.rs07.api.Equipment;
import org.osbot.rs07.api.GrandExchange;
import org.osbot.rs07.api.HintArrow;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.Keyboard;
import org.osbot.rs07.api.LocalWalker;
import org.osbot.rs07.api.LogoutTab;
import org.osbot.rs07.api.Magic;
import org.osbot.rs07.api.Map;
import org.osbot.rs07.api.Menu;
import org.osbot.rs07.api.Mouse;
import org.osbot.rs07.api.NPCS;
import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.Players;
import org.osbot.rs07.api.Prayer;
import org.osbot.rs07.api.Quests;
import org.osbot.rs07.api.Settings;
import org.osbot.rs07.api.Skills;
import org.osbot.rs07.api.Store;
import org.osbot.rs07.api.Tabs;
import org.osbot.rs07.api.Trade;
import org.osbot.rs07.api.Widgets;
import org.osbot.rs07.api.Worlds;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.util.ExperienceTracker;
import org.osbot.rs07.script.Script;

public class ScriptEmulator<S extends Script> {

	protected S script;

	public ScriptEmulator(S script) {
		setScript(script);
	}

	public void setScript(S script) {
		this.script = script;
	}

	public S getScript() {
		return script;
	}

	/* OSBot getters */
	protected Position myPosition() {
		return script.myPosition();
	}

	protected AntiBan getAntiBan() {
		return script.getAntiBan();
	}

	protected String getAuthor() {
		return script.getAuthor();
	}

	protected Bot getBot() {
		return script.getBot();
	}

	protected Camera getCamera() {
		return script.getCamera();
	}

	protected Chatbox getChatbox() {
		return script.getChatbox();
	}

	protected Client getClient() {
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

	protected Keyboard getKeyboard() {
		return script.getKeyboard();
	}

	protected LocalWalker getLocalWalker() {
		return script.getLocalWalker();
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

	protected Widgets getWidgets() {
		return script.getWidgets();
	}

	protected Worlds getWorlds() {
		return script.getWorlds();
	}

	protected Objects getObjects() {
		return script.getObjects();
	}

	protected NPCS getNpcs() {
		return script.getNpcs();
	}

	protected Player myPlayer() {
		return script.myPlayer();
	}

	protected Players getPlayers() {
		return script.getPlayers();
	}

	protected Bank getBank() {
		return script.getBank();
	}
}
