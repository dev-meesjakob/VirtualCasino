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
    private Label topField;
    @FXML
    private Label valLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Button spinBtn;
    @FXML
    private Label imgLabel;
    @FXML
    private Pane wheelPane;

    Image wheelImg = new Image("http://www.gis-informatik.de/~torge.neuendorf/Bilder/wheelImg.png");


    //Initialisieren des Fensters
    public void initialize() {
        WheelFort.wheelCreate();
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
        if (VirtualCasinoController.getCurrAmount() < 25) {
            spinBtn.setDisable(true);
        }
        imgLabel.setGraphic(new ImageView(wheelImg));
    }

    @FXML //passiert bei Drücken auf den Startknopf
    public void spin() {
            WheelField field = WheelFort.wheelSpin();
            int rotation = field.getnumber()*(-18) - 9;
            imgLabel.setRotate(rotation);
            topField.setText(field.getSymbol());
            valLabel.setText("Gewinn: " + field.getValue() + "$");
            amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
            if (VirtualCasinoController.getCurrAmount() < 25) {
                spinBtn.setDisable(true);
            }
    }
}
