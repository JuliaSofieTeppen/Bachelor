package com.julia.bachelor;

import android.support.annotation.NonNull;
import java.util.Iterator;
import java.util.List;

// math class can probably be static.
class Beregninger {

    private static final double SATS = 0.15;

    Beregninger() {
    }

    public static int belop_0(@NonNull int[] tabell) {
        int total = 0;
        for (int i : tabell) {
            total = total + i;
        }
        return total;
    }
    /* The list we want to use here is of type Annet.class, BondensMarked.class, Hjemme.class and Videresalg.class
        not an Integer
     */
    private static double belop(@NonNull List<Integer> liste) {
        Iterator<Integer> itererer = liste.iterator();
        double total = 0;
        while (itererer.hasNext()) {
            int tall = itererer.next();
            if (tall > 0) {
                total += tall;
            }
        }
        return total;
    }

    public String reverseDate(String date) {
        String[] strings = date.split("\\.");
        date = "";
        for (int i = strings.length - 1; i >= 0; i--) {
            date += strings[i];
            date += i >= 1 ? "." : "";
        }
        return date;
    }

    static double merverdi(List<Integer> belopet) {
        double merverdiavgift = 0.0;
        double total_d = belop(belopet);
        if (total_d > 0) {
            merverdiavgift = total_d * SATS;
        }
        return merverdiavgift;
    }

    /* sjekkeType is not used and probably wont be needed
        and seeing that it should check the object type,
        comparing it with a string will always make it false */
    @NonNull
    static Object sjekkeType(@NonNull int[] etTall, String type, String storrelse) {
        int total = 0;
        for (int anEtTall : etTall) {
            if ("Sommer".equals(type)) {
                total += anEtTall;
            } else if ("Lyng".equals(type)) {
                total += anEtTall;
            } else if ("Ingefær".equals(type)) {
                total += anEtTall;
            } else {
                total += anEtTall;
            }
        }
        return total + " " + type + " " + storrelse;
    }
}
