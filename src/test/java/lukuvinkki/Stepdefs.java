package lukuvinkki;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import lukuvinkki.dao.Tietokantahallinta;
import lukuvinkki.domain.StubIO;
import lukuvinkki.ui.Kayttoliittyma;

import static org.junit.Assert.assertTrue;

public class Stepdefs {
       
    private List<String> syotteet;
    private StubIO io;
    private Kayttoliittyma ui;
    private Tietokantahallinta tietokanta;

    @Before
    public void setup() {
        this.syotteet = new ArrayList<>();
    }
    
    @After 
    public void poistaTestiTietokanta() {
        tietokanta.poistaTestiTietokanta("cucemberTesti.db");
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
    
    @Given("komento hae valittu")
    public void komentoHaeValittu() {
        syotteet.add("3");
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
    
    @Then("Ohjelman tulostus sisältää {string} sivuston haetun otsikon {string}")
    public void ohjelmanTulostuksessaHaettuOtsikko(String url, String haettuOtsikko) {
        alustaStubTulostuksetJaKaynnistaOhjelma();
        
        String printtaus = String.join("", io.getPrints());
        assertTrue(printtaus.contains(url));
        assertTrue(printtaus.contains(haettuOtsikko));
    }

    @Then("ohjelman tulostus oikein parametreilla otsikko {string}, URL {string}, tagit {string}, {string}, {string}")
    public void ohjelmanTulostusVastaa(String otsikko, String url, String tag1, String tag2, String tag3) {
        alustaStubTulostuksetJaKaynnistaOhjelma();

        assertTrue(io.getPrints().contains("Uusi lukuvinkki:\n" + "Otsikko: " + otsikko + "\n" +
                "Url: " + url + "\n" +
                "Tagit: " + tag1 + ", " + tag2 + ", " + tag3 + "\nlisätty onnistuneesti tietokantaan!"));
    }

    @Then("ohjelman tulostus sisaltaa {string} tekstin")
    public void ohjelmaTulostusSisaltaaTekstin(String teksti) {
        alustaStubTulostuksetJaKaynnistaOhjelma();

        boolean loytykoHaettavaTekstiOsa = io.getPrints()
                .stream()
                .anyMatch(x -> x.contains(teksti));

        assertTrue(loytykoHaettavaTekstiOsa);
    }
    
    @Then("ohjelman tulostus listaa luodun vinkin otsikolla {string}, url {string}, tagit {string}")
    public void ohjelmanTulostusOikein(String otsikko, String url, String tagit) {
        alustaStubTulostuksetJaKaynnistaOhjelma();
        
        boolean alustusTekstiLoytyy = io.getPrints()
                                        .contains("Tietokannassa olevat lukuvinkit:");
        assertTrue(alustusTekstiLoytyy);
        
        boolean lisattyLukuvinkkiLoytyy = io.getPrints()
                                        .contains("Otsikko: " + otsikko + "\n"
                                                + "Url: " + url + "\n"
                                                + "Tagit: " + tagit + "\n");    
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
    
    public void alustaStubTulostuksetJaKaynnistaOhjelma() {
        io = new StubIO(syotteet);
        tietokanta = new Tietokantahallinta("cucemberTesti.db", io);
        tietokanta.otaYhteysTietokantaan();
        ui = new Kayttoliittyma(io, tietokanta);
        ui.kayttoliittymaStart();
    }
}
