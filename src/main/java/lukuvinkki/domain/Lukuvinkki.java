package lukuvinkki.domain;


import java.util.List;

public class Lukuvinkki {

    private String otsikko, url, tagitString;
    private List<String> tagit;

    public Lukuvinkki(String otsikko, String url, String tagitString) {
        this.otsikko = otsikko;
        this.url = url;
        this.tagitString = tagitString;
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

    // Tietokantahallinta lisää tällä hetkellä tagit tekstimuodossa pilkulla erotettuna
    public String getTagitString() {
        if (tagit.isEmpty()) {
            return "-";
        }

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

    @Override
    public String toString() {
        String teksti = "Otsikko: %s, Url: %s, Tagit: %s";
        return String.format(teksti, this.otsikko, this.url, this.tagitString);
    }
}