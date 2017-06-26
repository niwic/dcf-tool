package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class PerpetualFreeCashFlowCalculation extends FreeCashFlowCalculationImpl {

    public PerpetualFreeCashFlowCalculation(Period period) {
        super(period);
    }
    
    @Override
    public Optional<Long> getDiscountedFreeCashFlow(CostOfCapital costOfCapital) {
        long perpetualNoplat = getNOPLAT();
        double coc = costOfCapital.getCostOfCapital();
        double noplatGrowth = getNOPLATGrowth();
        double investmentGrowth = getInvestmentGrowthRate();
        
        return Optional.of((long) ((perpetualNoplat * (1-investmentGrowth) * (1+noplatGrowth)) / (coc - noplatGrowth)));
    }
    
    private Double getNOPLATGrowth() {
        long perpetualNOPLAT = getNOPLAT();
        long headPeriodNOPLAT = period.getFreeCashFlowCalculation().getNOPLAT();
        long profitGrowth = perpetualNOPLAT - headPeriodNOPLAT;
        
        return (double) profitGrowth / headPeriodNOPLAT;
    }
    
    private Double getInvestmentGrowthRate() {
        long noplat = getNOPLAT();
        long nwcDelta = getNetWorkingCapitalDelta().orElse(0L);
        long grossInv = getGrossInvestments().orElse(0L);
        long depreciation = getDepreciation();
        
        long investmentGrowth = nwcDelta + grossInv - depreciation;
        
        return (double) investmentGrowth / noplat;
    }

}
