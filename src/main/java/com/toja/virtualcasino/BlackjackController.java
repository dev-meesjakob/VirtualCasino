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
    public ArrayList<ImageView> playerCardImgs = new ArrayList<>();
    public ArrayList<Card> dealerHand = new ArrayList<>();
    public ArrayList<ImageView> dealerCardImgs = new ArrayList<>();
    public ArrayList<Integer> playerBet = new ArrayList<>();
    public Blackjack main = new Blackjack(deck1, playerBet, playerHands, dealerHand);
    // Punktzahl des Dealers
    private int dealerScore = 0;
    // Wurde das Spiel erstmals gestartet?
    private boolean isStart = true;

    private ImageView hand1LastCard;
    private ImageView hand2LastCard;
    private ImageView lastDealerCard;

    // UI Elemente
    @FXML
    private Label playerScoreLbl;
    @FXML
    private Label splitScoreLbl;
    @FXML
    private Label dealerScoreLbl;

    @FXML
    private Label playerCurrencyLbl;
    @FXML
    private Label playerBetLbl;
    @FXML
    private Label resultLbl;

    @FXML
    private TextField betFld;

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
    private ImageView playerHand1;
    @FXML
    private ImageView playerHand2;
    @FXML
    private ImageView dealerHandDisplay;
    @FXML
    private Pane mainPane;

    // Initialisieren (wird automatisch sofort aufgerufen, aktualisiert Geldanzeige, etc)
    public void initialize() {
        hand1LastCard = playerHand1;
        hand2LastCard = playerHand2;
        lastDealerCard = dealerHandDisplay;
        dealerHandDisplay.setLayoutX(308);
        playerHand1.setVisible(false);
        playerHand2.setVisible(false);
        dealerHandDisplay.setVisible(false);

        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

        playerScores.add(new ArrayList<>());
        playerScores.add(new ArrayList<>());
        playerScores.get(0).add(0);
        playerScores.get(0).add(0);
        playerScores.get(1).add(0);
        playerScores.get(1).add(0);

        playerBet.add(0);
        playerBet.add(0);

        finScores.add(0);
        finScores.add(0);
        isDone.add(false);
        isDone.add(false);
    }

    public void newCardDisplay(Card card, double xPos, boolean player, int hand) {
        ImageView temp = new ImageView();
        mainPane.getChildren().add(temp);
        temp.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(card.getFilePath()))));
        temp.setLayoutX(xPos + 17);

        if (player) {
            if (hand == 0) {
                temp.setLayoutY(25);
                hand1LastCard = temp;
            } else {
                temp.setLayoutY(145);
                hand2LastCard = temp;
            }
            temp.setFitWidth(65);
            temp.setFitHeight(94.38);
            playerCardImgs.add(temp);
        } else {
            temp.setLayoutY(30);
            temp.setFitWidth(100);
            temp.setFitHeight(145.2);
            lastDealerCard = temp;
            dealerCardImgs.add(temp);
        }
    }

    // Spiel starten/neu starten
    public void startGame() {
        if (!isStart) {
            for (ImageView playerCardImg : playerCardImgs) {
                mainPane.getChildren().remove(playerCardImg);
            }
            for (ImageView dealerCardImg : dealerCardImgs) {
                mainPane.getChildren().remove(dealerCardImg);
            }
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
            playerScoreLbl.setText("(1) Points: 0");
            resultLbl.setVisible(false);
            dealerScoreLbl.setText("Points: 0");
            betFld.clear();

            // Kartenanzeige zurücksetzen
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

            splitScoreLbl.setVisible(false);
            playerScoreLbl.setVisible(true);
            dealerScoreLbl.setVisible(true);

            lastDealerCard = dealerHandDisplay;
            hand1LastCard = playerHand1;
            hand2LastCard = playerHand2;
        }

        playerScoreLbl.setVisible(true);
        dealerScoreLbl.setVisible(true);

        // Dealer bekommt eine Karte
        hit();

        // Spieler bekommt zwei Karten
        playerHands.add(new ArrayList<>());

        playerHands.get(0).add(0, new Card(Card.Rank.ACE, Card.Suit.SPADES));
        newCardDisplay(playerHands.get(0).get(0), hand1LastCard.getLayoutX(), true, 0);
        playerHands.get(0).add(1, new Card(Card.Rank.ACE, Card.Suit.CLUBS));
        newCardDisplay(playerHands.get(0).get(1), hand1LastCard.getLayoutX(), true, 0);

        /*for (int i = 0; i < 2; i++) {
            hitButton(false, 0);
        }*/

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
        System.out.println(hitBtn.getLayoutX());
        // Karte ziehen
        Card temp = Blackjack.drawCard(deck1);
        // Karte zur Hand hinzufügen
        main.getPlayerHands().get(hand).add(temp);

        // Karte anzeigen & Verdoppeln ausschalten
        if (hand == 0 && split) {
            doubleBtn1.setDisable(true);
            newCardDisplay(temp, hand1LastCard.getLayoutX(), true, 0);
        } else if (hand == 1) {
            doubleBtn2.setDisable(true);
            newCardDisplay(temp, hand2LastCard.getLayoutX(), true, 1);
        } else {
            doubleBtn.setDisable(true);
            newCardDisplay(temp, hand1LastCard.getLayoutX(), true, 0);
        }

        // Zählung
        int cardVal = temp.getValue();
        scoreHands(hand, cardVal);

        if (playerScores.get(hand).get(1) < 22 && playerScores.get(hand).get(1) > playerScores.get(hand).get(0)) {
            if (hand == 0) {
                playerScoreLbl.setText("(1) Points: " + playerScores.get(hand).get(1));
            } else {
                splitScoreLbl.setText("(2) Points: " + playerScores.get(hand).get(1));
            }
        } else if (playerScores.get(hand).get(0) < 22) {
            if (hand == 0) {
                playerScoreLbl.setText("(1) Points: " + playerScores.get(hand).get(0));
            } else {
                splitScoreLbl.setText("(2) Points: " + playerScores.get(hand).get(0));
            }
        } else {
            if (hand == 0 && split) {
                playerScoreLbl.setText("(1) Points: " + playerScores.get(hand).get(0));
                hitBtn1.setDisable(true);
                standBtn1.setDisable(true);
                betBtn.setDisable(false);
                playerBetLbl.setText("No bet yet");
                isDone.set(0, true);
            } else if (hand == 1) {
                splitScoreLbl.setText("(2) Points: " + playerScores.get(hand).get(0));
                hitBtn2.setDisable(true);
                standBtn2.setDisable(true);
                betBtn.setDisable(false);
                playerBetLbl.setText("No bet yet");
                isDone.set(1, true);
            } else {
                playerScoreLbl.setText("(1) Points: " + playerScores.get(hand).get(0));
                resultLbl.setText("You lose!");
                resultLbl.setVisible(true);
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
        newCardDisplay(temp, lastDealerCard.getLayoutX(), false, 0);

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
                resultLbl.setText("You win!");
                resultLbl.setVisible(true);
                VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 2 * main.getPlayerBet().get(0));
                playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

                // Wette zurücksetzen
                playerBetLbl.setText("No bet yet");
                betBtn.setText("Bet to Restart");
                betBtn.setDisable(false);
            } else if (finScores.get(0) == dealerScore) {
                // Unentschieden
                resultLbl.setText("Draw!");
                resultLbl.setVisible(true);
                VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + main.getPlayerBet().get(0));
                playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

                playerBetLbl.setText("No bet yet");
                betBtn.setText("Bet to Restart");
                betBtn.setDisable(false);
            } else {
                // Spieler verliert
                resultLbl.setText("You lose!");
                resultLbl.setVisible(true);
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
                            resultLbl.setText("(1) Win!");
                            resultLbl.setVisible(true);

                        } else {
                            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + 2 * main.getPlayerBet().get(1));
                            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
                            resultLbl.setText(resultLbl.getText() + "; (2) Win!");
                            resultLbl.setVisible(true);

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
                            resultLbl.setText("(1) Draw!");
                            resultLbl.setVisible(true);
                        } else {
                            resultLbl.setText(resultLbl.getText() + "; (2) Draw!");
                            resultLbl.setVisible(true);

                            // Wette zurücksetzen
                            playerBetLbl.setText("No bet yet");
                            betBtn.setText("Bet to Restart");
                            betBtn.setDisable(false);
                        }
                    } else {
                        // Spieler verliert
                        if (i == 0) {
                            resultLbl.setText("(1) Loss!");
                            resultLbl.setVisible(true);
                        } else {
                            resultLbl.setText(resultLbl.getText() + "; (2) Loss!");
                            resultLbl.setVisible(true);

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
        int playerBet;

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

        if (split && hand == 0) {
            hitBtn1.setDisable(true);
            doubleBtn1.setDisable(true);
            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - main.getPlayerBet().get(0));
            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
            main.makeBet(2 * main.getPlayerBet().get(0), 0);
            playerBetLbl.setText((main.getPlayerBet()) + " VC$");
            hitButton(true, 0);

        } else if (hand == 1) {
            hitBtn2.setDisable(true);
            doubleBtn2.setDisable(true);
            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - main.getPlayerBet().get(1));
            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
            main.makeBet(2 * main.getPlayerBet().get(1), 1);
            playerBetLbl.setText((main.getPlayerBet()) + " VC$");
            hitButton(true, 1);

        } else {
            hitBtn.setDisable(true);
            doubleBtn.setDisable(true);
            VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - main.getPlayerBet().get(0));
            playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");
            main.makeBet(2 * main.getPlayerBet().get(0), 0);
            playerBetLbl.setText((main.getPlayerBet()) + " VC$");
            hitButton(false, 0);
        }

        if (playerScores.get(hand).get(1) == 0 && playerScores.get(hand).get(0) <= 21) {
            stand(split, hand, isDone);
        } else if (playerScores.get(hand).get(1) <= 21 && playerScores.get(hand).get(1) != 0) {
            stand(split, hand, isDone);
        }

        isDone.set(hand, true);
        if (isDone.get(0) && isDone.get(1)) {
            stand(split, hand, isDone);
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
        main.setPlayerBet(main.getPlayerBet().get(0), 1);
        playerBetLbl.setText("(1) " + main.getPlayerBet().get(0) + " VC$; (2) " + main.getPlayerBet().get(1) + " VC$");
        playerCurrencyLbl.setText(VirtualCasinoController.getCurrAmount() + " VC$");

        splitBtn.setDisable(true);
        hitBtn.setDisable(true);
        standBtn.setDisable(true);
        doubleBtn.setDisable(true);

        hitBtn1.setVisible(true);
        hitBtn1.setDisable(false);
        hitBtn2.setVisible(true);
        hitBtn2.setDisable(false);
        standBtn1.setVisible(true);
        standBtn1.setDisable(false);
        standBtn2.setVisible(true);
        standBtn2.setDisable(false);
        doubleBtn1.setVisible(true);
        doubleBtn2.setVisible(true);


        splitScoreLbl.setVisible(true);

        newCardDisplay(playerHands.get(0).get(1), playerHand2.getLayoutX(), true, 1);
        hand1LastCard.setVisible(false);
        hand1LastCard.setLayoutX(15);

        ArrayList<Card> temp = new ArrayList<>();
        Card test = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        playerHands.add(temp);
        playerHands.get(1).add(0, test);
        playerHands.get(1).set(0, playerHands.get(0).get(1));
        playerHands.get(0).remove(1);

        for (int i = 0; i < 2; i++) {
            hitButton(true, i);
        }
        doubleBtn1.setDisable(false);
        doubleBtn2.setDisable(false);

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
                        playerScoreLbl.setText("(1) Points: " + playerScores.get(i).get(1));
                    } else {
                        splitScoreLbl.setText("(2) Points: " + playerScores.get(i).get(1));
                    }
                } else if (playerScores.get(i).get(0) < 22) {
                    if (i == 0) {
                        playerScoreLbl.setText("(1) Points: " + playerScores.get(i).get(0));
                    } else {
                        splitScoreLbl.setText("(2) Points: " + playerScores.get(i).get(0));
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