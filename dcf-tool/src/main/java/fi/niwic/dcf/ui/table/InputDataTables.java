package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.ui.MainScene;
import fi.niwic.dcf.ui.vm.BalanceSheetViewModel;
import fi.niwic.dcf.ui.vm.IncomeStatementViewModel;
import javafx.scene.control.Label;

public class InputDataTables extends DataTablesAbstract {
    
    protected PeriodDataTable balanceSheetTable;
    protected PeriodDataTable incomeStatementTable;
    
    public InputDataTables(MainScene mainScene, Label errorLabel) {
        balanceSheetTable = new PeriodDataTable(new BalanceSheetViewModel(), errorLabel);
        incomeStatementTable = new PeriodDataTable(new IncomeStatementViewModel(), errorLabel);
        
        balanceSheetTable.addDependent(mainScene);
        incomeStatementTable.addDependent(mainScene);
        
        tables = new PeriodDataTable[] {
            incomeStatementTable, balanceSheetTable
        };
    }
    
}
