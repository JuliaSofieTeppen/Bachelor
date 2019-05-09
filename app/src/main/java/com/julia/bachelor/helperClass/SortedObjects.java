package com.julia.bachelor.helperClass;

import java.io.Serializable;

public class SortedObjects implements SalgTemplate, Serializable {
    private static final String KONTANT = "Kontant";
    private String Varer;
    private int[] amount;
    private String AnnetVarer;
    private int Belop;
    /**
     * if true sorts by month
     * if false sorts by year
     **/
    private boolean sortMethod;
    /**
     * Variable to see what type of payment was used.
     * Betaling[0] = Kontant,
     * Betaling[1] = Kort.
     **/
    private int[] Betaling;
    private String dato;

    public SortedObjects(boolean sortMethod) {
        Varer = "1-0,2-0,3-0,4-0,5-0,6-0,7-0,8-0,9-0";
        amount = new int[9];
        AnnetVarer = "1-0,2-0,3-0,4-0";
        Belop = 0;
        Betaling = new int[2];
        this.sortMethod = sortMethod;
    }

    // TODO should be called from DetailsActivity
    public String getAnnetVarer() {
        return AnnetVarer;
    }

    private void addVarer(String varer) {
        String[] addPair = varer.split(",");
        for (int i = 0; i < addPair.length; i++) {
            if (i == 9) break;
            String[] add = addPair[i].split("-");
            amount[i] += Integer.parseInt(add[1]);
        }
    }

    public void add(SalgTemplate obj) {
        if (!(obj instanceof Annet)) {
            addVarer(obj.getVarer());
        }
        Belop += obj.getBelop();
        Betaling[obj.getBetaling().equals(KONTANT) ? 0 : 1] += obj.getBelop();
    }

    @Override
    public String getKunde() {
        return sortMethod ? "Måned" : "År";
    }

    @Override
    public void setKunde(String kunde) {
    }

    @Override
    public String getDato() {
        return dato;
    }

    @Override
    public void setDato(String dato) {
        this.dato = dato;
    }

    @Override
    public String getVarer() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < amount.length; j++) {
            sb.append(j + 1).append("-").append(amount[j]).append(",");
        }
        return sb.toString();
    }

    @Override
    public void setVarer(String varer) {
    }

    @Override
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

    @Override
    public long get_ID() {
        return 0;
    }

    @Override
    public void set_ID(long _ID) {
    }
}
