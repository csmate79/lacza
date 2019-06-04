/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.google.gson.Gson;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Csicsek Máté
 *
 * Ebben a preferencben adjuk meg a változókat, és hozzuk létre a json txt-t.
 */
public class Preferences {
    private static final String CONFIG_FILE = "config.txt";

    private int nDaysWithoutFine;
    private float finePerDay;
    private String username;
    private String password;

    /**
     * Miket írjon ki az szövegdobozokba..
     */
    Preferences() {
        nDaysWithoutFine = 21;
        finePerDay = (float) 2.5;
        username = "admin";
        password = "admin";
    }

    public int getnDaysWithoutFine() {
        return nDaysWithoutFine;
    }

    public void setnDaysWithoutFine(int nDaysWithoutFine) {
        this.nDaysWithoutFine = nDaysWithoutFine;
    }

    public float getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Itt hozzuk létre a gson file-t.
     */
    private static void initConfig() {
        Writer writer = null;
        try {
            Preferences preference = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE); //Itt hozza létre a config.txt-t
            gson.toJson(preference,writer);
                    } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex){
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Itt kérjük le/adja vissza a tulajdonságokat a gsonből.
     *
     * @return vissza adja a preferences-t
     */
    public static Preferences getPreferences(){
        Gson gson = new Gson();
        Preferences preferences = new Preferences();
        try {
            preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preferences;
    }

    /**
     * Ez állítja át a beállítások fxml-ben a szöveg boxokban a text-eket.
     * Loggol amikor lefut.
     *
     * @param preference - preferences értékek.
     */
    public static void writePreferenceToFile(Preferences preference) {
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preference,writer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sikeres");
                alert.setHeaderText(null);
                alert.setContentText("Beállítások frissítve");
                alert.showAndWait();
            } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed");
                alert.setContentText("Nincsenek mentve a beállítások");
                alert.showAndWait();

        } finally {
            try {
                writer.close();
            } catch (IOException ex){
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
