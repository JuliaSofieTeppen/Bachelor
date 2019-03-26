package com.julia.bachelor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

// math class can probably be static.
class Beregninger implements Template {

    private static final SimpleDateFormat DATO_FORMAT = new java.text.SimpleDateFormat("yyyy-mm-dd", Locale.US);
    private static final double SATS = 0.15;
    private final double SATSH = 0.25;
    private final double SATSL = 0.15;
    private Context context;

    Beregninger(Context context) {
        this.context = context;
    }

    static boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    ArrayList<Object> separateAnnet(ArrayList<Object> list){
        ArrayList<Object> annet = new ArrayList<>();
        for(Object object : list){
            if(object instanceof Annet){
                annet.add( object);
            }
        }
        return annet;
    }

    ArrayList<Object> separateHjemme(ArrayList<Object> list){
        ArrayList<Object> hjemme = new ArrayList<>();
        for(Object object : list){
            if(object instanceof Hjemme){
                hjemme.add(object);
            }
        }
        return hjemme;
    }

    ArrayList<Object> separateBondensMarked(ArrayList<Object> list){
        ArrayList<Object> bondensMarked = new ArrayList<>();
        for(Object object : list){
            if(object instanceof BondensMarked){
                bondensMarked.add(object);
            }
        }
        return bondensMarked;
    }

    ArrayList<Object> separateVideresalg(ArrayList<Object> list){
        ArrayList<Object> videresalg = new ArrayList<>();
        for(Object object : list){
            if(object instanceof Videresalg){
                videresalg.add(object);
            }
        }
        return videresalg;
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
    public void reverseList(ArrayList<Object> list) {//ok
        Collections.reverse(list);
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
    public double sumList(ArrayList<Object> list) {
        double total = 0;
        /*
        total += sumAnnet(separateAnnet(list));
        total += sumHjemme(separateHjemme(list));
        total += sumBm(separateBondensMarked(list));
        total += sumVideresalg(separateVideresalg(list));
        */
        return total;
    }

    @Override
    public double mvaHoy(ArrayList<Object> list) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        double mva = ((double)sharedPreferences.getInt("ikkeferdig", 25))/100.0;
        ArrayList<Object> videresalgs =separateVideresalg(list);
        double avgift = 0;
        for(Object videresalg : videresalgs){
            Videresalg v = (Videresalg)videresalg;
            avgift += v.getMoms()*v.getBelop();
        }
        //avgift += sumAnnet(separateAnnet(list));
        return avgift;
    }

    @Override
    public double mvaLav(ArrayList<Object> list) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        double mva = ((double)sharedPreferences.getInt("ferdigprodukt", 15))/100.0;
        return mva;
    }

    //følgende metode er litt usikker
    @Override
    public void sortBelop(ArrayList<Object> list) {//ok

        double[] a = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            // gitt at hashcode metoden fungerer riktig
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
}

