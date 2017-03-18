package messhall.src.data;

/**
 * Created by RAIJIN on 3/12/2017.
 */
public enum CookingType {
    FOOD(FoodItems.values());

    private CookingItem[] items;

    CookingType(final CookingItem[] items) {
        this.items = items;
    }

    public CookingItem[] getItems() {
        return items;
    }

    @Override
    public String toString() {
        char[] name = name().toLowerCase().replace('_', ' ').toCharArray();
        name[0] = Character.toUpperCase(name[0]);
        return new String(name);
    }

}
