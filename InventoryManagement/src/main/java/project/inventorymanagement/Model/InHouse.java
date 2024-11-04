/** @author Alexandre Do */
package project.inventorymanagement.Model;


/** This class creates an In-House part object. */
public class InHouse extends Part {

    private int machineId;

    /** This is the constructor method for InHouse.
     It creates a new InHouse part object using provided parameters.
     @param id The part's ID.
     @param name The part's Name.
     @param price The part's Price/Cost.
     @param stock  The part's Inventory.
     @param min The part's minimum Inventory.
     @param max The part's maximum Inventory.
     @param machineId  The part's Machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** This method sets the In-House part's Machine ID.
     It takes an integer as a parameter and assigns it to the In-House part's Machine ID.
     @param machineId The integer to be set as the In-House part's Machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** This method gets the In-House part's Machine ID.
     It returns the In-House part's Machine ID.
     @return Returns the In-House part's Machine ID.
     */
    public int getMachineId() {
        return this.machineId;
    }
}
