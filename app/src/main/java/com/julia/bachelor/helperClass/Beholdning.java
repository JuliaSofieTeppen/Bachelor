package com.julia.bachelor.helperClass;

import java.io.Serializable;

public class Beholdning implements Serializable {

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

    public static Beholdning copy(Beholdning beholdning){
        Beholdning beholdningCopy = new Beholdning();
        beholdningCopy.set_ID(beholdning.get_ID());
        beholdningCopy.setDato(beholdning.getDato());
        beholdningCopy.setFlytende(beholdning.getFlytende());
        beholdningCopy.setIngeferH(beholdning.getIngeferH());
        beholdningCopy.setIngeferK(beholdning.getIngeferK());
        beholdningCopy.setLyng(beholdning.getLyng());
        beholdningCopy.setLyngH(beholdning.getLyngH());
        beholdningCopy.setLyngK(beholdning.getLyngK());
        beholdningCopy.setSommer(beholdning.getSommer());
        beholdningCopy.setSommerH(beholdning.getSommerH());
        beholdningCopy.setSommerK(beholdning.getSommerK());
        return beholdningCopy;
    }

    public void add(Beholdning beholdning){
        this.Sommer = beholdning.getSommer();
        this.SommerH = beholdning.getSommerH();
        this.SommerK = beholdning.getSommerK();
        this.Lyng = beholdning.getLyng();
        this.LyngH = beholdning.getLyngH();
        this.LyngK = beholdning.getLyngK();
        this.IngeferH = beholdning.getIngeferH();
        this.IngeferK = beholdning.getIngeferK();
        this.Flytende = beholdning.getFlytende();
    }
}
