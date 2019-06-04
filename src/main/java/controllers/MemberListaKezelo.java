package controllers;

import database.AdatbazisKezelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberListaKezelo implements Initializable {

    private ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> emailCol;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }

    /**
     * Ezzel töltjük be az ablak táblázatába az információkat.
     */
    private void loadData() {
        list.clear();
        AdatbazisKezelo kez = new AdatbazisKezelo();
        kez.memB();

        ResultSet rs = kez.kezelo.execQuery(kez.qu);
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String id = rs.getString("id");
                String email = rs.getString("email");

                list.add(new Member(name, id, mobile, email));

            }
        } catch (SQLException ex) {
            Logger.getLogger(View_2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.setItems(list);
    }

    /**
     * Itt vannak inicializálva az adatok.
     */
    public static class Member {

        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;

        Member(String name, String id, String mobile, String email) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }

    }

}
