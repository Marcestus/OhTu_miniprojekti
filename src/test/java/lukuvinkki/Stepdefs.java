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

    @Then("ohjelman tulostus oikein parametreilla otsikko {string}, URL {string}, tagit {string}, {string}, {string}")
    public void ohjelmanTulostusVastaa(String otsikko, String url, String tag1, String tag2, String tag3) {
        io = new StubIO(syotteet);
        tietokanta = new Tietokantahallinta("cucemberTesti.db", io);
        tietokanta.otaYhteysTietokantaan();
        ui = new Kayttoliittyma(io, tietokanta);
        ui.kayttoliittymaStart();

        assertTrue(io.getPrints().contains("Uusi lukuvinkki:\n" + "Otsikko: " + otsikko + "\n" +
                "Url: " + url + "\n" +
                "Tagit: " + tag1 + ", " + tag2 + ", " + tag3 + "\nlisätty onnistuneesti tietokantaan!"));
    }

    @Then("ohjelman tulostus sisaltaa {string} tekstin")
    public void ohjelmaTulostusSisaltaaTekstin(String teksti) {
        io = new StubIO(syotteet);
        tietokanta = new Tietokantahallinta("cucemberTesti.db", io);
        tietokanta.otaYhteysTietokantaan();
        ui = new Kayttoliittyma(io, tietokanta);
        ui.kayttoliittymaStart();

        boolean loytykoHaettavaTekstiOsa = io.getPrints()
                .stream()
                .anyMatch(x -> x.contains(teksti));

        assertTrue(loytykoHaettavaTekstiOsa);
    }
}
