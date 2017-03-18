package messhall.src.data;

/**
 * Created by RAIJIN on 3/12/2017.
 */
public enum FoodItems implements CookingItem {
    BOWL("Bowl", "Bowl of  water", 0),
    SERVERY_DISH("Servery dish", "Servery pie shell", 0),
    SERVERY_FLOUR("Servery flour", "", 0),
    SERVERY_POTATO("Servery potato", "", 0),
    SERVERY_STEW("Servery incomplete stew", "Servery stew", 3),
    SERVERY_MEAT("Servery raw meat", "Servery cooked meat", 1),
    SERVERY_PIE("Servery uncooked pie", "Servery meat pie", 3);

    private String rawName, finishedName;
    private double xp;

    public String getRawName() {
        return rawName;
    }

    public String getFinishedName() {
        return finishedName;
    }

    FoodItems(String rawName, String finishedName, int xp) {
        this.rawName = rawName;
        this.finishedName = finishedName;
        this.xp = xp;
    }

    @Override
    public double getXP() {
        return xp;
    }
}
