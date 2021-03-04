package lukuvinkki.dao;

import java.util.List;
import lukuvinkki.domain.IORajapinta;
import lukuvinkki.domain.KonsoliIO;
import lukuvinkki.domain.Lukuvinkki;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TietokantahallintaTest {
    private IORajapinta testiIO;
    private Tietokantahallinta testiTietokanta;
    
    @Before
    public void setUp() {
        testiIO = new KonsoliIO();
        testiTietokanta = new Tietokantahallinta("tietokantaTesti.db", testiIO);
        testiTietokanta.otaYhteysTietokantaan();
    }
    
    @After
    public void poistaTestiTietokanta() {
        testiTietokanta.poistaTestiTietokanta("tietokantaTesti.db");
    }
  
    @Test
    public void testLisaaUusiLukuvinkki() {
        assertTrue(testiTietokanta.lisaaUusiLukuvinkki(new Lukuvinkki("otsikko", "url.fi", "tag")));
        assertTrue(testiTietokanta.lisaaUusiLukuvinkki(new Lukuvinkki("testi", "rikisorsanveli.com", "tag1, tag2")));
    }  

    @Test
    public void testHaeKaikkiLukuvinkit() {
        testiTietokanta.lisaaUusiLukuvinkki(new Lukuvinkki("otsikko1", "url.fi", "tag"));        
        testiTietokanta.lisaaUusiLukuvinkki(new Lukuvinkki("otsikko2", "url.com", "tag2"));
        testiTietokanta.lisaaUusiLukuvinkki(new Lukuvinkki("otsikko3", "url.io", "tag3"));
        
        List<Lukuvinkki> vinkit = testiTietokanta.haeKaikkiLukuvinkit();
        assertEquals(3, vinkit.size());
        
        boolean otsikko1Loytyy = vinkit.stream()
                                            .anyMatch(vinkki -> vinkki.getOtsikko().equals("otsikko1"));
        
        boolean otsikko2Loytyy = vinkit.stream()
                                            .anyMatch(vinkki -> vinkki.getOtsikko().equals("otsikko2"));
        
        boolean otsikko3Loytyy = vinkit.stream()
                                            .anyMatch(vinkki -> vinkki.getOtsikko().equals("otsikko3"));
        
        assertTrue(otsikko1Loytyy);
        assertTrue(otsikko2Loytyy);
        assertTrue(otsikko3Loytyy);
    }
    
    @Test
    public void testPoistaLukuvinkki() {
        testiTietokanta.lisaaUusiLukuvinkki(new Lukuvinkki("poistettava1", "poistettava1.fi", "tag1poistoon"));
        testiTietokanta.poistaLukuvinkki(1);
        assertTrue(testiTietokanta.haeKaikkiLukuvinkit().isEmpty());
    }
}
