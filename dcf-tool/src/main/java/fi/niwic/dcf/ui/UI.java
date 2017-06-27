package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.Period;
import fi.niwic.dcf.tool.DCFCalculationImpl;
import fi.niwic.dcf.tool.FinancialStatementImpl;
import fi.niwic.dcf.tool.PerpetualPeriod;
import fi.niwic.dcf.tool.WACC;
import javafx.stage.Stage;

public class UI {

    private static String title = "DCF Tool";
    
    private Stage primaryStage;
    
    private MainScene mainScene;
    private StartScene startScene;
    
    private DCFCalculation calculation;
    
    public UI(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(title);
        
        Period pPeriod = new PerpetualPeriod();
        FinancialStatement fs = new FinancialStatementImpl();
        pPeriod.setCurrentFinancialStatement(fs);
        CostOfCapital coc = new WACC(pPeriod.getCurrentFinancialStatement().getBalanceSheet().getInvestedCapital());
        
        calculation = new DCFCalculationImpl(coc);
        calculation.setPerpetualPeriod(pPeriod);
        
        mainScene = new MainScene(primaryStage, calculation);
        startScene = new StartScene(primaryStage, mainScene, calculation);
    }
    
    public void render() {
        primaryStage.setScene(startScene.scene());
        primaryStage.show();
    }

}
