package lukuvinkki.domain;

import java.util.ArrayList;
import java.util.Arrays;
import lukuvinkki.dao.Tietokantahallinta;
import org.junit.*;
import static org.junit.Assert.*;

@SuppressWarnings("serial")
public class LukuvinkkipalveluTest {
    KonsoliIO testiIO;
    Tietokantahallinta testiTietokanta;
    Lukuvinkkipalvelu testiPalvelu;
    
    @Before
    public void setUp() {
        testiIO = new KonsoliIO();
        testiTietokanta = new Tietokantahallinta("./testi.db", testiIO);
        testiTietokanta.otaYhteysTietokantaan();
        testiPalvelu = new Lukuvinkkipalvelu(testiIO, testiTietokanta);
        
        
        testiPalvelu.lisaaLukuvinkki("testiVinkki1", "https://www.google.com/", new ArrayList<String>() {
            {
                add("tagi1vinkki1");
                add("samattagit");
            }
        });
        testiPalvelu.lisaaLukuvinkki("testiVinkki2", "https://www.testivinkki2.com/", new ArrayList<String>() {
            {
                add("samattagit");
                add("tagi2vinkki2");
            }
        });
        testiPalvelu.lisaaLukuvinkki("testiVinkki3", "https://www.testivinkki3.com/", new ArrayList<String>() {
            {
                add("tagi1vinkki3");
                add("tagi2vinkki3");
            }
        });
        testiPalvelu.lisaaLukuvinkki("testiVinkki4", "https://www.testivinkki4.com/", new ArrayList<String>() {
            {
                add("tagi1vinkki4");
                add("tagi2vinkki4");
            }
        });
    }
    
    @After
    public void poistaTestiDatabase() {
        testiTietokanta.poistaTestiTietokanta("./testi.db");
    }
    
    @Test
    public void testHaeLukuvinkitTaginPerusteellaKunTagiOlemassaYhdessaVinkissa() {
        ArrayList<String> kysytytTagit = new ArrayList<String>() {
            {
                add("tagi1vinkki1");
                add("samattagit");
            }
        };
        assertEquals(2, testiPalvelu.haeLukuvinkitTaginPerusteella(kysytytTagit).size());
        
    }
    
    @Test
    public void testHaeLukuvinkitTaginPerusteellaKunTagiOlemassaUseammassaVinkissa() {
        ArrayList<String> kysytytTagit = new ArrayList<String>() {
            {
                add("tagi1vinkki1");
            }
        };
        assertEquals(1, testiPalvelu.haeLukuvinkitTaginPerusteella(kysytytTagit).size());
    }
    
    @Test
    public void testHaeLukuvinkitTaginPerusteellaPalauttaaTyhjanListanKunVinkkiaVastaavallaTagillaEiLoydy() {
        ArrayList<String> kysytytTagit = new ArrayList<String>() {
            {
                add("tammostaeiole");
            }
        };
        assertTrue(testiPalvelu.haeLukuvinkitTaginPerusteella(kysytytTagit).isEmpty());
    }

    @Test
    public void testLisaaLukuvinkki() {
        Lukuvinkki vinkki = new Lukuvinkki("Otsikko", "google.com", "");
        vinkki.setTagit(new ArrayList<>(Arrays.asList("tag1, tag2")));
        assertTrue(testiPalvelu.lisaaLukuvinkki("Otsikko", "google.com", new ArrayList<>(Arrays.asList("tag1, tag2"))));
    }

    @Test
    public void testLisaaAutomaattisetTagitKunNiitaOn() {
        assertEquals(1, testiPalvelu.lisaaTagitURLPerusteella("https://www.medium.com/").size());
        assertEquals(1, testiPalvelu.lisaaTagitURLPerusteella("medium.com").size());
        assertEquals(1, testiPalvelu.lisaaTagitURLPerusteella("www.medium.com").size());
        assertEquals(1, testiPalvelu.lisaaTagitURLPerusteella("youtube.com").size());
        assertEquals("blogi", testiPalvelu.lisaaTagitURLPerusteella("https://www.medium.com/").get(0));
    }
    
    @Test
    public void testEiLisaaAutomaattisiaTagejaKunNiitaEiOle() {
        assertTrue(testiPalvelu.lisaaTagitURLPerusteella("https://www.helsinki.fi/").isEmpty());
        assertEquals(0, testiPalvelu.lisaaTagitURLPerusteella("facebook.com").size());
    }

    @Test
    public void testHaeLukuvinkkiSyotteenPerusteellaOnnistuu() {
        Lukuvinkki testiVinkki = testiPalvelu.haeLukuvinkkiSyotteenPerusteella("testiVinkki1", false);
        
        assertEquals("testiVinkki1", testiVinkki.getOtsikko());
        assertEquals("https://www.google.com/", testiVinkki.getUrl());
        assertEquals("tagi1vinkki1, samattagit", testiVinkki.getTagitString());
        
        testiVinkki = testiPalvelu.haeLukuvinkkiSyotteenPerusteella("www.testivinkki3.com", true);
        
        assertEquals("testiVinkki3", testiVinkki.getOtsikko());
        assertEquals("https://www.testivinkki3.com/", testiVinkki.getUrl());
        assertEquals("tagi1vinkki3, tagi2vinkki3", testiVinkki.getTagitString());
    }
    
    @Test
    public void testHaeLukuvinkkiSyotteenPerusteellaEpaonnistuu() {
        Lukuvinkki testiVinkki = testiPalvelu.haeLukuvinkkiSyotteenPerusteella("eiolemassaOtsikkoa", false);
        assertEquals(null, testiVinkki);
        
        testiVinkki = testiPalvelu.haeLukuvinkkiSyotteenPerusteella("www.eiolemassa.com", true);
        assertEquals(null, testiVinkki);
    }

    @Test
    public void testLoytyykoHakuSyoteVinkistaOnnistuu() {
        Lukuvinkki testiVinkki = new Lukuvinkki("testaja", "www.paljon.com", "");
        
        assertTrue(testiPalvelu.loytyykoHakuSyoteVinkista(testiVinkki, false, "test"));
        assertTrue(testiPalvelu.loytyykoHakuSyoteVinkista(testiVinkki, true, "paljon"));
    }
    
    @Test
    public void testLoytyykoHakuSyoteVinkistaEpaonnistuu() {
        Lukuvinkki testiVinkki = new Lukuvinkki("testaja", "www.paljon.com", "");
        
        assertFalse(testiPalvelu.loytyykoHakuSyoteVinkista(testiVinkki, false, "eiooOtsikossa"));
        assertFalse(testiPalvelu.loytyykoHakuSyoteVinkista(testiVinkki, true, "eiooURLssa"));
    }
    
}
