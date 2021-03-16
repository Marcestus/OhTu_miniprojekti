package lukuvinkki.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import lukuvinkki.dao.TietokantaRajapinta;

public class Lukuvinkkipalvelu {

    IORajapinta io;
    TietokantaRajapinta tietokanta;
    private HashMap<String, String> urlinMukaisetTagit;

    public Lukuvinkkipalvelu(IORajapinta io, TietokantaRajapinta tietokanta) {
        this.io = io;
        this.tietokanta = tietokanta;
        alustaUrlinMukaisetTagit();
    }

    private void alustaUrlinMukaisetTagit() {
        this.urlinMukaisetTagit = new HashMap<>();
        this.urlinMukaisetTagit.put("medium", "blogi");
        this.urlinMukaisetTagit.put("youtube", "video");
        this.urlinMukaisetTagit.put("dl.acm.org", "julkaisu");
        this.urlinMukaisetTagit.put("ieeexplore.ieee.org", "julkaisu");
    }

    public boolean lisaaLukuvinkki(String otsikko, String url, ArrayList<String> tagit) {
        try {
            Lukuvinkki lukuvinkki = new Lukuvinkki(otsikko, url, "");
            lukuvinkki.setTagit(tagit);
            return tietokanta.lisaaUusiLukuvinkki(lukuvinkki);
        } catch (Exception error) {
            io.print("Error: " + error.getMessage());
            return false;
        }
    } 

    public boolean lisaaLukuvinkitListasta(ArrayList<Lukuvinkki> lukuvinkit) {
        return tietokanta.lisaaLukuvinkitListasta(lukuvinkit);
    }

    public ArrayList<String> lisaaTagitURLPerusteella(String url) {
        ArrayList<String> tags = new ArrayList<>();

        for (String verrattavaURL : this.urlinMukaisetTagit.keySet()) {
            if (url.contains(verrattavaURL)) {
                tags.add(urlinMukaisetTagit.get(verrattavaURL));
                break;
            }
        }
        return tags;
    }

    public HashMap<String, String> getUrlinMukaisetTagitTesteihin() {
        return this.urlinMukaisetTagit;
    }

    public boolean poistaLukuvinkki(int id) {
        return tietokanta.poistaLukuvinkki(id);
    }
    
    public boolean asetaLukuvinkkiLuetuksi(int id) {
        return tietokanta.asetaLuetuksi(id);
    }

    public ArrayList<Lukuvinkki> haeLukuvunkit() {
        return tietokanta.haeKaikkiLukuvinkit();
    }

    public ArrayList<Lukuvinkki> haeLukuvinkitTaginPerusteella(ArrayList<String> kysytytTagit) {
        ArrayList<Lukuvinkki> vinkit = tietokanta.haeKaikkiLukuvinkit();

        ArrayList<Lukuvinkki> returnList = new ArrayList<>();
        for (Lukuvinkki lukuvinkki : vinkit) {
            String lukuvinkinTagit = lukuvinkki.getTagitString();
            for (String tagi : kysytytTagit) {
                if (lukuvinkinTagit.contains(tagi)) {
                    returnList.add(lukuvinkki);
                    break;
                }
            }
        }
        return returnList;
    }

    public List<Lukuvinkki> haeLukuvinkitSyotteenPerusteella(String syote) {
        switch (syote) {
            case "1":
                return haeLukuvunkit();
            case "2":
                return haeLukuvinkitLuetunStatuksenPerusteella(false);
            case "3":
                return haeLukuvinkitLuetunStatuksenPerusteella(true);
            default:
                return new ArrayList<>();
        }
    }
    
    public Lukuvinkki getLukematonLukuvinkkiOtsikonPerusteella(String otsikko) {
        for (Lukuvinkki lukematonVinkki : haeLukuvinkitSyotteenPerusteella("2")) {
            if (lukematonVinkki.getOtsikko().contains(otsikko)) {
                return lukematonVinkki;
            }
        }
        return null;
    }

    public List<Lukuvinkki> haeLukuvinkitLuetunStatuksenPerusteella(boolean onkoLuettu) {
        return haeLukuvunkit().stream()
                .filter(l -> l.getLuettu() == onkoLuettu)
                .collect(Collectors.toList());
    }

    public Lukuvinkki haeLukuvinkkiSyotteenPerusteella(String hakuSyote, boolean onkoURLPerusteella) {
        for (Lukuvinkki lukuvinkki : tietokanta.haeKaikkiLukuvinkit()) {
            if (loytyykoHakuSyoteVinkista(lukuvinkki, onkoURLPerusteella, hakuSyote)) {
                return lukuvinkki;
            }
        }
        return null;
    }

    public boolean loytyykoHakuSyoteVinkista(Lukuvinkki lukuvinkki, boolean onkoURLPerusteella, String syote) {
        if (onkoURLPerusteella) {
            return lukuvinkki.getUrl().contains(syote);
        } else {
            return lukuvinkki.getOtsikko().contains(syote);
        }
    }
}
