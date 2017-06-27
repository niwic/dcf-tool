package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;

public class PerpetualPeriod extends PeriodImpl {

    public PerpetualPeriod() {
        super(9999, true);
    }
    
    @Override
    public void setPastPeriod(Period pastPeriod) throws InvalidPastPeriodException {
        this.year = pastPeriod.getYear() + 1;
        super.setPastPeriod(pastPeriod);
    }
    
    @Override
    public FreeCashFlowCalculation getFreeCashFlowCalculation() {
        return new PerpetualFreeCashFlowCalculation(this);
    }
    
}
