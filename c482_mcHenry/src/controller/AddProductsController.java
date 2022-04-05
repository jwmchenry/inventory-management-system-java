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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProductsController implements Initializable {

    Stage stage;
    Parent scene;
    private static ObservableList<Part> tempAssocParts = FXCollections.observableArrayList();
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> allPartIdCol;

    @FXML
    private TableColumn<Part, Integer> allPartInvCol;

    @FXML
    private TableColumn<Part, String> allPartNameCol;

    @FXML
    private TableColumn<Part, Double> allPartPriceCol;

    @FXML
    private TableView<Part> allPartsTableView;

    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;

    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;

    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;

    @FXML
    private TableView<Part> associatedPartsTableView;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField searchPartsTxt;

    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        boolean partAdded = false;

        for (Part part : tempAssocParts) {
            if (part.getId() == allPartsTableView.getSelectionModel().getSelectedItem().getId()) {
                partAdded = true;
            }
        }

        if (!partAdded && !allPartsTableView.getSelectionModel().isEmpty()) {
            tempAssocParts.add(allPartsTableView.getSelectionModel().getSelectedItem());
        }

        associatedPartsTableView.setItems(tempAssocParts);

    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    //Here I ran into a runtime error where I tried to modify tempAssocParts while it was being iterated over.
    //To fix it, I created a clone of the list and iterated over the clone and removed the item from the
    //actual list. However, my initial effort at creating a clone failed because I just used the assignment
    //operator which caused the "clone" to point back to the original contents. Instead, I created a new
    //object and filled it with the contents of the original list.

    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        if (associatedPartsTableView.getSelectionModel().isEmpty()) {
            return;
        }

        tempAssocParts.removeIf(part -> part.getId() ==
                associatedPartsTableView.getSelectionModel().getSelectedItem().getId());

        associatedPartsTableView.setItems(tempAssocParts);
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int id = Main.uniqueIdCounter;
        Main.uniqueIdCounter++;

        String name = nameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int min = Integer.parseInt(minTxt.getText());

        Inventory.getAllProducts().add(new Product(id, name, price, stock, max, min));

        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == id) {
                product.getAllAssociatedParts().addAll(tempAssocParts);
            }
        }

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSearchParts(ActionEvent event) {

        String text = searchPartsTxt.getText();

        if (Main.isInteger(text)) {
            int partID = Integer.parseInt(text);
            filteredParts.clear();
            filteredParts.add(Inventory.lookupPart(partID));
            allPartsTableView.setItems(filteredParts);
        } else if (text.isEmpty()) {
            allPartsTableView.setItems(Inventory.getAllParts());
        } else if (!Main.isInteger(text)) {
            allPartsTableView.setItems(Inventory.lookupPart(text));
        } else {
            System.out.println("Error.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tempAssocParts.clear();

        allPartsTableView.setItems(Inventory.getAllParts());
        associatedPartsTableView.setItems(tempAssocParts);

        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
