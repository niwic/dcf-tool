package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class DCFCalculationImpl implements DCFCalculation {

    private String companyName;
    private Period period;
    private Optional<Period> perpetualPeriod;
    private Optional<CostOfCapital> costOfCapital;
    private Optional<Long> numberOfShares;

    public DCFCalculationImpl() {
        this.perpetualPeriod = Optional.empty();
        this.costOfCapital = Optional.empty();
        this.numberOfShares = Optional.empty();
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
            this.period = period;
        } else {
            period.setPastPeriod(this.period);
            if (perpetualPeriod.isPresent()) {
                perpetualPeriod.get().setPastPeriod(period);
            }
            this.period = period;
        }
    }

    @Override
    public Period getHeadPeriod() {
        return this.period;
    }

    @Override
    public void setPerpetualPeriod(Period period) throws InvalidPastPeriodException {
        this.perpetualPeriod = Optional.of(period);
        period.setPastPeriod(this.period);
    }

    @Override
    public Optional<Period> getPerpetualPeriod() {
        return perpetualPeriod;
    }

    @Override
    public Optional<CostOfCapital> getCostOfCapital() {
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
    public Optional<Long> calculateValuation() {
        Optional<Period> presentValuePeriod = presentValuePeriod();
        Optional<Period> currentPeriod = perpetualPeriod;

        long valuation = 0;
        while (currentPeriod.isPresent() && presentValuePeriod.isPresent()) {
            valuation += calculatePeriodDiscountedCashFlow(currentPeriod.get(), presentValuePeriod.get()).orElse(0L);
            currentPeriod = currentPeriod.flatMap(p -> p.getPastPeriod());
        }

        return Optional.of(valuation);
    }

    private Optional<Period> presentValuePeriod() {
        Optional<Period> currentPeriod = Optional.of(period);
        while (currentPeriod.isPresent()) {
            if (!currentPeriod.get().isPrediction()) {
                return currentPeriod;
            }

            currentPeriod = currentPeriod.get().getPastPeriod();
        }

        return Optional.empty();
    }

    private Optional<Long> calculatePeriodDiscountedCashFlow(Period period, Period pvPeriod) {
        long years = period.getYear() - pvPeriod.getYear();
        Optional<Long> result = costOfCapital.flatMap(coc -> period.getFreeCashFlowCalculation().getDiscountedFreeCashFlow(coc));

        return result;
    }

}
