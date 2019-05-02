package com.julia.bachelor.helperClass;

public class SalgFactory {
    private final static String[] urls = {
            "http://www.honningbier.no/PHP/AnnetOut.php",
            "http://www.honningbier.no/PHP/BeholdningOut.php",
            "http://www.honningbier.no/PHP/SalgOut.php",
            "http://www.honningbier.no/PHP/BondensMarkedOut.php",
            "http://www.honningbier.no/PHP/HjemmeOut.php",
            "http://www.honningbier.no/PHP/HonningOut.php",
            "http://www.honningbier.no/PHP/VideresalgOut.php"
    };

    public SalgTemplate getSalgObject(String type) {
        if (type.equalsIgnoreCase(urls[4])) return new Hjemme();
        if (type.equalsIgnoreCase(urls[3])) return new BondensMarked();
        if (type.equalsIgnoreCase(urls[6])) return new Videresalg();
        if (type.equalsIgnoreCase(urls[0])) return new Annet();
        return null;
    }
}
