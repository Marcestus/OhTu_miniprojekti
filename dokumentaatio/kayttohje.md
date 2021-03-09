## Asennus- ja käyttöohje

### Asennus

Lataa tiedosto[jar-tiedoston nimi](osoite,josta jarin voi ladata)

### Käynnistys

Syötä komentorivillä: "gradle run"
- Vaihtoehtoisesti voit myös syöttää komennon: "gradle run -q --console=plain" (ei näytä gradlen välitulostuksia)

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
- Ohjelma listaa lisätyt lukuvinkit

### Lukuvinkkien haku tagien avulla

Syötä: 6
-  Ohjelma kysyy käyttäjältä tageja, jotka syötetään yksi kerrallaan ja tagien välissä painetaan enter.
- Kun tagit on syötetty, painetaan vielä kerran enter, jonka jälkeen ohjelma listaa tagit sisältävät lukuvinkit.
- Jos tageja ei löydy, ei ohjelma tulosta mitään.

### Ohjelman sulkeminen

Syötä: -1
- Ohjelma sulkeutuu

Ohjelmassa on tällä hetkellä myös komennot: "selaa lukuvinkkejä" ja "muokkaa lukuvinkkejä", joiden takana ei ole vielä toiminnallisuutta.
    
