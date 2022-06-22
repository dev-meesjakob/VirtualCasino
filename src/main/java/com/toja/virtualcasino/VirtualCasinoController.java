package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VirtualCasinoController {

    // 100 VC$ am Anfang
    public static int currAmount = 100;

    @FXML
    private void startSlotMachine() throws IOException {
        // Starte Glücksspielautomaten
        Stage slotMachineStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("slotmachine-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 295);
        scene.getStylesheets().add((getClass().getResource("css/slotMachine.css")).toExternalForm());
        slotMachineStage.setResizable(false);
        slotMachineStage.setTitle("Slot Machine");
        slotMachineStage.setScene(scene);
        slotMachineStage.show();
    }

    @FXML
    private void startWheelFort() throws IOException {
        // Starte Glücksrad
        Stage wheelFortStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("wheelfort-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        scene.getStylesheets().add((getClass().getResource("css/wheelFort.css")).toExternalForm());
        wheelFortStage.setResizable(false);
        wheelFortStage.setTitle("Wheel of Fortune");
        wheelFortStage.setScene(scene);
        wheelFortStage.show();
    }

    @FXML
    private void startBlackjack() throws IOException {
        // Starte Blackjack
        Stage blackjackStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("blackjack-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        blackjackStage.setResizable(false);
        blackjackStage.setTitle("Blackjack");
        blackjackStage.setScene(scene);
        blackjackStage.getIcons().add(new Image(Objects.requireNonNull(VirtualCasino.class.getResourceAsStream("img/BlackjackLogo.png"))));
        blackjackStage.show();
    }

    @FXML
    private void startCurrMenu() throws IOException {
        // Starte Währungsmenü
        Stage currencyStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(VirtualCasino.class.getResource("currencymenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        currencyStage.setResizable(false);
        currencyStage.setTitle("Currency");
        currencyStage.setScene(scene);
        currencyStage.show();
    }

    static void setCurrAmount (int amount) {
        // Grenze Geld auf 999 Mio. festgelegt
        if (amount > 999999999) {
            currAmount = 999999999;
        }
    }

    static int getCurrAmount () {
        return currAmount;
    }
}