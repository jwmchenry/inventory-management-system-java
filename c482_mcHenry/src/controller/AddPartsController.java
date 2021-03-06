package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** This is the controller class for the AddParts fxml document.*/
public class AddPartsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private TextField invTxt;

    @FXML
    private Label lblIdCompanyName;

    @FXML
    private TextField machineIdCompanyNameTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField pricePerCostTxt;

    /**This method returns to main menu. On button click the program will move from the Add
     Parts window back to the main menu without saving.
     @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method adds a part. On button click the program will save the user input as a new part and
     * return to the main menu.
     * @param event This parameter is used to get the window where the button click is occurring.*/
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try {
            int id = Main.uniqueIdCounter;
            Main.uniqueIdCounter++;

            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(pricePerCostTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());

            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum should be less than or equal to maximum, " +
                        "and inventory stock should be between them.");
                alert.showAndWait();
                return;
            }

            if (inHouseRBtn.isSelected()) {
                int machineId = Integer.parseInt(machineIdCompanyNameTxt.getText());
                Inventory.addPart(new InHouse( id,  name,  price,  stock,  min,  max,  machineId));
            } else {
                String companyName = machineIdCompanyNameTxt.getText();
                Inventory.addPart( new Outsourced( id,  name,  price,  stock,  min,  max, companyName));
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

    /** This method changes the label to say Machine ID.*/
    @FXML
    void onActionInHouse(ActionEvent event) {
        lblIdCompanyName.setText("Machine ID");
    }

    /** This method changes the label to say Company Name.*/
    @FXML
    void onActionOutsourced(ActionEvent event) {
        lblIdCompanyName.setText("Company Name");
    }

    /** This method is run when the class is instantiated.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
