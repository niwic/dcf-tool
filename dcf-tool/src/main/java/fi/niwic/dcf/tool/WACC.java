package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.InvestedCapital;

/**
 * Keskimääräisen pääomakustannuksen laskelma.
 */
public class WACC implements CostOfCapital {

    private InvestedCapital investedCapital;
    private double costOfOwnCapital;
    private double costOfBorrowedCapital;
    
    /**
     * Luo uuden keskimääräisen pääomakustannuksen laskelman. Laskelma perustuu
     * oman ja vieraan pääoman kustannuksen painoitettuun arvoon.
     * @param investedCapital sijoitetun pääoman laskelma
     */
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
        
        return (own / totalCapital * costOfOwnCapital / 100) * 100 + (borrowed / totalCapital * costOfBorrowedCapital / 100) * 100;
    }

}
