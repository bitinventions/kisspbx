package org.kisspbx;

public abstract class ConfigurationChangedMonitor {
	private static boolean changed = false;
	
	public static boolean isChanged() {
		return changed;
	}
	
	public synchronized static void setChanged() {
		changed = true;
	}
	
	public synchronized static void clearChanged() {
		changed = false;
	}
}
