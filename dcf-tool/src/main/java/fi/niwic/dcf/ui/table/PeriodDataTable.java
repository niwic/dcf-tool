package fi.niwic.dcf.ui.table;

import fi.niwic.dcf.ui.vm.PeriodView;
import fi.niwic.dcf.ui.vm.PeriodViewModel;
import fi.niwic.dcf.api.Period;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class PeriodDataTable {

    private List<PeriodDataTable> dependants;
    private TableView table;

    public PeriodDataTable(PeriodViewModel viewModel) {
        createTable();
        dependants = new ArrayList();
        table.setItems(FXCollections.observableArrayList(viewModel.get()));
        table.setPrefHeight(25 * (table.getItems().size() + 1.01));
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
        headingColumn.setPrefWidth(300);
    }

    private TableColumn addPeriodColumn(Period period) {
        TableColumn column = addColumn();
        column.setText(period.getYear().toString());
        column.setEditable(true);
        column.setCellValueFactory(new PeriodValueFactory(period));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(onEditCommit(period));

        return column;
    }

    private EventHandler<CellEditEvent<PeriodView, String>> onEditCommit(Period period) {
        return new EventHandler<CellEditEvent<PeriodView, String>>() {
            @Override
            public void handle(CellEditEvent<PeriodView, String> ev) {
                try {
                    Long value = Long.parseLong(ev.getNewValue());
                    ev.getRowValue().getSetter().accept(period, value);
                    refresh();
                } catch (NumberFormatException ex) {

                }
            }
        };
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

    public void addDependant(PeriodDataTable pdt) {
        dependants.add(pdt);
    }

    public void refresh() {
        table.refresh();
        for (PeriodDataTable d : dependants) {
            d.refresh();
        }
    }

}
