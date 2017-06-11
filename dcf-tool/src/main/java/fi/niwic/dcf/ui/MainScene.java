package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.tool.FinancialStatementImpl;
import fi.niwic.dcf.tool.PeriodImpl;
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
import javafx.stage.Stage;

public class MainScene {

    private DCFCalculation calculation;
    private IncomeStatementTable incomeStatementTable;    
    
    private Stage stage;
    
    public MainScene(Stage stage, DCFCalculation calculation) {
        this.stage = stage;
        this.calculation = calculation;
        intializeTable();
    }
    
    public Scene scene() {
        
        incomeStatementTable.addPeriod(calculation.getHeadPeriod());
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
            createMenuBar(calculation.getCompanyName()),
            incomeStatementTable.getTable()
        );
        
        return new Scene(vbox);
    }
    
    private void intializeTable() {
        incomeStatementTable = new IncomeStatementTable();
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
            incomeStatementTable.addPeriod(newPeriod);
        } catch (InvalidPastPeriodException ex) {
            System.out.println("Näin ei pitäisi tapahtua!");
        }
    }
    
}
