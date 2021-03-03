package lukuvinkki.domain;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lukuvinkki {

    private String otsikko, url, tagitString;
    private int id;
    private List<String> tagit;

    public Lukuvinkki(String otsikko, String url, String tagitString) {
        this.otsikko = otsikko;
        this.url = url;
        this.tagitString = tagitString;
        alustaTagitLista(tagitString);
    }

    public Lukuvinkki(String otsikko, String url, String tagitString, int id) {
        this.otsikko = otsikko;
        this.url = url;
        this.tagitString = tagitString;
        this.id = id;
        alustaTagitLista(tagitString);
    }
    
    public void alustaTagitLista(String tag) {
        this.tagit = tag.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(tag));
    }
    
    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTagit() {
        return tagit;
    }

    public int getID() {
        return this.id;
    }

    // Tietokantahallinta lisää tällä hetkellä tagit tekstimuodossa pilkulla erotettuna
    public String getTagitString() {
        return tagit.isEmpty() ? "-" : muodostaTagitString();
    }
    
    public String muodostaTagitString() {
        StringBuilder palautettavaString = new StringBuilder();
        palautettavaString.append(tagit.get(0));

        if (tagit.size() > 1) {
            for (int i = 1; i < tagit.size(); i++) {
                palautettavaString.append(", " + tagit.get(i));
            }
        }
        
        return palautettavaString.toString();
    }

    public void setTagitString(String tagit) {
        this.tagitString = tagit;
    }

    public void setTagit(List<String> tagit) {
        this.tagit = tagit;
    }
    
    public void lisaaTagi(String tag) {
        tagit.add(tag);
    }

    @Override
    public String toString() {
        String teksti = "Otsikko: %s\nUrl: %s\nTagit: %s";
        return String.format(teksti, this.otsikko, this.url, getTagitString());
    }
}