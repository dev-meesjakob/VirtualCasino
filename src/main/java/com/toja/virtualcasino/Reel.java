package com.toja.virtualcasino;

import java.util.ArrayList;

public class Reel {
    public static ArrayList<String> reelIcons = new ArrayList<>();

    public Reel(ArrayList<String> reelIcons) {
        this.reelIcons = reelIcons;
    }

    //Zufällige FrontIcons ausgeben - wird noch überarbeitet
    public ArrayList<String> getFrontIcons() {
        //int rdm = (int) Math.round(Math.random() * 9);
        int rdm;
        ArrayList<String> frontIcons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
           /* if (rdm > 9) {
                rdm = 0;
            }
            frontIcons.add(reelIcons.get(rdm));
            rdm = rdm + 1;*/
            rdm = (int) Math.round(Math.random() * 9);
            frontIcons.add(reelIcons.get(rdm));
        }
        return frontIcons;
    }
}
