package fi.niwic.dcf.api;

/**
 * Laskelmaan lisätyt jaksot pitää seurata toisiaan.
 */
public class InvalidPastPeriodException extends Exception {
	
    /**
     * Luo uuden poikkeuksen.
     * @param pastYear edellinen vuosi
     * @param currentYear lisätty vuosi
     */
	public InvalidPastPeriodException(int pastYear, int currentYear) {
		super(String.format("Past year (%s) must be current year (%s) minus one!", pastYear, currentYear));
	}
		
}
