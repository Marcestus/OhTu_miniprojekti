package lukuvinkki.domain;

import java.io.File;

public class Tiedostopalvelu {   
    
    public boolean onkoTiedostoOlemassa(String polku) {
        File tiedosto = new File(polku);
        return tiedosto.exists();
    }
}
