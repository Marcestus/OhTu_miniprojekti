package lukuvinkki.UI;

import lukuvinkki.dao.*;
import lukuvinkki.domain.*;

public class Kayttoliittyma {

    private KonsoliIO io;
    private Lukuvinkkipalvelu palvelu;
    private TietokantaRajapinta tietokanta;

    public Kayttoliittyma(KonsoliIO io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.palvelu = new Lukuvinkkipalvelu(io);
        this.tietokanta = tietokanta;
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
                    break;
            }
        }
    }
}