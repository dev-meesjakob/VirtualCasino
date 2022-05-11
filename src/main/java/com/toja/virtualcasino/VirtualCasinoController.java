package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VirtualCasinoController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}