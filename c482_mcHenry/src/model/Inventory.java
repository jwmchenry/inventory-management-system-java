package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId) {

        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        filteredParts.clear();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(partName)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        filteredProducts.clear();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().contains(productName)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    public static void updatePart(int index, Part selectedPart) {

    }

    public static void updateProduct(int index, Product newProduct) {

    }

    public static boolean deletePart(Part selectedPart) {

        return false;
    }

    public static boolean deleteProduct(Product selectedProduct) {

        return false;
    }

    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

}
