Feature: Ohjelman kayttajana voin exportata tietokannan antamaani kansioon
    
    Scenario: Kayttajana voin exportata tietokannan juurikansioon
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento vie tiedosto valittu
        Then  ohjelman tulostus sisaltaa "Export-tiedoston luonti onnistui!" ilmoituksen
