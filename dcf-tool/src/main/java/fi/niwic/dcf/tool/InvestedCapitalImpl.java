package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.InvestedCapital;

/**
 * Sijoitettu pääoma.
 * @see InvestedCapital
 */
public class InvestedCapitalImpl implements InvestedCapital {

    private BalanceSheet bs;

    /**
     * Luo uuden laskelman sijoitetusta pääomasta.
     * @param balanceSheet tase josta lasketaan
     */
    public InvestedCapitalImpl(BalanceSheet balanceSheet) {
        bs = balanceSheet;
    }

    @Override
    public long getNetOperatingCapital() {
        long items[] = {
            bs.getNonInterestBearingFinancialAssets(),
            bs.getInventory(),
            Math.negateExact(bs.getShortTermNonInterestBearingLiabilities())
        };

        return Sum.ofItems(items);
    }

    @Override
    public long getLongTermAssets() {
        return bs.getFixedAssets();
    }

    @Override
    public long getLiabilities() {
        return Math.addExact(
                bs.getLongTermLiabilities(),
                bs.getShortTermInterestBearingLiabilities()
        );
    }

    @Override
    public long getEquity() {
        long items[] = {
            bs.getBoundEquity(),
            bs.getPastProfitsOrLoss(),
            bs.getCurrentPeriodProfitOrLoss()
        };

        return Sum.ofItems(items);
    }

    @Override
    public long getInterestBearingFinancialAssets() {
        return bs.getInterestBearingFinancialAssets();
    }

    @Override
    public long getInvestedCapitalFromAssets() {
        long items[] = {
            getNetOperatingCapital(),
            getLongTermAssets()
        };

        return Sum.ofItems(items);
    }

    @Override
    public long getInvestedCapitalFromEquityAndLiabilities() {
        long items[] = {
            getLiabilities(),
            getEquity(),
            Math.negateExact(getInterestBearingFinancialAssets())
        };

        return Sum.ofItems(items);
    }

}
