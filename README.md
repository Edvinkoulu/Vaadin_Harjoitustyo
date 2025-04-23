Sivulla voi selata ja hallita henkilöitä sek niihin liittyviä mittaustuloksia. Tiedot on tietokannassa, josta sovellus hakee tiedot automaattisesti.


Sovelluksen käyttöohje:

Sivun ylälaidassa navigointipalkki josta pääsee sivuille: Koti, Henkilöt ja Mittaukset. Eri käyttäjillä on eri oikeuksia ja kirjautumatta pääsee vain Koti sivulle, kaikki kirjautuneet pääsevät Henkilöt sivulle ja admin pääsee kaikkialle. Käyttäjänimet ja salasanat on kerrottu koti sivulla, jotta sovellusta voi testata. Käyttäjän vaihto tapahtuu Koti sivulla.

Koti sivu:
Kotisivu näyttää käyttäjän jona olet kirjautunut tai jos et ole kirjautunut. Kirjautumaan pääsee kotisivun napista tai kun koittaa navikoida muille sivuille kirjautumatta. täältä voi myös kirjautua ulos käyttäjältä.

Henkilöt sivu:

Henkilön voi lisätä kirjoittamalla uuden henkilön ja painamalla lisää nappia. Henkilölistaa voi lajitella aakkosjärjästyksessä a->ö ja ö->a. lisäksi voit katsoa jokaisen henkilön mittaukset "Katso mittauksia" napista, sekä lisätä henkilölle mittauksia "Lisää mittaus napista.

Mittaukset sivu:

Voit lisätä mittauksia täältä, pitää kertoa mitattava ominaisuuus ja mittatulos ja valita henkilö jolle lisätään. Listaa voi suodattaa valitsemalla henkilön, niin näkyy pelkästään kyseisen henkilön mittaukset. Aakkostenmukaan enkilön nimi tai mitattava ominaisuus tai mittayksikkö.

Olen tehnyt seuraavat Arviointimatriisin mukaiset ominaisuudet:

Data ja entiteetit:
1. yksi entieetti.  (Person)
2. Toinen entiteetti, jolle edelliselle toteutettuna 1 to 1 relaatio.  (Measurement)

Suodattimet:
1. Yksinkertainen suodatus yhdelle sarakkeelle. (Henkilöt sivulla nimen mukaan)
2. Yksinkertainen suodatus kahden tai useamman sarakkeen mukaan. (Mittaukset sivulla valitun henkilön ja mitattavan ominaisuuden mukaan)
3. Suodatus relaatiossa olevan entiteetin osalta. (Measurement sivulla suodattaa henkilön mukaan measurement taulua)

Tyylit:
1. Globaalien tyylien muuttaminen. (styles.css tiedostossa headerin tyylien muutos)
2. Tyylien muokkaaminen suoraan yksittäiselle komponentille. (LoginView title)
3. Tyylien muokkaaminen näkymässä yksittäiselle komponentille. (LoginView loginForm)

Ulkoasu
1. SPA-sovellus, jossa on päänäkymä
2. Header. (MainLayout)
3. Toimiva navigointipalkki. (MainLayout)
4. Footer. (MainLayout)

Autentikointi ja tietoturva:
1. Security-palikan käyttöönotto.  (SecurityConfig)
2. Sisäänkirjautumissivun luominen  (LoginView)
3. Käyttäjäentiteetin luominen ja roolien määrittäminen (Admin/user).  (Roolit ladataan kantaan dataInitializerissä ja otettu käyttöön koko sovellukselle)
4. Toteuta: - Kaikki käyttäjät näkevät päänäkymän - User ja Admin käyttäjät näkevät jonkun sivun - Sivu pelkästään ADMIN käyttäjille (Koti sivu kaikille, Henkilöt personille ja adminille, Mittaukset pelkälle adminille)

Lisätoiminnallisuudet:
1. Julkaise työ GIT:iin
2. Salasanojen salaus jollain menetelmällä (BCryot algoritmilla)

Pisteitä yht: 18.

Itsearviointi:

Sovellus helppo käyttää ja ei kaatuile. Henkilö ja Mittaus listat ei kovin järkeviä oikeassa käytössä, rakennettu vain vaaditut ominaisuudet. Security onnistui mielestäni hyvin ja käyttäjien oikeudet myös hyvät.


