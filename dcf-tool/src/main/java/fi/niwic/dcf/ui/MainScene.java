package fi.niwic.dcf.ui;

import fi.niwic.dcf.ui.table.PeriodDataTable;
import fi.niwic.dcf.api.*;
import fi.niwic.dcf.tool.*;
import fi.niwic.dcf.ui.table.*;
import fi.niwic.dcf.ui.vm.Refreshable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    
    /**
     * Luo päänäkymän käyttöliittymässä.
     * @param stage minne piirretään
     * @param calculation mikä manipuloidaan käyttöliittymästä
     */
    public MainScene(Stage stage, DCFCalculation calculation) {
        this.stage = stage;
        this.calculation = calculation;
        createErrorLabel();
        initializeTables();
        initializeLayout();
    }
    
    private void initializeLayout() {
        layout = new VBox();
        layout.getChildren().addAll(createMenuBar(), createInputBar(), createDataArea());
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
        HBox hbox = MainSceneComponents.createStyledHBox();
        
        hbox.getChildren().addAll(
                MainSceneComponents.createInput("Cost of equity:", this::updateCostOfEquity),
                MainSceneComponents.createInput("Cost of debt:", this::updateCostOfDebt),
                MainSceneComponents.createInput("Number of shares:", this::updateNumberOfShares),
                createMarketValueIndicator(),
                createMarketValuePerShareIndicator()
        );
        
        return hbox;
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
        HBox hbox = MainSceneComponents.createStyledHBox();
        hbox.getChildren().addAll(createCompanyNameLabel(), createAddYearButton(), errorLabel);
        
        return hbox;
    }
    
    private Label createCompanyNameLabel() {
        companyNameLabel = MainSceneComponents.createBoldLabel();
        return companyNameLabel;
    }
    
    private Label createErrorLabel() {
        errorLabel = new Label();
        return errorLabel;
    }
    
    private VBox createMarketValueIndicator() {
        marketValue = MainSceneComponents.createBoldLabel();
        return MainSceneComponents.createOutput("Market value:", marketValue);
    }
    
    private VBox createMarketValuePerShareIndicator() {
        marketValuePerShare = MainSceneComponents.createBoldLabel();
        return MainSceneComponents.createOutput("Per share:", marketValuePerShare);
    }
    
    private Button createAddYearButton() {
        Button addYear = new Button("Add year");
        addYear.setOnAction(this::addYear);
        
        return addYear;
    }
    
    private void addYear(ActionEvent e) {
        Integer nextYear = calculation.getHeadPeriod().getYear() + 1;
        Period newPeriod = new PeriodImpl(nextYear, true, new FinancialStatementImpl());
        
        try {
            calculation.addPeriod(newPeriod);
            inputDataTables.addPeriod(newPeriod, -1);
            outputDataTables.addPeriod(newPeriod, -1);
        } catch (InvalidPastPeriodException ex) {
            errorLabel.setText("An invalid period has been added, please restart!");
        }
    }
    
    private void setMarketValue() {
        calculation.calculateValuation().ifPresent(value -> marketValue.setText(value.toString()));
        calculation.calculateValuationPerShare().ifPresent(value -> marketValuePerShare.setText(value.toString()));
    }
    
    private void resetContent() {
        resetPeriods();
        companyNameLabel.setText(calculation.getCompanyName());
    }
    
    private void resetPeriods() {
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
    
    /**
     * Päivittää virhelaatikon, tulostaulukot, ja tulosarvot.
     */
    public void refresh() {
        errorLabel.setText("");
        outputDataTables.refresh();
        setMarketValue();
    }
    
    /**
     * Palauttaa käyttöliittymän näkymän.
     * @return näkymä
     */
    public Scene scene() {
        resetContent();
        return new Scene(layout);
    }
    
}
