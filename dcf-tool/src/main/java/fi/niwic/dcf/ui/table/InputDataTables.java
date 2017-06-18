package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.ui.vm.BalanceSheetViewModel;
import fi.niwic.dcf.ui.vm.IncomeStatementViewModel;

public class InputDataTables extends DataTablesAbstract {
    
    protected PeriodDataTable balanceSheetTable;
    protected PeriodDataTable incomeStatementTable;
    
    public InputDataTables() {
        balanceSheetTable = new PeriodDataTable(new BalanceSheetViewModel());
        incomeStatementTable = new PeriodDataTable(new IncomeStatementViewModel());
        
        tables = new PeriodDataTable[] {
            incomeStatementTable, balanceSheetTable
        };
    }
    
}
