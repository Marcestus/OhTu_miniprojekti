/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lukuvinkki.domain;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TiedostopalveluTest {
    private File testiTiedosto;
    private Tiedostopalvelu testiTiedostoPalvelu;
    private String testiTiedostonPolku;
        
    @Before
    public void setUp() {
        testiTiedostonPolku = "testiTiedosto.db";
        testiTiedostoPalvelu = new Tiedostopalvelu();
        luoTiedostoProjektinJuureen(testiTiedostonPolku);
    }
    
    public void luoTiedostoProjektinJuureen(String tiedostonPolku) {
        try {
            // testitiedosto luodaan projektin juureen
            testiTiedosto = new File(tiedostonPolku);
            testiTiedosto.createNewFile();
        } catch (IOException e) {
            System.out.println("Testitiedoston luonti epäonnistui.");
        }
    }
    
    @After
    public void poistaTestiTiedosto() {
        testiTiedosto.delete();
    }
    
    @Test
    public void testOnkoTiedostoOlemassaToimiiTekstitiedostona() {
        assertTrue(testiTiedostoPalvelu.onkoTiedostoOlemassa(testiTiedostonPolku));
    }
 
    @Test
    public void testOnkoTiedostoOlemassaEpaonnistuu() {
        assertFalse(testiTiedostoPalvelu.onkoTiedostoOlemassa("error40404040"));
    }
}
