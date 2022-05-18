package com.toja.virtualcasino;

import java.util.ArrayList;

public class WheelFort {
    public static ArrayList<WheelField> wheel = new ArrayList<>();

    public static WheelField wheelSpin() {
        /*for (int i = 0; i < 20; i++) {
            WheelField test = new WheelField("" + (char) (i+65), i+1);
            wheel.add(test);
        }*/
        int rdm = (int) Math.round(Math.random() * 19);
        WheelField field = wheel.get(rdm);
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - 25 + field.getValue());
        return field;
    }

    public static void wheelCreate() {
        for (int i = 0; i < 20; i++) {
            WheelField test = new WheelField("", i);
            wheel.add(test);
        }
        WheelField test = new WheelField("A", 0);
        wheel.set(0, test);
        wheel.set(4, test);
        wheel.set(8, test);
        wheel.set(12, test);
        wheel.set(16, test);
        test = new WheelField("B", 10);
        wheel.set(2, test);
        wheel.set(6, test);
        wheel.set(10, test);
        wheel.set(14, test);
        wheel.set(18, test);
        test = new WheelField("C", 25);
        wheel.set(1, test);
        wheel.set(7, test);
        wheel.set(15, test);
        test = new WheelField("D", 30);
        wheel.set(3, test);
        wheel.set(11, test);
        wheel.set(17, test);
        test = new WheelField("E", 50);
        wheel.set(9, test);
        wheel.set(19, test);
        test = new WheelField("F", 75);
        wheel.set(5, test);
        test = new WheelField("G", 100);
        wheel.set(13, test);
    }
}
