package com.julia.bachelor;

import java.util.Iterator;
import java.util.List;

public class Beregninger {

    static final double SATS = 0.15;

    public Beregninger() {

    }

    public static int belop_0(int[] tabell) {
        int total = 0;
        for (int i : tabell) {
            total = total + i;
        }
        return total;
    }

    public static int belop(List<Integer> liste) {

        Iterator<Integer> itererer = liste.iterator();

        int total = 0;
        while (itererer.hasNext()) {
            int tall = itererer.next();
            if (tall > 0) {
                total += tall;
            }
        }

        return total;
    }

    public static double merverdi(List<Integer> belopet) {

        double merverdiavgift = 0.0;
        int total_i = belop(belopet);
        double total_d = total_i;

        if (total_d > 0) {
            merverdiavgift = total_d * SATS;
        }
        return merverdiavgift;
    }

    public static Object sjekkeType(int[] etTall, String type, String storrelse) {

        int total = 0;

        for (int i = 0; i < etTall.length; i++) {

            if ("Sommer".equals(type)) {
                total += etTall[i];
            } else if ("Lyng".equals(type)) {
                total += etTall[i];
            } else if ("Ingefær".equals(type)) {
                total += etTall[i];
            } else {
                total += etTall[i];
            }

        }
        return total + " " + type + " " + storrelse;
    }
}
