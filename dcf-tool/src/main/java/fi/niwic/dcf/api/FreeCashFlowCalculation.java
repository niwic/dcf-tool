package fi.niwic.dcf.api;

import java.util.Optional;

public interface FreeCashFlowCalculation {
	
	public long getOperatingProfit();
	public long getTaxCosts();
	public long getInterestCostTaxEffect();
	public long getInterestIncomeTaxEffect();
	public long getOtherPostsTaxEffect();
	
	public long getNOPLAT();
	
	public long getDepreciation();
	
	public long getGrossCashFlow();
	
	public Optional<Long> getNetWorkingCapitalDelta();
	public Optional<Long> getGrossInvestments();
	
	public Optional<Long> getOperatingFreeCashFlow();
	
	public Long getNonOperatingCashFlow();
	
	public Optional<Long> getFreeCashFlow();
	
}
