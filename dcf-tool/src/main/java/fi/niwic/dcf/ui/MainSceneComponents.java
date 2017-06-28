package fi.niwic.dcf.ui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainSceneComponents {

    /**
     * Isolla tekstillä.
     * @return label
     */
    public static Label createBoldLabel() {
        Label label = new Label();
        Font font = Font.font(
                Font.getDefault().getFamily(),
                FontWeight.BOLD,
                Font.getDefault().getSize() + 5
        );
        label.setFont(font);
        
        return label;
    }
    
    /**
     * Arvolaatikko.
     * @param text vakio
     * @param output arvo
     * @return laatikko
     */
    public static VBox createOutput(String text, Label output) {
        Label label = new Label(text);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, output);
        
        return vbox;
    }
    
    /**
     * Syöttölaatikko.
     * @param labelText vakio
     * @param changeListener muutoskuuntellija
     * @return laatikko
     */
    public static VBox createInput(String labelText, ChangeListener<? super String> changeListener) {
        Label label = new Label(labelText);
        TextField ta = new TextField();
        ta.textProperty().addListener(changeListener);
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, ta);
        
        return vbox;
    }
    
    /**
     * Tyylitetty HBox.
     * @return HBox
     */
    public static HBox createStyledHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;-fx-text-fill: #FFFFFF;");
        
        return hbox;
    }
    
}
