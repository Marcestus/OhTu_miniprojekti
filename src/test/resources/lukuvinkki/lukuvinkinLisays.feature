Feature: Ohjelman kayttajana voin lisata uuden lukuvinkin

    Scenario: kayttaja voi lisata uuden lukuvinkin
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti" lisatty
        Then  ohjelman tulostus sisaltaa "lisätty onnistuneesti tietokantaan!" tekstin

    Scenario: kayttaja voi lisata uuden lukuvinkin usealla tagilla
        Given komento lisaa valittu
        When  lukuvinkki otsikolla "otsikkoTesti", URL "google.com", tageilla "tag1", "tag2", "tag3" lisatty
        Then  ohjelman tulostus oikein parametreilla otsikko "otsikkoTesti", URL "https://www.google.com", tagit "tag1", "tag2", "tag3", luettu "false"

    Scenario: kayttaja ei voi lisata lukuvinkkia epavalidilla url-osoitteella
        Given komento lisaa valittu
        When  lukuvinkki epavalidilla URL-osoitteella "huonoURL.com" lisatty
        Then  ohjelman tulostus sisaltaa "Ole hyvä ja anna kelvollinen lukuvinkin url:" tekstin

    Scenario: kayttajan lisaamassa lukuvinkissa tagit eroteltuna pilkuilla
        Given komento lisaa valittu
        When  lukuvinkki lisatty kolmella "tag1" "tag2" "tag3" tagilla
        Then  ohjelman tulostus sisaltaa "Tagit: tag1, tag2, tag3" tekstin

    Scenario: kayttaja voi valita valmiin otsikon hakemalla sen url-osoitteesta
        Given komento lisaa valittu
        When  lukuvinkki lisatty url "www.google.com" ja komennolla hae valmis otsikko "k"
        Then  Ohjelman tulostus sisältää "https://www.google.com" sivuston haetun otsikon "Google"

    Scenario: kayttaja voi valita valmiin otsikon hakemalla sen url-osoitteesta
        Given komento lisaa valittu
        When  lukuvinkki lisatty url "www.facebook.com" ja komennolla hae valmis otsikko "k"
        Then  Ohjelman tulostus sisältää "https://www.facebook.com" sivuston haetun otsikon "Facebook"

    Scenario: kayttaja voi valita, etta valmista otsikkoa ei haeta url-osoitteesta
        Given komento lisaa valittu
        When  lukuvinkki lisatty otsikolla "omaOtsikko", url "www.google.com", tageilla "tag1", "tag2", "tag3" ja komennolla hae valmis otsikko ""
        Then  ohjelman tulostus oikein parametreilla otsikko "omaOtsikko", URL "https://www.google.com", tagit "tag1", "tag2", "tag3", luettu "false"