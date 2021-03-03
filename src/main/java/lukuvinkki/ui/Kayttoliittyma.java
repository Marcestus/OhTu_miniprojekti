package lukuvinkki.ui;

import lukuvinkki.dao.*;
import lukuvinkki.domain.*;

public class Kayttoliittyma {

    private IORajapinta io;
    private Lukuvinkkipalvelu palvelu;
    private TietokantaRajapinta tietokanta;

    public Kayttoliittyma(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
        this.palvelu = new Lukuvinkkipalvelu(this.io, this.tietokanta);
    }

    public void kayttoliittymaStart() {
        System.out.println("Tervetuloa käyttämään lukuvinkkikirjastoa!");
        naytaKomennot();
        loop:
        while (true) {
            System.out.println("Anna komento: ");
            String vastaus = io.syote();
            switch (vastaus) {
                case "1":
                    palvelu.lisaaLukuvinkki();
                    break;
                case "2":
                    io.print("Tästä voi ohjelman tulevassa versiossa poistaa tallennettuja lukuvinkkejä");
                    break;
                case "3":
                    palvelu.haeLukuvunkit();
                    break;
                case "4":
                    io.print("Tästä voi ohjelman tulevassa versiossa selailla tallennettuja lukuvinkkejä");
                    break;
                case "5":
                    io.print("Tästä voi ohjelman tulevassa versiossa muokata tallennettuja lukuvinkkejä");
                    break;
                case "6":
                    palvelu.haeLukuvinkitTaginPerusteella();
                    break;
                case "-1":
                    System.out.println("Ohjelma sulkeutuu");
                    break loop;
                default:
                    io.print("Virheellinen komento");
                    break;
            }
        }
    }

    public void naytaKomennot() {
        io.print("Komennot:");
        io.print("1 - lisää lukuvinkki");
        io.print("2 - poista lukuvinkki");
        io.print("3 - hae lukuvinkit");
        io.print("4 - selaa lukuvinkkejä");
        io.print("5 - muokkaa lukuvinkkejä");
        io.print("6 - hae lukuvinkit tägeillä");
        io.print("-1 - lopeta ohjelma");
    }
}
