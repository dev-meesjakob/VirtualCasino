package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
    private Label lineLabel; // Hilfslabel für die Ausgabe der gewonnenen Linien

    //Initialisieren des Fensters beim Starten
    public void initialize() {
        SlotMachine.machineCreate(); //Erzeugen der SlotMachine
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$"); //aktuellen Kontostand anzeigen
        if (VirtualCasinoController.getCurrAmount() == 0) {
            playBtn.setDisable(true);
        }

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
        //prüft zu Beginn, ob Einsatz eingegeben wurde
        if (!betFld.getText().equals("")) {
            int bet = Integer.parseInt(betFld.getText());
            //ArrayList<String> allIcons = SlotMachine.spin(bet); //Aufrufen der eigentlichen Spiellogik
            SlotMachine.spin(bet);

            //Anzeigen der Symbole auf den Walzen (TextAreas), des Kontostand und der gewonnenen Linien
            /*int zaehler = 0;
            for (int i = 0; i < 5; i++) {
                reels.get(i).clear();
                for (int x = 0; x < 3; x++) {
                    reels.get(i).appendText(allIcons.get(zaehler) + "\n");
                    zaehler++;
                }
            }*/
            for (int i = 0; i < 5; i++) {
                reels.get(i).clear();
                for (int x = 0; x < 3; x++) {
                    reels.get(i).appendText(SlotMachine.machine.get(i).getFrontIcons().get(x) + "\n");
                }
            }
            amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
            if(!SlotMachine.lines.isEmpty()) {
                lineLabel.setText("Gewonnene Linien: " + SlotMachine.lines.get(0));
            }
        }
    }
}
