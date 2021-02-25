package lukuvinkki.domain;

import lukuvinkki.dao.TietokantaRajapinta;

public class Lukuvinkkipalvelu {

    IORajapinta io;
    TietokantaRajapinta tietokanta;

    public Lukuvinkkipalvelu(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
    }

    private boolean kelvollisetArvot(Lukuvinkki lukuvinkki) {
        return !lukuvinkki.getOtsikko().isEmpty()
                && !lukuvinkki.getUrl().isEmpty()
                && lukuvinkki.getOtsikko().length() > 3
                && lukuvinkki.getUrl().length() > 3;
    }

    // Komento 1
    public void lisaaLukuvinkki() {
        io.print("Komento (lisää lukuvinkki) valittu \n");

        try {
            io.print("Anna lukuvinkin otsikko: ");
            String otsikko = io.syote();

            io.print("Anna lukuvinkin url: ");
            String url = io.syote();

            io.print("(Huom. Tagit annetaan pilkulla eroteltuna, esim. seuraavasti: tagi1, tagi2, tagi3, ...)");
            io.print("Anna tagit lukuvinkille: ");
            String tagit = io.syote();

            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, tagit);

            // tietokanta.lisaaUusiLukuvinkki() heittää tällä hetkellä errorin: java.lang.NullPointerException
            // muuten alla olevassa if lausekkeessa lisättäisiin tietokantaan uusi lukuvinkki
            // antamalla ehdoksi: if (kelvollisetArvot(lukuvinkki) && tietokanta.lisaaUusiLukuvinkki(lukuvinkki))
            if (kelvollisetArvot(lukuvinkki)) {
                io.print("Uusi lukuvinkki: " + lukuvinkki.toString() + " lisätty tietokantaan!");
            } else {
                throw new Exception("Virheelliset arvot lukuvinkissä, muutoksia ei tehty.");
            }
        } catch (Exception error) {
            io.print("Error: " + error);
            io.print("Error: " + error.getMessage());
        }
    }

    // Komento 2
    public void poistaLukuvinkki() {
        io.print("Komento (poista lukuvinkki) valittu \n");
    }

    // Komento 3
    public void haeLukuvunkit() {
        io.print("Komento (hae lukuvinkit) valittu \n");

        if (tietokanta.haeKaikkiLukuvinkit().isEmpty()) {
            io.print("Tietokannassa ei lukuvinkkejä!");
            return;
        }

        io.print("Tietokannassa olevat lukuvinkit:");
        for (Lukuvinkki lukuvinkki : tietokanta.haeKaikkiLukuvinkit()) {
            io.print(lukuvinkki.toString());
        }
    }
}