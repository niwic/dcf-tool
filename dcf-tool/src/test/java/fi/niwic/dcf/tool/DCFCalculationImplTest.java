package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DCFCalculationImplTest {

    private BalanceSheet bs;
    private CostOfCapital coc;
    private DCFCalculation dcf;
    
    @Before
    public void setUp() {
        bs = new BalanceSheetImpl();
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
        Period period = new PeriodImpl(2017, false, null);
        dcf.addPeriod(period);
        assertEquals(period, dcf.getHeadPeriod());
    }
    
    @Test
    public void checkAddAdditionalPeriod() throws InvalidPastPeriodException {
        Period period = new PeriodImpl(2017, false, null);
        Period additional = new PeriodImpl(2018, false, null);
        dcf.addPeriod(period);
        dcf.addPeriod(additional);
        assertEquals(additional, dcf.getHeadPeriod());
        assertEquals(Optional.of(period), additional.getPastPeriod());
    }
    
    @Test
    public void checkSetPerpetualPeriod() throws InvalidPastPeriodException {
        Period perpetual = new PerpetualPeriod(null);
        dcf.setPerpetualPeriod(perpetual);
        
        assertEquals(perpetual, dcf.getPerpetualPeriod().get());
    }
    
    @Test
    public void checkSetPerpetualPeriodThenAddPeriod() throws InvalidPastPeriodException {
        Period perpetual = new PerpetualPeriod(null);
        dcf.setPerpetualPeriod(perpetual);
        
        Period period = new PeriodImpl(2017, false, null);
        dcf.addPeriod(period);
        
        assertEquals(period, perpetual.getPastPeriod().get());
        assertEquals(2018, (long) perpetual.getYear());
        assertEquals(1, (long) perpetual.getDiscountYears());
    }
    
    @Test
    public void checkAddPeriodThenSetPerpetualPeriod() throws InvalidPastPeriodException {
        Period period = new PeriodImpl(2017, false, null);
        dcf.addPeriod(period);
        
        Period perpetual = new PerpetualPeriod(null);
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
    
    private void setUpValuationTest() throws Exception {
        BalanceSheet bsPast = new BalanceSheetImpl();
        IncomeStatement isPast = new IncomeStatementImpl();
        FinancialStatement fsPast = new FinancialStatementImpl(isPast, bsPast);
        
        Period periodPast = new PeriodImpl(1234, false, fsPast);
        dcf.addPeriod(periodPast);
        
        IncomeStatement is = new IncomeStatementImpl();
        FinancialStatement fs = new FinancialStatementImpl(is, bs);
        
        Period period = new PerpetualPeriod(fs);
        dcf.setPerpetualPeriod(period);
        period.setDiscountYears(10);
        
        coc.setCostOfOwnCapital(9.2);
        coc.setCostOfBorrowedCapital(4.35);
        
        isPast.setTurnover(2168);
        isPast.setCosts(1626);
        isPast.setDepreciation(245);
        isPast.setInterestCosts(28);
        isPast.setTaxCosts(84);
        
        bsPast.setInterestBearingFinancialAssets(515);
        bsPast.setInventory(446);
        bsPast.setFixedAssets(1167);
        bsPast.setShortTermNonInterestBearingLiabilities(296);
        bsPast.setShortTermInterestBearingLiabilities(42);
        bsPast.setLongTermLiabilities(591);
        bsPast.setBoundEquity(160);
        bsPast.setPastProfitsOrLoss(852);
        bsPast.setCurrentPeriodProfitOrLoss(184);
        
        is.setTurnover(2211);
        is.setCosts(1658);
        is.setDepreciation(250);
        is.setInterestCosts(29);
        is.setTaxCosts(85);
        
        bs.setInterestBearingFinancialAssets(525);
        bs.setInventory(455);
        bs.setFixedAssets(1190);
        bs.setShortTermNonInterestBearingLiabilities(302);
        bs.setShortTermInterestBearingLiabilities(43);
        bs.setLongTermLiabilities(603);
        bs.setBoundEquity(160);
        bs.setPastProfitsOrLoss(872);
        bs.setCurrentPeriodProfitOrLoss(188);
    }
    
    @Test
    public void checkCalculateValuation() throws Exception {
        setUpValuationTest();
        assertEquals(1206, (long) dcf.calculateValuation().get());
    }
    
    @Test
    public void checkCalculateValuationPerShare() throws Exception {
        setUpValuationTest();
        dcf.setNumberOfShares(10L);
        assertEquals(120.6, (double) dcf.calculateValuationPerShare().get(), 0.01);
    }
    
}
