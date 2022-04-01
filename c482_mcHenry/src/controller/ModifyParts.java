package controller;

import javafx.collections.ObservableList;
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
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyParts implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField idText;

    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private TextField invText;

    @FXML
    private Label lblIDCompanyName;

    @FXML
    private Label lblMainParts;

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
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        ObservableList<Part> partList = Inventory.getAllParts();

        int id = Integer.parseInt(idText.getText());
        String name = nameTxt.getText();
        int stock = Integer.parseInt(invText.getText());
        double price = Double.parseDouble(priceCostTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());

        int updateIndex = -1;
        for (int i = 0; i < partList.size(); i++) {
            if (partList.get(i).getId() == id) {
                updateIndex = i;
            }
        }

        if (inHouseRBtn.isSelected()) {
            int machineId = Integer.parseInt(machineIDCompanyNameTxt.getText());
            Inventory.updatePart(updateIndex, new InHouse( id,  name,  price,  stock,  min,  max,  machineId));
        } else {
            String companyName = machineIDCompanyNameTxt.getText();
            Inventory.updatePart(updateIndex, new Outsourced( id,  name,  price,  stock,  min,  max, companyName));
        }

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void sendPart(Part part) {
        idText.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invText.setText(String.valueOf(part.getStock()));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
