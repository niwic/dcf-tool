package fi.niwic.dcf.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HeaderValueFactory implements Callback<TableColumn.CellDataFeatures<PeriodView,String>, ObservableValue<String>> {
    
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<PeriodView, String> cdf) {
        PeriodView pv = cdf.getValue();
        String value = pv.getHeader();
        
        return new SimpleStringProperty(value);
    }
}
