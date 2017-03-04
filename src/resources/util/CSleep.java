package resources.util;

import org.osbot.rs07.utility.ConditionalSleep;

import java.util.function.BooleanSupplier;

public final class CSleep extends ConditionalSleep {

    private final BooleanSupplier condition;

    public CSleep(final BooleanSupplier condition, int timeout) {
        super(timeout);
        this.condition = condition;
    }

    @Override
    public boolean condition() throws InterruptedException {
        return condition.getAsBoolean();
    }
}
