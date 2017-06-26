package fi.niwic.dcf.ui;

import fi.niwic.dcf.ui.table.PeriodDataTable;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.tool.FinancialStatementImpl;
import fi.niwic.dcf.tool.PeriodImpl;
import fi.niwic.dcf.ui.table.InputDataTables;
import fi.niwic.dcf.ui.table.OutputDataTables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
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
    
    private InputDataTables inputDataTables;
    private OutputDataTables outputDataTables;
    
    private VBox layout;
    private VBox inputDataArea;
    private VBox outputDataArea;
    private HBox dataArea;
    private Stage stage;
    private Label companyNameLabel;
    
    public MainScene(Stage stage, DCFCalculation calculation) {
        this.stage = stage;
        this.calculation = calculation;
        initializeTables();
        initializeLayout();
    }
    
    public void initializeLayout() {
        layout = new VBox();
        layout.getChildren().addAll(
            createMenuBar(), createDataArea()
        );
    }
    
    private HBox createDataArea() {
        dataArea = new HBox();
        inputDataArea = new VBox();
        outputDataArea = new VBox();
        
        dataArea.getChildren().addAll(inputDataArea, outputDataArea);
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(dataArea);
        
        return dataArea;
    }
    
    private void initializeTables() {
        inputDataTables = new InputDataTables();
        outputDataTables = new OutputDataTables(inputDataTables, calculation);
    }
    
    private HBox createMenuBar() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        
        hbox.getChildren().addAll(
                createCompanyNameLabel(),
                createAddYearButton()
        );
        
        return hbox;
    }
    
    private Label createCompanyNameLabel() {
        companyNameLabel = new Label();
        Font font = Font.font(
                Font.getDefault().getFamily(),
                FontWeight.BOLD,
                Font.getDefault().getSize() + 5
        );
        companyNameLabel.setFont(font);
        
        return companyNameLabel;
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
            inputDataTables.addPeriod(newPeriod);
            outputDataTables.addPeriod(newPeriod);
        } catch (InvalidPastPeriodException ex) {
            /* should be impossible */
        }
    }
    
    public void resetContent() {
        resetPeriods();
        companyNameLabel.setText(calculation.getCompanyName());
        inputDataArea.getChildren().clear();
        outputDataArea.getChildren().clear();
        
        for (PeriodDataTable table: inputDataTables.getList()) {
            inputDataArea.getChildren().add(table.getTable());
        }
        
        for (PeriodDataTable table: outputDataTables.getList()) {
            outputDataArea.getChildren().add(table.getTable());
        }
    }
    
    public void resetPeriods() {
        inputDataTables.clear();
        outputDataTables.clear();
        
        ArrayList<Period> periods = new ArrayList();
        Optional<Period> optPeriod = Optional.ofNullable(calculation.getHeadPeriod());
        while (optPeriod.isPresent()) {
            periods.add(optPeriod.get());
            optPeriod = optPeriod.get().getPastPeriod();
        }
        
        Collections.reverse(periods);
        for (Period period : periods) {
            inputDataTables.addPeriod(period);
            outputDataTables.addPeriod(period);
        }
    }
        
    public Scene scene() {
        resetContent();
        return new Scene(layout);
    }
    
}
