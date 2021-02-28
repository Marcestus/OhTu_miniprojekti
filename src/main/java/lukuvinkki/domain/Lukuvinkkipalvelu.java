package lukuvinkki.domain;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lukuvinkki.dao.TietokantaRajapinta;

public class Lukuvinkkipalvelu {
    IORajapinta io;
    TietokantaRajapinta tietokanta;

    public Lukuvinkkipalvelu(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
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
    public void lisaaLukuvinkki() {
        io.print("Komento (lisää lukuvinkki) valittu \n");

        try {
            io.print("Anna lukuvinkin otsikko: ");
            String otsikko = io.syote();

            io.print("Anna lukuvinkin url: ");
            String url = io.syote();
            
            io.print("Anna tagit lukuvinkille: ");
            io.print("(paina Enter, jos et tahdo lisätä tagejä.)");
            
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(muodostaTagit());
            
            if (kelvollisetArvot(lukuvinkki) && tietokanta.lisaaUusiLukuvinkki(lukuvinkki)) {
                io.print("Uusi lukuvinkki:");
                io.print(lukuvinkki.toString());
                io.print("lisätty onnistuneesti tietokantaan!");
            } else {
                throw new Exception("Virheelliset arvot lukuvinkissä, muutoksia ei tehty.");
            }
        } catch (Exception error) {
            io.print("Error: " + error.getMessage());
        }
    }
    
    private ArrayList<String> muodostaTagit() {
        int index = 1;
        ArrayList<String> tags = new ArrayList();
        
        while (true) {
            io.print("Tag " + index + ": ");
            String tagit = io.syote();

            if (tagit.isEmpty())  {
                break;
            }
            
            index++;
            tags.add(tagit);
        }
        
        return tags;
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