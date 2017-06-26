package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.tool.DCFCalculationImpl;
import fi.niwic.dcf.tool.PeriodImpl;
import fi.niwic.dcf.tool.WACC;
import javafx.stage.Stage;

public class UI {

    private static String title = "DCF Tool";
    
    private Stage primaryStage;
    
    private MainScene mainScene;
    private StartScene startScene;
    
    private DCFCalculation calculation;
    
    public UI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(title);
        
        calculation = new DCFCalculationImpl();
        mainScene = new MainScene(primaryStage, calculation);
        startScene = new StartScene(primaryStage, mainScene, calculation);
    }
    
    public void render() {
        primaryStage.setScene(startScene.scene());
        primaryStage.show();
    }

}
