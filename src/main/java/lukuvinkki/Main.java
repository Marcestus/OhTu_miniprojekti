package lukuvinkki;

import lukuvinkki.domain.*;
import lukuvinkki.UI.*;

public class Main {

    public static void main(String[] args) {

        Kayttoliittyma ui = new Kayttoliittyma(new KonsoliIO());
        ui.kayttoliittymaStart();

    }

}