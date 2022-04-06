package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Main;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class is the controller for the MainMenu fxml document.
 * FUTURE ENHANCEMENT - Visual information is lacking in this inventory management system, so I propose a future
 * enhancement could be to implement the functionality to display images associated with the parts and products. This
 * could help bridge the gap between hands-on part knowledge and usage of the system, allowing for individuals
 * less familiar with the part names and IDs to identify objects by sight.*/
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

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

    /** This method switches to the Add Parts screen.
     * @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddParts.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method switches to the Add Products screen.
     * @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProducts.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method deletes the selected part.*/
    @FXML
    void onActionDeleteParts(ActionEvent event) {
        if (!partsTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to delete the part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem())) {
                    partsTableView.setItems(Inventory.getAllParts());
                }

            }
        }
    }

    /** This method deletes the selected product.*/
    @FXML
    void onActionDeleteProducts(ActionEvent event) {
        if (!productsTableView.getSelectionModel().isEmpty()) {
            if (!productsTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please remove associated parts before attempting to delete the product.");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to delete the product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
                productsTableView.setItems(Inventory.getAllProducts());
            }

        }
    }

    /** This method switches to the Modify Parts screen.
     * @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {

        if (partsTableView.getSelectionModel().isEmpty()) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyParts.fxml"));
        loader.load();

        ModifyPartsController MPController = loader.getController();
        MPController.sendPart(partsTableView.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /** This method switches to the Modify Products screen.
     * @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {

        if (productsTableView.getSelectionModel().isEmpty()) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProducts.fxml"));
        loader.load();

        ModifyProductsController MPController = loader.getController();

        MPController.sendProduct(productsTableView.getSelectionModel().getSelectedItem());
        ModifyProductsController.tempAssocParts.clear();
        ModifyProductsController.tempAssocParts.addAll(productsTableView.getSelectionModel()
                .getSelectedItem().getAllAssociatedParts());

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method closes the program.*/
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }


    /** This method searches parts. On button click, the parts are filtered by name or ID.*/
    @FXML
    void onActionSearchParts(ActionEvent event) {

        String text = searchPartsTxt.getText();

        if (Main.isInteger(text)) {
            int partID = Integer.parseInt(text);
            filteredParts.clear();
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

    /** This method searches products. On button click, the products are filtered by name or ID.*/
    @FXML
    void onActionSearchProducts(ActionEvent event) {

        String text = searchProductsTxt.getText();

        if (Main.isInteger(text)) {
            int productID = Integer.parseInt(text);
            filteredProducts.clear();
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

    /** This method is run when the class is instantiated. Additionally, the tableview information is set and
     * displayed here.*/
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

