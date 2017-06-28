package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;

/**
 * Jatkuva kausi.
 * @see Period
 */
public class PerpetualPeriod extends PeriodImpl {

    /**
     * Luo uuden jatkuvan kauden. Jatkuva kausi on aina analyysin viimeinen kausi.
     * @param fs jatkuvan kauden tilinpäätös
     */
    public PerpetualPeriod(FinancialStatement fs) {
        super(9999, true, fs);
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
