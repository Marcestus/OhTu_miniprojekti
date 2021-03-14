package lukuvinkki.dao;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lukuvinkki.domain.*;

public class Tietokantahallinta implements TietokantaRajapinta {
    
    private Connection connection;
    private IORajapinta io;
    private String tiedostonURL;

    public Tietokantahallinta(String tiedosto, IORajapinta io) {

        this.io = io;
        this.tiedostonURL = "jdbc:sqlite:" + tiedosto;     

    }

    public boolean otaYhteysTietokantaan() {
        return alustaTietokanta();
    }

    public boolean lisaaUusiLukuvinkki(Lukuvinkki lukuvinkki) {
        String addKasky = "INSERT INTO lukuvinkki (otsikko, url, tagit, luettu) VALUES (?, ?, ?, 0);";

        try {
            this.connection = DriverManager.getConnection(this.tiedostonURL);
            PreparedStatement stmt = connection.prepareStatement(addKasky);
            stmt.setString(1, lukuvinkki.getOtsikko());
            stmt.setString(2, lukuvinkki.getUrl());
            stmt.setString(3, lukuvinkki.getTagitString());

            stmt.executeUpdate();
            this.connection.close();

            return true;
        } catch (SQLException error) {
            io.print("ERROR: " + error.getMessage());
            return false;
        }

    }

    public boolean lisaaLukuvinkitListasta(ArrayList<Lukuvinkki> lukuvinkit) {

        for(Lukuvinkki lukuvinkki : lukuvinkit) {
            if (!lisaaUusiLukuvinkki(lukuvinkki)) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<Lukuvinkki> haeKaikkiLukuvinkit() {
        String hakuKasky = "SELECT id, otsikko, url, tagit, luettu FROM lukuvinkki;";

        ArrayList<Lukuvinkki> lukuvinkit = new ArrayList<>();

        try {
            this.connection = DriverManager.getConnection(this.tiedostonURL);
            PreparedStatement stmt = connection.prepareStatement(hakuKasky);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                String otsikko = result.getString(2);
                String url = result.getString(3);
                String tagit = result.getString(4);
                boolean luettu = result.getBoolean(5);
                
                lukuvinkit.add(new Lukuvinkki(otsikko, url, tagit, id, luettu));
            }
            this.connection.close();
        } catch (SQLException error) {
            io.print("ERROR: " + error.getMessage());
        }

        return lukuvinkit;
    }

    public boolean poistaLukuvinkki(int poistettavanID) {
        String poistoKasky = "DELETE FROM lukuvinkki WHERE id = ?;";

        try {
            this.connection = DriverManager.getConnection(this.tiedostonURL);
            PreparedStatement stmt = connection.prepareStatement(poistoKasky);
            stmt.setInt(1, poistettavanID);
            stmt.executeUpdate();
            this.connection.close();
            
            return true;
        } catch (SQLException error) {
            io.print("ERROR: " + error.getMessage());
            return false;
        }
        
    }

    public boolean asetaLuetuksi(int muutettavanID) {
        String kasky = "UPDATE lukuvinkki SET luettu = 1 WHERE id = ?;";

        try {
            this.connection = DriverManager.getConnection(this.tiedostonURL);
            PreparedStatement stmt = connection.prepareStatement(kasky);
            stmt.setInt(1, muutettavanID);
            stmt.executeUpdate();
            this.connection.close();

            return true;
        } catch (SQLException error) {
            io.print("ERROR: " + error.getMessage());
            return false;
        }

    }

    private boolean alustaTietokanta() {

        String createTableKasky = "CREATE TABLE IF NOT EXISTS lukuvinkki (id integer PRIMARY KEY, otsikko text, url text, tagit text, luettu integer);";

        try {
            this.connection = DriverManager.getConnection(this.tiedostonURL);

            Statement stmt = connection.createStatement();

            stmt.execute(createTableKasky);

            this.connection.close();

            return true;

        } catch (SQLException error) {
            io.print("ERROR:" + error.getMessage());
            return false;
        }
    }
    
    public void poistaTestiTietokanta(String poistoDB) {
        try {
            Path testiTietokannanOsoite = FileSystems.getDefault().getPath(poistoDB);
            Files.delete(testiTietokannanOsoite);
        } catch (Exception e) {
            System.out.println("Testi tietokannan poisto ei onnistunut.");
        }
    }

}
