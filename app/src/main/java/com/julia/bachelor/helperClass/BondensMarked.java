package com.julia.bachelor.helperClass;

import java.io.Serializable;

public class BondensMarked implements Serializable {

    private long _ID;
    private String Dato;
    private String Varer;
    private int Belop;

    public BondensMarked() {
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getDato() {
        return Dato;
    }

    public void setDato(String dato) {
        Dato = dato;
    }

    public String getVarer() {
        return Varer;
    }

    public void setVarer(String varer) {
        Varer = varer;
    }

    public int getBelop() {
        return Belop;
    }

    public void setBelop(int belop) {
        Belop = belop;
    }
}