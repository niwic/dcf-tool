package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;

public class FinancialStatementImpl implements FinancialStatement {
	
	private IncomeStatement incomeStatement;
	private BalanceSheet balanceSheet;
	
	public FinancialStatementImpl(IncomeStatement incomeStatement,
			BalanceSheet balanceSheet) {
		
		this.incomeStatement = incomeStatement;
		this.balanceSheet = balanceSheet;
	}
			
	@Override
	public IncomeStatement getIncomeStatement() {
		return this.incomeStatement;
	}

	@Override
	public BalanceSheet getBalanceSheet() {
		return this.balanceSheet;
	}
	
}
