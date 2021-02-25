package lukuvinkki.domain;

import java.util.Scanner;

public class KonsoliIO {
    private Scanner lukija;
    
    public KonsoliIO() {
        lukija = new Scanner(System.in);
    }

    public void print(String tulostettava) {
        System.out.println(tulostettava);
    }

    public String syote() {
        return lukija.nextLine();
    }
}