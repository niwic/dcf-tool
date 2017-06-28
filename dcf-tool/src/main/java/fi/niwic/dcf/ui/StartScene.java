package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.tool.FinancialStatementImpl;
import fi.niwic.dcf.tool.PeriodImpl;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartScene {

    private static final String COMPANY_NAME = "Company name:";
    private static final String FIRST_YEAR = "First year:";
    
    private DCFCalculation calculation;
    
    private Stage stage;
    private MainScene mainScene;
    
    private GridPane grid;
    private TextField companyName;
    private TextField startYear;
    private Label errorLabel;
    
    /**
     * Luo uuden alkunäkymän jossa annetaan yrityksen nimi ja aloitus-vuosi.
     * @param stage minne piiretään
     * @param mainScene mistä jatketaan
     * @param calculation mikä manipuloidaan
     */
    public StartScene(Stage stage, MainScene mainScene, DCFCalculation calculation) {
        this.calculation = calculation;
        this.mainScene = mainScene;
        this.stage = stage;
        initialize();
    }
    
    private void initialize() {
        initializeGrid();
        companyName = createInput(COMPANY_NAME, 0, 0);
        startYear = createInput(FIRST_YEAR, 0, 1);
        errorLabel = createLabel("", 0, 2);
        createStartButton();
    }
    
    private void initializeGrid() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
    }
    
    private void createStartButton() {
        Button start = new Button("Start");
        GridPane.setConstraints(start, 1, 2);
        grid.getChildren().add(start);
        
        start.setOnAction(this::onStartButton);
    }
    
    private void onStartButton(ActionEvent e) {
        try {
            int year = Integer.parseInt(startYear.getText());
            calculation.setCompanyName(companyName.getText());
            Period period = new PeriodImpl(year, false, new FinancialStatementImpl());
            calculation.addPeriod(period);
            stage.setScene(mainScene.scene());
            stage.sizeToScene();
        } catch (NumberFormatException ex) {
            errorLabel.setText("Invalid start year");
        } catch (InvalidPastPeriodException ex) {
            errorLabel.setText("Years are not consecutive");
        }
    }
    
    private TextField createInput(String label, int colIndex, int rowIndex) {
        createLabel(label, colIndex, rowIndex);
        return createField(colIndex + 1, rowIndex);
    }
    
    private Label createLabel(String text, int colIndex, int rowIndex) {
        Label label = new Label(text);
        GridPane.setConstraints(label, colIndex, rowIndex);
        grid.getChildren().add(label);
        
        return label;
    }
    
    private TextField createField(int colIndex, int rowIndex) {
        TextField input = new TextField();
        GridPane.setConstraints(input, colIndex, rowIndex);
        grid.getChildren().add(input);
        
        return input;
    }
    
    /**
     * Palauttaa käyttöliitymän näkymän.
     * @return näkymä
     */
    public Scene scene() {
        return new Scene(grid, 350, 200);
    }
    
}
