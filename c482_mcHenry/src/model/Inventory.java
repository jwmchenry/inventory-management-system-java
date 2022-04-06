package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is used to keep track of inventory.*/
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /** This method adds a part to the inventory.*/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** This method adds a product to the inventory.*/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** This method gets the part associated with an ID.*/
    public static Part lookupPart(int partId) {
        for (Part part : getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /** This method gets the product associated with an ID.*/
    public static Product lookupProduct(int productId) {

        for (Product product : getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** This method gets the part associated with a part name.*/
    public static ObservableList<Part> lookupPart(String partName) {
        filteredParts.clear();
        for (Part part : getAllParts()) {
            if (part.getName().contains(partName)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    /** This method gets the product associated with a product name.*/
    public static ObservableList<Product> lookupProduct(String productName) {
        filteredProducts.clear();
        for (Product product : getAllProducts()) {
            if (product.getName().contains(productName)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    /** This method updates the information for a part.*/
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** This method updates the information for a product.*/
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /** This method deletes a part from the inventory.*/
    public static boolean deletePart(Part selectedPart) {
        for (Part part : getAllParts()) {
            if (part.getId() == selectedPart.getId()) {
                getAllParts().remove(part);
                return true;
            }
        }
        return false;
    }

    /** This method deletes a product from the inventory.*/
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : getAllProducts()) {
            if (product.getId() == selectedProduct.getId()) {
                getAllProducts().remove(product);
                return true;
            }
        }
        return false;
    }

    /** This gets the whole list of parts.*/
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /** This gets the whole list of products.*/
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

}
