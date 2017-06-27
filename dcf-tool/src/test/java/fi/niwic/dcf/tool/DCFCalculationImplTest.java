package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DCFCalculationImplTest {

    private CostOfCapital coc;
    private DCFCalculation dcf;
    
    @Before
    public void setUp() {
        BalanceSheet bs = new BalanceSheetImpl();
        coc = new WACC(bs.getInvestedCapital());
        dcf = new DCFCalculationImpl(coc);
    }
    
    @Test
    public void checkSetGetCompanyName() {
        String companyName = "test";
        dcf.setCompanyName(companyName);
        assertEquals(companyName, dcf.getCompanyName());
    }
    
    @Test
    public void checkAddInitialPeriod() throws InvalidPastPeriodException {
        Period period = new PeriodImpl(2017, false);
        dcf.addPeriod(period);
        assertEquals(period, dcf.getHeadPeriod());
    }
    
    @Test
    public void checkAddAdditionalPeriod() throws InvalidPastPeriodException {
        Period period = new PeriodImpl(2017, false);
        Period additional = new PeriodImpl(2018, false);
        dcf.addPeriod(period);
        dcf.addPeriod(additional);
        assertEquals(additional, dcf.getHeadPeriod());
        assertEquals(Optional.of(period), additional.getPastPeriod());
    }
    
    @Test
    public void checkSetPerpetualPeriod() throws InvalidPastPeriodException {
        Period perpetual = new PerpetualPeriod();
        dcf.setPerpetualPeriod(perpetual);
        
        assertEquals(perpetual, dcf.getPerpetualPeriod().get());
    }
    
    @Test
    public void checkSetPerpetualPeriodThenAddPeriod() throws InvalidPastPeriodException {
        Period perpetual = new PerpetualPeriod();
        dcf.setPerpetualPeriod(perpetual);
        
        Period period = new PeriodImpl(2017, false);
        dcf.addPeriod(period);
        
        assertEquals(period, perpetual.getPastPeriod().get());
        assertEquals(2018, (long) perpetual.getYear());
        assertEquals(1, (long) perpetual.getDiscountYears());
    }
    
    @Test
    public void checkAddPeriodThenSetPerpetualPeriod() throws InvalidPastPeriodException {
        Period period = new PeriodImpl(2017, false);
        dcf.addPeriod(period);
        
        Period perpetual = new PerpetualPeriod();
        dcf.setPerpetualPeriod(perpetual);
        
        assertEquals(period, perpetual.getPastPeriod().get());
        assertEquals(2018, (long) perpetual.getYear());
        assertEquals(1, (long) perpetual.getDiscountYears());
    }
    
    @Test
    public void checkGetCostOfCapital() {
        assertEquals(coc, dcf.getCostOfCapital());
    }
    
    @Test
    public void checkNumberOfShares() {
        dcf.setNumberOfShares(123456L);
        assertEquals(123456L, (long) dcf.getNumberOfShares().get());
    }
    
}
