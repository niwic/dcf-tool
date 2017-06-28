package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.IncomeStatement;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class FreeCashFlowCalculationImpl implements FreeCashFlowCalculation {

    protected Period period;

    /**
     * Luo uuden kassavirta-analyysin jaksolle.
     * @param period jakso joka analysoidaan
     */
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
        
        return Sum.ofItems(items);
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

    protected long getNetWorkingCapitalDelta(BalanceSheet previousBS) {

        long previousNOC = previousBS.getInvestedCapital().getNetOperatingCapital();
        long currentNOC = getCurrentBalanceSheet().getInvestedCapital().getNetOperatingCapital();

        return Math.subtractExact(previousNOC, currentNOC);

    }

    @Override
    public Optional<Long> getGrossInvestments() {

        Optional<BalanceSheet> previousBS = getPreviousBalanceSheet();
        Optional<Long> result = previousBS.map(this::getGrossInvestments);

        return result;
    }

    protected long getGrossInvestments(BalanceSheet previousBS) {

        long previousLT = previousBS.getInvestedCapital().getLongTermAssets();
        long currentLT = getCurrentBalanceSheet().getInvestedCapital().getLongTermAssets();
        long currentDepr = getCurrentIncomeStatement().getDepreciation();

        long items[] = {
            previousLT,
            Math.negateExact(currentLT),
            Math.negateExact(currentDepr)
        };

        return Math.negateExact(Sum.ofItems(items));

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
            getNetWorkingCapitalDelta(previousBS),
            Math.negateExact(getGrossInvestments(previousBS))
        };

        return Sum.ofItems(items);
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
    
    protected long getFreeCashFlow(BalanceSheet previousBS) {
        long items[] = {
            getOperatingFreeCashFlow(previousBS),
            getNonOperatingCashFlow()
        };
        
        return Sum.ofItems(items);
    }

    private IncomeStatement getCurrentIncomeStatement() {
        return period.getCurrentFinancialStatement().getIncomeStatement();
    }

    private BalanceSheet getCurrentBalanceSheet() {
        return period.getCurrentFinancialStatement().getBalanceSheet();
    }

    protected Optional<BalanceSheet> getPreviousBalanceSheet() {
        return period.getPastPeriod()
                .map(Period::getCurrentFinancialStatement)
                .map(FinancialStatement::getBalanceSheet);
    }

    private double getRealizedTaxRate() {
        return period.getCurrentFinancialStatement().getIncomeStatement().getRealizedTaxRate();
    }
    
    @Override
    public Optional<Long> getDiscountedFreeCashFlow(CostOfCapital coc) {
        Optional<BalanceSheet> previousBS = getPreviousBalanceSheet();
        Optional<Long> result = previousBS.map(bs -> getDiscountedFreeCashFlow(bs, coc));
        
        return result;
    }
    
    private long getDiscountedFreeCashFlow(BalanceSheet previousBS, CostOfCapital coc) {
        long fcf = getFreeCashFlow(previousBS);
        double discountedFcf = fcf / Math.pow(1 + coc.getCostOfCapital() / 100, period.getDiscountYears());
        
        return (long) discountedFcf;
    }

}
