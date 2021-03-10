Feature: Ohjelman kayttajana voin poistaa lukuvinkin

    Scenario: kayttaja voi poistaa lukuvinkin hakemalla oikean otsikon perusteella
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento poisto otsikolla valittu
        And   lukuvinkin poistoon annettu otsikko "otsikkoTesti" ja poisto vahvistus "k"
        Then  ohjelman tulostus sisaltaa "Lukuvinkki poistettu onnistuneesti!" tekstin ja lukuvinkin oikea tiedot "otsikkoTesti" ja "https://www.google.com" 

    Scenario: kayttaja voi poistaa lukuvinkin hakemalla oikean url perusteella
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "urlPerusteella", URL "facebook.com" ja ilman tageja lisatty
        And   komento poisto url valittu
        And   lukuvinkin poistoon annettu url "https://www.facebook.com" ja poisto vahvistus "k"
        Then  ohjelman tulostus sisaltaa "Lukuvinkki poistettu onnistuneesti!" tekstin ja lukuvinkin oikea tiedot "urlPerusteella" ja "https://www.facebook.com"

    Scenario: kayttaja voi aloittaa poiston ja perua sen
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com" ja ilman tageja lisatty
        And   komento poisto otsikolla valittu
        And   lukuvinkin poistoon annettu otsikko "otsikkoTesti" ja poisto vahvistus ""
        Then  ohjelman tulostus sisaltaa "Lukuvinkin poistaminen keskeytetty." tekstin