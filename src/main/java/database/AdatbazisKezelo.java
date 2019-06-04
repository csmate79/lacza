/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import controllers.View_2Controller;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author csicsek
 */
public final class AdatbazisKezelo {
    public static AdatbazisKezelo kezelo;

    private static final String DB_URL = "jdbc:derby:database/library2;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    public String qu;

    public AdatbazisKezelo() {
        createConnection();
        setupBookTable();
        setupMemberTable();
        setupKiadTable();
    }

    public static AdatbazisKezelo getInstance() {
        if (kezelo == null){
            kezelo = new AdatbazisKezelo();
        }
        return kezelo;
    }

    /**
     * Itt hozzuk létre a kapcsolatot a derby adatbázissal.
     */
    private void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Itt hozzuk létre a BOOK táblát.
     */
    private void setupBookTable() {
        String TABLE_NAME = "BOOK";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null, TABLE_NAME.toUpperCase(),null);
            if (tables.next()) {
                System.out.println("A " + TABLE_NAME + " már készen áll!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "  id varchar(200) primary key,\n"
                        + "  title varchar(200),\n"
                        + "  author varchar(200),\n"
                        + "  publisher varchar(200),\n"
                        + "  isAvail boolean default true"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " .. adatbázis hiba");
        }
    }

    /**
     * Lekérjük az adatbázis könyveinek a címet, és kiíratjuk a terminálra.
     */
    public static void checkData() {
        String qu = "SELECT title FROM BOOK";
        ResultSet rs = execQuery(qu);
        try {
            while (rs.next()) {
                String titlex = rs.getString("title");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(View_2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Itt hozzuk létre a MEMBER táblát.
     */
    private void setupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null,null, TABLE_NAME.toUpperCase(),null);
            if (tables.next()) {
                System.out.println("A " + TABLE_NAME + " asztal már készen áll!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "name varchar(50),\n "
                        + "id varchar(200), \n"
                        + "mobile varchar(25), \n"
                        + "email varchar(100), \n"
                        + "primary key (id))");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " .. adatbázis hiba");
        }
    }

    /**
     * "Tag" adatbázis lekérés.
     */
    public void memB() {
        kezelo = AdatbazisKezelo.getInstance();
        qu = "SELECT * FROM MEMBER";
    }

    /**
     *  "Könyv" adatbázis lekérés.
     */
    public void konyvB() {
        kezelo = AdatbazisKezelo.getInstance();
        qu = "SELECT * FROM BOOK";
    }


    /**
     * Itt hozzuk létre a KIAD táblát.
     */
    private void setupKiadTable() {
        String TABLE_NAME = "KIAD";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()){
                System.out.println("A " + TABLE_NAME + " asztal már készen áll!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "  bookID varchar(200) primary key,\n"
                        + "  memberID varchar(200),\n"
                        + "  kiadTime timestamp default CURRENT_TIMESTAMP,\n"
                        + "  megujit_count integer default 0,\n"
                        + "  FOREIGN KEY (bookID) REFERENCES BOOK(id),\n"
                        + "  FOREIGN KEY (memberID) REFERENCES MEMBER(id))\n");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " .. adatbázis hiba");
        }
    }

    /**
     * Az adatábzis létrehozást segíti.
     * @param query - lekér
     * @return - vissza adja az eredményt.
     */
    public static ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException exception) {
            System.out.println("Exception at execQuery:dataHandler" + exception.getLocalizedMessage());
            return null;
        }
        return result;
    }

    /**
     * Az adatábzis létrehozást segíti.
     * @param qu - query
     * @return - igaz vagy hamis értéket ad vissza.
     */
    public boolean  execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException exception)  {
            JOptionPane.showMessageDialog(null, "Error:" + exception.getMessage(),"Error occured",JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHendler" + exception.getLocalizedMessage());
            return false;
        }
    }
}

