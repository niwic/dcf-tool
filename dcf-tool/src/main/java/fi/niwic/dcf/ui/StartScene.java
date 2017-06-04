package fi.niwic.dcf.ui;

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

    private static String CompanyName = "Company name:";
    private static String FirstYear = "First year:";
    
    private Stage stage;
    private MainScene mainScene;
    
    private GridPane grid;
    private TextField companyName;
    private TextField startYear;
    private Label errorLabel;
    
    public StartScene(Stage stage, MainScene mainScene) {
        this.mainScene = mainScene;
        this.stage = stage;
        initialize();
    }
    
    private void initialize() {
        initializeGrid();
        companyName = createInput(CompanyName, 0, 0);
        startYear = createInput(FirstYear, 0, 1);
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
            stage.setScene(
                    mainScene.render(
                            companyName.getText(),
                            Integer.parseInt(startYear.getText())
                    )
            );
        } catch (NumberFormatException ex) {
            errorLabel.setText("Invalid start year");
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
    
    public Scene scene() {
        return new Scene(grid, 350, 200);
    }
    
}
