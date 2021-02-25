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

    public void setTagit(List<String> tagit) {
        this.tagit = tagit;
    }
}