Feature: Ohjelman kayttajana voin lisata uuden lukuvinkin

    Scenario: kayttaja voi lisata uuden lukuvinkin
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti" lisatty
        Then  ohjelman tulostus sisaltaa "lisätty onnistuneesti tietokantaan!" tekstin

    Scenario: kayttaja voi lisata uuden lukuvinkin usealla tagilla
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com", tageilla "tag1", "tag2", "tag3" lisatty
        Then  ohjelman tulostus oikein parametreilla otsikko "otsikkoTesti", URL "https://www.google.com", tagit "tag1", "tag2", "tag3", luettu "false"

    Scenario: kayttajan lisaamassa lukuvinkissa tagit eroteltuna pilkuilla
        Given komento lisaa valittu
        When  lukuvinkki lisatty kolmella "tag1" "tag2" "tag3" tagilla
        Then  ohjelman tulostus sisaltaa "Tagit: tag1, tag2, tag3" tekstin