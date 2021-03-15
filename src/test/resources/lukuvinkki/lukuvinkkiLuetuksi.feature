Feature: Ohjelman kayttajana voin lisata uuden lukuvinkin

    Scenario: kayttaja voi merkata lukuvinkin luetuksi
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento merkkaa luetuksi valittu
        And   lukuvinkin luetuksi merkkaamiseen annettu otsikko "otsikkoTesti" ja varmistus "k" annettu
        Then  ohjelman tulostus sisaltaa "Lukuvinkki asetettu onnistuneesti luetuksi!" tekstin

    Scenario: kayttaja voi perua lukuvinkin luetuksi merkkaamisen
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento merkkaa luetuksi valittu
        And   lukuvinkin luetuksi merkkaamiseen annettu otsikko "otsikkoTesti" ja varmistus "" annettu
        Then  ohjelman tulostus sisaltaa "Lukuvinkkiä ei merkattu luetuksi." tekstin
