package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class DCFCalculationImpl implements DCFCalculation {

    private String companyName;
    private Period period;
    private Period perpetualPeriod;
    private CostOfCapital costOfCapital;
    private long numberOfShares;

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
            this.period = period;
        }
    }

    @Override
    public Period getHeadPeriod() {
        return this.period;
    }

    @Override
    public void setPerpetualPeriod(Period period) {
        this.perpetualPeriod = period;
    }

    @Override
    public Period getPerpetualPeriod() {
        return perpetualPeriod;
    }

    @Override
    public void setCostOfCapital(CostOfCapital costOfCapital) {
        this.costOfCapital = costOfCapital;
    }

    @Override
    public CostOfCapital getCostOfCapital() {
        return costOfCapital;
    }

    @Override
    public void setNumberOfShares(Long shares) {
        this.numberOfShares = shares;
    }

    @Override
    public long getNumberOfShares() {
        return numberOfShares;
    }

    @Override
    public long calculateValuation() {
        long valuation = 0;
        Optional<Period> presentValuePeriod = presentValuePeriod();
        Optional<Period> currentPeriod = Optional.of(period);
        while (currentPeriod.isPresent() && presentValuePeriod.isPresent()) {
            valuation += calculatePeriodDiscountedCashFlow(currentPeriod.get(), presentValuePeriod.get());
        }
        
        valuation += calculatePerpetualPeriodDiscountedCashFlow();
        
        return valuation;
    }
    
    private Optional<Period> presentValuePeriod() {
        Optional<Period> currentPeriod = Optional.of(period);
        while (currentPeriod.isPresent()) {
            if (!currentPeriod.get().isPrediction())
                return currentPeriod;
            
            currentPeriod = currentPeriod.get().getPastPeriod();
        }
        
        return Optional.empty();
    }
    
    private long calculatePeriodDiscountedCashFlow(Period currentPeriod, Period presentValuePeriod) {
        if (!currentPeriod.isPrediction()) return 0;
        
        long years = currentPeriod.getYear() - presentValuePeriod.getYear();
        long fcf = currentPeriod.getFreeCashFlowCalculation().getFreeCashFlow().orElse(0L);
        double discountedFcf = fcf / Math.pow(1 + costOfCapital.getCostOfCapital()/100, years);
        
        return (long) discountedFcf;
    }
    
    private long calculatePerpetualPeriodDiscountedCashFlow() {
        if (perpetualPeriod == null) return 0;
        
        double coc = costOfCapital.getCostOfCapital();
        
        long profitGrowth = 
                perpetualPeriod.getFreeCashFlowCalculation().getNOPLAT() -
                period.getFreeCashFlowCalculation().getNOPLAT();
        double rateOfGrowth = profitGrowth / period.getFreeCashFlowCalculation().getNOPLAT();
        
        long investmentGrowth = 
                perpetualPeriod.getFreeCashFlowCalculation().getNetWorkingCapitalDelta().orElse(0L) + 
                perpetualPeriod.getFreeCashFlowCalculation().getGrossInvestments().orElse(0L) -
                perpetualPeriod.getFreeCashFlowCalculation().getDepreciation();
        long investmentRate = investmentGrowth / perpetualPeriod.getFreeCashFlowCalculation().getNOPLAT();
        
        return (long) (perpetualPeriod.getFreeCashFlowCalculation().getNOPLAT() * (1-investmentRate) * (1+rateOfGrowth) / (coc - rateOfGrowth));
    }

}
