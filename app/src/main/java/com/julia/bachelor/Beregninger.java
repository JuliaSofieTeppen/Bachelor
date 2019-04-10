package com.julia.bachelor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.Beholdning;
import com.julia.bachelor.helperClass.BeholdningTemplate;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.Salg;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.Videresalg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

class Beregninger {

    private static final SimpleDateFormat DATO_FORMAT = new java.text.SimpleDateFormat("yyyy-mm-dd", Locale.US);
    private Context context;

    Beregninger(){}

    Beregninger(Context context) {
        this.context = context;
    }

    static boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    ArrayList<SalgTemplate> separateAnnet(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> annet = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof Annet) {
                annet.add(object);
            }
        }
        return annet;
    }

    ArrayList<SalgTemplate> separateHjemme(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> hjemme = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof Hjemme) {
                hjemme.add(object);
            }
        }
        return hjemme;
    }

    static ArrayList<BeholdningTemplate> separateSalg(ArrayList<BeholdningTemplate> list) {
        ArrayList<BeholdningTemplate> salg = new ArrayList<>();
        for (BeholdningTemplate object : list) {
            if (object instanceof Salg) {
                salg.add(object);
            }
        }
        return salg;
    }

    static ArrayList<BeholdningTemplate> separateBeholdning(ArrayList<BeholdningTemplate> list) {
        ArrayList<BeholdningTemplate> beholdning = new ArrayList<>();
        for (BeholdningTemplate object : list) {
            if (object instanceof Beholdning) {
                beholdning.add(object);
            }
        }
        return beholdning;
    }

    ArrayList<SalgTemplate> separateBondensMarked(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> bondensMarked = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof BondensMarked) {
                bondensMarked.add(object);
            }
        }
        return bondensMarked;
    }

    ArrayList<SalgTemplate> separateVideresalg(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> videresalg = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof Videresalg) {
                videresalg.add(object);
            }
        }
        return videresalg;
    }

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


    public void reverseList(ArrayList<Object> list) {//ok
        Collections.reverse(list);
    }


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


    public double sumList(ArrayList<SalgTemplate> list) {
        double total = 0;
        for (SalgTemplate aList : list) {
            int tall = aList.getBelop();
            if (tall > 0) {
                total += tall;
            }
        }
        return total;
    }


    public double mvaHoy(ArrayList<SalgTemplate> list) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        double mva = ((double) sharedPreferences.getInt("ikkeferdig", 25)) / 100.0;
        ArrayList<SalgTemplate> videresalgs = separateVideresalg(list);
        double avgift = 0;
        for (Object videresalg : videresalgs) {
            Videresalg v = (Videresalg) videresalg;
            avgift += v.getMoms() * v.getBelop();
        }
        //avgift += sumAnnet(separateAnnet(list));
        return avgift;
    }


    public double mvaLav(ArrayList<Object> list) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return ((double) sharedPreferences.getInt("ferdigprodukt", 15)) / 100.0;
    }

    static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        Date now = new Date();
        return dateFormat.format(now);
    }
}

