package fi.niwic.dcf.api;

public interface FreeCashFlowCalculation {
	
	public long getOperatingProfit();
	public long getTaxCosts();
	public long getInterestCostTaxEffect();
	public long getInterestIncomeTaxEffect();
	public long getOtherPostsTaxEffect();
	
	public long getNOPLAT();
	
	public long getDepreciation();
	
	public long getGrossCashFlow();
	
	public long getNetWorkingCapital();
	public long getGrossInvestments();
	
	public long getOperatingFreeCashFlow();
	
	public long getNonOperatingCashFlow();
	
	public long getFreeCashFlow();
	
}
