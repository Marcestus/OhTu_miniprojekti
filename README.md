# OhTu_miniprojekti

![GitHub Actions](https://github.com/Marcestus/OhTu_miniprojekti/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![codecov](https://codecov.io/gh/Marcestus/OhTu_miniprojekti/branch/main/graph/badge.svg?token=3I741H9BV4)](https://codecov.io/gh/Marcestus/OhTu_miniprojekti)

- [Product and sprint backlogs](https://docs.google.com/spreadsheets/d/1jyfgLB1t1S6TO1p4N9tchziZxmQHy6vSwmqAWskcUM4/edit?usp=sharing)
- [Käyttöohje](https://github.com/Marcestus/OhTu_miniprojekti/blob/main/dokumentaatio/kayttohje.md)

## Definition of done

User story katsotaan valmiiksi, kun se täyttää seuraavat vaatimukset:

- User storylle on määritelty hyväksymiskriteerit, jotka on dokumentoitu sekä product backlogissa että [Cucumber-testaustyökalun featureinä](https://github.com/Marcestus/OhTu_miniprojekti/tree/main/src/test/resources/lukuvinkki).

- User storyn toteuttavaa koodia testataan kohtuullisella tasolla: ei-triviaalin koodin testikattavuuden tulee olla vähintään 70 %.

- Testaus toteutetaan niin yksikkö-, integraatio- kuin järjestelmätasolla. Järjestelmätason testaus toteutetaan Cucumberilla.

- Koodista on uusin toimiva versio GitHubissa, ja GitHub Actions -palvelun status badge -merkki README-tiedostossa kertoo, että koodi läpäisee kaikki testit.
 
- Koodi on tyyliltään ja nimennältään yhtenäistä ja läpäisee sekä katselmoinnin että Checkstyle-tarkastuksen:
  * uusi koodi on lisätty repositorioon pull requestin kautta ja läpäisee automaattiset testit
  * joku muu kuin koodin kirjoittaja(t) on katselmoinut koodin
  * koodi on lisätty päähaaraan vasta, kun se on katselmoitu.
