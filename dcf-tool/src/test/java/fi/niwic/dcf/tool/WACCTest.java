package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.InvestedCapital;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class WACCTest {

    private WACC wacc;
   
    private BalanceSheet bs;
    private InvestedCapital ic;
   
    @Before
    public void setUp() {
        bs = new BalanceSheetImpl();
        ic = new InvestedCapitalImpl(bs);
        wacc = new WACC(ic);
    }
    
    @Test
    public void checkGetCostOfCapital() {
        bs.setBoundEquity(100);
        bs.setLongTermLiabilities(100);
        wacc.setCostOfOwnCapital(5.0);
        wacc.setCostOfBorrowedCapital(5.0);
        
        assertEquals(5.0, wacc.getCostOfCapital(), 0.01);
    }
    
    @Test
    public void checkGetCostOfCapitalUneven() {
        bs.setBoundEquity(33);
        bs.setLongTermLiabilities(99);
        bs.setInterestBearingFinancialAssets(33);
        wacc.setCostOfOwnCapital(3.3);
        wacc.setCostOfBorrowedCapital(33.0);
        
        assertEquals(23.1, wacc.getCostOfCapital(), 0.01);
    }
    
}
