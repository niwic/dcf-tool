package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.InvestedCapital;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class InvestedCapitalImplTest {

    private BalanceSheet balanceSheet;
    private InvestedCapital investedCapital;
    
    @Before
    public void setUp() {
        balanceSheet = new BalanceSheetImpl();
        investedCapital = new InvestedCapitalImpl(balanceSheet);
    }
    
    @Test
    public void checkGetNetOperatingCapital() {
        balanceSheet.setNonInterestBearingFinancialAssets(5);
        balanceSheet.setInventory(5);
        balanceSheet.setShortTermNonInterestBearingLiabilities(2);
        
        assertEquals(8, investedCapital.getNetOperatingCapital());
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetNetOperatingCapitalOverflow() {
        balanceSheet.setNonInterestBearingFinancialAssets(Long.MAX_VALUE);
        balanceSheet.setInventory(1);
        
        investedCapital.getNetOperatingCapital();
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetNetOperatingCapitalUnderflow() {
        balanceSheet.setInventory(1);
        balanceSheet.setShortTermNonInterestBearingLiabilities(Long.MIN_VALUE);
        
        investedCapital.getNetOperatingCapital();
    }
    
    @Test    
    public void checkGetLongTermAssets() {
        balanceSheet.setFixedAssets(1);
        
        assertEquals(1, investedCapital.getLongTermAssets());
    }
    
    @Test
    public void checkGetLiabilities() {
        balanceSheet.setLongTermLiabilities(5);
        balanceSheet.setShortTermInterestBearingLiabilities(5);
        
        assertEquals(10, investedCapital.getLiabilities());
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetLiabilitiesOverflow() {
        balanceSheet.setLongTermLiabilities(Long.MAX_VALUE);
        balanceSheet.setShortTermInterestBearingLiabilities(1);
        
        investedCapital.getLiabilities();
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetLiabilitiesUnderflow() {
        balanceSheet.setLongTermLiabilities(1);
        balanceSheet.setShortTermInterestBearingLiabilities(Long.MAX_VALUE);
        
        investedCapital.getLiabilities();
    }
    
    @Test
    public void checkGetEquity() {
        balanceSheet.setBoundEquity(1);
        balanceSheet.setPastProfitsOrLoss(2);
        balanceSheet.setCurrentPeriodProfitOrLoss(3);
        
        assertEquals(6, investedCapital.getEquity());
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetEquityOverflow() {
        balanceSheet.setBoundEquity(Long.MAX_VALUE);
        balanceSheet.setPastProfitsOrLoss(1);
        
        investedCapital.getEquity();
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetEquityUnderflow() {
        balanceSheet.setPastProfitsOrLoss(1);
        balanceSheet.setCurrentPeriodProfitOrLoss(Long.MAX_VALUE);
        
        investedCapital.getEquity();
    }
    
    @Test
    public void checkGetInvestedCapitalFromAssets() {
        balanceSheet.setNonInterestBearingFinancialAssets(1);
        balanceSheet.setInventory(2);
        balanceSheet.setShortTermNonInterestBearingLiabilities(3);
        balanceSheet.setFixedAssets(4);
        
        assertEquals(4, investedCapital.getInvestedCapitalFromAssets());
    }
    
    @Test
    public void checkGetInvestedCapitalFromEquityAndLiabilities() {
        balanceSheet.setBoundEquity(1);
        balanceSheet.setLongTermLiabilities(2);
        
        assertEquals(3, investedCapital.getInvestedCapitalFromEquityAndLiabilities());
    }
    
}
