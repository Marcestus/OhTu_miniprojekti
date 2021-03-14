package lukuvinkki.dao;

import lukuvinkki.domain.Lukuvinkki;

import java.util.ArrayList;

public interface TietokantaRajapinta {
    boolean lisaaUusiLukuvinkki(Lukuvinkki lukuvinkki);
    boolean otaYhteysTietokantaan();
    ArrayList<Lukuvinkki> haeKaikkiLukuvinkit();
    boolean poistaLukuvinkki(int poistettavanID);
    boolean asetaLuetuksi(int muutettavanID);
}
