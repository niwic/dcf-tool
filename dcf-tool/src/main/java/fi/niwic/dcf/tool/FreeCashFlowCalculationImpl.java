package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class FreeCashFlowCalculationImpl implements FreeCashFlowCalculation {

    private Period period;

    public FreeCashFlowCalculationImpl(Period period) {
        this.period = period;
    }

    @Override
    public long getOperatingProfit() {
        return period.getCurrentFinancialStatement().getIncomeStatement().getOperatingProfit();
    }

    @Override
    public long getTaxCosts() {
        return period.getCurrentFinancialStatement().getIncomeStatement().getTaxCosts();
    }

    @Override
    public long getInterestCostTaxEffect() {
        long interestCosts = getCurrentIncomeStatement().getInterestCosts();
        double realizedTaxRate = getRealizedTaxRate();

        return Math.round(realizedTaxRate * interestCosts);
    }

    @Override
    public long getInterestIncomeTaxEffect() {
        long interestIncome = getCurrentIncomeStatement().getInterestIncome();
        double realizedTaxRate = getRealizedTaxRate();

        return Math.round(realizedTaxRate * interestIncome);
    }

    @Override
    public long getOtherPostsTaxEffect() {
        long otherIncomeAndCosts = getCurrentIncomeStatement().getOtherIncomeAndCosts();
        double realizedTaxRate = getRealizedTaxRate();

        return Math.round(realizedTaxRate * otherIncomeAndCosts);
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
        return Math.addExact(getNOPLAT(), getDepreciation());
    }

    @Override
    public Optional<Long> getNetWorkingCapitalDelta() {

        Optional<BalanceSheet> previousBS = getPreviousBalanceSheet();
        Optional<Long> result = previousBS.map(this::getNetWorkingCapitalDelta);

        return result;
    }

    private long getNetWorkingCapitalDelta(BalanceSheet previousBS) {

        long previousNOC = previousBS.getInvestedCapital().getNetOperatingCapital();
        long currentNOC = getCurrentBalanceSheet().getInvestedCapital().getNetOperatingCapital();

        return Math.subtractExact(currentNOC, previousNOC);

    }

    @Override
    public Optional<Long> getGrossInvestments() {

        Optional<BalanceSheet> previousBS = getPreviousBalanceSheet();
        Optional<Long> result = previousBS.map(this::getGrossInvestments);

        return result;
    }

    private long getGrossInvestments(BalanceSheet previousBS) {

        long previousLT = previousBS.getInvestedCapital().getLongTermAssets();
        long currentLT = getCurrentBalanceSheet().getInvestedCapital().getLongTermAssets();
        long currentDepr = getCurrentIncomeStatement().getDepreciation();

        long items[] = {
            previousLT,
            Math.negateExact(currentLT),
            Math.negateExact(currentDepr)
        };

        long sum = 0;
        for (long item : items) {
            sum = Math.addExact(sum, item);
        }

        return sum;

    }

    @Override
    public Optional<Long> getOperatingFreeCashFlow() {

        Optional<BalanceSheet> previousBS = getPreviousBalanceSheet();
        Optional<Long> result = previousBS.map(this::getOperatingFreeCashFlow);

        return result;
    }

    private long getOperatingFreeCashFlow(BalanceSheet previousBS) {
        long items[] = {
            getGrossCashFlow(),
            Math.negateExact(getNetWorkingCapitalDelta(previousBS)),
            Math.negateExact(getGrossInvestments(previousBS))
        };

        long sum = 0;
        for (long item : items) {
            sum = Math.addExact(sum, item);
        }

        return sum;
    }

    @Override
    public Long getNonOperatingCashFlow() {
        long otherIncomeAndCosts = getCurrentIncomeStatement().getOtherIncomeAndCosts();
        double realizedTaxRate = getRealizedTaxRate();

        return Math.round((1 - realizedTaxRate) * otherIncomeAndCosts);
    }

    @Override
    public Optional<Long> getFreeCashFlow() {
        Optional<BalanceSheet> previousBS = getPreviousBalanceSheet();
        Optional<Long> result = previousBS.map(this::getFreeCashFlow);
        
        return result;
    }
    
    private long getFreeCashFlow(BalanceSheet previousBS) {
        long items[] = {
            getOperatingFreeCashFlow(previousBS),
            getNonOperatingCashFlow()
        };
        
        long sum = 0;
        for (long item : items) {
            sum = Math.addExact(sum, item);
        }
        
        return sum;
    }

    private IncomeStatement getCurrentIncomeStatement() {
        return period.getCurrentFinancialStatement().getIncomeStatement();
    }

    private BalanceSheet getCurrentBalanceSheet() {
        return period.getCurrentFinancialStatement().getBalanceSheet();
    }

    private Optional<BalanceSheet> getPreviousBalanceSheet() {
        return period.getPastPeriod()
                .map(Period::getCurrentFinancialStatement)
                .map(FinancialStatement::getBalanceSheet);
    }

    private double getRealizedTaxRate() {
        return period.getCurrentFinancialStatement().getIncomeStatement().getRealizedTaxRate();
    }

}
