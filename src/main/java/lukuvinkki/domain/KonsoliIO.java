package lukuvinkki.domain;

import java.util.Scanner;

public class KonsoliIO implements IORajapinta {
    private Scanner lukija;
    
    public KonsoliIO() {
        lukija = new Scanner(System.in);
    }

    @Override
    public void print(String tulostettava) {
        System.out.println(tulostettava);
    }

    @Override
    public String syote() {
        return lukija.nextLine();
    }
    
    // vain testaukseen
    public void alustaTestiSyote(String testiSyote) {
        lukija = new Scanner(testiSyote);
    }
}