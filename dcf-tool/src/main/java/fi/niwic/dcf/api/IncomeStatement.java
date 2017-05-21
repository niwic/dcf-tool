package fi.niwic.dcf.api;

public interface IncomeStatement {
	
	public void setTurnover(long turnover);
	public long getTurnover();
	
	public void setCosts(long costs);
	public long getCosts();
	
	public long getEBITDA();
	
	public void setDepreciation(long depreciation);
	public long getDepreciation();
	
	public long getOperatingProfit();
	
	public void setOtherIncomeAndCosts(long otherIncomeAndCosts);
	public long getOtherIncomeAndCosts();
	
	public long getEBIT();
	
	public void setInterestIncome(long interestIncome);
	public long getInterestIncome();
	
	public void setInterestCosts(long interestCosts);
	public long getInterestCosts();
	
	public long getEBT();
	
	public void setTaxCosts(long taxCosts);
	public long getTaxCosts();
	
	public long getNetIncome();
	
	public void setDividends(long dividends);
	public long getDividends();
	
}
