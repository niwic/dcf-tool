package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.Period;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class PerpetualFreeCashFlowCalculationTest {

    protected IncomeStatement is;
    protected BalanceSheet bs;
    protected FinancialStatement fs;
    protected Period period;
    
    private PerpetualFreeCashFlowCalculation fcf;
    
    @Before
    public void setUp() {
        is = new IncomeStatementImpl();
        bs = new BalanceSheetImpl();
        fs = new FinancialStatementImpl(is, bs);
        period = new PerpetualPeriod();
        
        fcf = new PerpetualFreeCashFlowCalculation(period);
        period.setCurrentFinancialStatement(fs);
    }
    
    @Test
    public void checkGetDiscountedFreeCashFlow() {
        is.setTurnover(100);
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        
        CostOfCapital coc = new WACC(bs.getInvestedCapital());
        coc.setCostOfBorrowedCapital(10.0);
        coc.setCostOfOwnCapital(10.0);
        
        assertEquals(10, (long) fcf.getDiscountedFreeCashFlow(coc).get());
    }
    
}
