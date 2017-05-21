package fi.niwic.dcf.api;

import java.util.List;

public interface DCFCalculation {
	
	public void setCompanyName(String name);
	public String getCompanyName();
	
	public void addPeriod(Period period);
	public List<Period> listPeriods();
	
	public void setPerpetualPeriod(Period period);
	public Period getPerpetualPeriod();
	
	public void setCostOfCapital(CostOfCapital costOfCapital);
	public CostOfCapital getCostOfCapital();
	
	public void setNumberOfShares();
	public long getNumberOfShares();
	
	public long calculateValuation();
	
}
