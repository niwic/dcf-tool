package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.api.Period;

public abstract class DataTablesAbstract {

    protected PeriodDataTable[] tables;
    
    public PeriodDataTable[] getList() {
        return tables;
    }
    
    public void addPeriod(Period period) {
        for (PeriodDataTable table : tables) {
            table.addPeriod(period);
        }
    }
    
    public void clear() {
        for (PeriodDataTable table : tables) {
            table.clear();
        }
    }
    
}
