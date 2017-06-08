package fi.niwic.dcf.api;

/**
 * Yhdistää jakson tuloslaskelman ja taseen.
*/
public interface FinancialStatement {
	
	public IncomeStatement getIncomeStatement();
	public BalanceSheet getBalanceSheet();
	
}
