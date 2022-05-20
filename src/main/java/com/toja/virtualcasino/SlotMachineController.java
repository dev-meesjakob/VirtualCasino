package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SlotMachineController {

    @FXML
    private Label amountLabel;
    @FXML
    private Button playBtn;

    public void initialize() {
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
        if (VirtualCasinoController.getCurrAmount() == 0) {
            playBtn.setDisable(true);
        }
    }

    public void playSlot() {
        
    }
}
