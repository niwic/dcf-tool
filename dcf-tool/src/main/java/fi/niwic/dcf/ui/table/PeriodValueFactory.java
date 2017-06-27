package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.ui.vm.PeriodView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class PeriodValueFactory implements Callback<TableColumn.CellDataFeatures<PeriodView, String>, ObservableValue<String>> {

    private Period period;
    
    public PeriodValueFactory(Period period) {
        this.period = period;
    }
    
    public Period getPeriod() {
        return period;
    }
    
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<PeriodView, String> cdf) {
        PeriodView pv = cdf.getValue();
        Long value = pv.getGetter().apply(period);
        
        String output = "";
        if (value != null) {
            output = value.toString();
        }
        
        return new SimpleStringProperty(output);
    }
    
}
