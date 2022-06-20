package com.toja.virtualcasino;

import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

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
    private Label gainLabel; //label für Anzeige des Gewinns
    @FXML
    private TextArea lineArea; //Area für die gewonnenen Linien

    TranslateTransition reelSpin = new TranslateTransition();

    //ImageView wheelImg = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(""))));

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

                SlotMachine.machine.get(i).createFrontIcons();
                ImageView symbol = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/slotsymbols/" + SlotMachine.machine.get(i).getFrontIcons().get(j) + ".png"))));
                symbol.setFitWidth(30);
                symbol.setFitHeight(30);
                reels2.get(i).get(j).setGraphic(symbol);
            }
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

        int gain = SlotMachine.spin(bet);

        //Für das "Drehen" der Walzen
        /*reelSpin.setNode(reels2.get(0).get(0));
        reelSpin.setNode(reels2.get(0).get(1));
        reelSpin.setDuration(Duration.millis(100));
        reelSpin.setFromY(-10.0);
        reelSpin.setByY(30);
        reelSpin.setCycleCount(15);
        reelSpin.play();*/

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