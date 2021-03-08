package lukuvinkki.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        io.print("Tervetuloa käyttämään lukuvinkkikirjastoa!");
        tulostaKomennot();
        loop:
        while (true) {
            io.print("Anna komento: ");
            String vastaus = io.syote();
            switch (vastaus) {
                case "1":
                    lisaaLukuvinkki();
                    break;
                case "2":

                    //Tietokantahallinta.java -tiedostossa on nyt rakennettu tietokantakäsky tälle, nimellä "poistaLukuvinkki(int poistettavanID)"
                    poistaLukuvinkki();
                    break;
                case "3":
                    haeLukuvunkit();
                    break;
                case "4":
                    io.print("Tästä voi ohjelman tulevassa versiossa selailla tallennettuja lukuvinkkejä");
                    break;
                case "5":
                    io.print("Tästä voi ohjelman tulevassa versiossa muokata tallennettuja lukuvinkkejä");
                    break;
                case "6":
                    haeLukuvinkitTaginPerusteella();
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

    public void haeLukuvinkitTaginPerusteella() {
        io.print("Komento (hae lukuvinkit tägeillä) valittu \n");
        io.print("Anna tägit haulle \n");
        List<String> tagit = muodostaTagit("");
        List<Lukuvinkki> vinkit = palvelu.haeLukuvinkitTaginPerusteella(tagit);
        tulostaLukuvinkit(vinkit);
    }

    public void haeLukuvunkit() {
        io.print("Komento (hae lukuvinkit) valittu \n");

        List<Lukuvinkki> vinkit = palvelu.haeLukuvunkit();

        if (vinkit.isEmpty()) {
            io.print("Tietokannassa ei lukuvinkkejä!");
            return;
        }

        io.print("Tietokannassa olevat lukuvinkit:");
        tulostaLukuvinkit(vinkit);
    }

    public void tulostaLukuvinkit(List<Lukuvinkki> vinkit) {
        for (Lukuvinkki lukuvinkki : vinkit) {
            io.print(lukuvinkki.toString() + "\n");
        }
    }

    public ArrayList<String> muodostaTagit(String url) {
        ArrayList<String> tags = palvelu.lisaaTagitURLPerusteella(url);

        while (true) {
            io.print("Anna uusi tagi: ");
            String tagit = io.syote();

            if (tagit.isEmpty()) {
                break;
            } else if (tags.contains(tagit)) {
                continue;
            }

            tags.add(tagit);
        }

        return tags;
    }

    private String muodostaOtsikko() {
        while (true) {
            String otsikko = io.syote();

            if (otsikko.length() > 3) {
                return otsikko;
            } else {
                io.print("Virhe: Otsikon tulee olla vähintään 4 merkkiä pitkä");
                io.print("Ole hyvä ja anna kelvollinen lukuvinkin otsikko: ");
            }
        }
    }

    private String muodostaUrl() {
        while (true) {
            String url = palvelu.normalisoiUrl(io.syote());
            if (palvelu.sivustoOnOlemassa(url)) {
                return url;
            } else {
                io.print("Virhe: Osoitteella ei löytynyt sisältöä");
                io.print("Ole hyvä ja anna kelvollinen lukuvinkin url: ");
            }
        }
    }

    private String asetaValmisOtsikkoJosTarpeen(String aiemminSyotettyOtsikko, String syote, String url) {
        if (syote.equals("y")) {
            String haettuOtsikko = palvelu.getOtsikkoURLOsoitteesta(url);
            if (!haettuOtsikko.equals("epaonnistunut")) {
                io.print("Otsikko '" + haettuOtsikko + "' haettu onnistuneesti!");
                return haettuOtsikko;
            } else {
                io.print("Valitettavasti sivuston otsikkoa ei voitu hakea.");
                io.print("Käytämme aiemmin syöttämääsi otsikkoa.");
            }
        }

        return aiemminSyotettyOtsikko;
    }

    public void lisaaLukuvinkki() {
        io.print("Komento (lisää lukuvinkki) valittu \n");

        io.print("Anna lukuvinkin otsikko: ");
        String otsikko = muodostaOtsikko();

        io.print("Anna lukuvinkin url: ");
        String url = muodostaUrl();

        io.print("Haluatko korvata aiemmin syötetyn otsikon hakemalla sivuston otsikon?\n"
                + "(Syöta 'y' mikäli kyllä, muuten paina Enter)");
        String valmisOtsikkoKomento = io.syote();
        otsikko = asetaValmisOtsikkoJosTarpeen(otsikko, valmisOtsikkoKomento, url);

        io.print("Anna tagit lukuvinkille: ");
        io.print("(paina Enter, jos et tahdo lisätä omia tagejä.)");
        List<String> tagit = muodostaTagit(url);

        if (palvelu.lisaaLukuvinkki(otsikko, url, tagit)) {
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(tagit);
            io.print("Uusi lukuvinkki:\n" + lukuvinkki.toString() + "\nlisätty onnistuneesti tietokantaan!");
        } else {
            io.print("Virheelliset arvot lukuvinkissä, muutoksia ei tehty.");
        }
    }

    public void poistaLukuvinkki() {
        io.print("Komento (poista lukuvinkki) valittu \n");

        io.print("Syötä u, jos haluat poistaa lukuvinkin url:n perusteella\n"
                + "Syötä o, jos haluat poistaa lukuvinkin otsikon perusteella");

        String poistettava = io.syote();
        if (poistettava.equals("u")) {
            urlPoistoKyselyt();
        } else if (poistettava.equals("o")) {
            otsikkoPoistoKyselyt();
        } else {
            io.print("Virheellinen syöte!");
        }
    }

    public void urlPoistoKyselyt() {
        io.print("Syötä poistettavan lukuvinkin url:");
        String url = io.syote();
        int poistoID = palvelu.haeLukuvinkkiUrlPerusteella(url);
        if (poistoID != -1) {
            io.print("Poistetaanko " + url + " ?\n kyllä: syötä k\n ei: paina enter");
            String poisto = io.syote();
            palvelu.haeLukuvinkkiUrlPerusteella(url);
            if (poisto.equals("k")) {
                if (palvelu.poistaLukuvinkki(poistoID)) {
                    io.print("Poistaminen onnistui");
                } else {
                    io.print("Poistaminen ei onnistunut");
                }
            }
        } else {
            io.print("syöttämääsi url-osoitetta ei ole tietokannassa");
        }
    }

    public void otsikkoPoistoKyselyt() {
        io.print("Syötä poistettavan lukuvinkin otsikko:");
        String otsikko = io.syote();
        int poistoID = palvelu.haeLukuvinkkiOtsikonPerusteella(otsikko); 
        if (poistoID != -1) {
            io.print("Poistetaanko " + otsikko + " ?\n kyllä: syötä k\n ei: paina enter");
            String poisto = io.syote();
            if (poisto.equals("k")) {
                if (palvelu.poistaLukuvinkki(poistoID)) {
                    io.print("Poistaminen onnistui");
                } else {
                    io.print("Poistaminen ei onnistunut");
                }
            }
        } else {
            io.print("syöttämääsi otsikkoa ei ole tietokannassa");
        }
    }

    public void tulostaKomennot() {
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
