package lukuvinkki.domain;


import java.util.List;

public class Lukuvinkki {

    private String otsikko, url;
    private List<String> tagit;

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

    public void setTagit(List<String> tagit) {
        this.tagit = tagit;
    }
}