package com.julia.bachelor;

import android.os.Parcel;
import android.os.Parcelable;

public class Honning{

    private long _ID;
    private String Type;
    private double Storrelse;
    private int HjemmePris;
    private int BondensMarkedPris;

    public Honning() {}

    public Honning(String type, double storrelse, int hjemmePris, int bondensMarkedPris) {
        Type = type;
        Storrelse = storrelse;
        HjemmePris = hjemmePris;
        BondensMarkedPris = bondensMarkedPris;
    }

    public long get_ID() { return _ID; }

    public void set_ID(long _ID) { this._ID = _ID; }

    public String getType() { return Type; }

    public void setType(String type) { Type = type; }

    public double getStorrelse() { return Storrelse; }

    public void setStorrelse(double storrelse) { Storrelse = storrelse; }

    public int getHjemmePris() { return HjemmePris; }

    public void setHjemmePris(int hjemmePris) { HjemmePris = hjemmePris; }

    public int getBondensMarkedPris() { return BondensMarkedPris; }

    public void setBondensMarkedPris(int bondensMarkedPris) { BondensMarkedPris = bondensMarkedPris; }


}
