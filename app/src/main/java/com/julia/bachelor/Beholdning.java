package com.julia.bachelor;

public class Beholdning {
    
    private long _ID;
    private int Antall;
    private long H_ID;

    public Beholdning(){}

    public Beholdning(int antall, long h_ID) {
        Antall = antall;
        H_ID = h_ID;
    }

    public long get_ID() { return _ID; }

    public int getAntall() { return Antall; }

    public void setAntall(int antall) { Antall = antall; }

    public long getH_ID() { return H_ID; }

    public void setH_ID(long h_ID) { H_ID = h_ID; }
}
