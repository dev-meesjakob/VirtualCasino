package com.toja.virtualcasino;

public class WheelField {
    private String symbol;
    private int value;
    private int nr;

    public WheelField(int nr, String symbol, int value) {
        this.symbol = symbol;
        this.value = value;
        this.nr = nr;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getValue() {
        return this.value;
    }

    public int getnumber() {
        return this.nr;
    }
}
