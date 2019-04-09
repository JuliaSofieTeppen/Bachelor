package com.julia.bachelor;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.Videresalg;

import java.util.ArrayList;

/**
 * This is the template for Beregninger.java
 */
interface Template {
    /**
     * Should sum up the Belop from each of the objects.
     * All of these objects have a variable Belop that can be accessed with the getBelop() or setBelop() methods.
     * These method will all be very similar to each other.
     */
    double sumAnnet(ArrayList<SalgTemplate> list);

    double sumHjemme(ArrayList<SalgTemplate> list);

    double sumBm(ArrayList<SalgTemplate> list);

    double sumVideresalg(ArrayList<SalgTemplate> list);

    double sumList(ArrayList<SalgTemplate> list);

    /**
     * There will be difference in the mva.
     * Some of the objects will be using sats = 25% and some sats = 15%
     * Therefore there should be two methods for calculating this.
     */
    double mvaHoy(ArrayList<SalgTemplate> list);

    double mvaLav(ArrayList<Object> list);

    /**
     * This method should sort from newest to oldest.
     * Sort date may need one or more helper methods to be able to parse the dates.
     * The dates are stored in strings and are following "yyyy/MM/dd" format.
     */
    void sortDate(ArrayList<Object> list);

    /**
     * This method will reverse the given list
     */
    void reverseList(ArrayList<Object> list);
}
