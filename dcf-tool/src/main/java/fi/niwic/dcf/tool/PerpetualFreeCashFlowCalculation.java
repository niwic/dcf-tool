package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.Period;
import java.util.Optional;

public class PerpetualFreeCashFlowCalculation extends FreeCashFlowCalculationImpl {

    public PerpetualFreeCashFlowCalculation(Period period) {
        super(period);
    }
    
    @Override
    public Optional<Long> getDiscountedFreeCashFlow(CostOfCapital costOfCapital) {
        return period.getPastPeriod().map(pastPeriod -> getDiscountedFreeCashFlow(pastPeriod, costOfCapital));
    }
    
    public Long getDiscountedFreeCashFlow(Period pastPeriod, CostOfCapital costOfCapital) {
        long perpetualNoplat = getNOPLAT();
        double coc = costOfCapital.getCostOfCapital() / 100;
        double noplatGrowth = getNOPLATGrowth(pastPeriod);
        double investmentGrowth = getInvestmentGrowthRate(pastPeriod);
        
        double realCashFlow = (((double) perpetualNoplat * (1-investmentGrowth) * (1+noplatGrowth)) / (coc - noplatGrowth));
        
        return (long) (realCashFlow / Math.pow(1+coc, (double) period.getDiscountYears()));
    }
    
    private Double getNOPLATGrowth(Period pastPeriod) {
        long perpetualNOPLAT = getNOPLAT();
        long pastPeriodNOPLAT = pastPeriod.getFreeCashFlowCalculation().getNOPLAT();
        long profitGrowth = perpetualNOPLAT - pastPeriodNOPLAT;
        
        return (double) profitGrowth / pastPeriodNOPLAT;
    }
    
    private Double getInvestmentGrowthRate(Period pastPeriod) {
        BalanceSheet pastBalanceSheet = pastPeriod.getCurrentFinancialStatement().getBalanceSheet();
        
        long noplat = getNOPLAT();
        long nwcDelta = getNetWorkingCapitalDelta(pastBalanceSheet);
        long grossInv = getGrossInvestments(pastBalanceSheet);
        long depreciation = getDepreciation();
        
        long investmentGrowth = nwcDelta + grossInv - depreciation;
        
        return (double) investmentGrowth / noplat;
    }

}
