package fi.niwic.dcf.api;

import java.util.Optional;

/**
 * Yhdistää kaikki elementit jotka kuuluvat tiettyyn tilikauteen.
 */
public interface Period {
	
	public Integer getYear();
    public Long getDiscountYears();
    public void setDiscountYears(long years);
    
	public boolean isPrediction();
	
	public FinancialStatement getCurrentFinancialStatement();
	
	public void setPastPeriod(Period period) throws InvalidPastPeriodException;
	public Optional<Period> getPastPeriod();
	
	public FreeCashFlowCalculation getFreeCashFlowCalculation();
	
}
