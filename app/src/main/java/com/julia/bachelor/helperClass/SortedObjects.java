package com.julia.bachelor.helperClass;

import java.io.Serializable;

public class SortedObjects implements SalgTemplate, Serializable {
    private static final String KONTANT = "Kontant";
    private String Varer;
    private String AnnetVarer;
    private int Belop;
    boolean sortMethod;
    /**
     * Betaling[0] = Kontant, Betaling[1] = Kort.
     **/
    private int[] Betaling;
    private String dato;

    public SortedObjects(boolean sortMethod) {
        Varer = "1-0,2-0,3-0,4-0,5-0,6-0,7-0,8-0,9-0";
        AnnetVarer = "1-0,2-0,3-0,4-0";
        Belop = 0;
        Betaling = new int[2];
        this.sortMethod = sortMethod;
    }
    // should be called from DetailsActivity
    public String getAnnetVarer() {
        return AnnetVarer;
    }

    @Override
    public long get_ID() {
        return 0;
    }

    @Override
    public void set_ID(long _ID) {

    }

    @Override
    public String getKunde() {
        return sortMethod ? "Month" : "Year";
    }

    @Override
    public void setKunde(String kunde) {

    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getVarer() {
        return Varer;
    }

    @Override
    public void setVarer(String varer) {

    }

    public int getBelop() {
        return Belop;
    }

    @Override
    public void setBelop(int belop) {

    }

    @Override
    public String getBetaling() {
        return null;
    }

    @Override
    public void setBetaling(String betaling) {

    }

    public int[] getBetalings() {
        return Betaling;
    }

    @Override
    public double getMoms() {
        return 0.15;
    }

    @Override
    public void setMoms(double moms) {

    }
    // TODO fix this method
    private String addVarer(String varer) {
        String[] addPair = varer.split(",");
        String[] OriginalPair = Varer.split(",");
        int[] newValues = new int[addPair.length];
        for (int i = 0; i < addPair.length; i++) { // {1-2, 2-2, 3-2, 4-2, 5-2, 6-2, 7-2}
            // String[] add = addPair[i].split("-"); // {1,2} {2,2}
            // String[] original = OriginalPair[i].split("-"); // {1,2} {2,2}
            //int count = Integer.parseInt(add[1]) + Integer.parseInt(original[1]);
            //newValues[i] = count;
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < newValues.length; j++) {
            sb.append(j + 1).append("-").append(newValues[j]).append(",");
        }
        return sb.toString();
    }

    public void add(SalgTemplate obj) {
        if (obj instanceof Annet) {
        } else {
            Varer = obj.getVarer();
            Belop += obj.getBelop();
            Betaling[obj.getBetaling().equals(KONTANT) ? 0 : 1] += obj.getBelop();
        }
    }
}
