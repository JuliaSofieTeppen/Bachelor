package com.julia.bachelor;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
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
    double sumAnnet(ArrayList<Annet> list);

    double sumHjemme(ArrayList<Hjemme> list);

    double sumBm(ArrayList<BondensMarked> list);

    double sumVideresalg(ArrayList<Videresalg> list);

    double sumList(ArrayList<Object> list);

    /**
     * There will be difference in the mva.
     * Some of the objects will be using sats = 25% and some sats = 15%
     * Therefore there should be two methods for calculating this.
     */
    double mvaHoy(ArrayList<Object> list);

    double mvaLav(ArrayList<Object> list);

    /**
     * This method should sort from newest to oldest.
     * Sort date may need one or more helper methods to be able to parse the dates.
     * The dates are stored in strings and are following "yyyy/MM/dd" format.
     */
    void sortDate(ArrayList<Object> list);

    /**
     * Sorts the list on Belop from highest to lowest.
     */
    void sortBelop(ArrayList<Object> list);

    /**
     * Sorts the list on Kunde from A to Å
     */
    void sortKunde(ArrayList<Object> list);

    /**
     * This method will reverse the given list
     */
    void reverseList(ArrayList<Object> list);
}
