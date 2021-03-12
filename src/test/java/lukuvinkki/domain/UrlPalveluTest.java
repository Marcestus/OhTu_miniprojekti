package lukuvinkki.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UrlPalveluTest {
    private UrlPalvelu testiUrlPalvelu;

    @Before
    public void setUp() {
        testiUrlPalvelu = new UrlPalvelu();
    }

    @Test
    public void testNormalisoiUrl() {
        assertEquals("https://www.google.com", testiUrlPalvelu.normalisoiOsoite("google.com"));
        assertEquals("https://www.google.com", testiUrlPalvelu.normalisoiOsoite("www.google.com"));
        assertEquals("https://www.google.com", testiUrlPalvelu.normalisoiOsoite("https://www.google.com"));
    }

    @Test
    public void testGetOtsikkoURLOsoitteesta() {
        assertEquals("Google", testiUrlPalvelu.getOtsikkoURLOsoitteesta("https://www.google.com"));
        assertEquals("University of Helsinki", testiUrlPalvelu.getOtsikkoURLOsoitteesta("https://www.helsinki.fi"));
        assertEquals("The Free MMORPG - RuneScape - Online Fantasy RPG", testiUrlPalvelu.getOtsikkoURLOsoitteesta("https://www.runescape.com"));
    }

    @Test
    public void testSivustoOnOlemassa() {
        assertTrue(testiUrlPalvelu.sivustoOnOlemassa("https://www.helsinki.com"));
        assertFalse(testiUrlPalvelu.sivustoOnOlemassa("https://www.enuskoettataasolemassa.com"));
    }
  
    @Test
    public void testLisaaOsoitteenAlkuJosTarpeen() {
        assertEquals("www.google.com", testiUrlPalvelu.lisaaOsoitteenAlkuJosTarpeen("google.com"));
        assertEquals("www.google.com", testiUrlPalvelu.lisaaOsoitteenAlkuJosTarpeen("www.google.com"));
        assertEquals("https://google.com", testiUrlPalvelu.lisaaOsoitteenAlkuJosTarpeen("https://google.com"));
        assertEquals("http://google.com", testiUrlPalvelu.lisaaOsoitteenAlkuJosTarpeen("http://google.com"));
    }

    @Test
    public void testLisataankoURLprotokolla() {
        assertTrue(testiUrlPalvelu.lisataankoURLprotokolla("www.google.com"));
        assertFalse(testiUrlPalvelu.lisataankoURLprotokolla("https://www.google.com"));
    }
    
}
