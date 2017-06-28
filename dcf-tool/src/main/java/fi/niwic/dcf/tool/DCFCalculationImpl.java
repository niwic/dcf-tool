package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class DCFCalculationImpl implements DCFCalculation {

    private String companyName;
    private Period period;
    private Period firstPeriod;
    private CostOfCapital costOfCapital;
    private Optional<Period> perpetualPeriod;
    private Optional<Long> numberOfShares;

    /**
     * Luo uusi diskontattu kassavirta-analyysi kyseisellä pääomakustannuslaskelmalla.
     * @param costOfCapital pääomakustannuslakelma
     */
    public DCFCalculationImpl(CostOfCapital costOfCapital) {
        this.perpetualPeriod = Optional.empty();
        this.numberOfShares = Optional.empty();
        this.costOfCapital = costOfCapital;
    }

    @Override
    public void setCompanyName(String name) {
        this.companyName = name;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void addPeriod(Period period) throws InvalidPastPeriodException {
        if (this.period == null) {
            this.firstPeriod = period;
            this.period = period;
        } else {
            period.setPastPeriod(this.period);
            this.period = period;
        }
        
        if (perpetualPeriod.isPresent()) {
            perpetualPeriod.get().setPastPeriod(period);
        }
    }

    @Override
    public Period getHeadPeriod() {
        return this.period;
    }

    @Override
    public void setPerpetualPeriod(Period period) throws InvalidPastPeriodException {
        if (this.period != null) {
            period.setPastPeriod(this.period);
        }
        
        this.perpetualPeriod = Optional.of(period);
    }

    @Override
    public Optional<Period> getPerpetualPeriod() {
        return perpetualPeriod;
    }

    @Override
    public CostOfCapital getCostOfCapital() {
        return costOfCapital;
    }

    @Override
    public void setNumberOfShares(Long shares) {
        this.numberOfShares = Optional.of(shares);
    }

    @Override
    public Optional<Long> getNumberOfShares() {
        return numberOfShares;
    }

    @Override
    public Optional<Double> calculateValuationPerShare() {
        return getNumberOfShares().flatMap(this::calculateValuationPerShare);
    }
    
    private Optional<Double> calculateValuationPerShare(long shares) {
        return calculateValuation().map(value -> (double) value / shares);
    }
    
    @Override
    public Optional<Long> calculateValuation() {
        Optional<Period> currentPeriod = perpetualPeriod;

        long valuation = 0;
        while (currentPeriod.isPresent()) {
            valuation += calculatePeriodDiscountedCashFlow(currentPeriod.get()).orElse(0L);
            currentPeriod = currentPeriod.flatMap(p -> p.getPastPeriod());
        }

        long finAss = firstPeriod.getCurrentFinancialStatement().getBalanceSheet().getInterestBearingFinancialAssets();
        long shortTermLiab = firstPeriod.getCurrentFinancialStatement().getBalanceSheet().getShortTermInterestBearingLiabilities();
        long longTermLiab = firstPeriod.getCurrentFinancialStatement().getBalanceSheet().getLongTermLiabilities();
        
        return Optional.of(valuation + finAss - shortTermLiab - longTermLiab);
    }

    private Optional<Long> calculatePeriodDiscountedCashFlow(Period period) {
        Optional<Long> result = period.getFreeCashFlowCalculation().getDiscountedFreeCashFlow(costOfCapital);
        
        return result;
    }

}
