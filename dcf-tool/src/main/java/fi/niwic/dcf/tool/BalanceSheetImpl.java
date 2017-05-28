package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.InvestedCapital;

/**
* @see BalanceSheet
*/
public class BalanceSheetImpl implements BalanceSheet {

    private long nonIntBearFinAss;
    private long intBearFinAss;
    private long inventory;
    private long fixedAssets;
    
    private long shortTermNonIntBearLiab;
    private long shortTermIntBearLiab;
    private long longTermLiab;
    private long boundEquity;
    private long pastProfitOrLoss;
    private long currentProfitOrLoss;
    
    @Override
    public void setNonInterestBearingFinancialAssets(long amount) {
        this.nonIntBearFinAss = amount;
    }

    @Override
    public long getNonInterestBearingFinancialAssets() {
        return this.nonIntBearFinAss;
    }

    @Override
    public void setInterestBearingFinancialAssets(long amount) {
        this.intBearFinAss = amount;
    }

    @Override
    public long getInterestBearingFinancialAssets() {
        return intBearFinAss;
    }

    @Override
    public void setInventory(long amount) {
        this.inventory = amount;
    }

    @Override
    public long getInventory() {
        return this.inventory;
    }

    @Override
    public void setFixedAssets(long amount) {
        this.fixedAssets = amount;
    }

    @Override
    public long getFixedAssets() {
        return this.fixedAssets;
    }

    @Override
    public long getTotalAssets() {
        long[] items = {
            nonIntBearFinAss,
            intBearFinAss,
            inventory,
            fixedAssets
        };
        
        long sum = 0;
        for (int n = 0; n < items.length; n++) {
            sum = Math.addExact(sum, items[n]);
        }
        
        return sum;
    }

    @Override
    public void setShortTermNonInterestBearingLiabilities(long amount) {
        this.shortTermNonIntBearLiab = amount;
    }

    @Override
    public long getShortTermNonInterestBearingLiabilities() {
        return this.shortTermNonIntBearLiab;
    }

    @Override
    public void setShortTermInterestBearingLiabilities(long amount) {
        this.shortTermIntBearLiab = amount;
    }

    @Override
    public long getShortTermInterestBearingLiabilities() {
        return this.shortTermIntBearLiab;
    }

    @Override
    public void setLongTermLiabilities(long amount) {
        this.longTermLiab = amount;
    }

    @Override
    public long getLongTermLiabilities() {
        return this.longTermLiab;
    }

    @Override
    public void setBoundEquity(long amount) {
        this.boundEquity = amount;
    }

    @Override
    public long getBoundEquity() {
        return this.boundEquity;
    }

    @Override
    public void setPastProfitsOrLoss(long amount) {
        this.pastProfitOrLoss = amount;
    }

    @Override
    public long getPastProfitsOrLoss() {
        return this.pastProfitOrLoss;
    }

    @Override
    public void setCurrentPeriodProfitOrLoss(long amount) {
        this.currentProfitOrLoss = amount;
    }

    @Override
    public long getCurrentPeriodProfitOrLoss() {
        return this.currentProfitOrLoss;
    }

    @Override
    public long getTotalEquityAndLiabilities() {
        long[] items = {
            shortTermNonIntBearLiab,
            shortTermIntBearLiab,
            longTermLiab,
            boundEquity,
            pastProfitOrLoss,
            currentProfitOrLoss
        };
        
        long sum = 0;
        for (int n = 0; n < items.length; n++) {
            sum = Math.addExact(sum, items[n]);
        }
        
        return sum;
    }

    @Override
    public boolean isBalanced() {
        return getTotalEquityAndLiabilities() == getTotalAssets();
    }
	
	@Override
	public InvestedCapital getInvestedCapital() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
    
}
