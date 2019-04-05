package com.julia.bachelor.helperClass;

import java.io.Serializable;

public class BondensMarked implements SalgTemplate, Serializable {

    private long _ID;
    private String Dato;
    private String Varer;
    private int Belop;

    public BondensMarked() {
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
        return "Bondens Marked";
    }

    @Override
    public void setKunde(String kunde) {

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
        return "Kort";
    }

    @Override
    public void setBetaling(String betaling) {

    }

    @Override
    public void setMoms(double moms) {

    }

    @Override
    public double getMoms() {
        return 0.15;
    }
}
