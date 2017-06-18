package fi.niwic.dcf.ui.vm;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.Period;
import java.util.ArrayList;

public class BalanceSheetViewModel implements PeriodViewModel {

    @Override
    public ArrayList<PeriodView> get() {
        ArrayList<PeriodView> viewModel = new ArrayList();

        PeriodView rows[] = {
            faNonIB, faIB, inventory, fixedAssets, totalAssets, stnibl, stibl,
            lt, boundEquity, pastProfitLoss, currentProfitLoss, totalEquityLiabilities
        };

        for (PeriodView row : rows) {
            viewModel.add(row);
        }

        return viewModel;
    }

    private static BalanceSheet balanceSheet(Period period) {
        return period.getCurrentFinancialStatement().getBalanceSheet();
    }

    private static PeriodView faNonIB = new PeriodView(
        "Financial Assets Non Interest-Bearing",
        period -> balanceSheet(period).getNonInterestBearingFinancialAssets(),
        (period, value) -> balanceSheet(period).setNonInterestBearingFinancialAssets(value)
    );

    private static PeriodView faIB = new PeriodView(
        "Financial Assets",
        period -> balanceSheet(period).getInterestBearingFinancialAssets(),
        (period, value) -> balanceSheet(period).setInterestBearingFinancialAssets(value)
    );

    private static PeriodView inventory = new PeriodView(
        "Inventory",
        period -> balanceSheet(period).getInventory(),
        (period, value) -> balanceSheet(period).setInventory(value)
    );

    private static PeriodView fixedAssets = new PeriodView(
        "Fixed Assets",
        period -> balanceSheet(period).getFixedAssets(),
        (period, value) -> balanceSheet(period).setFixedAssets(value)
    );

    private static PeriodView totalAssets = new PeriodView(
        "Total Assets",
        period -> balanceSheet(period).getTotalAssets(),
        (period, value) -> { }
    );

    private static PeriodView stnibl = new PeriodView(
        "Short-term Non-IB Liabilities",
        period -> balanceSheet(period).getShortTermNonInterestBearingLiabilities(),
        (period, value) -> balanceSheet(period).setShortTermNonInterestBearingLiabilities(value)
    );

    private static PeriodView stibl = new PeriodView(
        "Short-Term Liabilities",
        period -> balanceSheet(period).getShortTermInterestBearingLiabilities(),
        (period, value) -> balanceSheet(period).setShortTermInterestBearingLiabilities(value)
    );

    private static PeriodView lt = new PeriodView(
        "Long-term Liabilities",
        period -> balanceSheet(period).getLongTermLiabilities(),
        (period, value) -> balanceSheet(period).setLongTermLiabilities(value)
    );

    private static PeriodView boundEquity = new PeriodView(
        "Bound Equity",
        period -> balanceSheet(period).getBoundEquity(),
        (period, value) -> balanceSheet(period).setBoundEquity(value)
    );

    private static PeriodView pastProfitLoss = new PeriodView(
        "Past Profit/Loss",
        period -> balanceSheet(period).getPastProfitsOrLoss(),
        (period, value) -> balanceSheet(period).setPastProfitsOrLoss(value)
    );

    private static PeriodView currentProfitLoss = new PeriodView(
        "Current Profit/Loss",
        period -> balanceSheet(period).getCurrentPeriodProfitOrLoss(),
        (period, value) -> balanceSheet(period).setCurrentPeriodProfitOrLoss(value)
    );

    private static PeriodView totalEquityLiabilities = new PeriodView(
        "Total Equity & Liabilities",
        period -> balanceSheet(period).getTotalEquityAndLiabilities(),
        (period, value) -> { }
    );

}
