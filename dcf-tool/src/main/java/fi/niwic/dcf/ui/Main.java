package fi.niwic.dcf.ui;

import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {
    
    /**
     * Metodi joka käynnistää käyttöliittymän.
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        UI ui = new UI(primaryStage);
        ui.render();
    }
    
}
