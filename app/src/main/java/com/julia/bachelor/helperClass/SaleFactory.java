package com.julia.bachelor.helperClass;

public class SaleFactory {
    private final static String[] urls = {
            "http://www.honningbier.no/PHP/AnnetOut.php",
            "http://www.honningbier.no/PHP/BondensMarkedOut.php",
            "http://www.honningbier.no/PHP/HjemmeOut.php",
            "http://www.honningbier.no/PHP/VideresalgOut.php"
    };

    public SalgTemplate getSaleObject(String type) {
        if (type.equalsIgnoreCase(urls[0])) return new Annet();
        if (type.equalsIgnoreCase(urls[1])) return new BondensMarked();
        if (type.equalsIgnoreCase(urls[2])) return new Hjemme();
        if (type.equalsIgnoreCase(urls[3])) return new Videresalg();

        return null;
    }
}
