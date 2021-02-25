package lukuvinkki.domain;


public class Lukuvinkkipalvelu {

    KonsoliIO io;

    public Lukuvinkkipalvelu(KonsoliIO io) {
        this.io = io;
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