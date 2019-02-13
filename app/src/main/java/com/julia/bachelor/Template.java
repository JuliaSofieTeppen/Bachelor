package com.julia.bachelor;

import java.util.ArrayList;

interface Template {
    /**
    * Should sum up the Belop from each of the objects
    * */
    double sumBelop(ArrayList<Hjemme> list);
    double sumAnnet(ArrayList<Annet> list);
    double sumHjemme(ArrayList<Hjemme> list);
    double sumBm(ArrayList<BondensMarked> list);
    double sumVideresalg(ArrayList<Videresalg> list);
    double sumList(ArrayList<Object> list);
    /**
     * there will be difference in the mva
     * */
    double mvaHoy(ArrayList<Object> list);
    double mvaLav(ArrayList<Object> list);

    /**
     * This method should sort from newest to oldest.
     * Sort date may need one or more helper methods to be able to parse the dates.
     * The dates are stored in strings and are following "yyyy/MM/dd" format.
     * */
    void sortDate(ArrayList<Object> list);
    /**
     * Sorts the list on Belop from highest to lowest.
     * */
    void sortBelop(ArrayList<Object>list);
    /**
    * Sorts the list on Kunde from A to Å
    * */
    void sortKunde(ArrayList<Object> list);
    /**
     * This method will reverse the given list
     * */
    void reverseList(ArrayList<Object> list);
}
