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

/** This is the controller class for the AddProducts fxml document.*/
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

    /** This method adds an associated part. On button click, the highlighted part is added to the associated parts
     * list and tableview.*/
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

    /**This method returns to main menu. On button click the program will move from the Add
     Products window back to the main menu without saving.
     @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method removes an associated part. On button click, the highlighted part is removed from the associated
     *  part list and tableview.*/
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        if (associatedPartsTableView.getSelectionModel().isEmpty()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you want dissociate the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            tempAssocParts.removeIf(part -> part.getId() ==
                    associatedPartsTableView.getSelectionModel().getSelectedItem().getId());
            associatedPartsTableView.setItems(tempAssocParts);
        }

    }

    /**This method adds a product. On button click the program will save the user input as a new product and
     * return to the main menu.
     * @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            int id = Main.uniqueIdCounter;
            Main.uniqueIdCounter++;

            String name = nameTxt.getText();
            double price = Double.parseDouble(priceTxt.getText());
            int stock = Integer.parseInt(invTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum should be less than or equal to maximum, " +
                        "and inventory stock should be between them.");
                alert.showAndWait();
                return;
            }

            Inventory.addProduct(new Product(id, name, price, stock, max, min));

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
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the appropriate form of data.");
            alert.showAndWait();
        }

    }

    /** This method searches through parts. On button click, the parts will be filtered by name which can include
     *  partials, or by part ID.*/
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

    /** This method is run when the class is instantiated. Additionally, the tableview information is set and
     * displayed here.*/
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
