Feature: Ohjelman kayttajana voin importata tietokannan kaytettavaksi tiedostopolusta
    
    Scenario: Kayttajana voin importata toisesta tietokannasta omaan tietokantaan lukuvinkit
        Given Import-tiedosto alustettu polulla "./importTestiDatabase.db" ja "10" lukuvinkilla
        Given komento tuo tiedosto valittu
        When  tiedoston tuontiin annettu "./importTestiDatabase.db" polku
        Then  ohjelman tulostus sisaltaa tekstin "Tiedoston sisältö lisätty onnistuneesti lukuvinkkikirjastoon." ja tietokannan koko "10" oikea

Scenario: Kayttajana voin importata toisesta tietokannasta omaan tietokantaan lukuvinkit ja vanhat lukuvinkit pysyvat
        Given Import-tiedosto alustettu polulla "./importTestiDatabase.db" ja "5" lukuvinkilla
        And   komento lisaa valittu
        And   lukuvinkki otsikolla "testia" lisatty
        And   komento tuo tiedosto valittu
        When  tiedoston tuontiin annettu "./importTestiDatabase.db" polku
        Then  ohjelman tulostus sisaltaa tekstin "Tiedoston sisältö lisätty onnistuneesti lukuvinkkikirjastoon." ja tietokannan koko "6" oikea