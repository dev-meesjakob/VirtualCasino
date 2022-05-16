package com.toja.virtualcasino;

import java.util.ArrayList;

public class WheelFort {
    public static ArrayList<WheelField> wheel = new ArrayList<>();

    public static String wheelSpin() {
        for (int i = 0; i < 20; i++) {
            WheelField test = new WheelField("" + (char) (i+65), i+1);
            wheel.add(test);
        }
        int rdm = (int) Math.round(Math.random() * 19);
        WheelField field = wheel.get(rdm);
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - 10 + field.getValue());
        return field.getSymbol();
    }
}
