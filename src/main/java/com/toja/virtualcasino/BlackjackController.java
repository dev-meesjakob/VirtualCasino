package com.toja.virtualcasino;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class BlackjackController {
    private final ArrayList<ArrayList<Integer>> playerScores = new ArrayList<>();
    private final ArrayList<Integer> finScores = new ArrayList<>();
    private final ArrayList<Boolean> isDone = new ArrayList<>();
    // Kartensatz, Hände und Spiel definieren
    public ArrayList<Card> deck1 = Card.create52Deck();
    public ArrayList<ArrayList<Card>> playerHands = new ArrayList<>();
    public ArrayList<Card> dealerHand = new ArrayList<>();
    public ArrayList<Integer> playerBet = new ArrayList<>();
    public Blackjack main = new Blackjack(deck1, playerBet, playerHands, dealerHand);
    // Punktzahl des Dealers
    private int dealerScore = 0;
    // Wurde das Spiel erstmals gestartet?
    private boolean isStart = true;

    private ImageView lastPlayerCard;
    private ImageView lastDealerCard;

    // UI Elemente
    @FXML
    private Label playerScoreLbl;
    @FXML
    private Label splitScoreLbl1;
    @FXML
    private Label splitScoreLbl2;
    @FXML
    private Label dealerScoreLbl;

    @FXML
    private Label playerCurrencyLbl;
    @FXML
    private Label playerBetLbl;

    @FXML
    private TextField betFld;

    @FXML
    private TextArea playerMainHand;
    @FXML
    private TextArea playerSplitHand1;
    @FXML
    private TextArea playerSplitHand2;
    @FXML
    private TextArea dealerCardDisplay;

    @FXML
    private Button hitBtn;
    @FXML
    private Button hitBtn1;
    @FXML
    private Button hitBtn2;

    @FXML
    private Button standBtn;
    @FXML
    private Button standBtn1;
    @FXML
    private Button standBtn2;

    @FXML
    private Button betBtn;

    @FXML
    private Button doubleBtn;
    @FXML
    private Button doubleBtn1;
    @FXML
    private Button doubleBtn2;

    @FXML
    private Button splitBtn;

    @FXML
    private ImageView testImg;

    @FXML private Pane mainPane;

    // Initialisieren (wird automatisch sofort aufgerufen, aktualisiert Geldanzeige, etc)
    public void initialize() {
        lastPlayerCard = testImg;
        Image testImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("img/blackjack/cards/2_of_spades.png")));
        ImageView test2 = new ImageView();
        mainPane.getChildren().add(test2);
        test2.setLayoutX(testImg.getLayoutX() + 17);
        test2.setLayoutY(25);
        test2.setFitWidth(75);
        test2.setFitHeight(100);
        testImg.setFitWidth(75);
        testImg.setFitHeight(100);
        test2.setImage(testImage);
        testImg.setImage(testImage);

        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
        doubleBtn.setDisable(true);

        ArrayList<Integer> temp1 = new ArrayList<>();
        ArrayList<Integer> temp2 = new ArrayList<>();

        playerBet.add(0);
        playerBet.add(0);

        playerScores.add(temp1);
        playerScores.add(temp2);

        playerScores.get(0).add(0);
        playerScores.get(0).add(0);
        playerScores.get(1).add(0);
        playerScores.get(1).add(0);

        finScores.add(0);
        finScores.add(0);
        isDone.add(false);
        isDone.add(false);
    }

    public void newCardDisplay(Card card, double xPos, boolean player) {
        ImageView temp = new ImageView();
        temp.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getFilePath()))));
        temp.setLayoutX(xPos + 17);
        temp.setLayoutY(25);
        temp.setFitWidth(75);
        temp.setFitHeight(100);

        if (player) {
            lastPlayerCard = temp;
        } else {
            lastDealerCard = temp;
        }
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
            playerScores.get(0).set(0, 0);
            playerScores.get(0).set(1, 0);
            playerScores.get(1).set(0, 0);
            playerScores.get(1).set(1, 0);

            finScores.set(0, 0);
            finScores.set(1, 0);

            isDone.set(0, false);
            isDone.set(1, false);

            dealerScore = 0;
            playerScoreLbl.setText("Points: 0");
            dealerScoreLbl.setText("Points: 0");
            betFld.clear();

            // Kartenanzeige zurücksetzen
            playerMainHand.setText("");
            dealerCardDisplay.setText("");

            playerMainHand.setVisible(true);
            playerScoreLbl.setVisible(true);

            hitBtn.setDisable(false);
            hitBtn1.setVisible(false);
            hitBtn2.setVisible(false);
            standBtn.setDisable(false);
            standBtn1.setVisible(false);
            standBtn2.setVisible(false);
            doubleBtn.setDisable(false);
            doubleBtn1.setVisible(false);
            doubleBtn2.setVisible(false);

            splitScoreLbl1.setVisible(false);
            splitScoreLbl2.setVisible(false);


            playerSplitHand1.setVisible(false);
            playerSplitHand2.setVisible(false);
        }
        // Dealer bekommt eine Karte
        hit();

        // Spieler bekommt zwei Karten
        playerHands.add(new ArrayList<>());

        /*playerHands.get(0).add(0, new Card(Card.Rank.ACE, Card.Suit.SPADES));
        playerHands.get(0).add(1, new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        playerMainHand.setText(playerHands.get(0).get(0).getNameString() + "\n");
        playerMainHand.setText(playerMainHand.getText() + playerHands.get(0).get(1).getNameString() + "\n");
        */
        for (int i = 0; i < 2; i++) {
            hitButton(false, 0);
        }

        if (playerHands.get(0).get(0).getRank() == playerHands.get(0).get(1).getRank()) {
            splitBtn.setDisable(false);
        }

        betBtn.setDisable(true);
        doubleBtn.setDisable(false);
        isStart = false;
    }

    // Hit für den Spieler
    @FXML
    public void hitButton(boolean split, int hand) {
        // Karte ziehen
        Card temp = Blackjack.drawCard(deck1);
        // Karte zur Hand hinzufügen
        main.getPlayerHands().get(hand).add(temp);

        // Karte anzeigen & Verdoppeln ausschalten
        if (hand == 0 && split) {
            doubleBtn1.setDisable(true);
            newCardDisplay(temp, lastPlayerCard.getX(), true);
            playerSplitHand1.setText(playerSplitHand1.getText() + temp.getNameString() + "\n");
        } else if (hand == 1) {
            doubleBtn2.setDisable(true);
            newCardDisplay(temp, lastPlayerCard.getX(), true);
            playerSplitHand2.setText(playerSplitHand2.getText() + temp.getNameString() + "\n");
        } else {
            doubleBtn.setDisable(true);
            newCardDisplay(temp, lastPlayerCard.getX(), true);
            playerMainHand.setText(playerMainHand.getText() + temp.getNameString() + "\n");
        }

        // Zählung
        int cardVal = temp.getValue();
        scoreHands(hand, cardVal);

        if (playerScores.get(hand).get(1) < 22 && playerScores.get(hand).get(1) > playerScores.get(hand).get(0)) {
            if (hand == 0 && split) {
                splitScoreLbl1.setText("Points: " + playerScores.get(hand).get(1));
            } else if (hand == 1) {
                splitScoreLbl2.setText("Points: " + playerScores.get(hand).get(1));
            } else {
                playerScoreLbl.setText("Points: " + playerScores.get(hand).get(1));
            }
        } else if (playerScores.get(hand).get(0) < 22) {
            if (hand == 0 && split) {
                splitScoreLbl1.setText("Points: " + playerScores.get(hand).get(0));
            } else if (hand == 1) {
                splitScoreLbl2.setText("Points: " + playerScores.get(hand).get(0));
            } else {
                playerScoreLbl.setText("Points: " + playerScores.get(hand).get(0));
            }
        } else {
            if (hand == 0 && split) {
                splitScoreLbl1.setText("Points: " + playerScores.get(hand).get(0));
                hitBtn1.setDisable(true);
                standBtn1.setDisable(true);
                betBtn.setDisable(false);
                playerBetLbl.setText("No bet yet");
                isDone.set(0, true);
            } else if (hand == 1) {
                splitScoreLbl2.setText("Points: " + playerScores.get(hand).get(0));
                hitBtn2.setDisable(true);
                standBtn2.setDisable(true);
                betBtn.setDisable(false);
                playerBetLbl.setText("No bet yet");
                isDone.set(1, true);
            } else {
                playerScoreLbl.setText("Points: " + playerScores.get(hand).get(0));
                playerMainHand.setText(playerMainHand.getText() + "You lose!");
                hitBtn.setDisable(true);
                betBtn.setDisable(false);
                playerBetLbl.setText("No bet yet");
                isDone.set(0, true);
            }
            if (isDone.get(0) && isDone.get(1)) {
                stand(true, 0, isDone);
            }
        }

    }

    private void scoreHands(int hand, int cardVal) {
        if (cardVal == 1) {
            if (playerScores.get(hand).get(0) + 11 <= 21 && playerScores.get(hand).get(1) == 0) {
                playerScores.get(hand).set(1, playerScores.get(hand).get(0) + 11);
                playerScores.get(hand).set(0, playerScores.get(hand).get(0) + 1);
            } else if (playerScores.get(hand).get(1) == 0) {
                playerScores.get(hand).set(0, playerScores.get(hand).get(0) + 1);
            } else {
                playerScores.get(hand).set(1, playerScores.get(hand).get(1) + 1);
                playerScores.get(hand).set(0, playerScores.get(hand).get(0) + 1);
            }
        } else {
            // Alle Karten außer Ass
            if (playerScores.get(hand).get(1) == 0) {
                playerScores.get(hand).set(0, playerScores.get(hand).get(0) + cardVal);
            } else {
                playerScores.get(hand).set(0, playerScores.get(hand).get(0) + cardVal);
                playerScores.get(hand).set(1, playerScores.get(hand).get(1) + cardVal);
            }
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
    public void stand(boolean split, int hand, ArrayList<Boolean> isDone) {
        /* fix when player loses one hand to going over 21*/

        if (split) {
            if (playerScores.get(hand).get(1) > playerScores.get(hand).get(0) && playerScores.get(hand).get(1) < 22) {
                finScores.set(hand, playerScores.get(hand).get(1));
            } else {
                finScores.set(hand, playerScores.get(hand).get(0));
            }

            isDone.set(hand, true);

            if (hand == 0) {
                hitBtn1.setDisable(true);
                standBtn1.setDisable(true);
            } else {
                hitBtn2.setDisable(true);
                standBtn2.setDisable(true);
            }
        } else {
            if (playerScores.get(hand).get(1) > playerScores.get(hand).get(0) && playerScores.get(hand).get(1) < 22) {
                finScores.set(hand, playerScores.get(hand).get(1));
            } else {
                finScores.set(hand, playerScores.get(0).get(0));
            }
            isDone.set(hand, true);
            standBtn.setDisable(true);

            // Dealer zieht jetzt seine Karten
            while (dealerScore < 17) {
                hit();
            }


            // Feststellung des Ergebnisses
            if (finScores.get(0) > dealerScore && finScores.get(0) <= 21 || (dealerScore > 21 && finScores.get(0) <= 21)) {
                // Spieler gewinnt -> Gewinn anzeigen & Währung hinzufügen
                playerMainHand.setText(playerMainHand.getText() + "You win!");
                VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 2 * main.getPlayerBet().get(0));
                playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

                // Wette zurücksetzen
                playerBetLbl.setText("No bet yet");
                betBtn.setText("Bet to Restart");
                betBtn.setDisable(false);
            } else if (finScores.get(0) == dealerScore) {
                // Unentschieden
                playerMainHand.setText(playerMainHand.getText() + "Draw!");
                VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + main.getPlayerBet().get(0));
                playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

                playerBetLbl.setText("No bet yet");
                betBtn.setText("Bet to Restart");
                betBtn.setDisable(false);
            } else {
                // Spieler verliert
                playerMainHand.setText(playerMainHand.getText() + "You lose!");

                playerBetLbl.setText("No bet yet");
                betBtn.setText("Bet to Restart");
                betBtn.setDisable(false);
            }
        }

        if (split) {
            if (isDone.get(0) && isDone.get(1)) {
                // Dealer zieht jetzt seine Karten
                while (dealerScore < 17) {
                    hit();
                }

                for (int i = 0; i < 2; i++) {
                    // Feststellung des Ergebnisses
                    if (finScores.get(i) > dealerScore && finScores.get(i) <= 21 || (dealerScore > 21 && finScores.get(i) <= 21)) {
                        // Spieler gewinnt -> Gewinn anzeigen & Währung hinzufügen
                        if (i == 0) {
                            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 2 * main.getPlayerBet().get(0));
                            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
                            playerSplitHand1.setText(playerSplitHand1.getText() + "You win!");

                        } else {
                            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 2 * main.getPlayerBet().get(1));
                            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
                            playerSplitHand2.setText(playerSplitHand2.getText() + "You win!");

                            // Wette zurücksetzen
                            playerBetLbl.setText("No bet yet");
                            betBtn.setText("Bet to Restart");
                            betBtn.setDisable(false);
                        }
                    } else if (finScores.get(i) == dealerScore) {
                        // Unentschieden
                        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + main.getPlayerBet().get(0));
                        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

                        if (i == 0) {
                            playerSplitHand1.setText(playerSplitHand1.getText() + "Draw!");
                        } else {
                            playerSplitHand2.setText(playerSplitHand2.getText() + "Draw!");

                            // Wette zurücksetzen
                            playerBetLbl.setText("No bet yet");
                            betBtn.setText("Bet to Restart");
                            betBtn.setDisable(false);
                        }
                    } else {
                        // Spieler verliert
                        if (i == 0) {
                            playerSplitHand1.setText(playerSplitHand1.getText() + "You lose!");
                        } else {
                            playerSplitHand2.setText(playerSplitHand2.getText() + "You lose!");

                            // Wette zurücksetzen
                            playerBetLbl.setText("No bet yet");
                            betBtn.setText("Bet to Restart");
                            betBtn.setDisable(false);
                        }
                    }
                }
            }
        }
    }

    // Einsatz des Spielers
    @FXML
    public void makeBet() {
        // Wert aus UI
        int playerBet = 0;

        try {
            playerBet = Integer.parseInt(betFld.getText());
            if (playerBet <= 0) {
                betFld.clear();
                betFld.setPromptText("Not a valid bet");
                return;
            }
        } catch (NumberFormatException e) {
            betFld.clear();
            betFld.setPromptText("Not a valid bet");
            return;
        }

        // Hat der Spieler genug Geld?
        if (playerBet > VirtualCasinoController.getCurrAmount()) {
            // Nein, Wette zurücksetzen
            betFld.clear();
            betFld.setPromptText("Not enough money");
            return;
        } else {
            // Ja, Wette angenommen -> Geld subtrahieren, Wette anzeigen, "Hit" anschalten
            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - playerBet);
            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
            main.makeBet(playerBet, 0);
            playerBetLbl.setText((main.getPlayerBet().get(0)) + " VC$");
            betBtn.setDisable(true);
            hitBtn.setDisable(false);
            doubleBtn.setDisable(false);
        }
        startGame();
    }

    // Spieler kann am Anfang verdoppeln
    @FXML
    public void playerDouble(boolean split, int hand) {

        if (VirtualCasinoController.getCurrAmount() < main.getPlayerBet().get(0)) {
            betFld.clear();
            betFld.setPromptText("Not enough money");
            return;
        }

        hitBtn.setDisable(true);
        doubleBtn.setDisable(true);

        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - main.getPlayerBet().get(0));
        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
        main.makeBet(2 * main.getPlayerBet().get(0), 0);
        playerBetLbl.setText((main.getPlayerBet()) + " VC$");

        hitButton(false, 0);

        if (playerScores.get(0).get(1) == 0 && playerScores.get(0).get(0) <= 21) {
            stand(false, 0, isDone);
        } else if (playerScores.get(0).get(1) <= 21 && playerScores.get(0).get(1) != 0) {
            stand(false, 0, isDone);
        }
    }

    @FXML
    public void playerSplit() {
        if (VirtualCasinoController.getCurrAmount() < main.getPlayerBet().get(0)) {
            betFld.clear();
            betFld.setPromptText("Not enough money");
            return;
        }

        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - main.getPlayerBet().get(0));
        playerBetLbl.setText(main.getPlayerBet() + " (1); " + main.getPlayerBet() + " (2) ");
        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
        playerMainHand.setVisible(false);
        playerScoreLbl.setVisible(false);

        splitBtn.setDisable(true);
        hitBtn.setDisable(true);
        hitBtn1.setDisable(false);
        hitBtn2.setDisable(false);
        hitBtn1.setVisible(true);
        hitBtn2.setVisible(true);
        standBtn.setDisable(true);
        standBtn1.setDisable(false);
        standBtn2.setDisable(false);
        standBtn1.setVisible(true);
        standBtn2.setVisible(true);
        doubleBtn.setDisable(true);
        doubleBtn1.setVisible(true);
        doubleBtn2.setVisible(true);

        splitScoreLbl1.setVisible(true);
        splitScoreLbl2.setVisible(true);


        playerSplitHand1.setVisible(true);
        playerSplitHand1.setText(playerHands.get(0).get(0).getNameString() + "\n");

        playerSplitHand2.setVisible(true);
        ArrayList<Card> temp = new ArrayList<>();
        Card test = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        playerHands.add(temp);
        playerHands.get(1).add(0, test);
        playerHands.get(1).set(0, playerHands.get(0).get(1));
        playerHands.get(0).remove(1);
        playerSplitHand2.setText(playerHands.get(1).get(0).getNameString() + "\n");

        for (int i = 0; i < 2; i++) {
            hitButton(true, i);
        }

        playerScores.get(0).set(0, 0);
        playerScores.get(0).set(1, 0);
        playerScores.get(1).set(0, 0);
        playerScores.get(1).set(1, 0);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                // Zählung
                int cardVal = playerHands.get(i).get(j).getValue();

                scoreHands(i, cardVal);

                if (playerScores.get(i).get(1) < 22 && playerScores.get(i).get(1) > playerScores.get(i).get(0)) {
                    if (i == 0) {
                        splitScoreLbl1.setText("Points: " + playerScores.get(i).get(1));
                    } else {
                        splitScoreLbl2.setText("Points: " + playerScores.get(i).get(1));
                    }
                } else if (playerScores.get(i).get(0) < 22) {
                    if (i == 0) {
                        splitScoreLbl1.setText("Points: " + playerScores.get(i).get(0));
                    } else {
                        splitScoreLbl2.setText("Points: " + playerScores.get(i).get(0));
                    }
                }
            }
        }
    }

    @FXML
    public void hitButtonHandler() {
        hitButton(false, 0);
    }

    @FXML
    public void hitButtonSplit1() {
        hitButton(true, 0);
    }

    @FXML
    public void hitButtonSplit2() {
        hitButton(true, 1);
    }

    @FXML
    public void standButtonHandler() {
        stand(false, 0, isDone);
    }

    @FXML
    public void standButtonSplit1() {
        stand(true, 0, isDone);
    }

    @FXML
    public void standButtonSplit2() {
        stand(true, 1, isDone);
    }

    @FXML
    public void doubleButtonHandler() {
        playerDouble(false, 0);
    }

    @FXML
    public void doubleButtonSplit1() {
        playerDouble(true, 0);
    }

    @FXML
    public void doubleButtonSplit2() {
        playerDouble(true, 1);
    }
}