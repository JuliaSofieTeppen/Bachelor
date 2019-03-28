package com.julia.bachelor.helperClass;

public class SortedObjects {
    private String Varer;
    private int Belop;
    private int[] Betaling;

    public SortedObjects() {
        Varer = "1-0,2-0,3-0,4-0,5-0,6-0,7-0";
        Belop = 0;
        // Betaling[0] = Kontant, Betaling[1] = kort.
        Betaling = new int[2];
    }

    private String addVarer(String varer) {
        String[] addPair = varer.split(",");
        String[] OriginalPair = Varer.split(",");
        int[] newValues = new int[addPair.length];
        for (int i = 0; i < addPair.length; i++) { // {1-2, 2-2, 3-2, 4-2, 5-2, 6-2, 7-2}
            String[] add = addPair[i].split("-"); // {1,2} {2,2}
            String[] original = OriginalPair[i].split("-"); // {1,2} {2,2}
            int count = Integer.parseInt(add[1]) + Integer.parseInt(original[1]);
            newValues[i] = count;
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < newValues.length; j++) {
            sb.append(j + 1).append("-").append(newValues[j]);
        }
        return sb.toString();
    }

    public void add(Object obj) {
        if (obj instanceof Hjemme) {
            Hjemme h = (Hjemme) obj;
            Varer = addVarer(h.getVarer());
            Belop += h.getBelop();
            Betaling[h.getBetaling().equals("Kontant") ? 0 : 1] += Belop;
        } else if (obj instanceof Videresalg) {
            Videresalg h = (Videresalg) obj;
            Varer = addVarer(h.getVarer());
            Belop += h.getBelop();
            Betaling[h.getBetaling().equals("Kontant") ? 0 : 1] += Belop;
        } else if (obj instanceof BondensMarked) {
            BondensMarked h = (BondensMarked) obj;
            Varer = addVarer(h.getVarer());
            Belop += h.getBelop();
            Betaling[1] += Belop;
        } else if (obj instanceof Annet) {
            Annet h = (Annet) obj;
            Varer = addVarer(h.getVarer());
            Belop += h.getBelop();
            Betaling[h.getBetaling().equals("Kontant") ? 0 : 1] += Belop;
        }
    }
}
