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
        assertEquals(true, testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("testi", "testi.com", "");
        assertEquals(true, testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("test", "test.fi", "tag1, tage1000");
        assertEquals(true, testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
    }
    
    @Test
    public void testKelvollisetArvotEiKelvollisillaVinkeilla() {
        Lukuvinkki kelvollinenVinkki = new Lukuvinkki("test", "eiUrl", "");
        assertEquals(false, testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("lol", "testi.com", "tagi");
        assertEquals(false, testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
        
        kelvollinenVinkki = new Lukuvinkki("test", "test.jokuRandom", "tag1, tage1000");
        assertEquals(false, testiPalvelu.kelvollisetArvot(kelvollinenVinkki));
    }

    @Test
    public void testOnkoUrlMuotoValidi() {
        assertEquals(true, testiPalvelu.onkoUrlMuotoValidi("google.com"));
        assertEquals(true, testiPalvelu.onkoUrlMuotoValidi("www.facebook.com"));
        assertEquals(true, testiPalvelu.onkoUrlMuotoValidi("ylilauta.fi"));
        assertEquals(true, testiPalvelu.onkoUrlMuotoValidi("https://www.kauppisenmaansiirtofirma.io"));
        assertEquals(true, testiPalvelu.onkoUrlMuotoValidi("www.testausta.org"));
        assertEquals(true, testiPalvelu.onkoUrlMuotoValidi("www.vitsitloppu.io"));
    }
    
    @Test
    public void testOnkoUrlMuotoValidiEiValideilla() {
        assertEquals(false, testiPalvelu.onkoUrlMuotoValidi("google.cm"));
        assertEquals(false, testiPalvelu.onkoUrlMuotoValidi("www.facebookcom"));
        assertEquals(false, testiPalvelu.onkoUrlMuotoValidi("ylilauta.f"));
        assertEquals(false, testiPalvelu.onkoUrlMuotoValidi("https://www.kauppisenmaansiirtofirma.o"));
        assertEquals(false, testiPalvelu.onkoUrlMuotoValidi("wwwtestaustaorg"));
        assertEquals(false, testiPalvelu.onkoUrlMuotoValidi("www.eikeksienaa.hehe"));
    }
    
    @Test
    public void testLisaaLukuvinkki() {
        String testiSyote = "Otsikko\n" + "otsikko.fi\n" + "tagi1\n" + "tag2\n\n";
        testiIO.alustaTestiSyote(testiSyote);
        assertEquals(true, testiPalvelu.lisaaLukuvinkki());
        
        String testiSyoteIlmanTageja = "Otsikko\n" + "otsikko.fi\n\n";
        testiIO.alustaTestiSyote(testiSyoteIlmanTageja);
        assertEquals(true, testiPalvelu.lisaaLukuvinkki());
    }
    
    @Test
    public void testLisaaLukuvinkkiEiKelvollisellaSyotteella() {
        String testiSyote = "Otsikko\n" + "otsikko.f\n" + "tagi1\n" + "tag2\n\n";
        testiIO.alustaTestiSyote(testiSyote);
        assertEquals(false, testiPalvelu.lisaaLukuvinkki());
        
        String testiSyoteIlmanTageja = "lyh\n" + "otsikko.fi\n\n";
        testiIO.alustaTestiSyote(testiSyoteIlmanTageja);
        assertEquals(false, testiPalvelu.lisaaLukuvinkki());
    }
}
