package lukuvinkki.dao;

import lukuvinkki.domain.Lukuvinkki;

import java.util.ArrayList;
import java.util.List;

public interface TietokantaRajapinta {
    boolean lisaaUusiLukuvinkki(Lukuvinkki lukuvinkki);
    boolean otaYhteysTietokantaan();
    List<Lukuvinkki> haeKaikkiLukuvinkit();
    boolean poistaLukuvinkki(int poistettavanID);
    boolean lisaaLukuvinkitListasta(ArrayList<Lukuvinkki> lukuvinkit);
}
