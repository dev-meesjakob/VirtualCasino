package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class WheelFortController {

    public void initialize() {
        WheelFort.wheelCreate();
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
        if (VirtualCasinoController.getCurrAmount() < 25) {
            spinBtn.setDisable(true);
        }
    }

    @FXML
    private Label topField;
    @FXML
    private Label valLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Button spinBtn;

    @FXML
    public void spin() {
            WheelField field = WheelFort.wheelSpin();
            topField.setText(field.getSymbol());
            valLabel.setText("Gewinn: " + field.getValue() + "$");
            amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
            if (VirtualCasinoController.getCurrAmount() < 25) {
                spinBtn.setDisable(true);
            }
    }
}
