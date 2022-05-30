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
        for (int i = 0; i < 10; i++) {
            list.add("" + (char) (i+65));
        }
        for (int i = 0; i < 5; i++) {
            Reel temp = new Reel(list);
            machine.add(temp);
        }

    }

    //Eigentliche Spiel-Operation
    public static ArrayList<String> spin(int b) {
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - b); //Abziehen des Wetteinsatzes vom Spielerkonto

        //FrontIcons der Walzen bekommen
        ArrayList<String> list= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.addAll(machine.get(i).getFrontIcons());
        }
        int add = (int) Math.round(b*winCheck()); //Gewinn berechnen und zum Konto addieren
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + add);

        return list;
    }

    public static double winCheck() {
        //win gibt an, welchen Anteil seines Einsatzes der Spieler als Gewinn zurückbekommt:
        //z.B. 0 - gar nichts, 0.5 - die Hälfte, 500 - das Fünfhundertfache (Hauptgewinn)
        //für jede Gewinnlinie (1 - 10) wird bestimmt, ob (und wie viel) gewonnen wurde
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
        win = win + lineCheck(10, 0, 1, 1, 1, 2);
        return win;
    }

    //Checken der einzelnen Gewinnlinie auf Gewinn
    public static double lineCheck(int line, int a, int b, int c, int d, int e) {
        int x = 0; //x ist die Gewinnstufe (0 - max. 2 gleiche Symbole in einer Reihe, 1 - 3 gleiche, 2 - 4 gleiche, 3 - 5 gleiche)
        String symbol;
        symbol = machine.get(0).getFrontIcons().get(a); //Symbol, dass in einer Reihe ist

        //Überprüfen, wie viele Symbole hintereinander (von links) gleich sind
        if (Objects.equals(machine.get(0).getFrontIcons().get(a), machine.get(1).getFrontIcons().get(b)) && Objects.equals(machine.get(1).getFrontIcons().get(b), machine.get(2).getFrontIcons().get(c))) {
            if (Objects.equals(machine.get(2).getFrontIcons().get(c), machine.get(3).getFrontIcons().get(d))) {
                if (Objects.equals(machine.get(3).getFrontIcons().get(d), machine.get(4).getFrontIcons().get(e))) {
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
            case "A", "F":
                switch (x) {
                    case 1 -> back = 0.5;
                    case 2 -> back = 4;
                    case 3 -> back = 15;
                    default -> {
                    }
                }
                break;
            case "C", "G", "J":
                switch (x) {
                    case 1 -> back = 0.5;
                    case 2 -> back = 3;
                    case 3 -> back = 10;
                    default -> {
                    }
                }
                break;
            case "B":
                switch (x) {
                    case 1 -> back = 3;
                    case 2 -> back = 13;
                    case 3 -> back = 42;
                    default -> {
                    }
                }
                break;
            case "D":
                switch (x) {
                    case 1 -> back = 4;
                    case 2 -> back = 24;
                    case 3 -> back = 75;
                    default -> {
                    }
                }
                break;
            case "E":
                switch (x) {
                    case 1 -> back = 6;
                    case 2 -> back = 40;
                    case 3 -> back = 125;
                    default -> {
                    }
                }
                break;
            case "H":
                switch (x) {
                    case 1 -> back = 10;
                    case 2 -> back = 75;
                    case 3 -> back = 500;
                    default -> {
                    }
                }
                break;
            case "I":
                back = 0;
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
