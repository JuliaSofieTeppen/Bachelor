package com.julia.bachelor.helperClass;

public class SalgFactory {
    public SalgTemplate getSalgObject(String type) {
        if (type.equalsIgnoreCase("1")) return new Hjemme();
        if (type.equalsIgnoreCase("2")) return new BondensMarked();
        if (type.equalsIgnoreCase("3")) return new Videresalg();
        if (type.equalsIgnoreCase("4")) return new Annet();
        return null;
    }

    public BeholdningTemplate getBeholdningObject(String type) {
        if (type.equalsIgnoreCase("1")) return new Beholdning();
        if (type.equalsIgnoreCase("2")) return new Salg();
        return null;
    }
}
