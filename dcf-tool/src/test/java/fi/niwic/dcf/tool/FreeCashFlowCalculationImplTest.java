package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class FreeCashFlowCalculationImplTest {

    private IncomeStatement is;
    private BalanceSheet bs;
    private FinancialStatement fs;
    private Period period;
    private FreeCashFlowCalculationImpl fcfCalculation;
   
    
    @Before
    public void setUp() {
        is = new IncomeStatementImpl();
        bs = new BalanceSheetImpl();
        fs = new FinancialStatementImpl(is, bs);
        period = new PeriodImpl(2017, false);
        fcfCalculation = new FreeCashFlowCalculationImpl(period);
        
        period.setCurrentFinancialStatement(fs);
    }
    
    @Test
    public void checkGetOperatingProfit() {
        is.setTurnover(3);
        is.setCosts(1);
        is.setDepreciation(1);
        
        assertEquals(1, fcfCalculation.getOperatingProfit());
    }
    
    @Test
    public void checkGetTaxCosts() {
        is.setTaxCosts(3);
        
        assertEquals(3, fcfCalculation.getTaxCosts());
    }
    
    @Test
    public void checkGetInterestCostTaxEffect() {
        is.setTurnover(200);
        is.setInterestCosts(100);
        is.setTaxCosts(33);
        
        assertEquals(33, fcfCalculation.getInterestCostTaxEffect());
    }
    
    @Test
    public void checkGetInterestCostTaxEffectUneven() {
        is.setTurnover(149);
        is.setInterestCosts(50);
        is.setTaxCosts(33);
        
        assertEquals(17, fcfCalculation.getInterestCostTaxEffect());
    }
    
    @Test
    public void checkGetInterestIncomeTaxEffect() {
        is.setTurnover(100);
        is.setInterestIncome(100);
        is.setTaxCosts(66);
        
        assertEquals(33, fcfCalculation.getInterestIncomeTaxEffect());
    }
    
    @Test
    public void checkGetInterestIncomeTaxEffectUneven() {
        is.setTurnover(49);
        is.setInterestIncome(50);
        is.setTaxCosts(33);
        
        assertEquals(17, fcfCalculation.getInterestIncomeTaxEffect());
    }
    
    @Test
    public void checkGetOtherPostsTaxEffect() {
        is.setTurnover(100);
        is.setOtherIncomeAndCosts(100);
        is.setTaxCosts(66);
        
        assertEquals(33, fcfCalculation.getOtherPostsTaxEffect());
    }
    
    @Test
    public void checkGetOtherPostsTaxEffectUneven() {
        is.setTurnover(149);
        is.setOtherIncomeAndCosts(-50);
        is.setTaxCosts(33);
        
        assertEquals(-17, fcfCalculation.getOtherPostsTaxEffect());
    }
    
    @Test
    public void checkGetNOPLAT() {
        is.setTurnover(100);
        is.setTaxCosts(33);
        is.setInterestCosts(100);
        is.setInterestIncome(100);
        
        assertEquals(67, fcfCalculation.getNOPLAT());
    }
    
    @Test
    public void checkGetDepreciation() {
        is.setDepreciation(100);
        
        assertEquals(100, fcfCalculation.getDepreciation());
    }
    
    @Test
    public void checkGetGrossCashFlow() {
        is.setTurnover(150);
        is.setTaxCosts(33);
        is.setInterestCosts(100);
        is.setInterestIncome(100);
        is.setDepreciation(50);
        
        assertEquals(67+50, fcfCalculation.getGrossCashFlow());
    }
    
    @Test
    public void checkGetNetWorkingCapitalDeltaNoPast() {
        assertEquals(Optional.empty(), fcfCalculation.getNetWorkingCapitalDelta());
    }
    
    @Test
    public void checkGetNetWorkingCapitalDeltaZero() throws InvalidPastPeriodException {
        Period past = new PeriodImpl(period.getYear()-1, false);
        past.setCurrentFinancialStatement(fs);
        period.setPastPeriod(past);
        
        assertEquals(0, (long) fcfCalculation.getNetWorkingCapitalDelta().get());
    }
    
    @Test
    public void checkGetNetWorkingCapitalDelta() throws InvalidPastPeriodException {
        BalanceSheet bsPast = new BalanceSheetImpl();
        IncomeStatement isPast = new IncomeStatementImpl();
        FinancialStatement fsPast = new FinancialStatementImpl(isPast, bsPast);
        
        Period past = new PeriodImpl(period.getYear()-1, false);
        past.setCurrentFinancialStatement(fsPast);
        period.setPastPeriod(past);
        
        bsPast.setNonInterestBearingFinancialAssets(100);
        bsPast.setInventory(50);
        bsPast.setShortTermNonInterestBearingLiabilities(75);
        
        bs.setNonInterestBearingFinancialAssets(200);
        bs.setInventory(100);
        bs.setShortTermNonInterestBearingLiabilities(150);
        
        assertEquals(75, (long) fcfCalculation.getNetWorkingCapitalDelta().get());
    }
    
}
