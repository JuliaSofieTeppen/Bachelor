package com.julia.bachelor.helperClass;

import java.io.Serializable;

public class Beholdning implements BeholdningTemplate, Serializable {

    private long _ID;
    private int Sommer;
    private int SommerH;
    private int SommerK;
    private int Lyng;
    private int LyngH;
    private int LyngK;
    private int IngeferH;
    private int IngeferK;
    private int Flytende;
    private String Dato;

    Beholdning() { }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public int getSommer() {
        return Sommer;
    }

    public void setSommer(int sommer) {
        Sommer = sommer;
    }

    public int getSommerH() {
        return SommerH;
    }

    public void setSommerH(int sommerH) {
        SommerH = sommerH;
    }

    public int getSommerK() {
        return SommerK;
    }

    public void setSommerK(int sommerK) {
        SommerK = sommerK;
    }

    public int getLyng() {
        return Lyng;
    }

    public void setLyng(int lyng) {
        Lyng = lyng;
    }

    public int getLyngH() {
        return LyngH;
    }

    public void setLyngH(int lyngH) {
        LyngH = lyngH;
    }

    public int getLyngK() {
        return LyngK;
    }

    public void setLyngK(int lyngK) {
        LyngK = lyngK;
    }

    public int getIngeferH() {
        return IngeferH;
    }

    public void setIngeferH(int ingeferH) {
        IngeferH = ingeferH;
    }

    public int getIngeferK() {
        return IngeferK;
    }

    public void setIngeferK(int ingeferK) {
        IngeferK = ingeferK;
    }

    public int getFlytende() {
        return Flytende;
    }

    public void setFlytende(int flytende) {
        Flytende = flytende;
    }

    public String getDato() {
        return Dato;
    }

    public void setDato(String dato) {
        Dato = dato;
    }

    public static BeholdningTemplate copy(BeholdningTemplate beholdningTemplate){
        BeholdningTemplate beholdning = new Beholdning();
        beholdning.set_ID(beholdningTemplate.get_ID());
        beholdning.setDato(beholdningTemplate.getDato());
        beholdning.setFlytende(beholdningTemplate.getFlytende());
        beholdning.setIngeferH(beholdningTemplate.getIngeferH());
        beholdning.setIngeferK(beholdningTemplate.getIngeferK());
        beholdning.setLyng(beholdningTemplate.getLyng());
        beholdning.setLyngH(beholdningTemplate.getLyngH());
        beholdning.setLyngK(beholdningTemplate.getLyngK());
        beholdning.setSommer(beholdningTemplate.getSommer());
        beholdning.setSommerH(beholdningTemplate.getSommerH());
        beholdning.setSommerK(beholdningTemplate.getSommerK());
        return beholdning;
    }
}
