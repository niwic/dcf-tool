package fi.niwic.dcf.api;

/**
 * Yrityksen rahoituksen kustannus.
 */
public interface CostOfCapital {
	
    /**
     * Palauttaa yrityksen tavoitetun pääomakustannusprosentin.
     * @return pääomakustannusprosentti
     */
	public Double getCostOfCapital();
	
}
