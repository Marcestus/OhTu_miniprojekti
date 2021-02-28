package lukuvinkki.domain;

import java.net.HttpURLConnection;
import java.net.URL;
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

    private boolean kelvollisetArvot(Lukuvinkki lukuvinkki) {
        return !lukuvinkki.getOtsikko().isEmpty()
                && !lukuvinkki.getUrl().isEmpty()
                && lukuvinkki.getOtsikko().length() > 3
                && lukuvinkki.getUrl().length() > 3;
    }
    
    private boolean onkoSivustoaOlemassa(String urlValidoitavaksi) {
        try {
            URL url = new URL("http://" + urlValidoitavaksi);
            HttpURLConnection yhteys = (HttpURLConnection) url.openConnection();
            yhteys.setRequestMethod("HEAD");
            int responseCode = yhteys.getResponseCode();
            
            if (HttpURLConnection.HTTP_OK == responseCode) {
                return true;
            } 

        } catch (Exception e) {
            return false;
        }
        return false;
    }
    
    private boolean tarkastaUrlMuoto(String url) {
        String urlRegex = "^[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
        return url.matches(urlRegex) ? true : false;
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
            io.print("(Huom. Tagit annetaan pilkulla eroteltuna, esim. seuraavasti: tagi1, tagi2, tagi3, ...)");
            String tagit = io.syote();

            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, tagit);
            
            // tietokanta.lisaaUusiLukuvinkki() heittää tällä hetkellä errorin: java.lang.NullPointerException
            // muuten alla olevassa if lausekkeessa lisättäisiin tietokantaan uusi lukuvinkki
            // antamalla ehdoksi: if (kelvollisetArvot(lukuvinkki) && tietokanta.lisaaUusiLukuvinkki(lukuvinkki))
            if (kelvollisetArvot(lukuvinkki) && tietokanta.lisaaUusiLukuvinkki(lukuvinkki)) {
                io.print("Uusi lukuvinkki:");
                io.print(lukuvinkki.toString());
                io.print("lisätty onnistuneesti tietokantaan!");
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
        List<Lukuvinkki> vinkit = tietokanta.haeKaikkiLukuvinkit();
        
        if (vinkit.isEmpty()) {
            io.print("Tietokannassa ei lukuvinkkejä!");
            return;
        }

        io.print("Tietokannassa olevat lukuvinkit:");
        for (Lukuvinkki lukuvinkki : vinkit) {
            io.print(lukuvinkki.toString());
            System.out.println("");
        }
    }
}