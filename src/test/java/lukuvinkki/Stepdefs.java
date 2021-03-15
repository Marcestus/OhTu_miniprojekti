package lukuvinkki;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import lukuvinkki.dao.Tietokantahallinta;
import lukuvinkki.domain.Lukuvinkki;
import lukuvinkki.domain.StubIO;
import lukuvinkki.ui.Kayttoliittyma;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Stepdefs {

    private ArrayList<String> syotteet;
    private StubIO io;
    private Kayttoliittyma ui;
    private Tietokantahallinta tietokanta, importTietokantaPalvelu;

    @Before
    public void setup() {
        this.syotteet = new ArrayList<>();
    }
    
    @After 
    public void poistaTestiTietokanta() {
        tietokanta.poistaTestiTietokanta("./cucumberTesti.db");
    }
    
    @Given("komento lisaa valittu")
    public void komentoLisaaValittu() {
        syotteet.add("1");
    }
    
    @Given("komento poisto otsikolla valittu")
    public void komentoPoistoValittu() {
        syotteet.add("2");
        syotteet.add("o");
    }
    
    @Given("komento poisto url valittu")
    public void komentoURLValittu() {
        syotteet.add("2");
        syotteet.add("u");
    }
        
    @Given("komento hae valittu ja listaus syotteella {string}")
    public void komentoHaeValittu(String listausSyote) {
        syotteet.add("3");
        syotteet.add(listausSyote);
    }
    
    @Given("komento tuo tiedosto valittu")
    public void komentoTuoTiedostoValittu() {
        syotteet.add("4");
    }
    
    @Given("komento merkkaa luetuksi valittu")
    public void komentoMerkkaaLuetuksiValittu() {
        syotteet.add("6");
    }
        
    @Given("Import-tiedosto alustettu polulla {string} ja {string} lukuvinkilla")
    public void alustaImportTiedosto(String polku, String lisattavienMaara) {
        StubIO tyhjaIO = new StubIO(new ArrayList());
        importTietokantaPalvelu = new Tietokantahallinta(polku, tyhjaIO);
        importTietokantaPalvelu.otaYhteysTietokantaan();
        int maara = Integer.valueOf(lisattavienMaara);
        
        for (int i = 1; i <= maara; i++) {
            importTietokantaPalvelu.lisaaUusiLukuvinkki(new Lukuvinkki("testi" + i, "www.google.com","tag1"));
        }
    }
    
    @When("tiedoston tuontiin annettu {string} polku")
    public void tiedostoTuontiinPolku(String polku) {
        syotteet.add(polku);
    }
    
    @When("lukuvinkin luetuksi merkkaamiseen annettu otsikko {string} ja varmistus {string} annettu")
    public void lukuvinkinMerkkaamiseenKomento(String otsikko, String vahvistus) {
        syotteet.add(otsikko);
        syotteet.add(vahvistus);
    }
    
    @When("lukuvinkin poistoon annettu url {string} ja poisto vahvistus {string}")
    public void lukuvinkkiPoistettuOikeellaURL(String url, String vahvistus) {
        syotteet.add(url);
        syotteet.add(vahvistus);
    }
    
    @When("lukuvinkin poistoon annettu otsikko {string} ja poisto vahvistus {string}")
    public void lukuvinkkiPoistettuOikeellaOtsikolla(String otsikko, String vahvistus) {
        syotteet.add(otsikko);
        syotteet.add(vahvistus);
    }
    
    @When("lukuvinkki otsikolla {string} lisatty")
    public void lukuvinkkiLisattyOnnistuneesti(String otsikko) {
        syotteet.add(otsikko);
        syotteet.add("url.fi");
        syotteet.add("");
        syotteet.add("tag1");
        syotteet.add("tag2");
        syotteet.add("");
    }

    @When("lukuvinkki epavalidilla URL-osoitteella {string} lisatty")
    public void lukuvinkkiLisattyEpavalidillaURL(String url) {
        syotteet.add("otsikkoTesti");
        syotteet.add(url);
        syotteet.add("google.com");
        syotteet.add(""); // ei haeta valmisotsikkoa
        syotteet.add("tag1");
        syotteet.add("tag2");
        syotteet.add("");
    }

    @When("lukuvinkki lisatty kolmella {string} {string} {string} tagilla")
    public void lukuvinkkiLisattyKolmellaTagilla(String tag1, String tag2, String tag3) {
        syotteet.add("otsikkoTesti");
        syotteet.add("google.com");
        syotteet.add(""); // ei haeta valmisotsikkoa
        syotteet.add(tag1);
        syotteet.add(tag2);
        syotteet.add(tag3);
        syotteet.add("");
    }

    @When("lukuvinkki otsikolla {string}, URL {string}, tageilla {string}, {string}, {string} lisatty")
    public void lukuvinkkiParameterillaJaUseallaTagilla(String otsikko, String url, String tag1, String tag2, String tag3) {
        syotteet.add(otsikko);
        syotteet.add(url);
        syotteet.add(""); // ei haeta valmisotsikkoa
        syotteet.add(tag1);
        syotteet.add(tag2);
        syotteet.add(tag3);
        syotteet.add("");
    }
    
    @When("lukuvinkki lisatty url {string} ja komennolla hae valmis otsikko {string}")
    public void lukuvinkinLisaysHakemallaOtsikkoURL(String url, String hakuKomento) {
        syotteet.add("aiemminSyotettyOtsikko");
        syotteet.add(url);
        syotteet.add(hakuKomento); 
        syotteet.add("");
    }
    
    @When("lukuvinkki lisatty otsikolla {string}, url {string}, tageilla {string}, {string}, {string} ja komennolla hae valmis otsikko {string}")
    public void lukuvinkinLisaysHakemallaOtsikkoURL(String omaOtsikko, String url, String tag1, String tag2, String tag3, String komento) {
        syotteet.add(omaOtsikko);
        syotteet.add(url);
        syotteet.add(komento); 
        syotteet.add(tag1);
        syotteet.add(tag2);
        syotteet.add(tag3);
        syotteet.add("");
    }

    @When("lukuvinkki otsikolla {string}, URL {string} ja ilman tageja lisatty")
    public void lukuvinkkiIlmanTagejaLisatty(String otsikko, String url) {
        syotteet.add(otsikko);
        syotteet.add(url);
        syotteet.add(""); 
        syotteet.add("");
    }

    @When("komento aseta lukuvinkki luetuksi valittu ja syötteenä {string}, {string}")
    public void asetaLukuvinkkiLuetuksi(String otsikko, String vastaus) {
        syotteet.add("6");
        syotteet.add(otsikko);
        syotteet.add(vastaus);
    }
    
    @Then("Ohjelman tulostus sisältää {string} sivuston haetun otsikon {string}")
    public void ohjelmanTulostuksessaHaettuOtsikko(String url, String haettuOtsikko) {
        alustaStubTulostuksetJaKaynnistaOhjelma();
        
        String printtaus = String.join("", io.getPrints());
        assertTrue(printtaus.contains(url));
        assertTrue(printtaus.contains(haettuOtsikko));
    }

    @Then("ohjelman tulostus oikein parametreilla otsikko {string}, URL {string}, tagit {string}, {string}, {string}, luettu {string}")
    public void ohjelmanTulostusVastaa(String otsikko, String url, String tag1, String tag2, String tag3, String luettu) {
        alustaStubTulostuksetJaKaynnistaOhjelma();

        assertTrue(io.getPrints().contains("Uusi lukuvinkki:\n" + "Otsikko: " + otsikko + "\n" +
                "Url: " + url + "\n" +
                "Tagit: " + tag1 + ", " + tag2 + ", " + tag3 + "\n" + 
                "Luettu: " + luettu + "\n" +
                "lisätty onnistuneesti tietokantaan!"));
    }

    @Then("ohjelman tulostus sisaltaa {string} tekstin")
    public void ohjelmaTulostusSisaltaaTekstin(String teksti) {
        alustaStubTulostuksetJaKaynnistaOhjelma();
        
        System.out.println(io.getPrints());

        boolean loytykoHaettavaTekstiOsa = io.getPrints()
                .stream()
                .anyMatch(x -> x.contains(teksti));

        assertTrue(loytykoHaettavaTekstiOsa);
    }
    
    @Then("ohjelman tulostus listaa luodun vinkin otsikolla {string}, url {string}, tagit {string}, luettu {string}")
    public void ohjelmanTulostusOikein(String otsikko, String url, String tagit, String luettu) {
        alustaStubTulostuksetJaKaynnistaOhjelma();
        
        boolean alustusTekstiLoytyy = io.getPrints()
                                        .contains("Tietokannassa olevat lukuvinkit:");
        assertTrue(alustusTekstiLoytyy);
        
        boolean lisattyLukuvinkkiLoytyy = io.getPrints()
                                        .contains("Otsikko: " + otsikko + "\n"
                                                + "Url: " + url + "\n"
                                                + "Tagit: " + tagit + "\n"
                                                + "Luettu: " + luettu + "\n"); 
        assertTrue(lisattyLukuvinkkiLoytyy);
    }
    
    @Then("ohjelman tulostus sisaltaa {string} tekstin ja lukuvinkin oikea tiedot {string} ja {string}")
    public void ohjelmaTulostusSisaltaaTekstinJaLukuvinkinTiedot(String teksti, String otsikko, String url) {
        alustaStubTulostuksetJaKaynnistaOhjelma();

        boolean loytykoHaettavaTekstiOsa = io.getPrints()
                .stream()
                .anyMatch(x -> x.contains(teksti));
        
        boolean loytykoOtsikko = io.getPrints()
                .stream()
                .anyMatch(x -> x.contains(otsikko));
        
        boolean loytykoURL = io.getPrints()
                .stream()
                .anyMatch(x -> x.contains(url));

        assertTrue(loytykoHaettavaTekstiOsa);
        assertTrue(loytykoOtsikko);
        assertTrue(loytykoURL);
    }
    
    @Then("ohjelman tulostus sisaltaa tekstin {string} ja tietokannan koko {string} oikea")
    public void importtauksessaOikeaLopputulema(String teksti, String koko) {
        alustaStubTulostuksetJaKaynnistaOhjelma();
        
        boolean loytykoHaettavaTekstiOsa = io.getPrints()
                .stream()
                .anyMatch(x -> x.contains(teksti));

        assertTrue(loytykoHaettavaTekstiOsa);
        assertEquals((int)Integer.valueOf(koko), tietokanta.haeKaikkiLukuvinkit().size());
        importTietokantaPalvelu.poistaTestiTietokanta("./importTestiDatabase.db");
    }
    
    public void alustaStubTulostuksetJaKaynnistaOhjelma() {
        io = new StubIO(syotteet);
        tietokanta = new Tietokantahallinta("./cucumberTesti.db", io);
        tietokanta.otaYhteysTietokantaan();
        ui = new Kayttoliittyma(io, tietokanta);
        ui.kayttoliittymaStart();
    }
}
