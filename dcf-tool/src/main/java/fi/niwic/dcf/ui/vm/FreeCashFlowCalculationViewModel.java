package fi.niwic.dcf.ui.vm;

import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.FreeCashFlowCalculation;
import fi.niwic.dcf.api.Period;
import java.util.ArrayList;

public class FreeCashFlowCalculationViewModel implements PeriodViewModel {

    private DCFCalculation dcf;
    
    public FreeCashFlowCalculationViewModel(DCFCalculation dcf) {
        this.dcf = dcf;
    }
    
    @Override
    public ArrayList<PeriodView> get() {
        ArrayList<PeriodView> viewModel = new ArrayList();

        PeriodView rows[] = {
            operatingProfit, taxCosts, interestCostTaxEffect, interestIncomeTaxEffect,
            otherTaxEffect, noplat, depreciation, grossCashFlow, nwcChange,
            grossInvestments, operatingCashFlow, nonOperCashFlow, freeCashFlow,
            discountedFreeCashFlow
        };

        for (PeriodView row : rows) {
            viewModel.add(row);
        }

        return viewModel;
    }

    private FreeCashFlowCalculation fcf(Period period) {
        return period.getFreeCashFlowCalculation();
    }

    private PeriodView operatingProfit = new PeriodView(
        "Operating Profit",
        period -> fcf(period).getOperatingProfit(),
        (period, value) -> { }
    );
    
    private PeriodView taxCosts = new PeriodView(
        "Tax Costs",
        period -> fcf(period).getTaxCosts(),
        (period, value) -> { }
    );
    
    private PeriodView interestCostTaxEffect = new PeriodView(
        "Interest Cost Tax Effect",
        period -> fcf(period).getInterestCostTaxEffect(),
        (period, value) -> { }
    );
    
    private PeriodView interestIncomeTaxEffect = new PeriodView(
        "Interest Income Tax Effect",
        period -> fcf(period).getInterestIncomeTaxEffect(),
        (period, value) -> { }
    );
 
    private PeriodView otherTaxEffect = new PeriodView(
        "Other Posts Tax Effect",
        period -> fcf(period).getOtherPostsTaxEffect(),
        (period, value) -> { }
    );
    
    private PeriodView noplat = new PeriodView(
        "NOPLAT",
        period -> fcf(period).getNOPLAT(),
        (period, value) -> { }
    );
    
    private PeriodView depreciation = new PeriodView(
        "Depreciation",
        period -> fcf(period).getDepreciation(),
        (period, value) -> { }
    );
    
    private PeriodView grossCashFlow = new PeriodView(
        "Gross Cash-flow",
        period -> fcf(period).getGrossCashFlow(),
        (period, value) -> { }
    );
    
    private PeriodView nwcChange = new PeriodView(
        "Net Working Capital Change",
        period -> fcf(period).getNetWorkingCapitalDelta().orElse(null),
        (period, value) -> { }
    );
    
    private PeriodView grossInvestments = new PeriodView(
        "Gross Investments",
        period -> fcf(period).getGrossInvestments().orElse(null),
        (period, value) -> { }
    );
    
    private PeriodView operatingCashFlow = new PeriodView(
        "Operating Free Cash-flow",
        period -> fcf(period).getOperatingFreeCashFlow().orElse(null),
        (period, value) -> { }
    );
    
    private PeriodView nonOperCashFlow = new PeriodView(
        "Non-operating Free Cash-flow",
        period -> fcf(period).getNonOperatingCashFlow(),
        (period, value) -> { }
    );
    
    private PeriodView freeCashFlow = new PeriodView(
        "Free Cash-flow",
        period -> fcf(period).getFreeCashFlow().orElse(null),
        (period, value) -> { }
    );
    
    private PeriodView discountedFreeCashFlow = new PeriodView(
        "Discounted Free Cash-flow",
        period -> fcf(period).getDiscountedFreeCashFlow(dcf.getCostOfCapital()).orElse(null),
        (period, value) -> { }
    );
    
}
