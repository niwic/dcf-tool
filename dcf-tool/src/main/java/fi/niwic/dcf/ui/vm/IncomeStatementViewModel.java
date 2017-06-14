package fi.niwic.dcf.ui.vm;

import java.util.ArrayList;

public class IncomeStatementViewModel implements PeriodViewModel {
    
    @Override
    public ArrayList<PeriodView> get() {
        ArrayList<PeriodView> viewModel = new ArrayList();
        
        PeriodView rows[] = {
            getTurnover(), getCosts(), getEBITDA(), getDepreciation(),
            getOperatingProfit(), getOtherIncomeAndCosts(), getEBIT(),
            getInterestIncome(), getInterestCosts(), getEBT(), getTax(),
            getNetIncome(), getDividends()};
        
        for (PeriodView row : rows) {
            viewModel.add(row);
        }
        
        return viewModel;
    }
    
    private static PeriodView getTurnover() {
        return new PeriodView(
                "Turnover",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getTurnover(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setTurnover(value)
        );
    }
    
    private static PeriodView getCosts() {
        return new PeriodView(
                "Costs",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getCosts(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setCosts(value)
        );
    }
    
    private static PeriodView getEBITDA() {
        return new PeriodView(
                "EBITDA",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getEBITDA(),
                (period, value) -> {}
        );
    }
    
    private static PeriodView getDepreciation() {
        return new PeriodView(
                "Depreciation",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getDepreciation(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setDepreciation(value)
        );
    }
    
     private static PeriodView getOperatingProfit() {
        return new PeriodView(
                "Operating Profit",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getOperatingProfit(),
                (period, value) -> {}
        );
    }
     
    private static PeriodView getOtherIncomeAndCosts() {
        return new PeriodView(
                "Other Income and Costs",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getOtherIncomeAndCosts(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setOtherIncomeAndCosts(value)
        );
    }
    
    private static PeriodView getEBIT() {
        return new PeriodView(
                "EBIT",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getEBIT(),
                (period, value) -> {}
        );
    }
    
    private static PeriodView getInterestIncome() {
        return new PeriodView(
                "Interest Income",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getInterestIncome(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setInterestIncome(value)
        );
    }
    
    private static PeriodView getInterestCosts() {
        return new PeriodView(
                "Interest Costs",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getInterestCosts(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setInterestCosts(value)
        );
    }
    
    private static PeriodView getEBT() {
        return new PeriodView(
                "EBT",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getEBT(),
                (period, value) -> {}
        );
    }
    
    private static PeriodView getTax() {
        return new PeriodView(
                "Tax",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getTaxCosts(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setTaxCosts(value)
        );
    }
    
    private static PeriodView getNetIncome() {
        return new PeriodView(
                "Net Income",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getNetIncome(),
                (period, value) -> {}
        );
    }
    
    private static PeriodView getDividends() {
        return new PeriodView(
                "Dividend Costs",
                period -> period.getCurrentFinancialStatement().getIncomeStatement().getDividendCosts(),
                (period, value) -> period.getCurrentFinancialStatement().getIncomeStatement().setDividendCosts(value)
        );
    }
    
}
