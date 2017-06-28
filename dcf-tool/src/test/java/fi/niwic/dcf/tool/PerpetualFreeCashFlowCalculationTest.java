package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class PerpetualFreeCashFlowCalculationTest {

    protected IncomeStatement isPast;
    protected BalanceSheet bsPast;
    protected FinancialStatement fsPast;
    protected Period periodPast;
    
    protected IncomeStatement is;
    protected BalanceSheet bs;
    protected FinancialStatement fs;
    protected Period period;
    
    private PerpetualFreeCashFlowCalculation fcf;
    
    @Before
    public void setUp() throws InvalidPastPeriodException {
        isPast = new IncomeStatementImpl();
        bsPast = new BalanceSheetImpl();
        fsPast = new FinancialStatementImpl(isPast, bsPast);
        periodPast = new PeriodImpl(2017, true, fsPast);
        
        is = new IncomeStatementImpl();
        bs = new BalanceSheetImpl();
        fs = new FinancialStatementImpl(is, bs);
        period = new PerpetualPeriod(fs);
        period.setPastPeriod(periodPast);
        
        fcf = new PerpetualFreeCashFlowCalculation(period);
    }
    
    @Test
    public void checkGetDiscountedFreeCashFlow() {
        isPast.setTurnover(100);
        bsPast.setBoundEquity(100);
        bsPast.setLongTermLiabilities(100);
        
        is.setTurnover(102);
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        
        CostOfCapital coc = new WACC(bs.getInvestedCapital());
        coc.setCostOfBorrowedCapital(10.0);
        coc.setCostOfOwnCapital(10.0);
        
        period.setDiscountYears(0);
        
        assertEquals(1300, (long) fcf.getDiscountedFreeCashFlow(coc).get());
    }
    
    @Test
    public void checkGetDiscountedFreeCashFlowWithDepreciation() {
        isPast.setTurnover(100);
        bsPast.setBoundEquity(100);
        bsPast.setLongTermLiabilities(100);
        
        is.setTurnover(102);
        is.setDepreciation(2);
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        
        CostOfCapital coc = new WACC(bs.getInvestedCapital());
        coc.setCostOfBorrowedCapital(10.0);
        coc.setCostOfOwnCapital(10.0);
        
        period.setDiscountYears(0);
        
        assertEquals(1000, (long) fcf.getDiscountedFreeCashFlow(coc).get());
    }
    
    @Test
    public void checkGetDiscountedFreeCashFlowWithInvestmentGrowth() {
        isPast.setTurnover(100);
        bsPast.setBoundEquity(100);
        bsPast.setLongTermLiabilities(100);
        bsPast.setFixedAssets(100);
        
        is.setTurnover(100);
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        bs.setFixedAssets(102);
        
        CostOfCapital coc = new WACC(bs.getInvestedCapital());
        coc.setCostOfBorrowedCapital(10.0);
        coc.setCostOfOwnCapital(10.0);
        
        period.setDiscountYears(0);
        
        assertEquals(980, (long) fcf.getDiscountedFreeCashFlow(coc).get());
    }
    
    @Test
    public void checkGetDiscountedFreeCashFlowWithInvestmentGrowthWithYears8() {
        isPast.setTurnover(100);
        bsPast.setBoundEquity(100);
        bsPast.setLongTermLiabilities(100);
        bsPast.setFixedAssets(100);
        
        is.setTurnover(100);
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        bs.setFixedAssets(102);
        
        CostOfCapital coc = new WACC(bs.getInvestedCapital());
        coc.setCostOfBorrowedCapital(10.0);
        coc.setCostOfOwnCapital(10.0);
        
        period.setDiscountYears(8);
        
        assertEquals(457, (long) fcf.getDiscountedFreeCashFlow(coc).get());
    }
    
}
