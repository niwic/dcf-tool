package fi.niwic.dcf.api;

import java.util.Optional;

/**
 * Yhdistää kaikki elementit jotka kuuluvat tiettyyn tilikauteen.
 */
public interface Period {
	
	public int getYear();
	public boolean isPrediction();
	
	public void setCurrentFinancialStatement(FinancialStatement financialStatement);
	public FinancialStatement getCurrentFinancialStatement();
	
	public void setPastPeriod(Period period) throws InvalidPastPeriodException;
	public Optional<Period> getPastPeriod();
	
	public FreeCashFlowCalculation getFreeCashFlowCalculation();
	
}
