package com.julia.bachelor;

public class BondensMarked {
    private long _ID;
    private String Dato;
    private int Belop;
    private String Varer;

    public BondensMarked(){

    }

    public BondensMarked(String dato, int belop, String varer) {
        Dato = dato;
        Belop = belop;
        Varer = varer;
    }

    public long get_ID() {
        return _ID;
    }

    public String getDato() {
        return Dato;
    }

    public void setDato(String dato) {
        Dato = dato;
    }

    public int getBelop() {
        return Belop;
    }

    public void setBelop(int belop) {
        Belop = belop;
    }

    public String getVarer() {
        return Varer;
    }

    public void setVarer(String varer) {
        Varer = varer;
    }
}
