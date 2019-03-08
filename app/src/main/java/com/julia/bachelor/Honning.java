package com.julia.bachelor;

import java.io.Serializable;

class Honning implements Serializable{

    private long _ID;
    private String Type;
    private double Storrelse;
    private int HjemmePris;
    private int BondensMarkedPris;
    private int FakturaPris;

    Honning() {
    }

    long get_ID() {
        return _ID;
    }

    void set_ID(long _ID) {
        this._ID = _ID;
    }

    String getType() {
        return Type;
    }

    void setType(String type) {
        Type = type;
    }

    double getStorrelse() {
        return Storrelse;
    }

    void setStorrelse(double storrelse) {
        Storrelse = storrelse;
    }

    int getHjemmePris() {
        return HjemmePris;
    }

    void setHjemmePris(int hjemmePris) {
        HjemmePris = hjemmePris;
    }

    int getBondensMarkedPris() {
        return BondensMarkedPris;
    }

    void setBondensMarkedPris(int bondensMarkedPris) {
        BondensMarkedPris = bondensMarkedPris;
    }

    int getFakturaPris() {
        return FakturaPris;
    }

    void setFakturaPris(int fakturaPris) {
        FakturaPris = fakturaPris;
    }
}
