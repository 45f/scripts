package resources.util;

/**
 * Created  on 8/16/2016.
 */
public class Timer {

    private long period;
    private long startTime;

    public Timer(long period) {
        this.period = period;
        startTime = System.currentTimeMillis();
    }

    public boolean isRunning() {
        return getElapsed() < period;
    }

    public long getElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        period = 0;
    }

    public static final String formatTime(final long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60, d = h / 24;
        s %= 60;
        m %= 60;
        h %= 24;

        return d > 0 ? String.format("%02d:%02d:%02d:%02d", d, h, m, s) :
                h > 0 ? String.format("%02d:%02d:%02d", h, m, s) :
                        String.format("%02d:%02d", m, s);
    }
}
