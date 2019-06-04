/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import controllers.MainKezelo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    /**
     * Itt indul az egész app.
     * Elsőnek a belepes.fxml fájlra ugrik.
     *
     * @param stage - stage
     * @throws Exception - hibakezelő
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/belepes.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Ablak megnyitás függvény.
     *
     * @param loc - melyik fxml fájlt.
     * @param title - mit írjon ki.
     */
    public static void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(Main.class.getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainKezelo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * A main ablakra való lépés függvényben.
     */
    public static void loadMain() {
        try {
            Parent parent = FXMLLoader.load(Main.class.getResource("/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Könyvtárkezelő");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainKezelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
