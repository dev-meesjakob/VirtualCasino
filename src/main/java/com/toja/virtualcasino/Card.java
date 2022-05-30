package com.toja.virtualcasino;

import java.util.ArrayList;

public class Card {
    enum Rank {
        TWO(2, "(2) Two of "), THREE(3, "(3) Three of "), FOUR(4, "(4) Four of "), FIVE(5, "(5) Five of "), SIX(6, "(6) Six of "), SEVEN(7, "(7) Seven of "), EIGHT(8, "(8) Eight of "), NINE(9, "(9) Nine of "), TEN(10, "(10) Ten of "), JACK(10, "(10) Jack of "), QUEEN(10, "(10) Queen of "), KING(10, "(10) King of "), ACE(1, "(1/11) Ace of ");

        private final int value;
        private final String name;

        Rank(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    enum Suit {
        CLUBS("Clubs"), SPADES("Spades"), HEARTS("Hearts"), DIAMONDS("Diamonds");

        private final String name;

        Suit(String name) {
            this.name = name;
        }
    }

    private final Rank rank;
    private final Suit suit;
    public final String nameString;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
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
        for (Card card : newDeck) {
            System.out.println("Card: " + card.getNameString());
        }
        return newDeck;
    }


}