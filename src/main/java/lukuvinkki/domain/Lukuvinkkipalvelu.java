package lukuvinkki.domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


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

    public String normalisoiUrl(String url) {
        // Alustavasti url alkuun lisätään https:// jos sitä ei valmiiksi löydy
        // Jatkossa paremmat validoinnit
        // Esim. jos url ei sisällä www. tai jos url käyttää http:// -> https:// sijasta jne.
        return !url.contains("https://") ? ("https://" + url) : (url);
    }

    public boolean sivuOnOlemassa(String osoite) {
        try {
            URL url = new URL(osoite);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            /*
            InputStream inputStream = connection.getInputStream();
            String result = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));
            io.print("REQUEST BODY: " + result);
            */

            return connection.getResponseCode() == 200;
        } catch (Exception error) {
            return false;
        }
    }

    public boolean lisaaLukuvinkki(String otsikko, String url, List<String> tagit) {
        try {
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(tagit);
            return kelvollisetArvot(lukuvinkki) && tietokanta.lisaaUusiLukuvinkki(lukuvinkki);
        } catch (Exception error) {
            io.print("Error: " + error.getMessage());
            return false;
        }
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

    public void poistaLukuvinkki() {

        //Tietokantahallinta.java -tiedostossa on nyt rakennettu tietokantakäsky tälle, nimellä "poistaLukuvinkki(int poistettavanID)"

        io.print("Komento (poista lukuvinkki) valittu \n");
    }

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
}