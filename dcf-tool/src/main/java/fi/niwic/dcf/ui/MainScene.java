package fi.niwic.dcf.ui;

import fi.niwic.dcf.ui.table.PeriodDataTable;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.tool.FinancialStatementImpl;
import fi.niwic.dcf.tool.PeriodImpl;
import fi.niwic.dcf.ui.table.PeriodDataTables;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainScene {

    private DCFCalculation calculation;
    
    private PeriodDataTables tables;
    private VBox layout;
    private VBox vbox;
    private Stage stage;
    
    public MainScene(Stage stage, DCFCalculation calculation) {
        this.stage = stage;
        this.calculation = calculation;
    }
    
    public void initializeLayout() {
        vbox = new VBox();
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(vbox);
        
        layout = new VBox();
        layout.getChildren().addAll(
            createMenuBar(calculation.getCompanyName()), sp
        );
    }
    
    private void intializeTables() {
        tables = new PeriodDataTables();
        for (PeriodDataTable table: tables.getList()) {
            vbox.getChildren().add(table.getTable());
        }
        tables.addPeriod(calculation.getHeadPeriod());
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
        Integer nextYear = calculation.getHeadPeriod().getYear() + 1;
        Period newPeriod = new PeriodImpl(nextYear, false);
        newPeriod.setCurrentFinancialStatement(new FinancialStatementImpl());
        
        try {
            calculation.addPeriod(newPeriod);
            tables.addPeriod(newPeriod);
        } catch (InvalidPastPeriodException ex) {
            /* should be impossible */
        }
    }
        
    public Scene scene() {
        initializeLayout();
        intializeTables();
        return new Scene(layout, 900, 700);
    }
    
}
