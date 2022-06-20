package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class SlotMachineController {

    @FXML
    private Label amountLabel; //Label für den aktuellen Kontostand
    @FXML
    private Button playBtn; //Button zum Starten
    @FXML
    private ArrayList<TextArea> reels = new ArrayList<>(); //TextAreas für die Walzen
    @FXML
    private Pane slotPane; //Hintergrund
    @FXML
    private TextField betFld; //Feld für den Einsatz des Spielers
    @FXML
    private Label gainLabel; //label für Anzeige des Gewinns
    @FXML
    private TextArea lineArea; //Area für die gewonnenen Linien

    //Initialisieren des Fensters beim Starten
    public void initialize() {
        SlotMachine.machineCreate(); //Erzeugen der SlotMachine
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$"); //aktuellen Kontostand anzeigen
        if (VirtualCasinoController.getCurrAmount() == 0) {
            playBtn.setDisable(true); //Btn "ausschalten" bei keinem Geld
        }
        amountLabel.getStyleClass().add("rightAlignment");

        //TextAreas für die einzelnen Walzen erstellen
        for (int i = 0; i < 5; i++) {
            TextArea temp = new TextArea();
            reels.add(temp);
            reels.get(i).setEditable(false);
            reels.get(i).setLayoutY(43.0);
            reels.get(i).setPrefHeight(120.0);
            reels.get(i).setPrefWidth(90.0);
            reels.get(i).setLayoutX(40 + 95*i);
            slotPane.getChildren().add(reels.get(i));
        }
    }

    //läuft ab bei Drücken auf den Knopf
    public void playSlot() {
        int bet;
        try {
            bet = Integer.parseInt(betFld.getText());
            if (bet <= 4) {
                betFld.clear();
                betFld.setPromptText("Einsatz mind. 5 VC$");
                return;
            } else if (bet > VirtualCasinoController.getCurrAmount()) {
                betFld.clear();
                betFld.setPromptText("Nicht genug Geld.");
                return;
            } else {
                betFld.setPromptText("");
            }
        } catch (NumberFormatException e) {
            betFld.clear();
            betFld.setPromptText("Nur Zahlen eingeben.");
            return;
        }

                //ArrayList<String> allIcons = SlotMachine.spin(bet); //Aufrufen der eigentlichen Spiellogik
                int gain = SlotMachine.spin(bet);

                for (int i = 0; i < 5; i++) {
                    reels.get(i).clear();
                    for (int x = 0; x < 3; x++) {
                        reels.get(i).appendText(SlotMachine.machine.get(i).getFrontIcons().get(x) + "\n");
                    }
                }
                amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$");
                if(!SlotMachine.lines.isEmpty()) {
                    lineArea.clear();
                    lineArea.appendText("Gewonnene Linien: \n");
                    for (int i = 0; i < SlotMachine.lines.size(); i++) {
                        lineArea.appendText(SlotMachine.lines.get(i) + "\n");
                    }
                }
                //Ausgeben des Gewinns bzw. ob zu wenig Geld da ist
                if (gain > 0) {
                    gainLabel.setText("Gewinn: " + gain + " VC$");
                } else {
                    gainLabel.setText("Kein Gewinn.");
                }
                if (VirtualCasinoController.getCurrAmount() == 0) {
                    playBtn.setDisable(true);
                }

        }
    }