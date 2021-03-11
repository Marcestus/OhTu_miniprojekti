package lukuvinkki.domain;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import lukuvinkki.dao.TietokantaRajapinta;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Lukuvinkkipalvelu {

    IORajapinta io;
    TietokantaRajapinta tietokanta;
    private HashMap<String, String> urlinMukaisetTagit;

    public Lukuvinkkipalvelu(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
        alustaUrlinMukaisetTagit();
    }

    public String normalisoiOsoite(String url) {
        url = lisaaOsoitteenAlkuJosTarpeen(url);
        return lisataankoURLprotokolla(url) ? ("https://" + url) : (url);
    }
    
    public String lisaaOsoitteenAlkuJosTarpeen(String url) {
        return !url.startsWith("http") && !url.contains("www.") ? ("www." + url) : url;
    }
    
    public boolean lisataankoURLprotokolla(String url) {
        return !url.contains("https://") && !url.contains("http://");
    }

    public String getOtsikkoURLOsoitteesta(String url) {
        try {
            Document sivusto = Jsoup.connect(url).get();
            return sivusto.title();
        } catch (Exception e) {
            return "epaonnistunut";
        }
    }

    public boolean sivustoOnOlemassa(String osoite) {
        try {
            URL url = new URL(osoite);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection.getResponseCode() == 200;
        } catch (Exception error) {
            return false;
        }
    }

    public boolean lisaaLukuvinkki(String otsikko, String url, ArrayList<String> tagit) {
        try {
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(tagit);
            return tietokanta.lisaaUusiLukuvinkki(lukuvinkki);
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
        this.urlinMukaisetTagit.put("medium", "blogi");
        this.urlinMukaisetTagit.put("youtube", "video");
        this.urlinMukaisetTagit.put("dl.acm.org", "julkaisu");
        this.urlinMukaisetTagit.put("ieeexplore.ieee.org", "julkaisu");
    }

    public HashMap<String, String> getUrlinMukaisetTagitTesteihin() {
        return this.urlinMukaisetTagit;
    }

    public boolean poistaLukuvinkki(int id) {
        return tietokanta.poistaLukuvinkki(id);

        //Tietokantahallinta.java -tiedostossa on nyt rakennettu tietokantakäsky tälle, nimellä "poistaLukuvinkki(int poistettavanID)"
    }

    public ArrayList<Lukuvinkki> haeLukuvunkit() {
        return tietokanta.haeKaikkiLukuvinkit();
    }

    public ArrayList<Lukuvinkki> haeLukuvinkitTaginPerusteella(ArrayList<String> kysytytTagit) {
        ArrayList<Lukuvinkki> vinkit = tietokanta.haeKaikkiLukuvinkit();

        ArrayList<Lukuvinkki> returnList = new ArrayList<>();
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

    public Lukuvinkki haeLukuvinkkiSyotteenPerusteella(String hakuSyote, boolean onkoURLPerusteella) {
        for (Lukuvinkki lukuvinkki : tietokanta.haeKaikkiLukuvinkit()) {
            if (loytyykoHakuSyoteVinkista(lukuvinkki, onkoURLPerusteella, hakuSyote)) {
                return lukuvinkki;
            }
        }
        
        return null;
    }
    
    public boolean loytyykoHakuSyoteVinkista(Lukuvinkki lukuvinkki, boolean onkoURLPerusteella, String syote) {
        if (onkoURLPerusteella) {
            if (lukuvinkki.getUrl().contains(syote)) {
                return true;
            }
        } else {
            if (lukuvinkki.getOtsikko().contains(syote)) {
                return true;
            }   
        }
        return false;
    }
}
