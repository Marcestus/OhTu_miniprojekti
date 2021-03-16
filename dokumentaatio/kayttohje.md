## Asennus- ja käyttöohje

### Asennus

Lataa tiedosto [lukuvinkki.jar](https://github.com/Marcestus/OhTu_miniprojekti/releases/tag/v1.0).

### Käynnistys

Navigoi hakemistoon, johon tallensit edellä ladatun jar-tiedoston, ja syötä komento `java -jar lukuvinkki.jar`. Komento luo uuden tietokannan samaan hakemistoon ja käynnistää ohjelman.

Vaihtoehtoisesti voit kloonata koko repositorion koneellesi. Tällöin saat ohjelman käynnistettyä navigoimalla repositorion juureen ja syöttämällä komennon `gradle run`. Voit myös käyttää komentoa `gradle run -q --console=plain`, jolloin ohjelma ei näytä gradlen välitulostuksia.

### Lukuvinkin lisäys

Syötä: 1
- Ohjelma kysyy tämän jälkeen otsikon, url:n ja tagit
- Ohjelma tarkistaa otsikon oikeellisuuden (väh. 4 merkkiä) ja että url löytyy internetistä (url:n voi kirjoittaa ilman www-etuliitettä).
- Ohjelma tarjoaa myös mahdollisuutta korvata syötetyn otsikon url-osoitteesta löytyvällä otsikolla, jolloin lukuvinkin otsikoksi asetetaan url-osoitteen tarjoama otsikko.


### Lukuvinkin poisto

Syötä: 2
- Ohjelma kysyy tämän jälkeen poistetaanko otsikon vai url:n perusteella
- Kun käyttäjä on syöttänyt otsikon tai url:n, niin ohjelma varmistaa vielä, että kyseinen lukuvinkki löytyy tietokannasta ja kysyy haluaako käyttäjä varmasti poistaa tämän lukuvinkin.
- Ohjelma ilmoittaa vielä lopuksi onnistuiko lukuvinkin poistaminen.

### Lukuvinkkien listaus

Syötä: 3
- Lukuvinkit voi listata neljällä eri tavalla seuraavilla syötteillä:
    - 1: listaa kaikki lukuvinkit
    - 2: listaa lukemattomat lukuvinkit
    - 3: listaa luetut lukuvinkit
    - 4: listaa lukuvinkit käyttäjän syöttämien tagien perusteella

### Erillisen lukuvinkkitiedoston liittäminen osaksi lukuvinkkikirjastoa

Syötä: 4
- Ohjelma kysyy polkua, josta liitettävä tiedosto haetaan
- Lukuvinkkitiedoston liittämisen onnistuessa ohjelma ilmoittaa tästä vielä vahvistusviestillä 

### Lukuvinkkien vienti erilliseen tiedostoon

Syötä: 5
- Ohjelma luo tietokannassa olevista lukuvinkeistä oman tiedoston
- Ohjelma vahvistaa tiedoston luonnin onnistuminen erillisellä vahvistusviestillä

### Lukuvinkkien asettaminen luetuksi

Syötä: 6
-  Ohjelma kysyy lukuvinkin otsikkoa, jonka käyttäjä haluaa merkitä luetuksi
-  Käyttäjän syöttäessä lukuvinkkikirjastosta löytyvän otsikon, ohjelma kysyy vielä käyttäjältä vahvistuksen luetuksi merkkaamiseen

### Ohjelman sulkeminen

Syötä: -1
- Ohjelma sulkeutuu

    
