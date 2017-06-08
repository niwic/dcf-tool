package fi.niwic.dcf.api;

/**
 * Pääomalaskelma.
 */
public interface InvestedCapital {
	
	public long getNetOperatingCapital();
	public long getLongTermAssets();
	public long getLiabilities();
	public long getEquity();
	
}
