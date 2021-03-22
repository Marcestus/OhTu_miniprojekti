# Raportti projektin kulusta

Ryhmän jäsenet: Elias Herranen, Miika Koskela, Tomi Lappeteläinen, Jenny Perttola, Janne Rautava, Mari Väänänen

### Sprinttien aikana kohdatut ongelmat

Työ lähti nopeasti käyntiin ensimmäisellä sprintillä. Suunnittelimme sovelluksen arkkitehtuurin yhdessä, minkä jälkeen jaoimme vastuut ryhmäläisille sovelluksen arkkitehtuurikerroksien (käyttöliittymä, sovelluslogiikka ja tietokanta) mukaan. Emme kohdanneet ensimmäisen sprintin aikana varsinaisia ongelmia omia osuuksia tehdessämme. Toisen sprintin alussa koodia piti kuitenkin refaktoroida, sillä arkkitehtuurikerrosten yhteensovittamisessa (metodien nimeäminen, mikä osa toiminnoista kuuluu mihinkin kerrokseen yms.) ilmeni joitakin korjattavia kohtia. Retrossa pohdimme, että sovelluksen entistä tarkempi suunnittelu koko ryhmän voimin olisi voinut ehkäistä refaktoroinnin tarpeen.

Toisen sprintin lopulla huomasimme releasen tekemisen yhteydessä yllättävän teknisen ongelman jar-tiedoston luomisessa, mutta selvisimme siitä hienosti tiimityöllä. Lisäksi demon aikana huomasimme, että muutama ääritapaus koodin toiminnassa oli jäänyt testaamatta sovelluksen viimeisessä versiossa. Retrossa asiaa läpikäydessä päätimme kiinnittää vielä tarkempaa huomiota testien kirjoittamiseen.

Kolmannessa sprintissä pieniä ongelmia aiheuttivat branchit. Koska muutama brancheista jäi pitkäikäisemmiksi kuin olisi ollut toivottavaa, pääsimme harjoittelemaan merge conflictien ratkomista porukalla. Totesimme, että branchien kanssa työskentelyä on vielä hyvä harjoitella erityisesti silloin, kun useampi ryhmäläinen luo koodia samaan branchiin eri aikoina.

Kaikkien sprinttien aikana harmistusta aiheutti myös vallitseva etätyöpakko ja retrojen mad-kategoriaa koristivatkin useat toiveet Oljenkorteen pääsemisestä.

### Mikä sujui hyvin ja mitä pitäisi parantaa

Positiivista hehkutusta sai erityisesti ryhmän yhteishenki ja kommunikaatio. Projektin aikana toteuttamamme pari- ja ryhmäkoodaus sekä ryhmätyöskentely yleisesti olivat antoisia kokemuksia: suuri osa koodista syntyi etäyhteyden välityksellä niin, että yksi taskin toteuttajista jakoi oman näyttönsä ja kirjoitti koodin yhteisen pohdinnan mukaan. (Tämän vuoksi etärepositoriossa näkyvät commitit jakautuvat epätasaisesti.) Ryhmä myös koki, ettei ongelmien ilmetessä jäänyt yksin, vaan apua löytyi nopeasti sitä pyydettäessä. Hyvää fiilistä loi myös ryhmäläisten oma-aloitteisuus ja kaikki tehtävät löysivätkin nopeasti tekijänsä. Ainoa pieni tehtävänjaon vaikeus syntyi loppudemossa, jonka pitäminen jännitti ryhmäläisiä. Totesimme myös projektin pysyneen hienosti aikataulussa.

Kehityskohteita listatessa esiin nousi uudestaan branchien käyttö. Arvelimme, että branchien käytön koko potentiaalia ei saatu vielä valjastettua, kun niiden käyttö oli vielä uutta. Lisäksi todettiin, että välillä tehtävänjako ja sprintin alkusuunnittelu saattoi venähtää turhan pitkäksi tapaamiseksi. Demoihin valmistautumisessa koettiin myös jonkin verran parannettavaa, vaikka itse esitykset menivätkin hyvin.

### Mitä opittiin, mitä oltaisiin haluttu oppia lisää ja mikä tuntui turhalta

Suurin osa oppimiskokemuksista liittyi ryhmätyöskentelyyn. Opimme ohjelmointia usean hengen tiimissä, erityisesti toisen koodin katselmointia ja oman koodin rakentamista toisten koodin kaveriksi. Keskustelimme myös huijarisyndroomasta ja siitä, kuinka piti hyväksyä se, että ryhmäläiset nimenomaan täydensivät toisiaan osaamalla eri asioita. Oli myös kiva huomata, että pärjäsi hyvin ryhmässä.

Ryhmätyöskentelyn kehittymisen lisäksi ryhmäläiset kokivat myös oppineensa aikatauluttamaan paremmin henkilökohtaista työskentelyään sekä arvioimaan erilaisiin tehtäviin kuluvaa aikaa. Oppimiskokemukseksi nousi myös pull requestien teko ja niiden hyödyt ryhmätyöskentelyssä. Ryhmässä olisi kuitenkin ollut halukkuutta treenata toisten koodin katselmointia vieläkin enemmän. Osaa harmitti myös jälkikäteen, että oma panos testien kirjoittamisessa jäi vähäiseksi. Lisäksi olisi toivottu oliosuunnitteluun vielä enemmän materiaalia, jotta varmuus erilaisten tilanteiden ja ratkaisujen yhdistämisessä olisi karttunut.

Ryhmä koki burndown-käyrän käyttämisen tämän mittaluokan projektissa turhaksi. Koettiin, ettei se antanut näin pienessä projektissa lisäarvoa.
