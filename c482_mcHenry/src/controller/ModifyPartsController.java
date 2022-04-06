package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyPartsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField idTxt;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private TextField invTxt;

    @FXML
    private Label lblIDCompanyName;

    @FXML
    private TextField machineIDCompanyNameTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField priceCostTxt;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try {
            ObservableList<Part> partList = Inventory.getAllParts();

            int id = Integer.parseInt(idTxt.getText());
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceCostTxt.getText());
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

            int updateIndex = -1;
            for (int i = 0; i < partList.size(); i++) {
                if (partList.get(i).getId() == id) {
                    updateIndex = i;
                }
            }

            if (inHouseRBtn.isSelected()) {
                int machineId = Integer.parseInt(machineIDCompanyNameTxt.getText());
                Inventory.updatePart(updateIndex, new InHouse( id,  name,  price,  stock,  min,  max,  machineId));
                for (Product product : Inventory.getAllProducts()) {
                    ObservableList<Part> allAssociatedParts = product.getAllAssociatedParts();
                    for (int i = 0; i < allAssociatedParts.size(); i++) {
                        Part part = allAssociatedParts.get(i);
                        if (part.getId() == id) {
                            allAssociatedParts.set(i, new InHouse( id,  name,  price,  stock,  min,  max,  machineId));
                        }
                    }
                }
            } else {
                String companyName = machineIDCompanyNameTxt.getText();
                Inventory.updatePart(updateIndex, new Outsourced( id,  name,  price,  stock,  min,  max, companyName));
                for (Product product : Inventory.getAllProducts()) {
                    ObservableList<Part> allAssociatedParts = product.getAllAssociatedParts();
                    for (int i = 0; i < allAssociatedParts.size(); i++) {
                        Part part = allAssociatedParts.get(i);
                        if (part.getId() == id) {
                            allAssociatedParts.set(i, new Outsourced( id,  name,  price,  stock,  min,  max,  companyName));
                        }
                    }
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

    public void sendPart(Part part) {
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(String.valueOf(part.getStock()));
        priceCostTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse) {
            lblIDCompanyName.setText("MachineID");
            machineIDCompanyNameTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
            inHouseRBtn.setSelected(true);
        } else if (part instanceof Outsourced) {
            lblIDCompanyName.setText("Company Name");
            machineIDCompanyNameTxt.setText(((Outsourced) part).getCompanyName());
            outsourcedRBtn.setSelected(true);
        }
    }

    @FXML
    void onActionInHouse(ActionEvent event) {
        lblIDCompanyName.setText("Machine ID");
    }

    @FXML
    void onActionOutsourced(ActionEvent event) {
        lblIDCompanyName.setText("Company Name");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

