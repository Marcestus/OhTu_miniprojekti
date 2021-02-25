package lukuvinkki.domain;

import lukuvinkki.dao.TietokantaRajapinta;

public class Lukuvinkkipalvelu {

    IORajapinta io;
    TietokantaRajapinta tietokanta;

    public Lukuvinkkipalvelu(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
    }

    public void lisaaLukuvinkki() {
        System.out.println("Lisää vinkki");
    }

    public void poistaLukuvinkki() {
        System.out.println("Poista vinkki");
    }

    public void haeLukuvunkit() {
        System.out.println("Lista vinkeistä");
    }
    
}