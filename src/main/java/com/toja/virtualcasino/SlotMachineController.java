package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Objects;

public class SlotMachineController {

    @FXML
    private Label amountLabel; //Label für den aktuellen Kontostand
    @FXML
    private Button playBtn; //Button zum Starten
    @FXML
    private ArrayList<TextArea> reels = new ArrayList<>(); //TextAreas für die Walzen
    @FXML
    private ArrayList<ArrayList<Label>> reels2 = new ArrayList<>(); //Label für die Walzen
    @FXML
    private Pane slotPane; //Hintergrund
    @FXML
    private TextField betFld; //Feld für den Einsatz des Spielers
    @FXML
    private Label lineLabel; // Hilfslabel für die Ausgabe der gewonnenen Linien
    @FXML
    private Label gainLabel; //label für Anzeige des Gewinns

    //ImageView wheelImg = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(""))));

    //Initialisieren des Fensters beim Starten
    public void initialize() {
        SlotMachine.machineCreate(); //Erzeugen der SlotMachine
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$"); //aktuellen Kontostand anzeigen
        if (VirtualCasinoController.getCurrAmount() == 0) {
            playBtn.setDisable(true); //Btn "ausschalten" bei keinem Geld
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
        for (int i = 0; i < 5; i++) {
            reels2.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                Label temp = new Label();
                reels2.get(i).add(temp);
                reels2.get(i).get(j).setLayoutY(48.0 + j*40);
                reels2.get(i).get(j).setPrefHeight(30);
                reels2.get(i).get(j).setPrefWidth(30);
                reels2.get(i).get(j).setLayoutX(70 + 95 * i);
                slotPane.getChildren().add(reels2.get(i).get(j));
            }
        }
    }

    //läuft ab bei Drücken auf den Knopf
    public void playSlot() {
        //prüft zu Beginn, ob Einsatz eingegeben wurde
        if (betFld.getText().equals("")) {
            gainLabel.setText("Wetteinsatz eingeben!"); //Wenn der Spieler nichts eingibt
        } else if (!betFld.getText().matches("[+-]?\\d*(\\.\\d+)?")) {
            gainLabel.setText("Nur Zahlen eingeben!"); //Wenn der Spieler Quatsch eingibt
        } else if (Integer.parseInt(betFld.getText()) > VirtualCasinoController.getCurrAmount()) {
            gainLabel.setText("Nicht genug Geld!"); //Wenn der Spieler etwas zu hohes eingibt
        } else{
                int bet = Integer.parseInt(betFld.getText());
                //ArrayList<String> allIcons = SlotMachine.spin(bet); //Aufrufen der eigentlichen Spiellogik
                int gain = SlotMachine.spin(bet);

                for (int i = 0; i < 5; i++) {
                    for (int x = 0; x < 3; x++) {
                        ImageView symbol = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/slotsymbols/" + SlotMachine.machine.get(i).getFrontIcons().get(x) + ".png"))));
                        symbol.setFitWidth(30);
                        symbol.setFitHeight(30);
                        reels2.get(i).get(x).setGraphic(symbol);
                    }
                }
                amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$");
                if(!SlotMachine.lines.isEmpty()) {
                    lineLabel.setText("Gewonnene Linien: " + SlotMachine.lines.get(0));
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
    }