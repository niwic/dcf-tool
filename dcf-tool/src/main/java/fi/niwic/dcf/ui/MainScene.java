package fi.niwic.dcf.ui;

import fi.niwic.dcf.ui.table.PeriodDataTable;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.tool.FinancialStatementImpl;
import fi.niwic.dcf.tool.PeriodImpl;
import fi.niwic.dcf.ui.table.InputDataTables;
import fi.niwic.dcf.ui.table.OutputDataTables;
import fi.niwic.dcf.ui.vm.Refreshable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainScene implements Refreshable {

    private DCFCalculation calculation;
    
    private InputDataTables inputDataTables;
    private OutputDataTables outputDataTables;
    
    private VBox layout;
    private VBox inputDataArea;
    private VBox outputDataArea;
    private HBox dataArea;
    private Stage stage;
    private Label errorLabel;
    private Label companyNameLabel;
    private Label marketValue;
    private Label marketValuePerShare;
    
    public MainScene(Stage stage, DCFCalculation calculation) {
        this.stage = stage;
        this.calculation = calculation;
        createErrorLabel();
        initializeTables();
        initializeLayout();
    }
    
    public void initializeLayout() {
        layout = new VBox();
        layout.getChildren().addAll(
            createMenuBar(), createInputBar(), createDataArea()
        );
    }
    
    private HBox createDataArea() {
        dataArea = new HBox();
        inputDataArea = new VBox();
        outputDataArea = new VBox();
        
        dataArea.getChildren().addAll(inputDataArea, outputDataArea);
        
        createDataAreaTables();
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(dataArea);
        
        return dataArea;
    }
    
    private HBox createInputBar() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;-fx-text-fill: #FFFFFF;");
        
        hbox.getChildren().addAll(
                createInput("Cost of equity:", this::updateCostOfEquity),
                createInput("Cost of debt:", this::updateCostOfDebt),
                createInput("Number of shares:", this::updateNumberOfShares),
                createMarketValueIndicator(),
                createMarketValuePerShareIndicator()
        );
        
        return hbox;
    }
    
    private VBox createInput(String labelText, ChangeListener<? super String> changeListener) {
        Label label = new Label(labelText);
        TextField ta = new TextField();
        ta.textProperty().addListener(changeListener);
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, ta);
        
        return vbox;
    }
    
    private void updateCostOfEquity(ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
        try {
            Double value = Double.parseDouble(newValue);
            calculation.getCostOfCapital().setCostOfOwnCapital(value);
            refresh();
        } catch (NumberFormatException e) {
            errorLabel.setText("Cost of equity is invalid!");
        }
    }
    
    private void updateCostOfDebt(ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
        try {
            Double value = Double.parseDouble(newValue);
            calculation.getCostOfCapital().setCostOfBorrowedCapital(value);
            refresh();
        } catch (NumberFormatException e) {
            errorLabel.setText("Cost of debt is invalid!");
        }
    }
    
    private void updateNumberOfShares(ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
        try {
            Long value = Long.parseLong(newValue);
            calculation.setNumberOfShares(value);
            refresh();
        } catch (NumberFormatException e) {
            errorLabel.setText("Number of shares is invalid!");
        }
    }
    
    private void createDataAreaTables() {
        for (PeriodDataTable table: inputDataTables.getList()) {
            inputDataArea.getChildren().add(table.getTable());
        }
        
        for (PeriodDataTable table: outputDataTables.getList()) {
            outputDataArea.getChildren().add(table.getTable());
        }
    }
    
    private void initializeTables() {
        inputDataTables = new InputDataTables(this, errorLabel);
        outputDataTables = new OutputDataTables(inputDataTables, calculation, errorLabel);
    }
    
    private HBox createMenuBar() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;-fx-text-fill: #FFFFFF;");
        
        hbox.getChildren().addAll(
                createCompanyNameLabel(),
                createAddYearButton(),
                errorLabel
        );
        
        return hbox;
    }
    
    private Label createCompanyNameLabel() {
        companyNameLabel = createBoldLabel();
        return companyNameLabel;
    }
    
    private Label createErrorLabel() {
        errorLabel = new Label();
        return errorLabel;
    }
    
    public VBox createMarketValueIndicator() {
        marketValue = createBoldLabel();
        return createOutput("Market value:", marketValue);
    }
    
    public VBox createMarketValuePerShareIndicator() {
        marketValuePerShare = createBoldLabel();
        return createOutput("Per share:", marketValuePerShare);
    }
    
    public VBox createOutput(String text, Label output) {
        Label label = new Label(text);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, output);
        
        return vbox;
    }
    
    public Label createBoldLabel() {
        Label label = new Label();
        Font font = Font.font(
                Font.getDefault().getFamily(),
                FontWeight.BOLD,
                Font.getDefault().getSize() + 5
        );
        label.setFont(font);
        
        return label;
    }
    
    private Button createAddYearButton() {
        Button addYear = new Button("Add year");
        addYear.setOnAction(this::addYear);
        
        return addYear;
    }
    
    private void addYear(ActionEvent e) {
        Integer nextYear = calculation.getHeadPeriod().getYear() + 1;
        Period newPeriod = new PeriodImpl(nextYear, true);
        newPeriod.setCurrentFinancialStatement(new FinancialStatementImpl());
        
        try {
            calculation.addPeriod(newPeriod);
            inputDataTables.addPeriod(newPeriod, -1);
            outputDataTables.addPeriod(newPeriod, -1);
        } catch (InvalidPastPeriodException ex) {
            errorLabel.setText("An invalid period has been added, please restart!");
        }
    }
    
    public void setMarketValue() {
        calculation.calculateValuation().ifPresent(value -> marketValue.setText(value.toString()));
        calculation.calculateValuationPerShare().ifPresent(value -> marketValuePerShare.setText(value.toString()));
    }
    
    public void resetContent() {
        resetPeriods();
        companyNameLabel.setText(calculation.getCompanyName());
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
        
        calculation.getPerpetualPeriod().ifPresent(p -> {
            inputDataTables.addPeriod(p);
            outputDataTables.addPeriod(p);
        });
    }
    
    public void refresh() {
        errorLabel.setText("");
        outputDataTables.refresh();
        setMarketValue();
    }
        
    public Scene scene() {
        resetContent();
        return new Scene(layout);
    }
    
}
