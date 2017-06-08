package fi.niwic.dcf.tool;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SumTest extends Sum {

    @Test
    public void checkSum() {
        long items[] = {
            -2,-1,0,1,2
        };
        
        assertEquals(0, Sum.ofItems(items));
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkSumOverFlow() {
        long items[] = {
            Long.MAX_VALUE, Long.MAX_VALUE
        };
        
        Sum.ofItems(items);
    }
    
}
