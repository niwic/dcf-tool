package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.FreeCashFlowCalculation;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PerpetualPeriodTest {

    private PerpetualPeriod period;

    @Before
    public void before() {
        period = new PerpetualPeriod(null);
    }
    
    @Test
    public void checkGetFreeCashFlowCalculation() {
        FreeCashFlowCalculation fcf = period.getFreeCashFlowCalculation();
        assertTrue(period.getFreeCashFlowCalculation() instanceof PerpetualFreeCashFlowCalculation);
    }
    
}
