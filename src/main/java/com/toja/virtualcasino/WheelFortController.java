package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import java.io.IOException;

public class WheelFortController {

    @FXML
    private Label valLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Button spinBtn;
    @FXML
    private Label imgLabel;

    //Bild des Glücksrads wird definiert
    Image wheelImg = new Image("http://www.gis-informatik.de/~torge.neuendorf/Bilder/wheelImg.png");


    //Initialisieren des Fensters
    public void initialize() {

        WheelFort.wheelCreate(); //Erstellen des Rads
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$"); //Anzeigen des aktuellen Kontostands
        if (VirtualCasinoController.getCurrAmount() < 25) {
            spinBtn.setDisable(true); //Button ausschalten, wenn zu wenig Geld da ist
        }
        imgLabel.setGraphic(new ImageView(wheelImg)); //Bild laden
        imgLabel.getStyleClass().add("wheel");
    }

    @FXML //passiert bei Drücken auf den Startknopf
    public void spin() {
            WheelField field = WheelFort.wheelSpin(); //Aufrufen der eigentlichen Logik
            int rotation = field.getnumber()*(-18) - 9;
            imgLabel.setRotate(rotation); //richtige Drehung des Rads einstellen
            valLabel.setText("Gewinn: " + field.getValue() + "$"); //Gewinn anzeigen
            amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$"); //Neuen Kontostand anzeigen
            if (VirtualCasinoController.getCurrAmount() < 25) {
                spinBtn.setDisable(true); //Button ausschalten, wenn zu wenig Geld da ist
            }
    }
}
