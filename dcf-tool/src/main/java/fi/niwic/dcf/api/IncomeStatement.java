package fi.niwic.dcf.api;

/**
 * Yrityksen yhden tilikauden tuloslaskelma.
 */
public interface IncomeStatement {
	
	/**
	 * Tilikauden liikevaihto.
     * 
	 * @param turnover liikevaito
	 */
	public void setTurnover(long turnover);
	
	/**
	 * Tilikauden liikevaihto.
     * 
	 * @return liikevaihto
	 */
	public long getTurnover();
	
	/**
	 * Tilikauden kustannukset.
     * 
	 * @param costs kustannukset
	 */
	public void setCosts(long costs);
	
	/**
	 * Tilikauden kustannukset.
     * 
	 * @return kustannukset
	 */
	public long getCosts();
	
	/**
	 * Tilikauden tulos ennen korkoja, veroja ja poistoja.
	 * EBITDA: Earnings Before Interest Tax Depreciation and Amortization
	 * 
	 * @return Tulos ennen korkoja, veroja ja poistoja
	 */
	public long getEBITDA();
	
	/**
	 * Tilikauden poistot.
     * 
	 * @param depreciation poistot
	 */
	public void setDepreciation(long depreciation);
	
	/**
	 * Tilikauden poistot.
     * 
	 * @return poistot
	 */
	public long getDepreciation();
	
	/**
	 * Tilikauden toiminnallinen tulos / liikevoitto.
     * 
	 * @return toiminnallinen tulos (EBITDA - poistot)
	 */
	public long getOperatingProfit();
	
	/**
	 * Tilikauden muut tulot ja kustannukset.
     * 
	 * @param otherIncomeAndCosts muut tulot ja kustannukset
	 */
	public void setOtherIncomeAndCosts(long otherIncomeAndCosts);
	
	/**
	 * Tilikauden muut tulot ja kustannukset.
     * 
	 * @return muut tulot ja kustannukset
	 */
	public long getOtherIncomeAndCosts();
	
	/**
	 * Tilikauden tulos ennen korkoja ja veroja.
     * 
	 * EBIT: Earnings Before Interest and Tax
	 * 
	 * @return tulos ennen korkoja ja veroja
	 */
	public long getEBIT();
	
	/**
	 * Tilikauden korkotuotot.
     * 
	 * @param interestIncome korkotuotot
	 */
	public void setInterestIncome(long interestIncome);
	
	/**
	 * Tilikauden korkotuotot.
     * 
	 * @return korkotuotot
	 */
	public long getInterestIncome();
	
	/**
	 * Tilikauden korkokustannukset.
     * 
	 * @param interestCosts korkokustannukset
	 */
	public void setInterestCosts(long interestCosts);
	
	/**
	 * Tilikauden korkokustannukset.
     * 
	 * @return korkokustannukset
	 */
	public long getInterestCosts();
	
	/**
	 * Tilikauden tulos ennen veroja.
     * 
	 * @return tulos ennen veroja
	 */
	public long getEBT();
	
	/**
	 * Tilikauden verokustannukset.
     * 
	 * @param taxCosts verokustannukset
	 */
	public void setTaxCosts(long taxCosts);
	
	/**
	 * Tilikauden verokustannukset.
     * 
	 * @return verokustannukset
	 */
	public long getTaxCosts();
	
	/**
	 * Tilikauden tulos.
     * 
	 * @return tulos
	 */
	public long getNetIncome();
	
	/**
	 * Tilikaudella maksetut osingot.
     * 
	 * @param dividends osingot
	 */
	public void setDividendCosts(long dividends);
	
	/**
	 * Tilikaudella maksetut osingot.
     * 
	 * @return osingot
	 */
	public long getDividendCosts();
	
	/**
	 * Tilikauden toteutunut veroprosentti.
     * 
	 * @return toteutunut veroprosentti
	 */
	public double getRealizedTaxRate();
	
}
