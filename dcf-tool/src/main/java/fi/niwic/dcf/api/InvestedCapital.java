package fi.niwic.dcf.api;

/**
 * Pääomalaskelma.
 */
public interface InvestedCapital {

    /**
     * Netto toimintapääoma.
     * @return netto toimintapääoma
     */
    public long getNetOperatingCapital();

    /**
     * Pitkäaikainen omaisuus.
     * @return pitkäaikainen omaisuus
     */
    public long getLongTermAssets();

    /**
     * Sijoitettu omaisuus laskettu omaisuudesta.
     * @return sijoitettu omaisuus
     */
    public long getInvestedCapitalFromAssets();

    /**
     * Velat.
     * @return velat
     */
    public long getLiabilities();

    /**
     * Oma pääoma.
     * @return oma pääoma
     */
    public long getEquity();

    /**
     * Korollinen rahoitusomaisuus.
     * @return Korollinen rahoitusomaisuus
     */
    public long getInterestBearingFinancialAssets();

    /**
     * Sijoitettu omaisuus laskettu pääomasta.
     * @return sijoitettu omaisuus
     */
    public long getInvestedCapitalFromEquityAndLiabilities();

}
