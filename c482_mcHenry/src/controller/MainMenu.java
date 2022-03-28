package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Double> partCostPerUnitCol;

    @FXML
    private TableColumn<Part, Integer> partInvLevelCol;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Product, Double> prodCostPerUnitCol;

    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, Integer> prodInvLevelCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddParts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProducts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDeleteParts(ActionEvent event) {

    }

    @FXML
    void onActionDeleteProducts(ActionEvent event) {

    }

    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyParts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProducts.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
        productsTableView.setItems(Inventory.getAllProducts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
