package com.julia.bachelor;

class Beholdning {

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

    Beholdning(){}

    long get_ID() { return _ID; }

    void set_ID(long _ID){ this._ID = _ID; }

    int getSommer() { return Sommer; }

    void setSommer(int sommer) { Sommer = sommer; }

    int getSommerH() { return SommerH; }

    void setSommerH(int sommerH) { SommerH = sommerH; }

    int getSommerK() { return SommerK; }

    void setSommerK(int sommerK) { SommerK = sommerK; }

    int getLyng() { return Lyng; }

    void setLyng(int lyng) { Lyng = lyng; }

    int getLyngH() { return LyngH; }

    void setLyngH(int lyngH) { LyngH = lyngH; }

    int getLyngK() { return LyngK; }

    void setLyngK(int lyngK) { LyngK = lyngK; }

    int getIngeferH() { return IngeferH; }

    void setIngeferH(int ingeferH) { IngeferH = ingeferH; }

    int getIngeferK() { return IngeferK; }

    void setIngeferK(int ingeferK) { IngeferK = ingeferK; }

    int getFlytende() { return Flytende; }

    void setFlytende(int flytende) { Flytende = flytende; }

    String getDato(){ return Dato; }

    void setDato(String dato){ Dato = dato; }
}
