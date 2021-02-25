package lukuvinkki.dao;

import lukuvinkki.domain.Lukuvinkki;

import java.util.List;

public interface TietokantaRajapinta {
    boolean lisaaUusiLukuvinkki(Lukuvinkki lukuvinkki);
    boolean otaYhteysTietokantaan();
    List<Lukuvinkki> haeKaikkiLukuvinkit();
}
