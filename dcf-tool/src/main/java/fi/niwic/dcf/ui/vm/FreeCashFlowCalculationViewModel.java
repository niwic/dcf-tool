package fi.niwic.dcf.ui.vm;

import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.Period;
import java.util.ArrayList;

public class FreeCashFlowCalculationViewModel implements PeriodViewModel {

    @Override
    public ArrayList<PeriodView> get() {
        ArrayList<PeriodView> viewModel = new ArrayList();

        PeriodView rows[] = {
            operatingProfit, taxCosts, interestCostTaxEffect, interestIncomeTaxEffect,
            otherTaxEffect, noplat, depreciation, grossCashFlow, nwcChange,
            grossInvestments, operatingCashFlow, nonOperCashFlow, freeCashFlow
        };

        for (PeriodView row : rows) {
            viewModel.add(row);
        }

        return viewModel;
    }

    private static FreeCashFlowCalculation fcf(Period period) {
        return period.getFreeCashFlowCalculation();
    }

    private static PeriodView operatingProfit = new PeriodView(
        "Operating Profit",
        period -> fcf(period).getOperatingProfit(),
        (period, value) -> { }
    );
    
    private static PeriodView taxCosts = new PeriodView(
        "Tax Costs",
        period -> fcf(period).getTaxCosts(),
        (period, value) -> { }
    );
    
    private static PeriodView interestCostTaxEffect = new PeriodView(
        "Interest Cost Tax Effect",
        period -> fcf(period).getInterestCostTaxEffect(),
        (period, value) -> { }
    );
    
    private static PeriodView interestIncomeTaxEffect = new PeriodView(
        "Interest Income Tax Effect",
        period -> fcf(period).getInterestIncomeTaxEffect(),
        (period, value) -> { }
    );
 
    private static PeriodView otherTaxEffect = new PeriodView(
        "Other Posts Tax Effect",
        period -> fcf(period).getOtherPostsTaxEffect(),
        (period, value) -> { }
    );
    
    private static PeriodView noplat = new PeriodView(
        "NOPLAT",
        period -> fcf(period).getNOPLAT(),
        (period, value) -> { }
    );
    
    private static PeriodView depreciation = new PeriodView(
        "Depreciation",
        period -> fcf(period).getDepreciation(),
        (period, value) -> { }
    );
    
    private static PeriodView grossCashFlow = new PeriodView(
        "Gross Cash-flow",
        period -> fcf(period).getGrossCashFlow(),
        (period, value) -> { }
    );
    
    private static PeriodView nwcChange = new PeriodView(
        "Net Working Capital Change",
        period -> fcf(period).getNetWorkingCapitalDelta().orElse(null),
        (period, value) -> { }
    );
    
    private static PeriodView grossInvestments = new PeriodView(
        "Gross Investments",
        period -> fcf(period).getGrossInvestments().orElse(null),
        (period, value) -> { }
    );
    
    private static PeriodView operatingCashFlow = new PeriodView(
        "Operating Free Cash-flow",
        period -> fcf(period).getOperatingFreeCashFlow().orElse(null),
        (period, value) -> { }
    );
    
    private static PeriodView nonOperCashFlow = new PeriodView(
        "Non-operating Free Cash-flow",
        period -> fcf(period).getNonOperatingCashFlow(),
        (period, value) -> { }
    );
    
    private static PeriodView freeCashFlow = new PeriodView(
        "Free Cash-flow",
        period -> fcf(period).getFreeCashFlow().orElse(null),
        (period, value) -> { }
    );
    
}
