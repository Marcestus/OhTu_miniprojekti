Feature: Ohjelman kayttajana voin hakea kaikki lukuvinkit

    Scenario: kayttaja voi hakea kaikki lukuvinkit
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento hae valittu ja listaus syotteella "1"
        Then  ohjelman tulostus listaa luodun vinkin otsikolla "otsikkoTesti", url "https://www.google.com", tagit "-", luettu "false"

    #Scenario: kayttaja voi hakea lukuvinkkejä tageilla tee vasta kun ui puolella tagihaku siirretty ison haun alle

    Scenario: kayttaja voi hakea kaikki luetut lukuvinkit
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento aseta lukuvinkki luetuksi valittu ja syötteenä "otsikkoTesti", "k"
        And   komento hae valittu ja listaus syotteella "3"
        Then  ohjelman tulostus listaa luodun vinkin otsikolla "otsikkoTesti", url "https://www.google.com", tagit "-", luettu "true"

    Scenario: kayttaha voi hakea kaikki luetut lukemattomat lukuvinkit
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento hae valittu ja listaus syotteella "2"
        Then  ohjelman tulostus listaa luodun vinkin otsikolla "otsikkoTesti", url "https://www.google.com", tagit "-", luettu "false"
    