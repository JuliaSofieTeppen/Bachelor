package com.julia.bachelor;

import java.io.Serializable;

class BondensMarked implements Serializable {

    private long _ID;
    private String Dato;
    private String Varer;
    private int Belop;

    BondensMarked() {
    }

    long get_ID() {
        return _ID;
    }

    void set_ID(long _ID) {
        this._ID = _ID;
    }

    String getDato() {
        return Dato;
    }

    void setDato(String dato) {
        Dato = dato;
    }

    String getVarer() {
        return Varer;
    }

    void setVarer(String varer) {
        Varer = varer;
    }

    int getBelop() {
        return Belop;
    }

    void setBelop(int belop) {
        Belop = belop;
    }
}
