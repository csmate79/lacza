/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.AdatbazisKezelo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author csicsek
 */
public class View_2Controller implements Initializable {

    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;

    private Boolean isInEditMode = Boolean.FALSE;

    private AdatbazisKezelo adatbaziskezelo;

    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adatbaziskezelo = AdatbazisKezelo.getInstance();

        AdatbazisKezelo.checkData();
    }

    /**
     * Ez a Könyv hozzáadás gombja.
     * Itt adjuk hozza a könyvek adatait az adatbázishoz.
     * Ha nem töltjük ki a mezőket errort dob ki.
     *
     * @param event - gombhoz
     */
    @FXML
    private void addBook(javafx.event.ActionEvent event){
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();

        if(bookID.isEmpty() || bookPublisher.isEmpty() || bookName.isEmpty() || bookAuthor.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Kérlek tölts ki minden mezőt.");
            alert.showAndWait();
            return;
        }
/*        stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "  id varchar(200) primary key,\n"
                        + "  title varchar(200),\n"
                        + "  author varchar(200),\n"
                        + "  publisher varchar(200),\n"
                        + "  isAvail boolean default true"
                        + " )");

*/      //Itt adjuk hozzá az adatbázishoz az adatokat.
        String qu = "INSERT INTO BOOK VALUES (" +
                "'" + bookID + "',"+
                "'" + bookName + "',"+
                "'" + bookAuthor + "'," +
                "'" + bookPublisher + "'," +
                "" + "true" + "" +
                ")";
        System.out.println(qu);
        if (adatbaziskezelo.execAction(qu)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Sikeres");
            alert.showAndWait();
            Logger.getLogger(View_2Controller.class.getName()).log(Level.INFO, "Sikerült a könyv felvétele");
        } else  { //error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nem sikerült");
            alert.showAndWait();
            Logger.getLogger(View_2Controller.class.getName()).log(Level.INFO, "Nem sikerült");
        }
    }

    /**
     * A bezárás gomb.
     *
     * @param event - gombhoz
     */
    @FXML
    private void cancel(javafx.event.ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}

