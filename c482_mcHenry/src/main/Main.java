package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root, 1000, 400));
        stage.show();
    }

    public static void main(String[] args) {

        InHouse part1 = new InHouse(4,"wrench", 99.99, 3, 0, 5, 1);
        Product product1 = new Product(1, "vacuum", 299.99, 7, 0, 10);

        Inventory.addPart(part1);
        Inventory.addProduct(product1);

        launch(args);
    }

    public static int uniqueIdCounter = 1;

}
