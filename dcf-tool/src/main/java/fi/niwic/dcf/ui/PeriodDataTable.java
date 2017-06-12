package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.Period;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class PeriodDataTable {

    private TableView table;
    
    public PeriodDataTable(PeriodViewModel viewModel) {
        createTable();
        table.setItems(FXCollections.observableArrayList(viewModel.get()));
    }
    
    private TableView createTable() {
        table = new TableView();
        table.setEditable(true);
        initializeHeadings();
        
        return table;
    }
    
    private void initializeHeadings() {
        TableColumn headingColumn = addColumn();
        headingColumn.setCellValueFactory(new HeaderValueFactory());
    }
    
    private TableColumn addPeriodColumn(Period period) {
        TableColumn column = addColumn();
        column.setText(period.getYear().toString());
        column.setEditable(true);
        column.setCellValueFactory(new PeriodValueFactory(period));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(
                new EventHandler<CellEditEvent<PeriodView, String>>() {
                    @Override
                    public void handle(CellEditEvent<PeriodView, String> ev) {
                        try {
                            Long value = Long.parseLong(ev.getNewValue());
                            ev.getRowValue().getSetter().accept(period, value);
                            table.refresh();
                        } catch (NumberFormatException ex) {
                            
                        }
                    }
                }
        );
        
        return column;
    }
    
    private TableColumn addColumn() {
        TableColumn column = new TableColumn();
        column.setSortable(false);
        column.setEditable(false);
        table.getColumns().add(column);
        
        return column;
    }
    
    public TableView getTable() {
        return table;
    }
    
    public void addPeriod(Period period) {
        addPeriodColumn(period);
    }
    
}
