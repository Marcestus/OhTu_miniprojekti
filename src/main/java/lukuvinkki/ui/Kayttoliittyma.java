package lukuvinkki.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @SuppressWarnings("checkstyle:methodlength")
    public void kayttoliittymaStart() {
        io.print("Tervetuloa käyttämään lukuvinkkikirjastoa!");
        tulostaKomennot();
        loop:
        while (true) {
            io.print("\nAnna komento (Paina Enter, jos haluat nähdä listauksen komennoista):");
            String vastaus = io.syote();
            switch (vastaus) {
                case "":
                    tulostaKomennot();
                    break;
                case "1":
                    lisaaLukuvinkki();
                    break;
                case "2":
                    kaynnistaLukuvinkinAsetusLuetuksi();
                    break;
                case "3":
                    poistaLukuvinkki();
                    break;
                case "4":
                    haeLukuvunkit();
                    break;
                case "5":
                    tuoTiedosto();
                    break;
                case "6":
                    vieTiedosto();
                    break;
                case "-1":
                    io.print("Ohjelma sulkeutuu...");
                    break loop;
                default:
                    io.print("Virheellinen komento.");
                    break;
            }
        }
    }
    
    public void kaynnistaLukuvinkinAsetusLuetuksi() {
        io.print("\nKomento (aseta lukuvinkki luetuksi) valittu");
        io.print("Jos haluat palata takaisin, paina Enter.\n");
        io.print("Syötä lukuvinkin otsikko, jonka haluat merkata luetuksi:");
        String lukuvinkinOtsikko = io.syote();
        if (lukuvinkinOtsikko.isEmpty()) {
            return;
        }
        Lukuvinkki haettuLukuvinkki = palvelu.haeLukematonLukuvinkkiOtsikonPerusteella(lukuvinkinOtsikko);
        asetaLukuvinkkiLuetuksiJosTarpeen(haettuLukuvinkki);
    }
    
    public void asetaLukuvinkkiLuetuksiJosTarpeen(Lukuvinkki lukuvinkkiLuetuksi) {
        if (lukuvinkkiLuetuksi != null) {
            io.print("\nHaluatko merkata lukuvinkin:");
            io.print(lukuvinkkiLuetuksi.toString() + "\n");
            io.print("luetuksi? (Syötä 'k' mikäli kyllä, muuten paina Enter)");
            
            String vahvistus = io.syote();
            vahvistaAsetus(vahvistus, lukuvinkkiLuetuksi);
        } else {
            io.print("Kyseisellä otsikolla ei löytynyt lukematonta lukuvinkkiä tietokannasta.");
        }
    }
    
    public void vahvistaAsetus(String vahvistus, Lukuvinkki lukuvinkkiLuetuksi) {
        if (vahvistus.equals("k")) {
            suoritaAsetus(lukuvinkkiLuetuksi.getID());
        } else {
            io.print("Lukuvinkkiä ei merkattu luetuksi.");
        }
    }
    
    public void suoritaAsetus(int lukuvinkinID) {
        if (palvelu.asetaLukuvinkkiLuetuksi(lukuvinkinID)) {
            io.print("Lukuvinkki asetettu onnistuneesti luetuksi!");
        } else {
            io.print("Lukuvinkin luetuksi asetuksessa tapahtui virhe.");
        }
    }
    
    public void tuoTiedosto() {
        io.print("\nKomento (tuo tiedosto) valittu\n");
        io.print("Anna tiedoston polku:");
        String tiedostonPolku = io.syote();

        if (tiedostopalvelu.onkoTiedostoOlemassa(tiedostonPolku)) {
            io.print("Tiedosto löytyi.");
            yhdistaImportTiedosto(tiedostonPolku);
        } else {
            io.print("Tiedostoa ei löytynyt.");
        }

    }

    public void yhdistaImportTiedosto(String tiedostonPolku) {
        Tietokantahallinta importTietokanta = new Tietokantahallinta(tiedostonPolku, io);
        if (!importTietokanta.otaYhteysTietokantaan()) {
            io.print("Pahoittelut, tietokannassa on häiriö. Kokeile ohjelmaa uudestaan!");
            return;
        }
        Lukuvinkkipalvelu importPalvelu = new Lukuvinkkipalvelu(io, importTietokanta);

        ArrayList<Lukuvinkki> importattavatLukuvinkit = importPalvelu.haeLukuvunkit();
        if (palvelu.lisaaLukuvinkitListasta(importattavatLukuvinkit)) {
            io.print("Tiedoston sisältö lisätty onnistuneesti lukuvinkkikirjastoon.");
        } else {
            io.print("Tiedoston lisäys ei onnistunut.");
        }
    }

    public void vieTiedosto() {
        String exportNimi = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "-lukuvinkkikirjasto.db";
        io.print("Komento (vie tiedosto) valittu \n");
        Tietokantahallinta exportTietokanta = new Tietokantahallinta(exportNimi, io);
        if (!exportTietokanta.otaYhteysTietokantaan()) {
            io.print("Pahoittelut, tietokannassa on häiriö. Kokeile ohjelmaa uudestaan!");
            return;
        }
        Lukuvinkkipalvelu exportPalvelu = new Lukuvinkkipalvelu(io, exportTietokanta);

        ArrayList<Lukuvinkki> exportattavatLukuvinkit = palvelu.haeLukuvunkit();
        if (exportPalvelu.lisaaLukuvinkitListasta(exportattavatLukuvinkit)) {
            io.print("Export-tiedoston luonti onnistui!");
            io.print("Tiedosto löytyy samasta hakemistosta missä ajoit .jar tiedoston. Tiedoston nimi: " + exportNimi);
        } else {
            io.print("Export-tiedoston luonti ei onnistunut.");
        }

    }

    public List<Lukuvinkki> haeLukuvinkitTagienPerusteella() {
        io.print("\nKomento (hae lukuvinkit tägeillä) valittu");
        io.print("Anna tagit haulle, (Painamalla Enter voit lopettaa tagien syöttämisen) \n");
        ArrayList<String> tagit = muodostaTagit("");
        return palvelu.haeLukuvinkitTaginPerusteella(tagit);
    }

    public void haeLukuvunkit() {
        io.print("\nKomento (hae lukuvinkit) valittu");
        io.print("Jos haluat palata takaisin, paina Enter.\n");
        String syote;

        while (true) {
            io.print("Valitse 1: Hae kaikki");
            io.print("Valitse 2: Hae lukemattomat");
            io.print("Valitse 3: Hae luetut");
            io.print("Valitse 4: Hae tagien perusteella");

            syote = io.syote();

            if (syote.isEmpty()) {
                return;
            }

            if (syote.matches("[1234]")) {
                break;
            }

            io.print("Virheellinen valinta. Valitse komento väliltä 1-4.");
        }

        List<Lukuvinkki> vinkit = syote.equals("4")
                ? haeLukuvinkitTagienPerusteella()
                : palvelu.haeLukuvinkitSyotteenPerusteella(syote);
        
        if (vinkit.isEmpty()) {
            io.print("Tietokannassa ei lukuvinkkejä!");
            return;
        }

        io.print("Tietokannassa olevat lukuvinkit:\n");
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
            } else if (otsikko.isEmpty()) {
                return "";
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
                io.print("Virhe: Osoitteella ei löytynyt sisältöä.");
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
        io.print("\nKomento (lisää lukuvinkki) valittu");
        io.print("Jos haluat palata takaisin, paina Enter.\n");

        io.print("Anna lukuvinkin otsikko: ");
        String otsikko = muodostaOtsikko();

        if (otsikko.isEmpty()) {
            return;
        }

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
        io.print("\nKomento (poista lukuvinkki) valittu.");
        io.print("Jos haluat palata takaisin, paina Enter.\n");
        io.print("Syötä 'u', jos haluat poistaa lukuvinkin url:n perusteella");
        io.print("Syötä 'o', jos haluat poistaa lukuvinkin otsikon perusteella");

        String poistoKomento = io.syote();
        if (onkoPoistoSyoteValidi(poistoKomento)) {
            boolean onkoURLPerusteella = poistoKomento.equals("u");
            aloitaPoisto(onkoURLPerusteella);
        } else if (poistoKomento.isEmpty()) {
            return;
        } else {
            io.print("Virheellinen syöte!");
        }
    }

    public boolean onkoPoistoSyoteValidi(String komento) {
        return komento.equals("u") || komento.equals("o");
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
            io.print(" kyllä: syötä 'k'");
            io.print(" ei: paina Enter");
            String poistoVahvistus = io.syote();

            suoritaPoistoJosTarpeen(poistoVahvistus, poistettavaLukuvinkki.getID());
        } else {
            io.print("Syöttämääsi hakusyötettä ei ole tietokannassa.");
        }
    }

    public void suoritaPoistoJosTarpeen(String poistoVahvistus, int poistettavanLukuvinkinID) {
        if (poistoVahvistus.equals("k")) {
            if (palvelu.poistaLukuvinkki(poistettavanLukuvinkinID)) {
                io.print("Lukuvinkki poistettu onnistuneesti!");
            } else {
                io.print("Poistaminen ei onnistunut.");
            }
        } else {
            io.print("Lukuvinkin poistaminen keskeytetty.");
        }
    }

    public void tulostaKomennot() {
        io.print("Komennot:");
        io.print("1 - lisää lukuvinkki");
        io.print("2 - aseta lukuvinkki luetuksi");
        io.print("3 - poista lukuvinkki");
        io.print("4 - hae lukuvinkit");
        io.print("5 - tuo tiedosto");
        io.print("6 - vie tiedosto");
        io.print("-1 - lopeta ohjelma");
    }
}
