package com.toja.virtualcasino;

import java.util.ArrayList;

public class SlotMachine {
    public static ArrayList<Reel> machine = new ArrayList<>();

    public static void machineCreate() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + (char) (i+65));
        }
        for (int i = 0; i < 5; i++) {
            Reel temp = new Reel(list);
            machine.add(temp);
        }

    }

    public static ArrayList<String> spin(int b) {
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() - b);

        ArrayList<String> list= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.addAll(machine.get(i).getFrontIcons());
        }
        int add = (int) Math.round(b*winCheck());
        VirtualCasinoController.setCurrAmount(VirtualCasinoController.getCurrAmount() + add);

        return list;
    }

    public static double winCheck() {
        double win = 0;
        win = win + lineCheck(1, 1, 1, 1, 1, 1);

        return win;
    }

    public static double lineCheck(int line, int a, int b, int c, int d, int e) {
        int x = 0;
        String symbol;
        symbol = machine.get(0).getFrontIcons().get(a);
        if (machine.get(0).getFrontIcons().get(a) == machine.get(1).getFrontIcons().get(b) && machine.get(1).getFrontIcons().get(b) == machine.get(2).getFrontIcons().get(c)) {
            if (machine.get(2).getFrontIcons().get(c) == machine.get(3).getFrontIcons().get(d)) {
                if (machine.get(3).getFrontIcons().get(d) == machine.get(4).getFrontIcons().get(e)) {
                    x = 3;
                } else {
                    x = 2;
                }
            } else {
                x = 1;
            }
        }
        double back = 0;
        switch (symbol) {
            case "A", "F":
                switch (x) {
                    case 1:
                        back = 0.5;
                        break;
                    case 2:
                        back = 4;
                        break;
                    case 3:
                        back = 15;
                        break;
                    default:
                        break;
                }
                break;
            case "C", "G", "J":
                switch (x) {
                    case 1:
                        back = 0.5;
                        break;
                    case 2:
                        back = 3;
                        break;
                    case 3:
                        back = 10;
                        break;
                    default:
                        break;
                }
                break;
            case "B":
                switch (x) {
                    case 1:
                        back = 3;
                        break;
                    case 2:
                        back = 13;
                        break;
                    case 3:
                        back = 42;
                        break;
                    default:
                        break;
                }
                break;
            case "D":
                switch (x) {
                    case 1:
                        back = 4;
                        break;
                    case 2:
                        back = 24;
                        break;
                    case 3:
                        back = 75;
                        break;
                    default:
                        break;
                }
                break;
            case "E":
                switch (x) {
                    case 1:
                        back = 6;
                        break;
                    case 2:
                        back = 40;
                        break;
                    case 3:
                        back = 125;
                        break;
                    default:
                        break;
                }
                break;
            case "H":
                switch (x) {
                    case 1:
                        back = 10;
                        break;
                    case 2:
                        back = 75;
                        break;
                    case 3:
                        back = 500;
                        break;
                    default:
                        break;
                }
                break;
            case "I":
                back = 75;
                break;
            default:
                break;
        }
        return back;
    }

}
