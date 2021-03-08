Feature: Ohjelman kayttajana voin poistaa lukuvinkin

    Scenario: kayttaja voi poistaa lukuvinkin hakemalla sen otsikon perusteella
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento poisto valittu
