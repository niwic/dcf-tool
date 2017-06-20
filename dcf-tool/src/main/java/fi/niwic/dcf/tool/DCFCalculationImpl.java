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
            if (perpetualPeriod != null) {
                perpetualPeriod.setPastPeriod(period);
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
        this.perpetualPeriod = period;
        this.perpetualPeriod.setPastPeriod(this.period);
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
        Optional<Period> presentValuePeriod = presentValuePeriod();
        Optional<Period> currentPeriod = Optional.of(period);
        
        long valuation = 0;
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
        
        long perpetualNoplat = perpetualPeriod.getFreeCashFlowCalculation().getNOPLAT();
        double coc = costOfCapital.getCostOfCapital();
        double noplatGrowth = getNOPLATGrowth();
        double investmentGrowth = getInvestmentGrowthRate();
        
        return (long) ((perpetualNoplat * (1-investmentGrowth) * (1+noplatGrowth)) / (coc - noplatGrowth));
    }
    
    private Double getNOPLATGrowth() {
        long perpetualNOPLAT = perpetualPeriod.getFreeCashFlowCalculation().getNOPLAT();
        long headPeriodNOPLAT = period.getFreeCashFlowCalculation().getNOPLAT();
        long profitGrowth = perpetualNOPLAT - headPeriodNOPLAT;
        
        return (double) profitGrowth / headPeriodNOPLAT;
    }
    
    private Double getInvestmentGrowthRate() {
        long noplat = perpetualPeriod.getFreeCashFlowCalculation().getNOPLAT();
        long nwcDelta = perpetualPeriod.getFreeCashFlowCalculation().getNetWorkingCapitalDelta().orElse(0L);
        long grossInv = perpetualPeriod.getFreeCashFlowCalculation().getGrossInvestments().orElse(0L);
        long depreciation = perpetualPeriod.getFreeCashFlowCalculation().getDepreciation();
        
        long investmentGrowth = nwcDelta + grossInv - depreciation;
        
        return (double) investmentGrowth / noplat;
    }

}
