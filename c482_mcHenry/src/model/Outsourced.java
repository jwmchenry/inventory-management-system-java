package model;

/** This is a subclass for Parts. This class gets used when Outsourced radio button is selected.*/
public class Outsourced extends Part{

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This is the getter for Company Name.*/
    public String getCompanyName() {
        return companyName;
    }

    /** This is the setter for Company Name.*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
