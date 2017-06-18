package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.ui.vm.FreeCashFlowCalculationViewModel;
import fi.niwic.dcf.ui.vm.InvestedCapitalViewModel;

public class OutputDataTables extends DataTablesAbstract {

    public OutputDataTables(InputDataTables inputDataTables) {
        PeriodDataTable investedCapitalTable = new PeriodDataTable(new InvestedCapitalViewModel());
        inputDataTables.balanceSheetTable.addDependant(investedCapitalTable);
        
        PeriodDataTable freeCashFlowTable = new PeriodDataTable(new FreeCashFlowCalculationViewModel());
        inputDataTables.balanceSheetTable.addDependant(freeCashFlowTable);
        inputDataTables.incomeStatementTable.addDependant(freeCashFlowTable);
        
        tables = new PeriodDataTable[] {
            investedCapitalTable, freeCashFlowTable
        };
    }
    
}
