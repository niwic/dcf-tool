package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FreeCashFlowCalculationImplTest
        extends FreeCashFlowCalculationImplTestAbstract {

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
        Period past = new PeriodImpl(period.getYear()-1, false, fs);
        period.setPastPeriod(past);
        
        assertEquals(0, (long) fcfCalculation.getNetWorkingCapitalDelta().get());
    }
    
}
