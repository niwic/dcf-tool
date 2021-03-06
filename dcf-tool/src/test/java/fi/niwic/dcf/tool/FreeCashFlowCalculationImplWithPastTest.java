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

public class FreeCashFlowCalculationImplWithPastTest
        extends FreeCashFlowCalculationImplTestAbstract {

    private IncomeStatement isPast;
    private BalanceSheet bsPast;
    private FinancialStatement fsPast;
    private Period periodPast;
    
    @Override @Before
    public void setUp() throws InvalidPastPeriodException {
        super.setUp();
        
        bsPast = new BalanceSheetImpl();
        isPast = new IncomeStatementImpl();
        fsPast = new FinancialStatementImpl(isPast, bsPast);
        
        periodPast = new PeriodImpl(period.getYear()-1, true, fsPast);
        period.setPastPeriod(periodPast);
    }
    
    @Test
    public void checkGetNetWorkingCapitalDelta() throws InvalidPastPeriodException {
        bsPast.setNonInterestBearingFinancialAssets(100);
        bsPast.setInventory(50);
        bsPast.setShortTermNonInterestBearingLiabilities(75);
        
        bs.setNonInterestBearingFinancialAssets(200);
        bs.setInventory(100);
        bs.setShortTermNonInterestBearingLiabilities(150);
        
        assertEquals(-75, (long) fcfCalculation.getNetWorkingCapitalDelta().get());
    }
    
    @Test
    public void checkGetGrossInvestments() {
        bsPast.setFixedAssets(100);
        isPast.setDepreciation(25);
        
        bs.setFixedAssets(200);
        is.setDepreciation(25);
        
        assertEquals(125, (long) fcfCalculation.getGrossInvestments().get());
    }
    
    @Test
    public void checkGetOperatingFreeCashFlow() {
        is.setTurnover(150);
        is.setTaxCosts(33);
        is.setInterestCosts(100);
        is.setInterestIncome(100);
        is.setDepreciation(25);
        
        bs.setNonInterestBearingFinancialAssets(200);
        bs.setInventory(100);
        bs.setShortTermNonInterestBearingLiabilities(150);
        bs.setFixedAssets(200);
        
        bsPast.setNonInterestBearingFinancialAssets(100);
        bsPast.setInventory(50);
        bsPast.setShortTermNonInterestBearingLiabilities(75);
        bsPast.setFixedAssets(100);
        
        assertEquals(-83, (long) fcfCalculation.getOperatingFreeCashFlow().get());
    }
    
    @Test
    public void checkGetFreeCashFlow() {
        is.setTurnover(150);
        is.setTaxCosts(33);
        is.setInterestCosts(100);
        is.setInterestIncome(50);
        is.setOtherIncomeAndCosts(50);
        is.setDepreciation(25);
        
        bs.setNonInterestBearingFinancialAssets(200);
        bs.setInventory(100);
        bs.setShortTermNonInterestBearingLiabilities(150);
        bs.setFixedAssets(200);
        
        bsPast.setNonInterestBearingFinancialAssets(100);
        bsPast.setInventory(50);
        bsPast.setShortTermNonInterestBearingLiabilities(75);
        bsPast.setFixedAssets(100);
        
        assertEquals(-59, (long) fcfCalculation.getFreeCashFlow().get());
    }
    
    @Test
    public void checkGetDiscountedFreeCashFlow() {
        is.setTurnover(100);
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        
        CostOfCapital coc = new WACC(bs.getInvestedCapital());
        coc.setCostOfBorrowedCapital(10.0);
        coc.setCostOfOwnCapital(10.0);
        
        assertEquals(90, (long) fcfCalculation.getDiscountedFreeCashFlow(coc).get());
    }
    
    @Test
    public void checkGetDiscountedFreeCashFlowPeriod8() {
        is.setTurnover(100);
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        
        CostOfCapital coc = new WACC(bs.getInvestedCapital());
        coc.setCostOfBorrowedCapital(10.0);
        coc.setCostOfOwnCapital(10.0);
        
        period.setDiscountYears(8);
        
        assertEquals(46, (long) fcfCalculation.getDiscountedFreeCashFlow(coc).get());
    }
    
}
