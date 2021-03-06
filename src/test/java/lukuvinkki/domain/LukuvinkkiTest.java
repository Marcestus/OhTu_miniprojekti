package lukuvinkki.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LukuvinkkiTest {
    private Lukuvinkki lukuvinkki;
        
    @Before
    public void setUp() {
        lukuvinkki = new Lukuvinkki("testi", "urlTesti", "tagTest");
    }

    @Test
    public void testMuodostaTagitString() {
        assertEquals("tagTest", lukuvinkki.muodostaTagitString());
        
        lukuvinkki.lisaaTagi("toinenTagi");
        assertEquals("tagTest, toinenTagi", lukuvinkki.muodostaTagitString());
    }

    @Test
    public void testToString() {
        String testiString = "Otsikko: testi\nUrl: urlTesti\nTagit: tagTest\nLuettu: false";
        assertEquals(testiString, lukuvinkki.toString());
        
        lukuvinkki.lisaaTagi("toinenTagi");
        
        String testiString2 = "Otsikko: testi\nUrl: urlTesti\nTagit: tagTest, toinenTagi\nLuettu: false";
        assertEquals(testiString2, lukuvinkki.toString());
    }
    
    @Test
    public void lukuvinkinLuontiIlmanTagiaTest() {
        Lukuvinkki vinkkiIlmanTagia = new Lukuvinkki("testi", "urlTesti", "");
        String testiString = "Otsikko: testi\nUrl: urlTesti\nTagit: -\nLuettu: false";
        assertEquals(testiString, vinkkiIlmanTagia.toString());
    }
    
    @Test
    public void lukuvinkinLuontiIlmanTagiaJaLisaaminenTest() {
        Lukuvinkki vinkkiIlmanTagia = new Lukuvinkki("testi", "urlTesti", "");
        vinkkiIlmanTagia.lisaaTagi("ekaTagiTyhjanJalkeen");
        
        String testiString = "Otsikko: testi\nUrl: urlTesti\nTagit: ekaTagiTyhjanJalkeen\nLuettu: false";
        assertEquals(testiString, vinkkiIlmanTagia.toString());
        
        vinkkiIlmanTagia.lisaaTagi("tokaTagi");
        vinkkiIlmanTagia.lisaaTagi("kolmasTagi");
        vinkkiIlmanTagia.lisaaTagi("neljasTagi");
        
        testiString = "Otsikko: testi\nUrl: urlTesti\nTagit: ekaTagiTyhjanJalkeen, tokaTagi, kolmasTagi, neljasTagi\nLuettu: false";
        assertEquals(testiString, vinkkiIlmanTagia.toString());
    }   
    @Test
    public void otsikkoOikeinTestilla() {
        assertEquals("testi", lukuvinkki.getOtsikko());
    }
}
