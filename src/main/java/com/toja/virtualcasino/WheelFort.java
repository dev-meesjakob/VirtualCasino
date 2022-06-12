package com.toja.virtualcasino;

import java.util.ArrayList;

public class WheelFort {
    public static ArrayList<WheelField> wheel = new ArrayList<>();

    //Eigentliche Spieloperation
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

    //Erstellen des Glücksrads
    public static void wheelCreate() {
        //20 Felder erstellen
        for (int i = 0; i < 20; i++) {
            WheelField test = new WheelField(i,"", i);
            wheel.add(test);
        }
        //alle Felder mit Symbolen füllen
        //WheelField test = new WheelField("A", 0);
        wheel.set(0, new WheelField(0, "A", 0));
        wheel.set(4, new WheelField(4, "A", 0));
        wheel.set(8, new WheelField(8, "A", 0));
        wheel.set(12, new WheelField(12, "A", 0));
        wheel.set(16, new WheelField(16, "A", 0));
        //test = new WheelField("B", 10);
        wheel.set(2, new WheelField(2, "B", 10));
        wheel.set(6, new WheelField(6, "B", 10));
        wheel.set(10, new WheelField(10, "B", 10));
        wheel.set(14, new WheelField(14, "B", 10));
        wheel.set(18, new WheelField(18, "B", 10));
        //test = new WheelField("C", 25);
        wheel.set(1, new WheelField(1, "C", 25));
        wheel.set(7, new WheelField(7, "C", 25));
        wheel.set(15, new WheelField(15, "C", 25));
        //test = new WheelField("D", 30);
        wheel.set(3, new WheelField(3, "D", 30));
        wheel.set(11, new WheelField(11, "D", 30));
        wheel.set(17, new WheelField(17, "D", 30));
        //test = new WheelField("E", 50);
        wheel.set(9, new WheelField(9, "E", 50));
        wheel.set(19, new WheelField(19, "E", 50));
        //test = new WheelField("F", 75);
        wheel.set(5, new WheelField(5, "F", 75));
        //test = new WheelField("G", 100);
        wheel.set(13, new WheelField(13, "G", 100));
    }
}
