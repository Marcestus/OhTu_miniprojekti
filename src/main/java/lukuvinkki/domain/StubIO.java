package lukuvinkki.domain;

import java.util.ArrayList;

public class StubIO implements IORajapinta {

    private ArrayList<String> syotteet;
    private ArrayList<String> tulostukset;
    private int index;

    public StubIO(ArrayList<String> syotteet) {
        this.syotteet = syotteet;
        this.tulostukset = new ArrayList<>();
        index = 0;
    }

    public ArrayList<String> getPrints() {
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
