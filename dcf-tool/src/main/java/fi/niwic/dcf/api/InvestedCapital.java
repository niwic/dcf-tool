package fi.niwic.dcf.api;

/**
 * Pääomalaskelma.
 */
public interface InvestedCapital {

    public long getNetOperatingCapital();

    public long getLongTermAssets();

    public long getInvestedCapitalFromAssets();

    public long getLiabilities();

    public long getEquity();

    public long getInterestBearingFinancialAssets();

    public long getInvestedCapitalFromEquityAndLiabilities();

}
