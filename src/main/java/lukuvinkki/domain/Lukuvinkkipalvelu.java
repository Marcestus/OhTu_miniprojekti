package lukuvinkki.domain;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.JobOriginatingUserName;

import lukuvinkki.dao.TietokantaRajapinta;

public class Lukuvinkkipalvelu {
    IORajapinta io;
    TietokantaRajapinta tietokanta;
    private HashMap<String, String> urlinMukaisetTagit;

    public Lukuvinkkipalvelu(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
        alustaUrlinMukaisetTagit();
    }

    public boolean kelvollisetArvot(Lukuvinkki lukuvinkki) {
        return lukuvinkki.getOtsikko().length() > 3
                && lukuvinkki.getUrl().length() > 3
                && onkoUrlMuotoValidi(lukuvinkki.getUrl());
    }
        
    public boolean onkoUrlMuotoValidi(String url) {       
        String[] paatteet = {".fi", ".com", ".io", ".org", ".net"};
       
        for (String paate : paatteet) {
            if (url.contains(paate)) {
                return true;
            }
        }
       
        return false;
    }

    // Komento 1
    public boolean lisaaLukuvinkki() {
        io.print("Komento (lisää lukuvinkki) valittu \n");

        try {
            io.print("Anna lukuvinkin otsikko: ");
            
            String otsikko = io.syote();

            io.print("Anna lukuvinkin url: ");

            // muutos muotoon String url = normalisointi(io.syote())
            String url = io.syote();
            
            io.print("Anna tagit lukuvinkille: ");
            io.print("(paina Enter, jos et tahdo lisätä omia tagejä.)");
            
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(muodostaTagit(url));
            
            if (kelvollisetArvot(lukuvinkki) && tietokanta.lisaaUusiLukuvinkki(lukuvinkki)) {
                io.print("Uusi lukuvinkki:");
                io.print(lukuvinkki.toString());
                io.print("lisätty onnistuneesti tietokantaan!");
                return true;
            } else {
                throw new Exception("Virheelliset arvot lukuvinkissä, muutoksia ei tehty.");
            }
        } catch (Exception error) {
            io.print("Error: " + error.getMessage());
        }
        return false;
    }
    
    private ArrayList<String> muodostaTagit(String url) {

        ArrayList<String> tags = lisaaTagitURLPerusteella(url);
        
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

    private ArrayList<String> lisaaTagitURLPerusteella(String url) {
        ArrayList<String> tags = new ArrayList<>();

        for (String verrattavaURL : this.urlinMukaisetTagit.keySet()) {
            if (url.contains(verrattavaURL)) {
                tags.add(urlinMukaisetTagit.get(verrattavaURL));
                break;
            }
        }

        return tags;
    }

    private void alustaUrlinMukaisetTagit() {

        this.urlinMukaisetTagit = new HashMap<>();

        this.urlinMukaisetTagit.put("https://www.medium.com/", "blogi");
        this.urlinMukaisetTagit.put("https://www.youtube.com/", "video");
        this.urlinMukaisetTagit.put("https://dl.acm.org/", "julkaisu");
        this.urlinMukaisetTagit.put("https://ieeexplore.ieee.org/", "julkaisu");

    }

    public HashMap<String, String> getUrlinMukaisetTagitTesteihin() {
        return this.urlinMukaisetTagit;
    }
    

    // Komento 2
    public void poistaLukuvinkki() {
        io.print("Komento (poista lukuvinkki) valittu \n");
    }

    // Komento 3
    public void haeLukuvunkit() {
        io.print("Komento (hae lukuvinkit) valittu \n");
        List<Lukuvinkki> vinkit = tietokanta.haeKaikkiLukuvinkit();
        
        if (vinkit.isEmpty()) {
            io.print("Tietokannassa ei lukuvinkkejä!");
            return;
        }

        io.print("Tietokannassa olevat lukuvinkit:");
        for (Lukuvinkki lukuvinkki : vinkit) {
            io.print(lukuvinkki.toString() + "\n");
        }
    }
}