package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.Period;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class PeriodValueFactory implements Callback<TableColumn.CellDataFeatures<PeriodView,String>, ObservableValue<String>> {

    private Period period;
    
    public PeriodValueFactory(Period period) {
        this.period = period;
    }
    
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<PeriodView, String> cdf) {
        PeriodView pv = cdf.getValue();
        Long value = pv.getGetter().apply(period);
        
        return new SimpleStringProperty(value.toString());
    }
    
}
