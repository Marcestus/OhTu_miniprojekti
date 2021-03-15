Feature: Ohjelman kayttajana voin importata tietokannan kaytettavaksi tiedostopolusta
    
    Scenario: Kayttajana voin importata toisesta tietokannasta omaan tietokantaan lukuvinkit
        Given Import-tiedosto alustettu polulla "./importTestiDatabase.db" ja kahdella lukuvinkilla
        Given komento tuo tiedosto valittu
        When  tiedoston tuontiin anettu "./importTestiDatabase.db" polku


