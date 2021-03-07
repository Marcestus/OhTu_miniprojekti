Feature: Ohjelman kayttajana voin hakea kaikki lukuvinkit

    Scenario: kayttaja voi hakea kaikki lukuvinkit
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento hae valittu
        Then  ohjelman tulostus listaa luodun vinkin otsikolla "otsikkoTesti", url "https://www.google.com", tagit "-"

