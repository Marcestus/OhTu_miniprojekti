package lukuvinkki;

import lukuvinkki.domain.*;
import lukuvinkki.UI.*;
import lukuvinkki.dao.TietokantaRajapinta;
import lukuvinkki.dao.Tietokantahallinta;

public class Main {

    public static void main(String[] args) {


        IORajapinta io = new KonsoliIO();
        TietokantaRajapinta tietokanta = new Tietokantahallinta("lukuvinkkikirjasto.db", io);
        
        if(!tietokanta.otaYhteysTietokantaan()) {
            io.print("Pahoittelut, tietokannassa on häiriö. Kokeile ohjelmaa uudestaan!");
        } else {
            Kayttoliittyma ui = new Kayttoliittyma(io, tietokanta);
            ui.kayttoliittymaStart();
        }

        

    }

}