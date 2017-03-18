package dependencies.util;

import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.HashMap;

public abstract class Trade {

    private static MethodProvider s;

    public static boolean isTrading() {
        return s.getTrade().isCurrentlyTrading() || s.getTrade().isFirstInterfaceOpen() || s.getTrade().isSecondInterfaceOpen();
    }

    public static void trade(String name, HashMap<String, Integer> itemSet, boolean acceptLast) {
        String cleanName = name.replaceAll(" ", "\\u00a0");
        Player player = s.getPlayers().closest(cleanName);
        if (player != null && !isTrading() && player.interact("trade with")) {
            new ConditionalSleep(3000, 4000) {
                @Override
                public boolean condition() {
                    return isTrading();
                }
            }.sleep();
        }

        if (isTrading() && s.getTrade().isFirstInterfaceOpen()) {
            if (!tradeOfferMatches(itemSet)) {
                for (String item : itemSet.keySet()) {
                    if (!s.getTrade().getOurOffers().contains(item)) {
                        if (s.getTrade().offer(item, itemSet.get(item))) {
                            new ConditionalSleep(2000, 3000) {

                                @Override
                                public boolean condition() {
                                    return s.getTrade().getOurOffers().contains(item);
                                }
                            }.sleep();
                        }
                    }
                }
            } else {
                if (acceptLast && s.getTrade().didOtherAcceptTrade()) {
                    if (Widgets.interactTil("Accept", 335, 11, new ConditionalSleep(1500, 2000) {
                        @Override
                        public boolean condition() {
                            return s.getTrade().isSecondInterfaceOpen();
                        }
                    })) {
                        new ConditionalSleep(3000, 4000) {
                            @Override
                            public boolean condition() {
                                return s.getTrade().isSecondInterfaceOpen();
                            }
                        }.sleep();
                    }

                } else if (!acceptLast && !hasAccepted()) {
                    if (Widgets.interactTil("Accept", 335, 11, new ConditionalSleep(1500, 2000) {
                        @Override
                        public boolean condition() {
                            return hasAccepted();
                        }
                    })) {
                        new ConditionalSleep(3000, 4000) {

                            @Override
                            public boolean condition() {
                                return s.getTrade().isSecondInterfaceOpen();
                            }
                        }.sleep();
                    }
                }

            }
        } else if (isTrading() && s.getTrade().isSecondInterfaceOpen()) {

            if (acceptLast && s.getTrade().didOtherAcceptTrade()) {
                if (Widgets.interactTil("Accept", 334, 25, new ConditionalSleep(1500, 2000) {
                    @Override
                    public boolean condition() {
                        return !isTrading();
                    }
                })) {
                    new ConditionalSleep(3000, 4000) {

                        @Override
                        public boolean condition() {
                            return s.getTrade().isSecondInterfaceOpen();
                        }
                    }.sleep();
                }

            } else if (!acceptLast && !hasAccepted()) {
                if (Widgets.interactTil("Accept", 334, 25, new ConditionalSleep(1500, 2000) {
                    @Override
                    public boolean condition() {
                        return !isTrading();
                    }
                })) {
                    new ConditionalSleep(3000, 4000) {
                        @Override
                        public boolean condition() {
                            return s.getTrade().isSecondInterfaceOpen();
                        }
                    }.sleep();
                }

            }
        }
    }

    private static boolean hasAccepted() {
        return Widgets.containsText(335, 30, "Waiting for other player...") || Widgets.containsText(334, 4, "Waiting for other player...");
    }

    private static boolean tradeOfferMatches(HashMap<String, Integer> itemSet) {
        for (String item : itemSet.keySet()) {
            if (isTrading() && s.getTrade().getOurOffers().getItem(item) == null) {
                s.log("Trade Offer Missing: " + item);
                return false;
            }
        }
        return true;
    }
}