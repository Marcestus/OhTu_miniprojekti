package lukuvinkki.UI;

import lukuvinkki.domain.*;
import lukuvinkki.domain.*;

public class Kayttoliittyma {

    private KonsoliIO io;
    private Lukuvinkkipalvelu palvelu;

    public Kayttoliittyma(KonsoliIO io) {
        this.io = io;
        this.palvelu = new Lukuvinkkipalvelu(io);
    }

    public void kayttoliittymaStart() {

        System.out.println("Komennot:");
        System.out.println("1 - lisää lukuvinkki");
        System.out.println("2 - poista lukuvinkki");
        System.out.println("3 - hae lukuvinkit");

        while (true) {
            System.out.println("Anna komento: ");
            String vastaus = io.syote();

            switch (vastaus) {
                case "1":
                    palvelu.lisaaLukuvinkki();
                    break;
                case "2":
                    palvelu.poistaLukuvinkki();
                    break;
                case "3":
                    palvelu.haeLukuvunkit();
                default:
                    System.out.println("Virheellinen komento");
            }
        }
    }
}