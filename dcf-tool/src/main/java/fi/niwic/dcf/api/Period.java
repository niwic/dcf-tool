package fi.niwic.dcf.api;

public interface Period {
	
	public int getYear();
	public boolean isPrognosis();
	
	public void setCurrentFinancialStatement(FinancialStatement financialStatement);
	public FinancialStatement getCurrentFinancialStatement();
	
	public void setPastPeriod(Period period);
	public Period getPastPeriod();
	
	public FreeCashFlowCalculation getFreeCashFlowCalculation();
	
}
