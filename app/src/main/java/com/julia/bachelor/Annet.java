package com.julia.bachelor;

import java.io.Serializable;

class Annet implements SalgTemplate, Serializable {

    private long _ID;
    private String Kunde;
    private String Dato;
    private String Varer;
    private int Belop;
    private String Betaling;

    Annet() {
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getKunde() {
        return Kunde;
    }

    public void setKunde(String kunde) {
        Kunde = kunde;
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

    public String getBetaling() {
        return Betaling;
    }

    public void setBetaling(String betaling) {
        Betaling = betaling;
    }
}
