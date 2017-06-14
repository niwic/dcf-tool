package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.ui.vm.BalanceSheetViewModel;
import fi.niwic.dcf.ui.vm.IncomeStatementViewModel;
import fi.niwic.dcf.ui.vm.InvestedCapitalViewModel;

public class PeriodDataTables {

    private PeriodDataTable[] tables;
    
    public PeriodDataTables() {
        PeriodDataTable incomeStatementTable = new PeriodDataTable(new IncomeStatementViewModel());
        PeriodDataTable balanceSheetTable = new PeriodDataTable(new BalanceSheetViewModel());
        PeriodDataTable investedCapitalTable = new PeriodDataTable(new InvestedCapitalViewModel());
        balanceSheetTable.addDependant(investedCapitalTable);
        
        tables = new PeriodDataTable[] {
            incomeStatementTable, balanceSheetTable, investedCapitalTable
        };
    }
    
    public PeriodDataTable[] getList() {
        return tables;
    }
    
    public void addPeriod(Period period) {
        for (PeriodDataTable table : tables) {
            table.addPeriod(period);
        }
    }
    
}
