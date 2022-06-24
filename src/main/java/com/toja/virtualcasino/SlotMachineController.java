package com.toja.virtualcasino;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private ArrayList<Label> lines = new ArrayList<>(); //Anzeige der gewonnenen Linien
    @FXML
    private Pane slotPane; //Hintergrund
    @FXML
    private TextField betFld; //Feld für den Einsatz des Spielers
    @FXML
    private Label gainLabel; //label für Anzeige des Gewinns
    @FXML
    private TextArea lineArea; //Area für die gewonnenen Linien
    @FXML
    private VBox box;


    //Initialisieren des Fensters beim Starten
    public void initialize() {
        SlotMachine.machineCreate(); //Erzeugen der SlotMachine
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$"); //aktuellen Kontostand anzeigen
        if (VirtualCasinoController.getCurrAmount() == 0) {
            playBtn.setDisable(true); //Button "ausschalten" bei keinem Geld
        }

        //TextAreas für die einzelnen Walzen erstellen
        for (int i = 0; i < 5; i++) {
            TextArea temp = new TextArea();
            reels.add(temp);
            reels.get(i).setEditable(false);
            reels.get(i).setLayoutY(43.0);
            reels.get(i).setPrefHeight(120.0);
            reels.get(i).setPrefWidth(80.0);
            reels.get(i).setLayoutX(65 + 85*i);
            slotPane.getChildren().add(reels.get(i));
        }
        for (int i = 0; i < 5; i++) {
            reels2.add(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                //Label für die Symbole erstellen
                Label temp = new Label();
                reels2.get(i).add(temp);
                reels2.get(i).get(j).setLayoutY(48.0 + j*40);
                reels2.get(i).get(j).setPrefHeight(30);
                reels2.get(i).get(j).setPrefWidth(30);
                reels2.get(i).get(j).setLayoutX(90 + 85 * i);
                slotPane.getChildren().add(reels2.get(i).get(j));

                //Symbole in die Label packen
                SlotMachine.machine.get(i).createFrontIcons();
                ImageView symbol = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/slotsymbols/" + SlotMachine.machine.get(i).getFrontIcons().get(j) + ".png"))));
                symbol.setFitWidth(30);
                symbol.setFitHeight(30);
                reels2.get(i).get(j).setGraphic(symbol);
            }
        }
        for (int i = 0; i < 9; i++) {
            //Gewinnlinien laden und anzeigen
            Label temp = new Label();
            lines.add(temp);
            lines.get(i).setLayoutY(38);
            lines.get(i).setPrefHeight(130);
            lines.get(i).setPrefWidth(460);
            lines.get(i).setLayoutX(45);
            lines.get(i).setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/slotlines/line" + (i+1) + ".png")))));
            lines.get(i).setVisible(false);
            slotPane.getChildren().add(lines.get(i));
        }

    }

    //Anzeige aller möglichen Gewinnlinien
    public void showLines() {
        for (int i = 0; i < 9; i++) {
            lines.get(i).setVisible(true);
            lines.get(i).setOpacity(0.5);
        }
    }

    //läuft ab bei Drücken auf den Knopf
    public void playSlot() {
        int bet;
        //Prüfen, ob ein sinnvoller Einsatz angegeben wurde
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

        //Gewinnlinien unsichtbar machen
        for (int i = 0; i < 9; i++) {
            lines.get(i).setVisible(false);
            lines.get(i).setOpacity(1.0);
        }

        //Eigentliche Spiellogik
        int gain = SlotMachine.spin(bet);



                //Anzeige der bekommenen Symbole
                for (int i = 0; i < 5; i++) {
                    for (int x = 0; x < 3; x++) {
                        ImageView symbol = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/slotsymbols/" + SlotMachine.machine.get(i).getFrontIcons().get(x) + ".png"))));
                        symbol.setFitWidth(30);
                        symbol.setFitHeight(30);
                        reels2.get(i).get(x).setGraphic(symbol);
                    }
                }
                //Anzeige der gewonnenen Gewinnlinien (textlich und visuell)
                amountLabel.setText(VirtualCasinoController.getCurrAmount() + " VC$");
                if(!SlotMachine.lines.isEmpty()) {
                    lineArea.clear();
                    lineArea.appendText("Gewonnene Linien: \n");
                    for (int i = 0; i < SlotMachine.lines.size(); i++) {
                        lineArea.appendText(SlotMachine.lines.get(i) + "\n");
                        lines.get(SlotMachine.lines.get(i) - 1).setVisible(true);
                    }
                }
                //Ausgeben des Gewinns
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