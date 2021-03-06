package com.julia.bachelor.helperClass;

public interface SalgTemplate {
    long get_ID();

    void set_ID(long _ID);

    String getKunde();

    void setKunde(String kunde);

    String getDato();

    void setDato(String dato);

    String getVarer();

    void setVarer(String varer);

    int getBelop();

    void setBelop(int belop);

    String getBetaling();

    void setBetaling(String betaling);

    double getMoms();

    void setMoms(double moms);
}
