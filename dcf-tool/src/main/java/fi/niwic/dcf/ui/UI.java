package fi.niwic.dcf.ui;

import javafx.stage.Stage;

public class UI {

    private static String title = "DCF Tool";
    
    private Stage primaryStage;
    
    private MainScene mainScene;
    private StartScene startScene;
    
    public UI(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(title);
        
        mainScene = new MainScene();
        startScene = new StartScene(primaryStage, mainScene);
    }
    
    public void render() {
        primaryStage.setScene(startScene.scene());
        primaryStage.show();
    }

}
