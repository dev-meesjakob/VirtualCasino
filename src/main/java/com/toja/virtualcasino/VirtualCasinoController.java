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
    public static int currAmount = 100;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void startSlotMachine() throws IOException {
        Stage slotMachineStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("slotmachine-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 295);
        slotMachineStage.setTitle("Hello!");
        slotMachineStage.setScene(scene);
        slotMachineStage.show();
    }

    @FXML
    private void startWheelFort() throws IOException {
        Stage wheelFortStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("wheelfort-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        wheelFortStage.setTitle("Glücksrad");
        wheelFortStage.setScene(scene);
        wheelFortStage.show();
    }

    static void setCurrAmount (int amount) {
        currAmount = amount;
    }

    static int getCurrAmount () {
        return currAmount;
    }
}