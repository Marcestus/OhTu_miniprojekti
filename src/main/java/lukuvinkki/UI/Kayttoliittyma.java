package lukuvinkki.UI;

import lukuvinkki.domain.*;

public class Kayttoliittyma {

    private KonsoliIO io;

    public Kayttoliittyma(KonsoliIO io) {
        this.io = io;
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