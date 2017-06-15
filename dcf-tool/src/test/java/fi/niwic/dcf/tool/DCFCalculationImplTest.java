package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DCFCalculationImplTest {

    private DCFCalculation dcf;
    
    @Before
    public void setUp() {
        dcf = new DCFCalculationImpl();
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
    
}
