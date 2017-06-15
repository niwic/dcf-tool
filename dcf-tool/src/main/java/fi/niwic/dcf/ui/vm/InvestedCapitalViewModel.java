package fi.niwic.dcf.ui.vm;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.InvestedCapital;
import fi.niwic.dcf.api.Period;
import java.util.ArrayList;

public class InvestedCapitalViewModel implements PeriodViewModel {

    @Override
    public ArrayList<PeriodView> get() {
        ArrayList<PeriodView> viewModel = new ArrayList();

        PeriodView rows[] = {
            netOperatingCapital, longTermAssets, liabilities, equity
        };

        for (PeriodView row : rows) {
            viewModel.add(row);
        }

        return viewModel;
    }

    private static InvestedCapital investedCapital(Period period) {
        return period.getCurrentFinancialStatement().getBalanceSheet().getInvestedCapital();
    }

    private static PeriodView netOperatingCapital = new PeriodView(
        "Net Operating Capital",
        period -> investedCapital(period).getNetOperatingCapital(),
        (period, value) -> { }
    );

    private static PeriodView longTermAssets = new PeriodView(
        "Long term assets",
        period -> investedCapital(period).getLongTermAssets(),
        (period, value) -> { }
    );

    private static PeriodView liabilities = new PeriodView(
        "Liabilities",
        period -> investedCapital(period).getLiabilities(),
        (period, value) -> { }
    );

    private static PeriodView equity = new PeriodView(
        "Equity",
        period -> investedCapital(period).getEquity(),
        (period, value) -> { }
    );

}
