package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CurrencyMenuController {

    public void initialize() {
        currAmountLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
    }

    @FXML
    private Label currAmountLbl;

    @FXML
    private void buy500() {
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 500);
        currAmountLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
    }

    @FXML
    private void buy1000() {
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 1000);
        currAmountLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
    }

    @FXML
    private void buy5000() {
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 5000);
        currAmountLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
    }

    @FXML
    private void buy10000() {
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 10000);
        currAmountLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
    }

}
