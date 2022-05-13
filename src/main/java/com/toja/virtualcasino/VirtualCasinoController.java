package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VirtualCasinoController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void startSlotMachine() throws IOException {
        Stage slotMachineStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("slotmachine-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        slotMachineStage.setTitle("Hello!");
        slotMachineStage.setScene(scene);
        slotMachineStage.show();
    }
}