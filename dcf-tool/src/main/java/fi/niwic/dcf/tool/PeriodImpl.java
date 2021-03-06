package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

/**
 * Tilikausi.
 * @see Period
 */
public class PeriodImpl implements Period {

    protected int year;
    private long discountYears;
    private boolean isPrediction;

    private FinancialStatement financialStatement;
    private Optional<Period> pastPeriod;

    /**
     * Luo uuden tilikauden.
     * @param year tilikauden vuosi
     * @param isPrediction true jos kyseessa arvio
     * @param fs tilikauden tilinpäätös
     */
    public PeriodImpl(int year, boolean isPrediction, FinancialStatement fs) {
        this.year = year;
        this.isPrediction = isPrediction;
        this.pastPeriod = Optional.empty();
        this.financialStatement = fs;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public void setDiscountYears(long years) {
        this.discountYears = years;
    }
    
    @Override
    public Long getDiscountYears() {
        return discountYears;
    }
    
    @Override
    public boolean isPrediction() {
        return isPrediction;
    }

    @Override
    public FinancialStatement getCurrentFinancialStatement() {
        return financialStatement;
    }

    @Override
    public void setPastPeriod(Period pastPeriod) throws InvalidPastPeriodException {
        if (pastPeriod.getYear() != year - 1) {
            throw new InvalidPastPeriodException(pastPeriod.getYear(), year);
        }
        
        if (isPrediction()) {
            setDiscountYears(pastPeriod.getDiscountYears() + 1);
        }

        this.pastPeriod = Optional.of(pastPeriod);
    }

    @Override
    public Optional<Period> getPastPeriod() {
        return pastPeriod;
    }

    @Override
    public FreeCashFlowCalculation getFreeCashFlowCalculation() {
        return new FreeCashFlowCalculationImpl(this);
    }

}
