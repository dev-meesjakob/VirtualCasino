package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class WheelFortController {
    @FXML
    private Label topField;
    @FXML
    private Label valLabel;

    @FXML
    public void spin() {
        topField.setText(WheelFort.wheelSpin());
        valLabel.setText("" + VirtualCasinoController.getCurrAmount());
    }
}
