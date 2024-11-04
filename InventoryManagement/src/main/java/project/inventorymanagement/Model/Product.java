/** @author Alexandre Do */
package project.inventorymanagement.Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates a Product object. */
public class Product implements Comparable<Product> {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** This is the constructor for the Product.
     @param id The product's ID.
     @param name The product's Name.
     @param stock The product's Inventory.
     @param price The product's Price.
     @param min The product's minimum Inventory.
     @param max The product's maximum Inventory.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This method compares a product to another product based on ID.
     This method enables comparison between products by comparing their ID.
     @param otherProduct The product that will be compared to the calling product.
     @return Returns 1 if the calling product's ID is greater, -1 if the calling product's ID is lesser, and 0 if the two products' IDs are the same.
     */
    @Override
    public int compareTo(Product otherProduct) {
        int currentId = this.getId();
        int otherId = otherProduct.getId();
        return Integer.compare(currentId, otherId);
    }

    /** This method sets the product's ID.
     @param id The value to be set as the product's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** This method sets the product's Name.
     @param name The string to be set as the product's Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** This method sets the product's Price.
     @param price The value to be set as the product's Price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This method sets the product's Inventory level.
     @param stock The value to be set as the product's Inventory level.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This method sets the product's minimum Inventory level.
     @param min The value to be set as the product's minimum Inventory level.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method sets the product's maximum Inventory level.
     @param max The value to be set as the product's maximum Inventory level.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method returns the product's ID.
     @return Returns the product's ID.
     */
    public int getId() {
        return this.id;
    }

    /** This method returns the product's Name.
     @return Returns the product's Name.
     */
    public String getName() {
        return this.name;
    }

    /** This method returns the product's Price.
     @return Returns the product's Price.
     */
    public double getPrice() {
        return this.price;
    }

    /** This method returns the product's Inventory level.
     @return Returns the product's Inventory level.
     */
    public int getStock() {
        return this.stock;
    }

    /** This method returns the product's minimum Inventory level.
     @return Returns the product's minimum Inventory level.
     */
    public int getMin() {
        return this.min;
    }

    /** This method returns the product's maximum Inventory level.
     @return Returns the product's maximum Inventory level.
     */
    public int getMax() {
        return this.max;
    }

    /** This method adds a part to the product's Associated Parts list.
     @param part The part to be added to the product's Associated Parts list.
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /** This method removes a part from the product's Associated Parts list.
     @param selectedAssociatedPart The part to be removed from the product's Associated Parts list.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    /** This method provides a list of the product's associated parts.
     @return Returns a list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

}
