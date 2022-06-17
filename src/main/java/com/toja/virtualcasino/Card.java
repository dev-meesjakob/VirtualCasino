package com.toja.virtualcasino;

import java.util.ArrayList;

public class Card {
    enum Rank {
        TWO(2, "(2) Two of ", "2_of_"), THREE(3, "(3) Three of ", "3_of_"), FOUR(4, "(4) Four of ", "4_of_"),
        FIVE(5, "(5) Five of ", "5_of_"), SIX(6, "(6) Six of ", "6_of_"), SEVEN(7, "(7) Seven of ", "7_of_"),
        EIGHT(8, "(8) Eight of ", "8_of_"), NINE(9, "(9) Nine of ", "9_of_"), TEN(10, "(10) Ten of ", "10_of_"),
        JACK(10, "(10) Jack of ", "jack_of_"), QUEEN(10, "(10) Queen of ", "queen_of_"), KING(10, "(10) King of ",
                "king_of_"), ACE(1, "(1/11) Ace of ", "ace_of_");

        private final int value;
        private final String name;
        private final String fileStart;

        Rank(int value, String name, String fileStart) {
            this.value = value;
            this.name = name;
            this.fileStart = fileStart;
        }
    }

    enum Suit {
        CLUBS("Clubs", "clubs.png"), SPADES("Spades", "spades.png"), HEARTS("Hearts", "hearts.png"), DIAMONDS(
                "Diamonds",
                "diamonds.png");

        private final String name;
        private final String fileEnd;

        Suit(String name, String fileEnd) {
            this.name = name;
            this.fileEnd = fileEnd;
        }
    }

    private final Rank rank;
    private final Suit suit;
    private final String nameString;
    private final String filePath;


    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.filePath = rank.fileStart + suit.fileEnd;
        this.nameString = rank.name + suit.name;
    }

    public String getNameString() {
        return this.nameString;
    }

    public int getValue() {
        return this.rank.value;
    }

    public static ArrayList<Card> create52Deck() {
        ArrayList<Card> newDeck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card temp = new Card(rank, suit);
                newDeck.add(temp);
            }
        }
        return newDeck;
    }

    public Card.Rank getRank() {
        return this.rank;
    }
}