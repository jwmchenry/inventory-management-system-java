package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is where the products are created.*/
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getter for ID.*/
    public int getId() {
        return id;
    }

    /** Setter for ID.*/
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for name.*/
    public String getName() {
        return name;
    }

    /** Setter for name.*/
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for price.*/
    public double getPrice() {
        return price;
    }

    /** Setter for price.*/
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter for inventory stock.*/
    public int getStock() {
        return stock;
    }

    /** Setter for inventory stock.*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter for minimum.*/
    public int getMin() {
        return min;
    }

    /** Setter for minimum.*/
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter for maximum.*/
    public int getMax() {
        return max;
    }

    /** Setter for maximum.*/
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds the selected part to the associated parts list.*/
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** This method deletes the selected part from the associated parts list.*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        boolean wasDeleted = false;

        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(i);
                wasDeleted = true;
            }
        }

        return wasDeleted;
    }

    /** This method returns all of the associated parts for the product.*/
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}
