package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.InvestedCapital;

public class WACC implements CostOfCapital {

    private InvestedCapital investedCapital;
    private Double costOfOwnCapital;
    private Double costOfBorrowedCapital;
    
    public WACC(InvestedCapital investedCapital) {
        this.investedCapital = investedCapital;
    }
    
    public void setCostOfOwnCapital(Double rate) {
        this.costOfOwnCapital = rate;
    }
    
    public void setCostOfBorrowedCapital(Double rate) {
        this.costOfBorrowedCapital = rate;
    }
    
    @Override
    public Double getCostOfCapital() {
        Long totalCapital = investedCapital.getInvestedCapitalFromEquityAndLiabilities();
        Double own = (double) investedCapital.getEquity();
        Double borrowed = (double) (investedCapital.getLiabilities() - investedCapital.getInterestBearingFinancialAssets());
        
        return (own/totalCapital * costOfOwnCapital/100) * 100 + (borrowed/totalCapital * costOfBorrowedCapital / 100) * 100;
    }

}
