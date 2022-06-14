package com.toja.virtualcasino;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

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
    RotateTransition rotate = new RotateTransition();
    int rotation = -9;


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
        //Rad drehen
        rotate.setNode(imgLabel); //Was sich drehen soll
        rotate.setDuration(Duration.millis(2500)); //Dauer der Drehung
        int rotationBefore = rotation; //Zur Berechnung - Winkel vorher
        rotation = field.getnumber()*(-18) - 9; //benötigter Winkel
        //Anpassen der Drehung (je nachdem, ob der neue Winkel größer oder kleiner als der alte ist), ca. 3 Drehungen
        if (rotation <= rotationBefore) {
            rotate.setByAngle(-1080 + (rotation - rotationBefore));
        } else {
            rotate.setByAngle(-1080 - (rotationBefore - rotation));
        }
        rotate.play(); //Drehung abspielen

        valLabel.setText("Gewinn: " + field.getValue() + "$"); //Gewinn anzeigen
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$"); //Neuen Kontostand anzeigen
        if (VirtualCasinoController.getCurrAmount() < 25) {
            spinBtn.setDisable(true); //Button ausschalten, wenn zu wenig Geld da ist
        }
    }
}
