package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;

import java.util.Objects;


public class Main extends Application {

    public static int uniqueIdCounter = 1;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setTitle("First Screen");
        stage.setScene(new Scene(root, 1000, 400));
        stage.show();
    }

    public static void main(String[] args) {

        InHouse part1 = new InHouse(321,"wrench", 99.99, 3, 0, 5, 1);
        InHouse part2 = new InHouse(322,"spanner", 299.99, 4, 0, 5, 1);
        InHouse part3 = new InHouse(333,"corkscrew", 19.99, 5, 0, 5, 1);
        InHouse part4 = new InHouse(344,"testitem", 39.99, 123, 0, 5, 1);
        InHouse part5 = new InHouse(355,"kit", 20.99, 45, 0, 5, 1);
        Product product1 = new Product(565757, "vacuum", 299.99, 7, 0, 10);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addProduct(product1);

        launch(args);
    }

    //In this isInteger method, I made a logical error by not accounting for a string length of 0, which would
    //skip the for loop and immediately return true, which would consider an empty string an integer. I fixed this by
    //affixing an if statement at the beginning that checks for an empty string (and returns false if so).

    public static boolean isInteger(String string) {
        if (string.isEmpty()) {
            return false;
        }

        int strLength = string.length();

        for (int i = 0; i < strLength; i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
