package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.Period;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class IncomeStatementTable {

    private TableView table;
    
    public IncomeStatementTable() {
        createTable();
    }
    
    private TableView createTable() {
        table = new TableView();
        table.setEditable(true);
        initializeHeadings();
        initializeRows();
        
        return table;
    }
    
    private void initializeHeadings() {
        TableColumn headingColumn = addColumn();
        headingColumn.setCellValueFactory(new HeaderValueFactory());
    }
    
    private void initializeRows() {
        List<PeriodView> viewModel = IncomeStatementViewModel.get();
        table.setItems(FXCollections.observableArrayList(viewModel));
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
                            ev.getTableView().refresh();
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
