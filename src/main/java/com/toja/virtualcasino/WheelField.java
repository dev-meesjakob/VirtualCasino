package com.toja.virtualcasino;

public class WheelField {
    private String symbol;
    private int value;

    public WheelField(String symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getValue() {
        return this.value;
    }
}
