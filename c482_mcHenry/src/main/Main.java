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

/** This is the main class for the program.*/
public class Main extends Application {

    public static int uniqueIdCounter = 1;

    /** This method is where the UI elements are loaded.*/
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1000, 400));
        stage.show();
    }

    /** This is the entry point into the program.*/
    public static void main(String[] args) {
        launch(args);

    }

    /** This method checks if a string can be an integer. Takes a string as an input and returns bool.
     * LOGICAL ERROR - In this isInteger method, I made a logical error by not accounting for a string length of 0,
     * which would skip the for loop and immediately return true, which would consider an empty string an integer. Since
     * an empty string would throw an exception, I fixed this by affixing an if statement at the beginning that checks
     * for an empty string (and returns false if so).
     * @param string This is the input to check if it is an integer.*/
    public static boolean isInteger(String string) {
        if (string.isEmpty()) {
            return false;
        }

        int strLength = string.length();

        for (int i = 0; i <= strLength; i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
