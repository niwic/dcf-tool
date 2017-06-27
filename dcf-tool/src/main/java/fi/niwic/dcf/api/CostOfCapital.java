package fi.niwic.dcf.api;

/**
 * Yrityksen rahoituksen kustannus.
 */
public interface CostOfCapital {

    /**
     * Palauttaa yrityksen tavoitetun pääomakustannusprosentin.
     *
     * @return pääomakustannusprosentti
     */
    public Double getCostOfCapital();

    /**
     * Asettaa oman pääoman kustannusprosentin.
     * 
     * @param rate kustannus prosentti
     */
    public void setCostOfOwnCapital(Double rate);
    
    /**
     * Asettaa vieraan pääoman kustannusprosentin.
     * 
     * @param rate korko
     */
    public void setCostOfBorrowedCapital(Double rate);
    
}
