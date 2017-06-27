package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.api.Period;

public abstract class DataTablesAbstract {

    protected PeriodDataTable[] tables;
    
    public PeriodDataTable[] getList() {
        return tables;
    }
    
    public void addPeriod(Period period) {
        addPeriod(period, 0);
    }
    
    public void addPeriod(Period period, int offset) {
        for (PeriodDataTable table : tables) {
            table.addPeriod(period, offset);
            table.refresh();
        }
    }
    
    public void clear() {
        for (PeriodDataTable table : tables) {
            table.clear();
        }
    }
    
}
