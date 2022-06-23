package com.toja.virtualcasino;

import java.util.ArrayList;
import java.util.Objects;

public class SlotMachine {
    public static ArrayList<Reel> machine = new ArrayList<>();
    public static ArrayList<Integer> lines = new ArrayList<>();

    //Erstellen der SlotMachine
    public static void machineCreate() {
        //Erstellen einer Liste mit 10 Symbolen (Buchstaben A - J) und Erstellen der Walzen
        ArrayList<String> list = new ArrayList<>();
        list.add("apple");
        list.add("lemon");
        list.add("grapes");
        list.add("cherry");
        list.add("melon");
        list.add("bell");
        list.add("star");
        list.add("diamond");
        list.add("seven");
        list.add("clover");

        for (int i = 0; i < 5; i++) {
            Reel temp = new Reel(list);
            machine.add(temp);
        }

    }

    //Eigentliche Spiel-Operation
    public static int spin(int b) {
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - b); //Abziehen des Wetteinsatzes vom Spielerkonto
        for (int i = 0; i < 5; i++) {
            machine.get(i).createFrontIcons();
        }

        int add = (int) Math.round(b * winCheck()); //Gewinn berechnen und zum Konto addieren
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + add);
        return add;
    }

    public static double winCheck() {
        //win gibt an, welchen Anteil seines Einsatzes der Spieler als Gewinn zurückbekommt:
        //z.B. 0 - gar nichts, 0.5 - die Hälfte, 500 - das Fünfhundertfache (Hauptgewinn)
        //für jede Gewinnlinie (1 - 10) wird bestimmt, ob (und wie viel) gewonnen wurde
        lines.clear();
        double win = 0;
        win = win + lineCheck(1, 1, 1, 1, 1, 1);
        win = win + lineCheck(2, 0, 0, 0, 0, 0);
        win = win + lineCheck(3, 2, 2, 2, 2, 2);
        win = win + lineCheck(4, 0, 1, 2, 1, 0);
        win = win + lineCheck(5, 2, 1, 0, 1, 2);
        win = win + lineCheck(6, 1, 2, 2, 2, 1);
        win = win + lineCheck(7, 1, 0, 0, 0, 1);
        win = win + lineCheck(8, 2, 2, 1, 0, 0);
        win = win + lineCheck(9, 0, 0, 1, 2, 2);
        //win = win + lineCheck(10, 0, 1, 1, 1, 2);
        return win;
    }

    //Checken der einzelnen Gewinnlinie auf Gewinn
    public static double lineCheck(int line, int a, int b, int c, int d, int e) {
        int x = 0; //x ist die Gewinnstufe (0 - max. 2 gleiche Symbole in einer Reihe, 1 - 3 gleiche, 2 - 4 gleiche, 3 - 5 gleiche)
        String symbol;
        if (machine.get(0).getFrontIcons().get(a).equals("clover")) {
            if (machine.get(1).getFrontIcons().get(b).equals("clover")) {
                if (machine.get(2).getFrontIcons().get(c).equals("clover")) {
                    if (machine.get(3).getFrontIcons().get(d).equals("clover")) {
                        symbol = machine.get(4).getFrontIcons().get(e);
                    } else {
                        symbol = machine.get(3).getFrontIcons().get(d);
                    }
                } else {
                    symbol = machine.get(2).getFrontIcons().get(c);
                }
            } else {
                symbol = machine.get(1).getFrontIcons().get(b);
            }
        } else {
            symbol = machine.get(0).getFrontIcons().get(a); //Symbol, auf dass in der Reihe geprüft wird
        }

        //Überprüfen, wie viele Symbole hintereinander (von links) gleich sind
        if ((machine.get(1).getFrontIcons().get(b).equals(symbol) || machine.get(1).getFrontIcons().get(b).equals("clover")) && (machine.get(2).getFrontIcons().get(c).equals(symbol) || machine.get(2).getFrontIcons().get(c).equals("clover"))) {
            if (machine.get(3).getFrontIcons().get(d).equals(symbol) || machine.get(3).getFrontIcons().get(d).equals("clover")) {
                if (machine.get(4).getFrontIcons().get(e).equals(symbol) || machine.get(4).getFrontIcons().get(e).equals("clover")) {
                    x = 3;
                } else {
                    x = 2;
                }
            } else {
                x = 1;
            }
        }
        double back = 0; //Gewinn für diese Linie

        //Unterschiedliche Gewinne für Anzahl Symbole in einer Reihe und Art des Symbols
        switch (symbol) {
            case "lemon", "apple":
                switch (x) {
                    case 1 -> back = 0.5;
                    case 2 -> back = 4;
                    case 3 -> back = 15;
                    default -> back = 0;
                }
                break;
            case "cherry", "grapes", "melon":
                switch (x) {
                    case 1 -> back = 0.5;
                    case 2 -> back = 3;
                    case 3 -> back = 10;
                    default -> back = 0;
                }
                break;
            case "bell":
                switch (x) {
                    case 1 -> back = 3;
                    case 2 -> back = 13;
                    case 3 -> back = 42;
                    default -> back = 0;
                }
                break;
            case "star":
                switch (x) {
                    case 1 -> back = 4;
                    case 2 -> back = 24;
                    case 3 -> back = 75;
                    default -> back = 0;
                }
                break;
            case "diamond":
                switch (x) {
                    case 1 -> back = 6;
                    case 2 -> back = 40;
                    case 3 -> back = 125;
                    default -> back = 0;
                }
                break;
            case "seven":
                switch (x) {
                    case 1 -> back = 10;
                    case 2 -> back = 75;
                    case 3 -> back = 500;
                    default -> back = 0;
                }
                break;
            case "clover":
                back = 75;
                break;
            default:
                break;
        }
        //Wiedergeben, ob diese Linie gewonnen wurde
        if (back != 0) {
            lines.add(line);
        }
        return back;
    }

}
