package lukuvinkki.domain;

import java.io.File;

public class Tiedostopalvelu {
    
    
    public boolean onkoTiedostoOlemassa(String polku) {
        try {
            File tiedosto = new File(polku);
            return tiedosto.exists();
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            return false;
        }
    }
}
