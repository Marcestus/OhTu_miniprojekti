package lukuvinkki.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lukuvinkki.domain.*;

public class Tietokantahallinta implements TietokantaRajapinta {
    
    private Connection connection;
    private IORajapinta io;
    private String tiedostonURL;

    public Tietokantahallinta(String tiedosto, IORajapinta io) {

        this.io = io;
        this.tiedostonURL = "jdbc:sqlite:./" + tiedosto;     

    }

    public boolean otaYhteysTietokantaan() {
        return alustaTietokanta();
    }

    public boolean lisaaUusiLukuvinkki(Lukuvinkki lukuvinkki) {
        String addKasky = "INSERT INTO lukuvinkki (otsikko, url, tagit) VALUES (?, ?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(addKasky)) {
            stmt.setString(1, lukuvinkki.getOtsikko());
            stmt.setString(2, lukuvinkki.getUrl());
            stmt.setString(3, lukuvinkki.getTagitString());

            stmt.executeUpdate();
            return true;
        } catch (SQLException error) {
            io.print("ERROR: " + error.getMessage());
            return false;
        }
  
    }

    // Tällä hetkellä tulostus tapahtuu tässä metodissa suoraan, on mahdollista siirtää sovelluslogiikan alle, mutta proof of concept
    public void haeKaikkiLukuvinkit() {
        String hakuKasky = "SELECT otsikko, url, tagit FROM lukuvinkki;";

        try (PreparedStatement stmt = connection.prepareStatement(hakuKasky)) {

            ResultSet result = stmt.executeQuery();

            if (!result.next()) {
                io.print("Tietokannassa ei lukuvinkkejä!");
                return;
            }

            while (result.next()) {
                io.print("Otsikko: " + result.getString(0));
                io.print("Url: " + result.getString(1));
                io.print("Tagit: " + result.getString(2));
                io.print("");
            }

        } catch (SQLException error) {
            io.print("ERROR: " + error.getMessage());
        }

    }

    private boolean alustaTietokanta() {

        String createTableKasky = "CREATE TABLE IF NOT EXISTS lukuvinkki (id integer PRIMARY KEY, otsikko text, url text, tagit text);";

        try {
            this.connection = DriverManager.getConnection(tiedostonURL);

            Statement stmt = connection.createStatement();

            stmt.execute(createTableKasky);

            return true;

        } catch (SQLException error) {
            io.print("ERROR:" + error.getMessage());
            return false;
        }
    }

}
