package fi.niwic.dcf.api;

public interface BalanceSheet {
	
	public void setNonInterestBearingFinancialAssets(long amount);
	public long getNonInterestBearingFinancialAssets();
	
	public void setInterestBearingFinancialAssets(long amount);
	public long getInterestBearingFinancialAssets();
	
	public void setInventory(long amount);
	public long getInventory();
	
	public void setFixedAssets(long amount);
	public long getFixedAssets();
	
	public void getTotalAssets();
	
	public void setShortTermNonInterestBearingLiabilities(long amount);
	public long getShortTermNonInterestBearingLiabilities();
	
	public void setShortTermInterestBearingLiabilities(long amount);
	public void getShortTermInterestBearingLiabilities();
	
	public void setLongTermLiabilities(long amount);
	public long getLongTermLiabilities();
	
	public void setBoundEquity(long amount);
	public long getBoundEquity();
	
	public void setPastProfitsOrLoss(long amount);
	public long getPastProfitsOrLoss();
	
	public void setCurrentPeriodProfitOrLoss(long amount);
	public long getCurrentPeriodProfitOrLoss();
	
	public void getTotalEquityAndLiabilities();
	
	public boolean isBalanced();
	
}
