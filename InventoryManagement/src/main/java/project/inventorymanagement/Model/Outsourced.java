/** @author Alexandre Do */
package project.inventorymanagement.Model;



/** This class creates an Outsourced part object. */
public class Outsourced extends Part {
    private String companyName;

    /** This is the constructor method for Outsourced.
     It creates a new Outsourced part object using provided parameters.
     @param id The part's ID.
     @param name The part's Name.
     @param price The part's Price/Cost.
     @param stock  The part's Inventory.
     @param min The part's minimum Inventory.
     @param max The part's maximum Inventory.
     @param companyName The part's Company Name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method sets the Outsourced part's Company Name.
     It takes a string as a parameter and assigns it to the Outsourced part's Company Name.
     @param companyName The string to be set as the Outsourced part's Company Name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** This method gets the Outsourced part's Company Name.
     It returns the Outsourced part's Company Name.
     @return Returns the Outsourced part's Company Name.
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
