package com.toja.virtualcasino;

import java.util.ArrayList;

public class Reel {
    private ArrayList<String> reelIcons = new ArrayList<>();
    private ArrayList<String> frontIcons = new ArrayList<>();

    public Reel(ArrayList<String> reelIcons) {
        this.reelIcons = reelIcons;
    }

    //Zufällige FrontIcons ausgeben
    public void createFrontIcons() {
        if (!this.frontIcons.isEmpty()) {
            this.frontIcons.clear();
        }
        int rdm1;
        int rdm2;
        for (int i = 0; i < 3; i++) {
            rdm1 = (int) Math.round(Math.random() * 120);
            if (rdm1 < 15) {rdm2 = 0;}
            else if (rdm1 < 30) {rdm2 = 1;}
            else if (rdm1 < 50) {rdm2 = 2;}
            else if (rdm1 < 70) {rdm2 = 3;}
            else if (rdm1 < 90) {rdm2 = 4;}
            else if (rdm1 < 102) {rdm2 = 5;}
            else if (rdm1 < 110) {rdm2 = 6;}
            else if (rdm1 < 114) {rdm2 = 7;}
            else if (rdm1 < 116) {rdm2 = 8;}
            else {rdm2 = 9;}
            this.frontIcons.add(this.reelIcons.get(rdm2));
        }
    }

    public ArrayList<String> getFrontIcons() {
        return this.frontIcons;
    }
}
