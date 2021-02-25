package lukuvinkki.UI;

import lukuvinkki.domain.*;
import lukuvinkki.domain.*;

public class Kayttoliittyma {

    private KonsoliIO io;
    private Lukuvinkkipalvelu palvelu;

    public Kayttoliittyma(KonsoliIO io) {
        this.io = io;
        this.palvelu = new Lukuvinkkipalvelu(io);
    }

    public void kayttoliittymaStart() {

        

        while (true) {
            System.out.println("Moikka, laita 1 jos haluat lopettaa!");
            String vastaus = io.syote();
            if(vastaus.equals("1")){
                io.print("moikka");
                break;
            }
                
        }

    }

}