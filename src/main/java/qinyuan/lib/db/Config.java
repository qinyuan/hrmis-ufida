package qinyuan.lib.db;

public class Config {

	private static boolean updated = false;

	static boolean isUpdated() {
		return updated;
	}

	private Config() {
	}

	static int WAIT_TIME_OUT = 1000 * 3600 * 4;

	static int getWaitTimeOut() {
		return WAIT_TIME_OUT;
	}

	static void setWaitTimeOut(int waitTimeOut) {
		WAIT_TIME_OUT = waitTimeOut * 1000;
		updated = true;
	}
}
