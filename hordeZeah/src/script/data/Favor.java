package script.data;

import org.osbot.rs07.script.MethodProvider;

/**
 * <p>
 * </p>
 *
 * @author Capitals <onlytainted@gmail.com>
 */
public class Favor {
    MethodProvider ctx;

    int playerSetting1317;
    int playerSetting1318;

    public Favor(MethodProvider ctx) {
        this.ctx = ctx;
        playerSetting1317 = ctx.getSettings().getConfigs().get(1317);
        playerSetting1318 = ctx.getSettings().getConfigs().get(1318);
    }

    public Favor() {}

    public double getArceuusPercent() {
        String info = splitSetting(playerSetting1318)[0];
        return Integer.parseInt(info,2)/10.0;
    }
    public double getArceuusPercent(boolean update) {
        if(update)update();
        return getArceuusPercent();
    }
    public double getHosidiousPercent() {
        String info = splitSetting(playerSetting1318)[1];
        return Integer.parseInt(info,2)/10.0;
    }
    public double getHosidiousPercent(boolean update) {
        if(update)update();
        return getHosidiousPercent();
    }
    public double getLovakengjiPercent() {
        String info = splitSetting(playerSetting1317)[2];
        return Integer.parseInt(info,2)/10.0;
    }
    public double getLovakengjiPercent(boolean update) {
        if(update)update();
        return getLovakengjiPercent();
    }
    public double getPiscarillusPercent() {
        String info = splitSetting(playerSetting1318)[1];
        return Integer.parseInt(info,2)/10.0;
    }
    public double getPiscarillusPercent(boolean update) {
        if(update)update();
        return getPiscarillusPercent();
    }
    public double getShayzienPercent() {
        String info = splitSetting(playerSetting1318)[2];
        return Integer.parseInt(info,2)/10.0;
    }
    public double getShayzienPercent(boolean update) {
        if(update)update();
        return getShayzienPercent();
    }
    private String[] splitSetting(int playerSetting) {
        String splits = Integer.toBinaryString(playerSetting);
        return new String[]{splits.substring(2,12),splits.substring(12,22),splits.substring(22)};
    }
    public void update() {
        playerSetting1317 = ctx.getSettings().getConfigs().get(1317);
        playerSetting1318 = ctx.getSettings().getConfigs().get(1318);
    }

}
