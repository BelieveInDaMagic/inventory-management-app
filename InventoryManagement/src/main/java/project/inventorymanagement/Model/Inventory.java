/** @author Alexandre Do */
package project.inventorymanagement.Model;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates an Inventory object. */
public class Inventory {

    /** List of All Parts in Inventory. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** List of All Products in Inventory. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method adds a part to the Inventory.
     It takes a part parameter and adds it to the allParts list.
     @param newPart The part to be added.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** This method adds a product to the Inventory.
     It takes a product parameter and adds it to the allProducts list.
     @param newProduct The product to be added.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** This method looks up a part based on its ID.
     The method takes an integer as a parameter, and iterates through allParts looking for an ID match, returning null if no match is found.
     @param partId The Part ID to be looked up.
     @return Returns a part if there is a match, returns null if no match is found.
     */
    public static Part lookupPart(int partId) {
        for (Part tempPart : allParts) {
            if (partId == tempPart.getId()) {
                return tempPart;
            }
        }
        return null;
    }

    /** This method looks up a product based on its ID.
     The method takes an integer as a parameter, and iterates through allProducts looking for an ID match, returning null if no match is found.
     @param productId The Product ID to be looked up.
     @return Returns a product if there is a match, returns null if no match is found.
     */
    public static Product lookupProduct(int productId) {
        for (Product tempProduct : allProducts) {
            if (productId == tempProduct.getId()) {
                return tempProduct;
            }
        }
        return null;
    }


    /** This method looks up a part based on its Name.
     The method takes a string as a parameter, and iterates through allParts adding any matches to a results list and returning the list.
     @param partName The Part Name to be looked up.
     @return Returns a list of matching parts (list is empty if no matches).
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        for (Part tempPart : allParts) {
            if (tempPart.getName().trim().toLowerCase().contains(partName.trim().toLowerCase())) {
                partSearchResults.add(tempPart);
            }
        }
        return partSearchResults;
    }

    /** This method looks up a product based on its Name.
     The method takes a string as a parameter, and iterates through allProducts adding any matches to a results list and returning the list.
     @param productName The Product Name to be looked up.
     @return Returns a list of matching products (list is empty if no matches).
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productSearchResults = FXCollections.observableArrayList();
        for (Product tempProduct : allProducts) {
            if (tempProduct.getName().trim().toLowerCase().contains(productName.trim().toLowerCase())) {
                productSearchResults.add(tempProduct);
            }
        }
        return productSearchResults;
    }

    /** This method updates a part in the Inventory.
     The method updates the part at the index in allParts with the part passed in as a parameter.
     @param index The index of the part in allParts to be updated.
     @param selectedPart The part to replace the current part at the index in allParts.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** This method updates a product in the Inventory.
     The method updates the product at the index in allProducts with the product passed in as a parameter.
     @param index The index of the product in allProducts to be updated.
     @param newProduct The product to replace the current product at the index in allProducts.
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /** This method removes a part from the Inventory.
     The method takes a part as a parameter and removes the part from allParts.
     @param selectedPart The part to be removed.
     @return Returns true if removal is successful, returns false if removal fails.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /** This method removes a product from the Inventory.
     The method takes a product as a parameter and removes the product from allProducts.
     @param selectedProduct The product to be removed.
     @return Returns true if removal is successful, returns false if removal fails.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /** This method provides a list of all Parts in Inventory.
     @return Returns a list of all Parts in Inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** This method provides a list of all Products in Inventory.
     @return Returns a list of all Products in Inventory.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
