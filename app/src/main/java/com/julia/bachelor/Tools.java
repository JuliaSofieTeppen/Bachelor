package com.julia.bachelor;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.Videresalg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class Tools {

    //gets current date
    static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.US);
        Date now = new Date();
        return dateFormat.format(now);
    }

    //checks if date has right format
    static boolean checkDate(String date) {
        String regex = "^\\d{4}\\.(0?[1-9]|1[012])\\.(0?[1-9]|[12][0-9]|3[01])$";
        return date.matches(regex);
    }

    //only get Annet values from the honeysale list.
    static ArrayList<SalgTemplate> separateAnnet(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> annet = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof Annet) {
                annet.add(object);
            }
        }
        return annet;
    }

    //only get hjemmesalg from the honeysale list
    static ArrayList<SalgTemplate> separateHjemme(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> hjemme = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof Hjemme) {
                hjemme.add(object);
            }
        }
        return hjemme;
    }

    static ArrayList<SalgTemplate> separateBondensMarked(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> bondensMarked = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof BondensMarked) {
                bondensMarked.add(object);
            }
        }
        return bondensMarked;
    }

    static ArrayList<SalgTemplate> separateVideresalg(ArrayList<SalgTemplate> list) {
        ArrayList<SalgTemplate> videresalg = new ArrayList<>();
        for (SalgTemplate object : list) {
            if (object instanceof Videresalg) {
                videresalg.add(object);
            }
        }
        return videresalg;
    }

    static double sumList(ArrayList<SalgTemplate> list) {
        double total = 0;
        for (SalgTemplate aList : list) {
            int tall = aList.getBelop();
            if (tall > 0) {
                total += tall;
            }
        }
        return total;
    }
}

