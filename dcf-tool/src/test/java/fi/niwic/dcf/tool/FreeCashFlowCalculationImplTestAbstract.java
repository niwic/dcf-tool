package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import org.junit.Before;

public class FreeCashFlowCalculationImplTestAbstract {

    protected IncomeStatement is;
    protected BalanceSheet bs;
    protected FinancialStatement fs;
    protected Period period;
    
    protected FreeCashFlowCalculationImpl fcfCalculation;
    
    @Before
    public void setUp() throws InvalidPastPeriodException {
        is = new IncomeStatementImpl();
        bs = new BalanceSheetImpl();
        fs = new FinancialStatementImpl(is, bs);
        period = new PeriodImpl(2017, true, fs);
        fcfCalculation = new FreeCashFlowCalculationImpl(period);
    }
    
}
