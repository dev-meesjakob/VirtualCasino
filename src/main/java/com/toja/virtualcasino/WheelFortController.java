package com.toja.virtualcasino;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class WheelFortController {

    @FXML
    private Label valLabel; //Für Anzeige des Gewinns
    @FXML
    private Label amountLabel; //Für Anzeige des Kontostands
    @FXML
    private Button spinBtn; //Zum Drehen des Rads
    @FXML
    private Label imgLabel; //Zur Anzeige des Rads

    //Bild des Glücksrads wird definiert
    Image wheelImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/wheelFortWheel.png")));;
    RotateTransition rotate = new RotateTransition();
    int rotation = -9;
    WheelField field;


    //Initialisieren des Fensters
    public void initialize() {

        WheelFort.wheelCreate(); //Erstellen des Rads
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$"); //Anzeigen des aktuellen Kontostands
        if (VirtualCasinoController.getCurrAmount() < 25) {
            spinBtn.setDisable(true); //Button ausschalten, wenn zu wenig Geld da ist
        }
        imgLabel.setGraphic(new ImageView(wheelImg)); //Bild laden
        imgLabel.getStyleClass().add("wheel");
        amountLabel.getStyleClass().add("rightAlignment");
    }

    @FXML //passiert bei Drücken auf den Startknopf
    public void spin() {

        amountLabel.setText((VirtualCasinoController.getCurrAmount() - 25) + " VC$");
        field = WheelFort.wheelSpin(); //Aufrufen der eigentlichen Logik

        //Rad drehen
        rotate.setNode(imgLabel); //Was sich drehen soll
        rotate.setDuration(Duration.millis(2500)); //Dauer der Drehung
        int rotationBefore = rotation; //Zur Berechnung - Winkel vorher
        rotation = field.getnumber() * (-18) - 9; //benötigter Winkel
        //Anpassen der Drehung (je nachdem, ob der neue Winkel größer oder kleiner als der alte ist), ca. 3 Drehungen
        if (rotation <= rotationBefore) {
            rotate.setByAngle(-1080 + (rotation - rotationBefore));
        } else {
            rotate.setByAngle(-1080 - (rotationBefore - rotation));
        }
        rotate.play(); //Drehung abspielen
        spinBtn.setDisable(true);
        valLabel.setText("");
        rotate.setOnFinished(event -> afterSpin()); //Pause machen, bis sich das Rad fertig gedreht hat
    }

    public void afterSpin() {
        valLabel.setText("Gewinn: " + field.getValue() + " VC$"); //Gewinn anzeigen
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$"); //Neuen Kontostand anzeigen
        if (VirtualCasinoController.getCurrAmount() >= 25) {
            spinBtn.setDisable(false); //Button wieder anschalten, wenn genug Geld da ist
        }
    }
}
