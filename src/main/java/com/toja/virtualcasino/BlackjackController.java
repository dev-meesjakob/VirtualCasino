package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class BlackjackController {
    // Kartensatz, Hände und Spiel definieren
    public ArrayList<Card> deck1 = Card.create52Deck();
    public ArrayList<ArrayList<Card>> playerHands = new ArrayList<>();
    public ArrayList<Card> dealerHand = new ArrayList<>();
    public Blackjack main = new Blackjack(deck1, 0, playerHands, dealerHand);

    // playerScore1 = Spieler Punkte mit allen Assen als 1 Punkt
    private int playerScore1 = 0;
    // playerScore11 = Spieler Punkte mit einem Ass als 11 Punkte
    private int playerScore11 = 0;
    // Punktzahl des Dealers
    private int dealerScore = 0;
    // Wurde das Spiel erstmals gestartet?
    private boolean isStart = true;

    // UI Elemente
    @FXML private Label playerScoreLbl;
    @FXML private Label dealerScoreLbl;

    @FXML private Label playerCurrencyLbl;
    @FXML private Label playerBetLbl;

    @FXML private TextField betFld;

    @FXML private TextArea playerCardDisplay;
    @FXML private TextArea dealerCardDisplay;

    @FXML private Button hitBtn;
    @FXML private Button betBtn;
    @FXML private Button doubleBtn;

    // Initialisieren (wird automatisch sofort aufgerufen, aktualisiert Geldanzeige
    public void initialize() {
        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
        doubleBtn.setDisable(true);
    }

    // Spiel starten/neu starten
    public void startGame() {
        if (!isStart) {
            // Spielelemente zurücksetzen
            deck1 = Card.create52Deck();
            playerHands = new ArrayList<>();
            dealerHand = new ArrayList<>();
            main = new Blackjack(deck1, main.getPlayerBet(), playerHands, dealerHand);

            // Punkteberechnung zurücksetzen
            playerScore1 = 0;
            playerScore11 = 0;
            dealerScore = 0;
            playerScoreLbl.setText("Points: 0");
            dealerScoreLbl.setText("Points: 0");
            betFld.clear();

            // Kartenanzeige zurücksetzen
            playerCardDisplay.setText("");
            dealerCardDisplay.setText("");
        }
        // Dealer bekommt eine Karte
        hit();

        // Spieler bekommt zwei Karten
        playerHands.add(new ArrayList<>());
        for (int i = 0; i < 2; i++) {
            hitButton();
        }

        betBtn.setDisable(true);
        doubleBtn.setDisable(false);
        isStart = false;
    }

    // Hit für den Spieler
    @FXML
    public void hitButton() {
        // Verdoppeln ausschalten
        doubleBtn.setDisable(true);
        // Karte ziehen
        Card temp = Blackjack.drawCard(deck1);
        // Karte zur (ersten) Hand hinzufügen
        main.getPlayerHands().get(0).add(temp);
        // Karte anzeigen
        playerCardDisplay.setText(playerCardDisplay.getText() + temp.getNameString() + "\n");

        // Ass Zählung (noch nicht ganz richtig)
        int cardVal = temp.getValue();
        if (cardVal == 1) {
            if (playerScore1 + 11 <= 21 && playerScore11 == 0) {
                playerScore11 = playerScore1 + 11;
                playerScore1 += 1;
            } else if (playerScore11 == 0) {
                playerScore1 += 1;
            } else {
                playerScore11 += 1;
                playerScore1 += 1;
            }
        } else {
            // Alle Karten außer Ass
            if (playerScore11 == 0) {
                playerScore1 += cardVal;
            } else {
                playerScore1 += cardVal;
                playerScore11 += cardVal;
            }
        }

        if (playerScore11 < 22 && playerScore11 > playerScore1) {
            playerScoreLbl.setText("Points: " + playerScore11);
        } else if (playerScore1 < 22) {
            playerScoreLbl.setText("Points: " + playerScore1);
        } else {
            playerScoreLbl.setText("Points: " + playerScore1);
            hitBtn.setDisable(true);

            // Spieler verliert
            playerCardDisplay.setText(playerCardDisplay.getText() + "You lose!");

            betBtn.setDisable(false);
            playerBetLbl.setText("No bet yet");
        }

    }

    // "Hit" für den Dealer (fast selbes Prinzip wie Spieler)
    public void hit() {
        Card temp = Blackjack.drawCard(deck1);
        main.getDealerCards().add(temp);
        dealerCardDisplay.setText(dealerCardDisplay.getText() + temp.getNameString() + "\n");

        if (temp.getValue() == 1) {
            dealerScore += 11;
        } else if (temp.getValue() == 1 && (dealerScore + 11) > 21) {
            dealerScore += temp.getValue();
        } else {
            dealerScore += temp.getValue();
        }
        dealerScoreLbl.setText("Points: " + dealerScore);
    }

    // Spieler hört auf
    @FXML
    public void stand() {

        // Feststellung der besten Punktzahl des Spielers
        int finScore;

        if (playerScore11 > playerScore1 && playerScore11 < 22) {
            finScore = playerScore11;
        } else {
            finScore = playerScore1;
        }
        hitBtn.setDisable(true);

        // Dealer zieht jetzt seine Karten
        while (dealerScore < 17) {
            hit();
        }

        // Feststellung des Ergebnisses
        if (finScore > dealerScore || dealerScore > 21) {
            // Spieler gewinnt -> Gewinn anzeigen & Währung hinzufügen
            playerCardDisplay.setText(playerCardDisplay.getText() + "You win!");
            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 2*main.getPlayerBet());
            System.out.println("Added money: " + main.getPlayerBet());
            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

            // Wette zurücksetzen
            playerBetLbl.setText("No bet yet");
            betBtn.setText("Bet to Restart");
            betBtn.setDisable(false);
        } else if (finScore == dealerScore) {
            // Unentschieden
            playerCardDisplay.setText(playerCardDisplay.getText() + "Draw!");
            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + main.getPlayerBet());
            System.out.println("Added money: " + main.getPlayerBet());
            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

            playerBetLbl.setText("No bet yet");
            betBtn.setText("Bet to Restart");
            betBtn.setDisable(false);
        } else {
            // Spieler verliert
            playerCardDisplay.setText(playerCardDisplay.getText() + "You lose!");

            playerBetLbl.setText("No bet yet");
            betBtn.setText("Bet to Restart");
            betBtn.setDisable(false);
        }
    }

    // Einsatz des Spielers
    @FXML public void makeBet() {
        // Wert aus UI holen
        int playerBet = Integer.parseInt(betFld.getText());
        // Hat der Spieler genug Geld?
        if (playerBet > VirtualCasinoController.getCurrAmount()) {
            // Nein, Wette zurücksetzen
            betFld.clear();
            betFld.setPromptText("Not enough money");
        } else {
            // Ja, Wette angenommen -> Geld subtrahieren, Wette anzeigen, "Hit" anschalten
            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - playerBet);
            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
            main.makeBet(playerBet);
            System.out.println("Made bet: " + main.getPlayerBet());
            playerBetLbl.setText((main.getPlayerBet()) + " VC$");
            betBtn.setDisable(true);
            hitBtn.setDisable(false);
            doubleBtn.setDisable(false);
        }
        startGame();
    }

    // Spieler kann am Anfang verdoppeln
    @FXML public void playerDouble() {
        if (VirtualCasinoController.getCurrAmount() < main.getPlayerBet()) {
            betFld.clear();
            betFld.setPromptText("Not enough money");
            return;
        }

        hitBtn.setDisable(true);
        doubleBtn.setDisable(true);

        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - main.getPlayerBet());
        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
        main.makeBet(2*main.getPlayerBet());
        playerBetLbl.setText((main.getPlayerBet()) + " VC$");

        hitButton();

        if (playerScore11 == 0 && playerScore1 <= 21) {
            stand();
        } else if (playerScore11 <= 21 && playerScore11 != 0) {
            stand();
        }
    }
}