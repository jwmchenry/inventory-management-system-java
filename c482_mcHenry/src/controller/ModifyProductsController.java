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
import java.util.ListIterator;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductsController implements Initializable {

    Stage stage;
    Parent scene;
    public static ObservableList<Part> tempAssocParts = FXCollections.observableArrayList();
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Part, Integer> allPartIdCol;

    @FXML
    private TableColumn<Part, Integer> allPartsInvCol;

    @FXML
    private TableColumn<Part, String> allPartNameCol;

    @FXML
    private TableColumn<Part, Double> allPartsPriceCol;

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
    private TableView<Part> assocPartsTableView;

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
    void onActionAddPart(ActionEvent event) {
        boolean partAdded = false;

        for (Part part : tempAssocParts) {
            if (part.getId() == allPartsTableView.getSelectionModel().getSelectedItem().getId()) {
                partAdded = true;
            }
        }

        if (!partAdded && !allPartsTableView.getSelectionModel().isEmpty()) {
            tempAssocParts.add(allPartsTableView.getSelectionModel().getSelectedItem());
        }

        assocPartsTableView.setItems(tempAssocParts);
    }




    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        if (assocPartsTableView.getSelectionModel().isEmpty()) {
            return;
        }

        tempAssocParts.removeIf(part -> part.getId() ==
                assocPartsTableView.getSelectionModel().getSelectedItem().getId());

        assocPartsTableView.setItems(tempAssocParts);
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int id = Integer.parseInt(idTxt.getText());
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        int min = Integer.parseInt(minTxt.getText());

        int updateIndex = -1;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (id == Inventory.getAllProducts().get(i).getId()) {
                updateIndex = i;
            }
        }

        Inventory.updateProduct(updateIndex, new Product(id, name, price, stock, max, min));

        for (Part part : tempAssocParts) {
            Inventory.getAllProducts().get(updateIndex).addAssociatedPart(part);
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

    public void sendProduct(Product product) {
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        invTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));

        assocPartsTableView.setItems(product.getAllAssociatedParts());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartsTableView.setItems(Inventory.getAllParts());

        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
