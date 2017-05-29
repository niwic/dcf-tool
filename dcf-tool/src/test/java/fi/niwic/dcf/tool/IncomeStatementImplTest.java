package fi.niwic.dcf.tool;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class IncomeStatementImplTest {

    private IncomeStatementImpl incomeStatement;

    @Before
    public void setUp() {
        this.incomeStatement = new IncomeStatementImpl();
    }

    @Test
    public void checkEBITDAcalculationPositive() {
        incomeStatement.setTurnover(100);
        incomeStatement.setCosts(50);

        assertEquals(50, incomeStatement.getEBITDA());
    }

    @Test
    public void checkEBITDAcalculationWithZeros() {
        incomeStatement.setTurnover(0);
        incomeStatement.setCosts(0);

        assertEquals(0, incomeStatement.getEBITDA());
    }

    @Test
    public void checkEBITDAcalculationNegative() {
        incomeStatement.setTurnover(100);
        incomeStatement.setCosts(200);

        assertEquals(-100, incomeStatement.getEBITDA());
    }

    @Test
    public void checkEBITDAcalculationWithMaxValues() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setCosts(Long.MAX_VALUE);

        assertEquals(0, incomeStatement.getEBITDA());
    }

    @Test
    public void checkEBITDAcalculationWithMinValues() {
        incomeStatement.setTurnover(Long.MIN_VALUE);
        incomeStatement.setCosts(Long.MIN_VALUE);

        assertEquals(0, incomeStatement.getEBITDA());
    }

    @Test(expected = ArithmeticException.class)
    public void checkEBITDAcalculationOverflow() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setCosts(Long.MIN_VALUE);

        incomeStatement.getEBITDA();
    }

    @Test
    public void checkGetOperatingProfit() {
        incomeStatement.setTurnover(200);
        incomeStatement.setCosts(50);
        incomeStatement.setDepreciation(50);

        assertEquals(100, incomeStatement.getOperatingProfit());
    }

    @Test
    public void checkGetOperatingProfitWithMaxValues() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setDepreciation(Long.MAX_VALUE);

        assertEquals(0, incomeStatement.getOperatingProfit());
    }

    @Test
    public void checkGetOperatingProfitWithMinValues() {
        incomeStatement.setTurnover(Long.MIN_VALUE);
        incomeStatement.setDepreciation(Long.MIN_VALUE);

        assertEquals(0, incomeStatement.getOperatingProfit());
    }

    @Test(expected = ArithmeticException.class)
    public void checkGetOperatingProfitOverflow() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setDepreciation(Long.MIN_VALUE);

        incomeStatement.getOperatingProfit();
    }

    @Test
    public void checkGetEBIT() {
        incomeStatement.setTurnover(200);
        incomeStatement.setCosts(50);
        incomeStatement.setDepreciation(50);
        incomeStatement.setOtherIncomeAndCosts(100);

        assertEquals(200, incomeStatement.getEBIT());
    }

    @Test
    public void checkGetEBITWithMaxValues() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setDepreciation(Long.MAX_VALUE);

        assertEquals(0, incomeStatement.getEBIT());
    }

    @Test
    public void checkGetEBITWithMaxAndMinValues() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setOtherIncomeAndCosts(Long.MIN_VALUE);
        
        assertEquals(-1, incomeStatement.getEBIT());
    }

    @Test(expected = ArithmeticException.class)
    public void checkGetEBITProfitOverflow() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setOtherIncomeAndCosts(Long.MAX_VALUE);

        incomeStatement.getEBIT();
    }

    @Test
    public void checkGetEBT() {
        incomeStatement.setTurnover(400);
        incomeStatement.setCosts(50);
        incomeStatement.setDepreciation(50);
        incomeStatement.setInterestIncome(75);
        incomeStatement.setInterestCosts(50);

        assertEquals(325, incomeStatement.getEBT());
    }

    @Test
    public void checkGetEBTWithMaxValues() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setInterestCosts(Long.MAX_VALUE);

        assertEquals(0, incomeStatement.getEBT());
    }

    @Test
    public void checkGetEBTWithMinValues() {
        incomeStatement.setTurnover(Long.MIN_VALUE);
        incomeStatement.setInterestCosts(Long.MIN_VALUE);

        assertEquals(0, incomeStatement.getEBT());
    }

    @Test
    public void checkGetEBTWithMaxAndMinValues() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setInterestCosts(Long.MAX_VALUE);
        incomeStatement.setInterestIncome(Long.MAX_VALUE);

        assertEquals(Long.MAX_VALUE, incomeStatement.getEBT());
    }

    @Test(expected = ArithmeticException.class)
    public void checkGetEBTProfitOverflowByCosts() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setInterestCosts(Long.MIN_VALUE);

        incomeStatement.getEBT();
    }

    @Test(expected = ArithmeticException.class)
    public void checkGetEBTProfitOverflowByIncome() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setInterestIncome(Long.MAX_VALUE);

        incomeStatement.getEBT();
    }

    @Test
    public void checkGetNetIncome() {
        incomeStatement.setTurnover(200);
        incomeStatement.setCosts(50);
        incomeStatement.setTaxCosts(50);

        assertEquals(100, incomeStatement.getNetIncome());
    }

    @Test
    public void checkGetNetIncomeWithMaxValues() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setTaxCosts(Long.MAX_VALUE);

        assertEquals(0, incomeStatement.getNetIncome());
    }

    @Test
    public void checkGetNetIncomeWithMinValues() {
        incomeStatement.setTurnover(Long.MIN_VALUE);
        incomeStatement.setTaxCosts(Long.MIN_VALUE);

        assertEquals(0, incomeStatement.getNetIncome());
    }

    @Test(expected = ArithmeticException.class)
    public void checkGetNetIncomeProfitOverflow() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setTaxCosts(Long.MIN_VALUE);

        incomeStatement.getNetIncome();
    }

    @Test
    public void checkRealizedTaxRate() {
        incomeStatement.setTurnover(100);
        incomeStatement.setTaxCosts(50);

        assertEquals(0.5, incomeStatement.getRealizedTaxRate(), 0.01);
    }

    @Test
    public void checkRealizedTaxRateOneThird() {
        incomeStatement.setTurnover(99);
        incomeStatement.setTaxCosts(33);

        assertEquals(0.33, incomeStatement.getRealizedTaxRate(), 0.004);
    }

    @Test
    public void checkRealizedTaxRateMax() {
        incomeStatement.setTurnover(Long.MAX_VALUE);
        incomeStatement.setTaxCosts(0);

        assertEquals(0, incomeStatement.getRealizedTaxRate(), 0);
    }

    @Test
    public void checkRealizedTaxRateMin() {
        incomeStatement.setTurnover(0);
        incomeStatement.setTaxCosts(Long.MAX_VALUE);

        assertEquals(Double.POSITIVE_INFINITY, incomeStatement.getRealizedTaxRate(), 0);
    }

}
