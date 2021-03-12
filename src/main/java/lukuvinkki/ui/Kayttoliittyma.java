package lukuvinkki.ui;

import java.util.ArrayList;
import java.util.List;

import lukuvinkki.dao.*;
import lukuvinkki.domain.*;

public class Kayttoliittyma {

    private IORajapinta io;
    private Lukuvinkkipalvelu palvelu;
    private TietokantaRajapinta tietokanta;
    private Tiedostopalvelu tiedostopalvelu;
    private UrlPalvelu urlPalvelu;

    public Kayttoliittyma(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
        this.palvelu = new Lukuvinkkipalvelu(this.io, this.tietokanta);
        this.tiedostopalvelu = new Tiedostopalvelu();
        this.urlPalvelu = new UrlPalvelu();
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
                    poistaLukuvinkki();
                    break;
                case "3":
                    haeLukuvunkit();
                    break;
                case "4":
                    tuoTiedosto();
                    break;
                case "5":
                    io.print("Tästä voi ohjelman tulevassa versiossa muokata tallennettuja lukuvinkkejä.");
                    break;
                case "6":
                    haeLukuvinkitTaginPerusteella();
                    break;
                case "-1":
                    System.out.println("Ohjelma sulkeutuu...");
                    break loop;
                default:
                    io.print("Virheellinen komento.");
                    break;
            }
        }
    }
    
    public void tuoTiedosto() {
        io.print("Komento (tuo tiedosto) valittu \n");
        io.print("Anna tiedoston polku.");
        String tiedostonPolku = io.syote();
        
        if (tiedostopalvelu.onkoTiedostoOlemassa(tiedostonPolku)) {
            System.out.println("Tiedosto löytyi.");
        } else {
            System.out.println("Tiedostoa ei löytynyt.");
        }
    }

    public void haeLukuvinkitTaginPerusteella() {
        io.print("Komento (hae lukuvinkit tägeillä) valittu \n");
        io.print("Anna tägit haulle \n");
        ArrayList<String> tagit = muodostaTagit("");
        ArrayList<Lukuvinkki> vinkit = palvelu.haeLukuvinkitTaginPerusteella(tagit);
        tulostaLukuvinkit(vinkit);
    }

    public void haeLukuvunkit() {
        io.print("Komento (hae lukuvinkit) valittu \n");

        io.print("Valitse 1: Hae kaikki");
        io.print("Valitse 2: Hae vain lukemattomat");
        io.print("Valitse 3: Hae vain luetut");
        String syote = io.syote();

        List<Lukuvinkki> vinkit = palvelu.haeLukuvinkitSyotteenPerusteella(syote);

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
            String url = urlPalvelu.normalisoiOsoite(io.syote());
            if (urlPalvelu.sivustoOnOlemassa(url)) {
                return url;
            } else {
                io.print("Virhe: Osoitteella ei löytynyt sisältöä");
                io.print("Ole hyvä ja anna kelvollinen lukuvinkin url: ");
            }
        }
    }

    private String asetaValmisOtsikkoJosTarpeen(String aiemminSyotettyOtsikko, String syote, String url) {
        if (syote.equals("k")) {
            String haettuOtsikko = urlPalvelu.getOtsikkoURLOsoitteesta(url);
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
                + "(Syötä 'k' mikäli kyllä, muuten paina Enter)");
        String valmisOtsikkoKomento = io.syote();
        otsikko = asetaValmisOtsikkoJosTarpeen(otsikko, valmisOtsikkoKomento, url);

        io.print("Anna tagit lukuvinkille: ");
        io.print("(paina Enter, jos et tahdo lisätä omia tagejä.)");
        ArrayList<String> tagit = muodostaTagit(url);

        if (palvelu.lisaaLukuvinkki(otsikko, url, tagit)) {
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(tagit);
            io.print("Uusi lukuvinkki:\n" + lukuvinkki.toString() + "\nlisätty onnistuneesti tietokantaan!");
        } else {
            io.print("Virheelliset arvot lukuvinkissä, muutoksia ei tehty.");
        }
    }

    public void poistaLukuvinkki() {
        io.print("Komento (poista lukuvinkki) valittu.");
        io.print("Syötä u, jos haluat poistaa lukuvinkin url:n perusteella");
        io.print("Syötä o, jos haluat poistaa lukuvinkin otsikon perusteella");

        String poistoKomento = io.syote();
        if (onkoPoistoSyoteValidi(poistoKomento)) {
            boolean onkoURLPerusteella = onkoURLPerusteella(poistoKomento);
            aloitaPoisto(onkoURLPerusteella);
        } else {
            io.print("Virheellinen syöte!");
        }
    }
    
    public boolean onkoPoistoSyoteValidi(String komento) {
        return komento.equals("u") || komento.equals("o");
    }
    
    public boolean onkoURLPerusteella(String komento) {
        return komento.equals("u");
    }
    
    public void aloitaPoisto(boolean onkoURLPerusteella) {
        String perusteella = onkoURLPerusteella ? "url" : "otsikko";
        io.print("Syötä poistettavan lukuvinkin " + perusteella + ":");
        
        String hakuSyote = io.syote();
        Lukuvinkki poistettavaLukuvinkki = palvelu.haeLukuvinkkiSyotteenPerusteella(hakuSyote, onkoURLPerusteella);
        
        vahvistaPoisto(poistettavaLukuvinkki);
    }
    
    public void vahvistaPoisto(Lukuvinkki poistettavaLukuvinkki) {
        if (poistettavaLukuvinkki != null) {
            io.print("\n" + poistettavaLukuvinkki + "\n");
            io.print("Poistetaanko kyseinen lukuvinkki?");
            io.print(" kyllä: syötä k");
            io.print(" ei: paina enter");
            String poistoVahvistus = io.syote();
            
            suoritaPoistoJosTarpeen(poistoVahvistus, poistettavaLukuvinkki.getID());
        } else {
            io.print("Syöttämääsi hakusyötettä ei ole tietokannassa.");
        }
    }
    
    public void suoritaPoistoJosTarpeen(String poistoVahvistus, int poistettavanLukuvinkinID) {
        if (poistoVahvistus.equals("k")) {
            if (palvelu.poistaLukuvinkki(poistettavanLukuvinkinID)) {
                io.print("Lukuvinkki poistettu onnistuneesti!\n");
            } else {
                io.print("Poistaminen ei onnistunut.\n");
            }
        } else {
            io.print("Lukuvinkin poistaminen keskeytetty.\n");
        }
    }

    public void tulostaKomennot() {
        io.print("Komennot:");
        io.print("1 - lisää lukuvinkki");
        io.print("2 - poista lukuvinkki");
        io.print("3 - hae lukuvinkit");
        io.print("4 - tuo tiedosto");
        io.print("5 - muokkaa lukuvinkkejä");
        io.print("6 - hae lukuvinkit tägeillä");
        io.print("-1 - lopeta ohjelma");
    }
}
