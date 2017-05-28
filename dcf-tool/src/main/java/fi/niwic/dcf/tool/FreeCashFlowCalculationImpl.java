package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.InvestedCapital;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class FreeCashFlowCalculationImpl implements FreeCashFlowCalculation {

    private Period period;

    public FreeCashFlowCalculationImpl(Period period) {
        this.period = period;
    }

    @Override
    public long getOperatingProfit() {
        return period.getCurrentFinancialStatement()
                .getIncomeStatement()
                .getOperatingProfit();
    }

    @Override
    public long getTaxCosts() {
        return period.getCurrentFinancialStatement()
                .getIncomeStatement()
                .getTaxCosts();
    }

    @Override
    public long getInterestCostTaxEffect() {
        long interestCosts = getCurrentIncomeStatement().getInterestCosts();
        double realizedTaxRate = getRealizedTaxRate();

        return (long) (realizedTaxRate * interestCosts);
    }

    @Override
    public long getInterestIncomeTaxEffect() {
        long interestIncome = getCurrentIncomeStatement().getInterestIncome();
        double realizedTaxRate = getRealizedTaxRate();

        return (long) (realizedTaxRate * interestIncome);
    }

    @Override
    public long getOtherPostsTaxEffect() {
        long otherIncomeAndCosts = getCurrentIncomeStatement()
                .getOtherIncomeAndCosts();
        double realizedTaxRate = getRealizedTaxRate();

        return (long) (realizedTaxRate * otherIncomeAndCosts);
    }

    @Override
    public long getNOPLAT() {
        long[] items = {
            getOperatingProfit(),
            Math.negateExact(getTaxCosts()),
            Math.negateExact(getInterestCostTaxEffect()),
            getInterestIncomeTaxEffect()
        };

        long sum = 0;
        for (long item : items) {
            sum = Math.addExact(sum, item);
        }

        return sum;
    }

    @Override
    public long getDepreciation() {
        return getCurrentIncomeStatement().getDepreciation();
    }

    @Override
    public long getGrossCashFlow() {
        return Math.addExact(getNOPLAT(), getGrossCashFlow());
    }

    @Override
    public Optional<Long> getNetWorkingCapitalDelta() {
        long currentNOC = getCurrentBalanceSheet()
                .getInvestedCapital()
                .getNetOperatingCapital();

        Optional<Long> previous = getPreviousBalanceSheet()
                .map(BalanceSheet::getInvestedCapital)
                .map(InvestedCapital::getNetOperatingCapital);

        Optional<Long> result = previous.map(previousNOC
                -> Math.subtractExact(currentNOC, previousNOC)
        );

        return result;
    }

    @Override
    public Optional<Long> getGrossInvestments() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Long> getOperatingFreeCashFlow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Long> getNonOperatingCashFlow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Long> getFreeCashFlow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private IncomeStatement getCurrentIncomeStatement() {
        return period.getCurrentFinancialStatement()
                .getIncomeStatement();
    }

    private BalanceSheet getCurrentBalanceSheet() {
        return period.getCurrentFinancialStatement()
                .getBalanceSheet();
    }

    private Optional<BalanceSheet> getPreviousBalanceSheet() {
        return period.getPastPeriod()
                .map(Period::getCurrentFinancialStatement)
                .map(FinancialStatement::getBalanceSheet);
    }

    private double getRealizedTaxRate() {
        return period.getCurrentFinancialStatement()
                .getIncomeStatement()
                .getRealizedTaxRate();
    }

}
