package com.julia.bachelor.helperClass;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.julia.bachelor.MainActivity;

import java.io.Serializable;

/**
 * This class is used to store information from the database
 * Stores info about a sale
 **/
public class Annet implements SalgTemplate, Serializable {

    private long _ID;
    private String Kunde;
    private String Dato;
    private String Varer;
    private int Belop;
    private String Betaling;

    public Annet() {
    }

    @Override
    public long get_ID() {
        return _ID;
    }

    @Override
    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    @Override
    public String getKunde() {
        return Kunde;
    }

    @Override
    public void setKunde(String kunde) {
        Kunde = kunde;
    }

    @Override
    public String getDato() {
        return Dato;
    }

    @Override
    public void setDato(String dato) {
        Dato = dato;
    }

    @Override
    public String getVarer() {
        return Varer;
    }

    @Override
    public void setVarer(String varer) {
        Varer = varer;
    }

    @Override
    public int getBelop() {
        return Belop;
    }

    @Override
    public void setBelop(int belop) {
        Belop = belop;
    }

    @Override
    public String getBetaling() {
        return Betaling;
    }

    @Override
    public void setBetaling(String betaling) {
        Betaling = betaling;
    }

    @Override
    public double getMoms() {
        MainActivity main = new MainActivity();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main.shareContext());
        double moms = sharedPreferences.getInt("ikkeferdig", 15);
        return moms / 100;
    }

    @Override
    public void setMoms(double moms) {

    }
}
