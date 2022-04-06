package model;

/** This is a subclass for Parts. This class gets used when InHouse radio button is selected.*/
public class InHouse extends Part{

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Getter for Machine ID.*/
    public int getMachineId() {
        return machineId;
    }

    /** Setter for Machine ID.*/
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
