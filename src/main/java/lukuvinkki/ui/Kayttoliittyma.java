package lukuvinkki.ui;

import lukuvinkki.dao.*;
import lukuvinkki.domain.*;

public class Kayttoliittyma {

    private IORajapinta io;
    private Lukuvinkkipalvelu palvelu;
    private TietokantaRajapinta tietokanta;

    public Kayttoliittyma(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.palvelu = new Lukuvinkkipalvelu(io, tietokanta);
        this.tietokanta = tietokanta;
    }

    public void kayttoliittymaStart() {

        System.out.println("Komennot:");
        System.out.println("1 - lisää lukuvinkki");
        System.out.println("2 - poista lukuvinkki");
        System.out.println("3 - hae lukuvinkit");
        System.out.println("4 - selaa lukuvinkkejä");
        System.out.println("5 - muokkaa lukuvinkkejä");
        System.out.println("-1 - lopeta ohjelma");
        System.out.println("");

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
                    break;
                case "4":
                    System.out.println("Tästä voi ohjelman tulevassa versiossa selailla tallennettuja lukuvinkkejä");
                    break;
                case "5":
                    System.out.println("Tästä voi ohjelman tulevassa versiossa muokata tallennettuja lukuvinkkejä");
                    break;
                case "-1":
                    lopeta();
                default:
                    System.out.println("Virheellinen komento");
                    break;
            }
        }
    }

    public void lopeta() {
        System.exit(0);
    }

}
