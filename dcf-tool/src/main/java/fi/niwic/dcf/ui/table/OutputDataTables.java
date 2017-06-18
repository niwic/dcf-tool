package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.ui.vm.InvestedCapitalViewModel;

public class OutputDataTables extends DataTablesAbstract {

    public OutputDataTables(InputDataTables inputDataTables) {
        PeriodDataTable investedCapitalTable = new PeriodDataTable(new InvestedCapitalViewModel());
        inputDataTables.balanceSheetTable.addDependant(investedCapitalTable);
        
        tables = new PeriodDataTable[] {
            investedCapitalTable
        };
    }
    
}
