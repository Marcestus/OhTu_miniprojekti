package lukuvinkki.ui;

import java.util.ArrayList;
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
        System.out.println("Tervetuloa käyttämään lukuvinkkikirjastoa!");
        naytaKomennot();
        loop:
        while (true) {
            System.out.println("Anna komento: ");
            String vastaus = io.syote();
            switch (vastaus) {
                case "1":
                    lisaaLukuvinkki();
                    break;
                case "2":
                    io.print("Tästä voi ohjelman tulevassa versiossa poistaa tallennettuja lukuvinkkejä");
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

            if (tagit.isEmpty())  {
                break;
            } else if (tags.contains(tagit)) {
                continue;
            }
            
            tags.add(tagit);
        }
        
        return tags;
    }
    
    
    public void lisaaLukuvinkki() {
        io.print("Komento (lisää lukuvinkki) valittu \n");
        io.print("Anna lukuvinkin otsikko: ");

        String otsikko = io.syote();

        io.print("Anna lukuvinkin url: ");

        // muutos muotoon String url = normalisointi(io.syote())
        String url = io.syote();

        io.print("Anna tagit lukuvinkille: ");
        io.print("(paina Enter, jos et tahdo lisätä omia tagejä.)");
        
        List<String> tagit = muodostaTagit(url);
        
        String success = palvelu.lisaaLukuvinkki(otsikko, url, tagit);
        
        if (success.isEmpty()) {
            io.print("Virheelliset arvot lukuvinkissä, muutoksia ei tehty.");
        } else {
            io.print(success);
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
