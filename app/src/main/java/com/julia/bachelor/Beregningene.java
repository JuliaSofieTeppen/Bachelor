package com.julia.bachelor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

class Beregningene implements Template {

    private final double SATSH = 0.25;
    private final double SATSL = 0.15;
    private static final SimpleDateFormat DATO_FORMAT = new java.text.SimpleDateFormat("yyyy-mm-dd");

    public Beregningene() {

    }

    @Override
    public double sumAnnet(ArrayList<Annet> list) {//ok

        Iterator<Annet> itererer = list.iterator();
        int total = 0;

        while (itererer.hasNext()) {

            int tall = itererer.next().getBelop();

            if (tall > 0) {

                total += tall;
            }
        }

        return total;
    }

    @Override
    public double sumHjemme(ArrayList<Hjemme> list) {//ok

        Iterator<Hjemme> itererer = list.iterator();
        int total = 0;

        while (itererer.hasNext()) {

            int tall = itererer.next().getBelop();

            if (tall > 0) {

                total += tall;
            }
        }

        return total;
    }

    @Override
    public double sumBm(ArrayList<BondensMarked> list) {//ok

        Iterator<BondensMarked> itererer = list.iterator();
        int total = 0;

        while (itererer.hasNext()) {

            int tall = itererer.next().getBelop();

            if (tall > 0) {

                total += tall;
            }
        }

        return total;
    }

    @Override
    public double sumVideresalg(ArrayList<Videresalg> list) {//ok

        Iterator<Videresalg> itererer = list.iterator();//.listIterator();
        int total = 0;

        while (itererer.hasNext()) {

            int tall = itererer.next().getBelop();

            if (tall > 0) {

                total += tall;
            }
        }

        return total;
    }

    @Override
    public double sumList(ArrayList<Object> list) {//ok

        Iterator<Object> itererer = list.iterator();
        int total = 0;

        while (itererer.hasNext()) {

            int tall = (Integer) itererer.next();

            if (tall > 0) {

                total += tall;
            }
        }
        return total;
    }

    @Override
    public double mvaHoy(ArrayList<Object> list) {//ok

        Iterator<Object> itererer = list.iterator();
        double total = 0.0;

        while (itererer.hasNext()) {

            double tall = (double) itererer.next();

            if (tall > 0) {

                total += tall;
            }
        }
        double merverdiavgift = total * SATSH;

        return merverdiavgift;
    }

    @Override
    public double mvaLav(ArrayList<Object> list) {//ok

        Iterator<Object> itererer = list.iterator();
        double total = 0.0;

        while (itererer.hasNext()) {

            double tall = (double) itererer.next();

            if (tall > 0) {

                total += tall;
            }
        }
        double merverdiavgift = total * SATSL;

        return merverdiavgift;
    }

    //følgende metode er litt usikker
    @Override
    public void sortBelop(ArrayList<Object> list) {//ok

        double[] a = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            //gitt at hashcode metoden fungerer riktig
            a[i] = list.get(i).hashCode();
        }
        Arrays.sort(a);
    }

    /**
     * 2. versjon som tar i mot både String og Integer deretter sorterer dem.
     */
    public void sortBelop_2(ArrayList list) {//ok

        Collections.sort(list);
    }

    /**
     * 3. versjon som tar i mot både String og Integer deretter sorterer dem.
     */
    public void sortBelop_3(ArrayList<Object> list) {//ok

        ArrayList liste = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            liste.add(list.get(i));
        }
        Collections.sort(liste);
    }

    @Override
    public void sortKunde(ArrayList<Object> list) {//ok

        String[] a = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {

            a[i] = list.get(i).toString();
        }

        Arrays.sort(a);
    }

    /**
     * 2. versjon som tar i mot String objekter deretter sorterer dem.
     */
    public void sortKunde_2(ArrayList<Object> list) {//ok

        String[] a = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {

            a[i] = list.get(i).toString();
        }
        for (int i = 1; i < a.length; i++) {

            String verdi = a[i];
            int j = i - 1;

            for (; j >= 0 && verdi.compareTo(a[j]) < 0; j--) {
                a[j + 1] = a[j];

            }

            a[j + 1] = verdi;
        }
    }

    @Override
    public void reverseList(ArrayList<Object> list) {//ok

        ArrayList liste = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            liste.add(list.get(i));
        }
        Collections.reverse(liste);
        System.out.println("Liste fra main " + liste);
    }

    @Override
    public void sortDate(ArrayList<Object> list) {//ok

        String[] a = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {

            a[i] = list.get(i).toString();
        }
        Arrays.sort(a, new Comparator<String>() {
            @Override
            public int compare(String objekt_1, String objekt_2) {
                int resultat = -1;

                try {
                    resultat = DATO_FORMAT.parse(objekt_2).compareTo(DATO_FORMAT.parse(objekt_1));
                } catch (ParseException e) {
                    e.getErrorOffset();
                }
                return resultat;
            }
        });
    }

}

