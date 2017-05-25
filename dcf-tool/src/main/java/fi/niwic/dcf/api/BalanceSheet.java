package fi.niwic.dcf.api;

/**
 * Rajapinta kuvaa yrityksen yhden tilikauden tasetta
 * 
 * @author Nic Wichmann
 */
public interface BalanceSheet {

    /**
     * Korolliset aineettomat hyödykkeet
     * @param amount Korolliset aineettomat hyödykkeet
     */
    public void setNonInterestBearingFinancialAssets(long amount);

    /**
     * Korolliset aineettomat hyödykkeet
     * @return Korolliset aineettomat hyödykkeet
     */
    public long getNonInterestBearingFinancialAssets();

    /**
     * Korottomat aineettomat hyödykkeet
     * @param amount Korottomat aineettomat hyödykkeet
     */
    public void setInterestBearingFinancialAssets(long amount);

    /**
     * Korottomat aineettomat hyödykkeet
     * @return Korottomat aineettomat hyödykkeet
     */
    public long getInterestBearingFinancialAssets();

    /**
     * Vaihto-omaisuus
     * @param amount Vaihto-omaisuus
     */
    public void setInventory(long amount);

    /**
     * Vaihto-omaisuus
     * @return Vaihto-omaisuus
     */
    public long getInventory();

    /**
     * Aineelliset hyödykkeet
     * @param amount Aineelliset hyödykkeet
     */
    public void setFixedAssets(long amount);

    /**
     * Aineelliset hyödykkeet
     * @return Aineelliset hyödykkeet
     */
    public long getFixedAssets();

    /**
     * Vastaavat (aktiivat) yhteensä
     * @return vastaavat yhteensä
     */
    public long getTotalAssets();

    /**
     * Lyhytaikaiset korottomat velat
     * @param amount Lyhytaikaiset korottomat velat
     */
    public void setShortTermNonInterestBearingLiabilities(long amount);

    /**
     * Lyhytaikaiset korottomat velat
     * @return Lyhytaikaiset korottomat velat
     */
    public long getShortTermNonInterestBearingLiabilities();

    /**
     * Lyhytaikaiset korolliset velat
     * @param amount Lyhytaikaiset korolliset velat
     */
    public void setShortTermInterestBearingLiabilities(long amount);

    /**
     * Lyhytaikaiset korolliset velat
     * @return Lyhytaikaiset korolliset velat
     */
    public long getShortTermInterestBearingLiabilities();

    /**
     * Pitkäaikaiset korolliset velat
     * @param amount Pitkäaikaiset korolliset velat
     */
    public void setLongTermLiabilities(long amount);

    /**
     * Pitkäaikaiset korolliset velat
     * @return Pitkäaikaiset korolliset velat
     */
    public long getLongTermLiabilities();

    /**
     * Sidottu oma pääoma
     * @param amount Sidottu oma pääoma
     */
    public void setBoundEquity(long amount);

    /**
     * Sidottu oma pääoma
     * @return Sidottu oma pääoma
     */
    public long getBoundEquity();

    /**
     * Eddelisten tilikausein voitto/tappio
     * @param amount Eddelisten tilikausein voitto/tappio
     */
    public void setPastProfitsOrLoss(long amount);

    /**
     * Eddelisten tilikausein voitto/tappio
     * @return Eddelisten tilikausein voitto/tappio
     */
    public long getPastProfitsOrLoss();

    /**
     * Tämän tilikauden voitto/tappio
     * @param amount Tämän tilikauden voitto/tappio
     */
    public void setCurrentPeriodProfitOrLoss(long amount);

    /**
     * Tämän tilikauden voitto/tappio
     * @return Tämän tilikauden voitto/tappio
     */
    public long getCurrentPeriodProfitOrLoss();

    /**
     * Vastattavat (passiivat) yhteensä
     * @return Vastattavat yhteensä
     */
    public long getTotalEquityAndLiabilities();

    /**
     * Onko tase tasapainossa?
     * Tase on tasapainossa jos Vastaavat ja Vastattavat ovat yhtä suuret
     * @return tase tasapainossa
     */
    public boolean isBalanced();

}
