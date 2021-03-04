package lukuvinkki.domain;

import java.util.ArrayList;
import java.util.Arrays;
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
        Lukuvinkki eiKelvollinenVinkki = new Lukuvinkki("test", "eiUrl", "");
        assertFalse(testiPalvelu.kelvollisetArvot(eiKelvollinenVinkki));
        
        eiKelvollinenVinkki = new Lukuvinkki("lol", "testi.com", "tagi");
        assertFalse(testiPalvelu.kelvollisetArvot(eiKelvollinenVinkki));
        
        eiKelvollinenVinkki = new Lukuvinkki("test", "test.jokuRandom", "tag1, tage1000");
        assertFalse(testiPalvelu.kelvollisetArvot(eiKelvollinenVinkki));
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
        Lukuvinkki vinkki = new Lukuvinkki("Otsikko", "otsikko.fi", "");
        vinkki.setTagit(new ArrayList<>(Arrays.asList("tag1, tag2")));
        
        String testiString = "Uusi lukuvinkki:\n" + vinkki.toString() + "\nlisätty onnistuneesti tietokantaan!";
        assertEquals(testiString, testiPalvelu.lisaaLukuvinkki("Otsikko", "otsikko.fi", new ArrayList<>(Arrays.asList("tag1, tag2"))));
    }
    
    @Test
    public void testLisaaLukuvinkkiEiKelvollisellaSyotteella() {
        assertEquals("", testiPalvelu.lisaaLukuvinkki("Otsikko", "otsikko.vaara", new ArrayList<>(Arrays.asList("tag1, tag2"))));
    }
}
