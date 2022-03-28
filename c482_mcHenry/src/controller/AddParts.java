package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddParts implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField idText;

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
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField pricePerCostTxt;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        int id = Main.uniqueIdCounter;
        Main.uniqueIdCounter++;

        String name = nameTxt.getText();
        int stock = Integer.parseInt(invTxt.getText());
        double price = Double.parseDouble(pricePerCostTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());

        if (inHouseRBtn.isSelected()) {
            int machineId = Integer.parseInt(machineIdCompanyNameTxt.getText());
            Inventory.addPart(new InHouse( id,  name,  price,  stock,  min,  max,  machineId));
        } else {
            String companyName = machineIdCompanyNameTxt.getText();
            Inventory.addPart( new Outsourced( id,  name,  price,  stock,  min,  max, companyName));
        }

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionInHouse(ActionEvent event) {
        lblIdCompanyName.setText("Machine ID");
    }

    @FXML
    void onActionOutsourced(ActionEvent event) {
        lblIdCompanyName.setText("Company Name");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
