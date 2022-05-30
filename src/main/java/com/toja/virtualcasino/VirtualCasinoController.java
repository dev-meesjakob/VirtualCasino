package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VirtualCasinoController {

    public static int currAmount = 100;

    @FXML
    private void startSlotMachine() throws IOException {
        Stage slotMachineStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("slotmachine-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 295);
        slotMachineStage.setTitle("Slot Machine");
        slotMachineStage.setScene(scene);
        slotMachineStage.show();
    }

    @FXML
    private void startWheelFort() throws IOException {
        Stage wheelFortStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("wheelfort-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        wheelFortStage.setTitle("Wheel of Fortune");
        wheelFortStage.setScene(scene);
        wheelFortStage.show();
    }

    @FXML
    private void startBlackjack() throws IOException {
        Stage blackjackStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("blackjack-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        blackjackStage.setTitle("Blackjack");
        blackjackStage.setScene(scene);
        blackjackStage.show();
    }

    static void setCurrAmount (int amount) {
        currAmount = amount;
    }

    static int getCurrAmount () {
        return currAmount;
    }
}