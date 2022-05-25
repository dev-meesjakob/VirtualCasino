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
    private Label amountLabel;
    @FXML
    private Button playBtn;
    @FXML
    private ArrayList<TextArea> reels = new ArrayList<>();
    @FXML
    private Pane slotPane;
    @FXML
    private TextField betFld;

    public void initialize() {
        SlotMachine.machineCreate();
        amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
        if (VirtualCasinoController.getCurrAmount() == 0) {
            playBtn.setDisable(true);
        }
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

    public void playSlot() {
        if (!betFld.getText().equals("")) {
            int bet = Integer.parseInt(betFld.getText());
            ArrayList<String> allIcons = SlotMachine.spin(bet);

            int zaehler = 0;
            for (int i = 0; i < 5; i++) {
                reels.get(i).clear();
                for (int x = 0; x < 3; x++) {
                    reels.get(i).appendText(allIcons.get(zaehler) + "\n");
                    zaehler++;
                }
            }
            amountLabel.setText(VirtualCasinoController.getCurrAmount() + "$");
        }
    }
}
