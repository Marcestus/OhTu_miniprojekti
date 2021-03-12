package lukuvinkki.domain;

import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlPalvelu {
    
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
}
