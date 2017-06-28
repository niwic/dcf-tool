package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.ui.vm.Refreshable;

public abstract class DataTablesAbstract implements Refreshable {

    protected PeriodDataTable[] tables;
    
    /**
     * Palauttaa kaikki data-taulut.
     * @return data-taulut
     */
    public PeriodDataTable[] getList() {
        return tables;
    }
    
    /**
     * Lisää jakson taulukkoihin.
     * @param period jakso
     */
    public void addPeriod(Period period) {
        addPeriod(period, 0);
    }
    
    /**
     * Lisää jakson taulukkoihin tiettyyn paikkaan.
     * @param period jakso
     * @param offset paikka laskettu viimeisestä sarakkeesta
     */
    public void addPeriod(Period period, int offset) {
        for (PeriodDataTable table : tables) {
            table.addPeriod(period, offset);
            table.refresh();
        }
    }
    
    /**
     * Päivittää taulukoiden arvot.
     */
    public void refresh() {
        for (PeriodDataTable table : tables) {
            table.refresh();
        }
    }
    
    /**
     * Tyhjentää taulukot.
     */
    public void clear() {
        for (PeriodDataTable table : tables) {
            table.clear();
        }
    }
    
}
