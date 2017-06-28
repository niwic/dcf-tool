package fi.niwic.dcf.api;

/**
 * Yhdistää jakson tuloslaskelman ja taseen.
*/
public interface FinancialStatement {
	
    /**
     * Palauttaa tuloslaskelman.
     * @return tuloslaskelma
     */
	public IncomeStatement getIncomeStatement();
    
    /**
     * Palauttaa taseen.
     * @return tase
     */
	public BalanceSheet getBalanceSheet();
	
}
