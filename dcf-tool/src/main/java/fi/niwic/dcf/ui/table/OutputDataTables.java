package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.ui.vm.FreeCashFlowCalculationViewModel;
import fi.niwic.dcf.ui.vm.InvestedCapitalViewModel;
import javafx.scene.control.Label;

public class OutputDataTables extends DataTablesAbstract {

    /**
     * Alustaa laskelmataulukot.
     * @param inputDataTables syöttötaulukot
     * @param dcf laskelma
     * @param errorLabel virheet kirjoitetaan tähän
     */
    public OutputDataTables(InputDataTables inputDataTables, DCFCalculation dcf, Label errorLabel) {
        PeriodDataTable investedCapitalTable = new PeriodDataTable(new InvestedCapitalViewModel(), errorLabel);
        inputDataTables.balanceSheetTable.addDependent(investedCapitalTable);

        PeriodDataTable freeCashFlowTable = new PeriodDataTable(new FreeCashFlowCalculationViewModel(dcf), errorLabel);
        inputDataTables.balanceSheetTable.addDependent(freeCashFlowTable);
        inputDataTables.incomeStatementTable.addDependent(freeCashFlowTable);
        
        tables = new PeriodDataTable[] {
            investedCapitalTable, freeCashFlowTable
        };
    }
    
}
