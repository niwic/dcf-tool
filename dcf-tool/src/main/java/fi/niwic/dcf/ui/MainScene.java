package fi.niwic.dcf.ui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainScene {

    private int currentYear;
    private TableView table;
    
    public Scene render(String companyName, int startYear) {
        currentYear = startYear;
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
            createMenuBar(companyName),
            createTable()
        );
        
        return new Scene(vbox);
    }
    
    private TableView createTable() {
        table = new TableView();
        TableColumn headings = new TableColumn();
        headings.setSortable(false);
        table.getColumns().add(headings);
        
        return table;
    }
    
    private HBox createMenuBar(String companyName) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        
        hbox.getChildren().addAll(
                createCompanyNameLabel(companyName),
                createAddYearButton()
        );
        
        return hbox;
    }
    
    private Label createCompanyNameLabel(String name) {
        Label companyName = new Label(name);
        Font font = Font.font(
                Font.getDefault().getFamily(),
                FontWeight.BOLD,
                Font.getDefault().getSize() + 1
        );
        companyName.setFont(font);
        
        return companyName;
    }
    
    private Button createAddYearButton() {
        Button addYear = new Button("Add year");
        addYear.setOnAction(this::addYear);
        
        return addYear;
    }
    
    private void addYear(ActionEvent e) {
        Integer nextYear = currentYear + 1;
        TableColumn newYear = new TableColumn(nextYear.toString());
        currentYear++;
        table.getColumns().add(newYear);
    }
    
}
