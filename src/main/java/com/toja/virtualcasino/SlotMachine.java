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

        winCheck();

        return list;
    }

    public static void winCheck() {
        for (int i = 0; i < 3; i++) {
            int x = 0;
            if (machine.get(0).getFrontIcons().get(i) == machine.get(1).getFrontIcons().get(i) || machine.get(1).getFrontIcons().get(i) == machine.get(2).getFrontIcons().get(i)) {
                if (machine.get(2).getFrontIcons().get(i) == machine.get(3).getFrontIcons().get(i)) {
                    if (machine.get(3).getFrontIcons().get(i) == machine.get(4).getFrontIcons().get(i)) {
                        x = 10;
                    } else {
                        x = 3;
                    }
                } else {
                    x = 1;
                }
            }

        }
    }

}
