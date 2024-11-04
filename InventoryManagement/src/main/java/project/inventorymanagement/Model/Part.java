/** @author Alexandre Do
 * Supplied class Part.java, updated by author to implement comparable for sorting when using ID recycling and for Associated Parts table display.
 */
package project.inventorymanagement.Model;




/** This class creates a Part object. */
public abstract class Part implements Comparable<Part> {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This the constructor for Part.
     @param id The part's ID.
     @param name The part's Name.
     @param stock The part's Inventory.
     @param price The part's Price/Cost.
     @param min The part's minimum Inventory.
     @param max The part's maximum Inventory.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /** This method compares a part to another part based on ID.
     This method enables comparison between parts by comparing their IDs using Integer's compare method.
     @param otherPart The part that will be compared to the calling part.
     @return Returns 1 if the calling part's ID is greater, -1 if the calling part's ID is lesser, and 0 if the two parts' IDs are the same.
     */
    @Override
    public int compareTo(Part otherPart) {
        int currentId = this.getId();
        int otherId = otherPart.getId();
        return Integer.compare(currentId, otherId);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}