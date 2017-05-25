package fi.niwic.dcf.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BalanceSheetImplTest {
    
    private BalanceSheetImpl balanceSheet;
    
    @Before
    public void setUp() {
        this.balanceSheet = new BalanceSheetImpl();
    }
    
    @Test
    public void checkGetTotalAssets() {
        balanceSheet.setNonInterestBearingFinancialAssets(1);
        balanceSheet.setInterestBearingFinancialAssets(1);
        balanceSheet.setInventory(1);
        balanceSheet.setFixedAssets(1);
        
        assertEquals(4, balanceSheet.getTotalAssets());
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetTotalAssetsOverflow() {
        balanceSheet.setNonInterestBearingFinancialAssets(Long.MAX_VALUE);
        balanceSheet.setInterestBearingFinancialAssets(1);
        
        balanceSheet.getTotalAssets();
    }
    
    @Test
    public void checkGetTotalAssetsMaxNIBFinancialAssets() {
        balanceSheet.setNonInterestBearingFinancialAssets(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalAssets());
    }
    
    @Test
    public void checkGetTotalAssetsMaxIBFinancialAssets() {
        balanceSheet.setInterestBearingFinancialAssets(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalAssets());
    }
    
    @Test
    public void checkGetTotalAssetsMaxInventory() {
        balanceSheet.setInventory(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalAssets());
    }
    
    @Test
    public void checkGetTotalAssetsMaxFixedAssets() {
        balanceSheet.setFixedAssets(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalAssets());
    }
    
    @Test
    public void checkGetTotalEquityAndLiabilities() {
        balanceSheet.setShortTermNonInterestBearingLiabilities(1);
        balanceSheet.setShortTermInterestBearingLiabilities(1);
        balanceSheet.setLongTermLiabilities(1);
        balanceSheet.setBoundEquity(1);
        balanceSheet.setPastProfitsOrLoss(1);
        balanceSheet.setCurrentPeriodProfitOrLoss(1);
        
        assertEquals(6, balanceSheet.getTotalEquityAndLiabilities());
    }
    
    @Test(expected=ArithmeticException.class)
    public void checkGetTotalEquityAndLiabilitiesOverflow() {
        balanceSheet.setShortTermNonInterestBearingLiabilities(Long.MAX_VALUE);
        balanceSheet.setShortTermInterestBearingLiabilities(1);
        
        balanceSheet.getTotalEquityAndLiabilities();
    }
    
    @Test
    public void checkGetTotalEquityAndLiabilitiesMaxSTNIBL() {
        balanceSheet.setShortTermNonInterestBearingLiabilities(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalEquityAndLiabilities());
    }
    
    @Test
    public void checkGetTotalEquityAndLiabilitiesMaxSTIBL() {
        balanceSheet.setShortTermInterestBearingLiabilities(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalEquityAndLiabilities());
    }
    
    @Test
    public void checkGetTotalEquityAndLiabilitiesMaxLTL() {
        balanceSheet.setLongTermLiabilities(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalEquityAndLiabilities());
    }
    
    @Test
    public void checkGetTotalEquityAndLiabilitiesMaxBoundEq() {
        balanceSheet.setBoundEquity(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalEquityAndLiabilities());
    }
    
    @Test
    public void checkGetTotalEquityAndLiabilitiesMaxPPOL() {
        balanceSheet.setPastProfitsOrLoss(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalEquityAndLiabilities());
    }
    
    @Test
    public void checkGetTotalEquityAndLiabilitiesMaxCPPOL() {
        balanceSheet.setCurrentPeriodProfitOrLoss(Long.MAX_VALUE);
        
        assertEquals(Long.MAX_VALUE, balanceSheet.getTotalEquityAndLiabilities());
    }
    
    @Test
    public void checkIsBalanced() {
        balanceSheet.setNonInterestBearingFinancialAssets(1);
        balanceSheet.setInterestBearingFinancialAssets(1);
        balanceSheet.setInventory(2);
        balanceSheet.setFixedAssets(2);
        
        balanceSheet.setShortTermNonInterestBearingLiabilities(1);
        balanceSheet.setShortTermInterestBearingLiabilities(1);
        balanceSheet.setLongTermLiabilities(1);
        balanceSheet.setBoundEquity(1);
        balanceSheet.setPastProfitsOrLoss(1);
        balanceSheet.setCurrentPeriodProfitOrLoss(1);
        
        assertTrue(balanceSheet.isBalanced());        
    }
    
    @Test
    public void checkIsNotBalanced() {
        balanceSheet.setNonInterestBearingFinancialAssets(1);
        balanceSheet.setInterestBearingFinancialAssets(1);
        balanceSheet.setInventory(1);
        balanceSheet.setFixedAssets(1);
        
        balanceSheet.setShortTermNonInterestBearingLiabilities(1);
        balanceSheet.setShortTermInterestBearingLiabilities(1);
        balanceSheet.setLongTermLiabilities(1);
        balanceSheet.setBoundEquity(1);
        balanceSheet.setPastProfitsOrLoss(1);
        balanceSheet.setCurrentPeriodProfitOrLoss(1);
        
        assertFalse(balanceSheet.isBalanced());        
    }
    
}
