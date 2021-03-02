package lukuvinkki.domain;

import lukuvinkki.dao.Tietokantahallinta;
import org.junit.*;
import static org.junit.Assert.*;

public class LukuvinkkipalveluTest {
    KonsoliIO testiIO;
    Tietokantahallinta testiTietokanta;
    Lukuvinkkipalvelu testiPalvelu;
    
    @Before
    public void setUp() {
        testiIO = new KonsoliIO();
        testiTietokanta = new Tietokantahallinta("testi.db", testiIO);
        testiTietokanta.otaYhteysTietokantaan();
        testiPalvelu = new Lukuvinkkipalvelu(testiIO, testiTietokanta);
    }
    
    @After
    public void poistaTestiDatabase() {
        testiTietokanta.poistaTestiTietokanta("testi.db");
    }

    @Test
    public void testKelvollisetArvotKelvollisillaVinkeilla() {
        Lukuvinkki kelvollinenVinkki = new Lukuvinkki("testi", "testi.fi", "tag1");
        assertTrue(testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("testi", "testi.com", "");
        assertTrue(testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("test", "test.fi", "tag1, tage1000");
        assertTrue(testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
    }
    
    @Test
    public void testKelvollisetArvotEiKelvollisillaVinkeilla() {
        Lukuvinkki kelvollinenVinkki = new Lukuvinkki("test", "eiUrl", "");
        assertFalse(testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("lol", "testi.com", "tagi");
        assertFalse(testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("test", "test.jokuRandom", "tag1, tage1000");
        assertFalse(testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
    }

    @Test
    public void testOnkoUrlMuotoValidi() {
        assertTrue(testiPalvelu.onkoUrlMuotoValidi("google.com"));
        assertTrue(testiPalvelu.onkoUrlMuotoValidi("www.facebook.com"));
        assertTrue(testiPalvelu.onkoUrlMuotoValidi("ylilauta.fi"));
        assertTrue(testiPalvelu.onkoUrlMuotoValidi("https://www.kauppisenmaansiirtofirma.io"));
        assertTrue(testiPalvelu.onkoUrlMuotoValidi("www.testausta.org"));
        assertTrue(testiPalvelu.onkoUrlMuotoValidi("www.vitsitloppu.io"));
    }
    
    @Test
    public void testOnkoUrlMuotoValidiEiValideilla() {
        assertFalse(testiPalvelu.onkoUrlMuotoValidi("google.cm"));
        assertFalse(testiPalvelu.onkoUrlMuotoValidi("www.facebookcom"));
        assertFalse(testiPalvelu.onkoUrlMuotoValidi("ylilauta.f"));
        assertFalse(testiPalvelu.onkoUrlMuotoValidi("https://www.kauppisenmaansiirtofirma.o"));
        assertFalse(testiPalvelu.onkoUrlMuotoValidi("wwwtestaustaorg"));
        assertFalse(testiPalvelu.onkoUrlMuotoValidi("www.eikeksienaa.hehe"));
    }
    
    @Test
    public void testLisaaLukuvinkki() {
        String testiSyote = "Otsikko\n" + "otsikko.fi\n" + "tagi1\n" + "tag2\n\n";
        testiIO.alustaTestiSyote(testiSyote);
        assertTrue(testiPalvelu.lisaaLukuvinkki());
        
        String testiSyoteIlmanTageja = "Otsikko\n" + "otsikko.fi\n\n";
        testiIO.alustaTestiSyote(testiSyoteIlmanTageja);
        assertTrue(testiPalvelu.lisaaLukuvinkki());
    }
    
    @Test
    public void testLisaaLukuvinkkiEiKelvollisellaSyotteella() {
        String testiSyote = "Otsikko\n" + "otsikko.f\n" + "tagi1\n" + "tag2\n\n";
        testiIO.alustaTestiSyote(testiSyote);
        assertFalse(testiPalvelu.lisaaLukuvinkki());
        
        String testiSyoteIlmanTageja = "lyh\n" + "otsikko.fi\n\n";
        testiIO.alustaTestiSyote(testiSyoteIlmanTageja);
        assertFalse(testiPalvelu.lisaaLukuvinkki());
    }
}
