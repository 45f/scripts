package omniapi.debug;

public enum LogLevel {
	LOG(0), DEBUG(1), WARN(2);

	LogLevel(int i) {
		level = i;
	}

	public int getLevel() {
		return level;
	}

	private int level;
}
