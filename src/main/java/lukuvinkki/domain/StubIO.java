package lukuvinkki.domain;

import java.util.ArrayList;
import java.util.List;

public class StubIO implements IORajapinta {

    private List<String> syotteet;
    private ArrayList<String> tulostukset;
    private int index;

    public StubIO(List<String> syotteet) {
        this.syotteet = syotteet;
        this.tulostukset = new ArrayList();
        index = 0;
    }

    public List<String> getPrints() {
        return tulostukset;
    }

    @Override
    public void print(String syote) {
        tulostukset.add(syote);
    }

    @Override
    public String syote() {
        if (index < syotteet.size()) {
            String palautettava = syotteet.get(index);
            index++;
            return palautettava;
        }
        return "-1";
    }
}
