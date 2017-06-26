package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.ui.vm.FreeCashFlowCalculationViewModel;
import fi.niwic.dcf.ui.vm.InvestedCapitalViewModel;
import java.util.Optional;

public class OutputDataTables extends DataTablesAbstract {

    public OutputDataTables(InputDataTables inputDataTables, DCFCalculation dcf) {
        PeriodDataTable investedCapitalTable = new PeriodDataTable(new InvestedCapitalViewModel());
        inputDataTables.balanceSheetTable.addDependant(investedCapitalTable);

        PeriodDataTable freeCashFlowTable = new PeriodDataTable(new FreeCashFlowCalculationViewModel(dcf));
        inputDataTables.balanceSheetTable.addDependant(freeCashFlowTable);
        inputDataTables.incomeStatementTable.addDependant(freeCashFlowTable);
        
        tables = new PeriodDataTable[] {
            investedCapitalTable, freeCashFlowTable
        };
    }
    
}
