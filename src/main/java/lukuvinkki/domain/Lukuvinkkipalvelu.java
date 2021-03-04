package lukuvinkki.domain;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String lisaaLukuvinkki(String otsikko, String url, List<String> tagit) {
        try {
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(tagit);
            
            if (kelvollisetArvot(lukuvinkki) && tietokanta.lisaaUusiLukuvinkki(lukuvinkki)) {
                String pal = "Uusi lukuvinkki:\n" + lukuvinkki.toString() + "\nlisätty onnistuneesti tietokantaan!";
                return pal;
            } else {
                throw new Exception("Virheelliset arvot lukuvinkissä, muutoksia ei tehty.");
            }
        } catch (Exception error) {
            io.print("Error: " + error.getMessage());
        }
        return "";
    }

    public ArrayList<String> lisaaTagitURLPerusteella(String url) {
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
        
        //Tietokantahallinta.java -tiedostossa on nyt rakennettu tietokantakäsky tälle, nimellä "poistaLukuvinkki(int poistettavanID)"
        
        io.print("Komento (poista lukuvinkki) valittu \n");
    }

    // Komento 3
    public List<Lukuvinkki> haeLukuvunkit() {
        return tietokanta.haeKaikkiLukuvinkit();
    }

    public List<Lukuvinkki> haeLukuvinkitTaginPerusteella(List<String> kysytytTagit) {        
        List<Lukuvinkki> vinkit = tietokanta.haeKaikkiLukuvinkit();
        
        List<Lukuvinkki> returnList = new ArrayList<>();
        for (Lukuvinkki lukuvinkki : vinkit) {
            String lukuvinkinTagit = lukuvinkki.getTagitString();
            for (String tagi : kysytytTagit) {
                if (lukuvinkinTagit.contains(tagi)) {
                    returnList.add(lukuvinkki);
                    break;
                }
            }
        }
        return returnList;
    }
    
    
    // kesken
    public boolean onkoSivustoaOlemassa(String urlValidoitavaksi) {
        try {
            URL url = new URL("http://" + urlValidoitavaksi); // www.google.com
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
}