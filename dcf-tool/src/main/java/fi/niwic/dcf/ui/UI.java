package fi.niwic.dcf.ui;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
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
    
    /**
     * Luo uuden käyttöliittymän ja laskelman joka annetaan käyttöliittymälle.
     * @param primaryStage javafxn pääpiirtoalue
     * @throws Exception 
     */
    public UI(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(title);
        
        Period pPeriod = new PerpetualPeriod(new FinancialStatementImpl());
        CostOfCapital coc = new WACC(pPeriod.getCurrentFinancialStatement().getBalanceSheet().getInvestedCapital());
        
        calculation = new DCFCalculationImpl(coc);
        calculation.setPerpetualPeriod(pPeriod);
        
        mainScene = new MainScene(primaryStage, calculation);
        startScene = new StartScene(primaryStage, mainScene, calculation);
    }
    
    /**
     * Avaa käyttöliittymän ensimmäisen ruudun.
     */
    public void render() {
        primaryStage.setScene(startScene.scene());
        primaryStage.show();
    }

}
