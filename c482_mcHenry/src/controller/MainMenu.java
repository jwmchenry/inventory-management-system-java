package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Inventory;
import model.Part;
import model.Product;

import java.awt.event.KeyEvent;
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
    private TextField searchPartsTxt;

    @FXML
    private TextField searchProductsTxt;

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


    //This is a button click event for searching, it takes the text field and checks if the entered string can be
    //converted to an integer. If so, it does that and searches by partID. If blank, shows whole list. If string,
    //searches by part name.
    @FXML
    void onActionSearchParts(ActionEvent event) {
        String text = searchPartsTxt.getText();
        if (Main.isInteger(text)) {
            int partID = Integer.parseInt(text);
            ObservableList<Part> filteredParts = FXCollections.observableArrayList();
            filteredParts.add(Inventory.lookupPart(partID));
            partsTableView.setItems(filteredParts);
        } else if (text.isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        } else if (!Main.isInteger(text)) {
            partsTableView.setItems(Inventory.lookupPart(text));
        } else {
            System.out.println("Error.");
        }
    }

    @FXML
    void onActionSearchProducts(ActionEvent event) {
        String text = searchProductsTxt.getText();
        if (Main.isInteger(text)) {
            int productID = Integer.parseInt(text);
            ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
            filteredProducts.add(Inventory.lookupProduct(productID));
            productsTableView.setItems(filteredProducts);
        } else if (text.isEmpty()) {
            productsTableView.setItems(Inventory.getAllProducts());
        } else if (!Main.isInteger(text)) {
            productsTableView.setItems(Inventory.lookupProduct(text));
        } else {
            System.out.println("Error.");
        }
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
