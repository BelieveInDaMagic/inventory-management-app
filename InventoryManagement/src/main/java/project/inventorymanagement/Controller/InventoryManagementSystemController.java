/** @author Alexandre Do */
package project.inventorymanagement.Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import project.inventorymanagement.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class creates a controller for the Inventory Management System form. */
public class InventoryManagementSystemController implements Initializable {
    public TableView partsTable;
    public TableColumn partsIdColumn;
    public TableColumn partsNameColumn;
    public TableColumn partsInvColumn;
    public TableColumn partsCostColumn;
    public Button partsPaneDelete;
    public Button partsPaneModify;
    public Button partsPaneAdd;
    public TextField partsPaneSearch;
    public TableView productsTable;
    public TableColumn productsIdColumn;
    public TableColumn productsNameColumn;
    public TableColumn productsInvColumn;
    public TableColumn productsCostColumn;
    public Button productsPaneDelete;
    public Button productsPaneModify;
    public Button productsPaneAdd;
    public TextField productsPaneSearch;
    public Button systemExit;

    /** This static integer serves as an incrementing ID count for parts, guaranteeing unique IDs. */
    public static int partsIdCount = 1;

    /** This static integer serves as an incrementing ID count for products, guaranteeing unique IDs. */
    public static int productsIdCount = 1;

    /** This list holds the search results for the Parts pane's search. */
    public ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();

   /** This list holds the search results for the Products pane's search. */
    public ObservableList<Product> productsSearchResults = FXCollections.observableArrayList();

    /** This list holds IDs of removed parts for recycling. */
    public static ObservableList<Integer> partsIdSave = FXCollections.observableArrayList();

    /** This list holds IDs of removed products for recycling. */
    public static ObservableList<Integer> productsIdSave = FXCollections.observableArrayList();

    /** This is the method that executes when the Inventory Management form is initialized.
     It populates the Parts and Products tables with the relevant object data.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This the action event method for the Parts pane's Add button.
     The method redirects to the Add Part form.
     */
    public void toPartsAdd(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/AddPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 525, 510);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** This the action event method for the Products pane's Add button.
     The method redirects to the Add Product form.
     */
    public void toProductsAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/AddProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 882.5,625);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** This the action event method for the Parts pane's Modify button.
     The method first checks if a part is selected, displaying an error if no part is selected.
     If a part is selected, the method passes the selected part to the Modify Part Controller before redirecting to the Modify Part form.
     */
    public void toPartsModify(ActionEvent actionEvent) throws IOException{
        Part tempPart = (Part)partsTable.getSelectionModel().getSelectedItem();
        if (tempPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setContentText("No part selected.");
            alert.showAndWait();
        }
        else {
            ModifyPartController.passPart(tempPart);
            Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 525, 510);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This the action event method for the Products pane's Modify button.
     The method first checks if a product is selected, displaying an error if no product is selected.
     If a product is selected, the method passes the select4ed product to the Modify Product controller before redirecting to the Modify Product form.
     */
    public void toProductsModify(ActionEvent actionEvent) throws IOException {
        Product tempProduct = (Product)productsTable.getSelectionModel().getSelectedItem();
        if (tempProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Error");
            alert.setContentText("No product selected.");
            alert.showAndWait();
            return;
        }
        else {
            ModifyProductController.passProduct(tempProduct);
            Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 882.5, 625);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This the action event method for the Parts pane's Delete button.
     The method first checks if a part is selected, displaying an error if no part is selected.
     The method then confirms the user's delete action.
     If the action is confirmed, the method deletes the part from Inventory, saving its ID for recycling or displaying an error message if deletion is unsuccessful.
     The method also deletes the part from any product's associated parts, clears search and repopulates the parts table with all parts.
     */
    public void partsDeleteClick(ActionEvent actionEvent) {
        Part tempPart = (Part)partsTable.getSelectionModel().getSelectedItem();
        if (tempPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Delete unsuccessful, no part selected.");
            alert.showAndWait();
        }
        else {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int tempId = tempPart.getId();
                boolean success = Inventory.deletePart(tempPart);
                if (!success) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete Error");
                    alert.setContentText("Delete unsuccessful, part not found.");
                    alert.showAndWait();
                }
                else {
                    partsIdSave.add(tempId);
                    Collections.sort(partsIdSave);
                    for (Product tempProduct : Inventory.getAllProducts()) {
                        if (tempProduct.getAllAssociatedParts().contains(tempPart)) {
                            tempProduct.deleteAssociatedPart(tempPart);
                        }
                    }
                    partsSearchResults.clear();
                    partsPaneSearch.setText("");
                    partsTable.setItems(Inventory.getAllParts());
                    partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                    partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    partsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    partsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

                }
            }
            else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                return;
            }
        }
    }

    /** This the action event method for the Products pane's Delete button.
     The method first checks if a product is selected, displaying an error if no product is selected.
     The method then checks whether the selected Product still has any associated parts, displaying an error if there are still associated parts.
     The method then confirms the user's delete action.
     If the action is confirmed, the method deletes the product from Inventory, saving its ID for recycling or displaying an error message if deletion is unsuccessful.
     The method then clears search and repopulates the products table with all products.
     */
    public void productsDeleteClick(ActionEvent actionEvent) {
        Product selectedProduct = (Product)productsTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("No product selected.");
            alert.showAndWait();
            return;
        }
        else {
            if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Must remove all associated parts before deleting product.");
                alert.showAndWait();
                return;
            }
            else {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int tempId = selectedProduct.getId();
                    boolean success = Inventory.deleteProduct(selectedProduct);
                    if (!success) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Delete Error");
                        alert.setContentText("Delete unsuccessful, product not found.");
                        alert.showAndWait();
                        return;
                    }
                    else {
                        productsIdSave.add(tempId);
                        Collections.sort(productsIdSave);
                        productsSearchResults.clear();
                        productsPaneSearch.setText("");
                        productsTable.setItems(Inventory.getAllProducts());
                        productsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                        productsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                        productsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

                    }
                }
                else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                    return;
                }
            }
        }
    }

    /** This is the action event method for the Exit button.
     The method exits the program with a status code of 0.
     */
    public void onExitClick(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** This is a helper method designed to check whether a string's text is an integer.
     The method takes a string and tries to parse it as an integer. If it is successful, the method returns true.
     If an error occurs, meaning the string is not an integer, the error is caught and the method returns false.
     @param string The string to be checked.
     @return Returns true if string is an integer, returns false if string is not an integer.
     */
    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch(NumberFormatException exception) {
            return false;
        }
    }

    /** This is a helper method designed to check whether a string's text is a double.
     The method takes a string and tries to parse it as a double. If it is successful, the method returns true.
     If an error occurs, meaning the string is not a double, the error is caught and the method returns false.
     @param string The string to be checked.
     @return Returns true if string is a double, returns false if string is not a double.
     */
    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch(NumberFormatException exception) {
            return false;
        }
    }

    /** This is the action event method that handles a character being typed into or deleted from the Products pane's search bar.
     The method first clears any previous search results from the search results list.
     If the search bar is not empty, then a search is carried out. The method adds to the search results list every product with a name containing a partial or full match with the search bar's text.
     The method then adds to the search results list every product with an ID containing a partial or full match with the search bar's text. not including duplicates already in the search results list.
     The method then populates the Products table with the search results, displaying an error if no matching product is found.
     If the search bar is empty, the search results list is cleared and the table is repopulated with all products.
     */
    public void productSearchTyped(KeyEvent keyEvent) {
        productsSearchResults.clear();
        if (!productsPaneSearch.getText().trim().isEmpty()) {
            productsSearchResults.addAll(Inventory.lookupProduct(productsPaneSearch.getText()));
            for (Product product : Inventory.getAllProducts()) {
                if (String.valueOf(product.getId()).trim().toLowerCase().contains(productsPaneSearch.getText().trim().toLowerCase()) && !productsSearchResults.contains(product)) {
                    productsSearchResults.add(product);
                }
            }
            productsTable.setItems(productsSearchResults);
            productsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            if (productsSearchResults.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setContentText("No matching product found.");
                alert.showAndWait();
                return;
            }
        }
        else if (productsPaneSearch.getText().trim().isEmpty()) {
            productsSearchResults.clear();
            productsTable.setItems(Inventory.getAllProducts());
            productsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /** This is the action event method that handles a character being typed into or deleted from the Parts pane's search bar.
     The method first clears any previous search results from the search results list.
     If the search bar is not empty, then a search is carried out. The method adds to the search results list every part with a name containing a partial or full match with the search bar's text.
     The method then adds to the search results list every part with an ID containing a partial or full match with the search bar's text. not including duplicates already in the search results list.
     The method then populates the Parts table with the search results, displaying an error if no matching part is found.
     If the search bar is empty, the search results list is cleared and the table is repopulated with all parts.
     */
    public void partsSearchTyped(KeyEvent keyEvent) {
        partsSearchResults.clear();
        if (!partsPaneSearch.getText().trim().isEmpty()) {
            partsSearchResults.addAll(Inventory.lookupPart(partsPaneSearch.getText()));
            for (Part part : Inventory.getAllParts()) {
                if (String.valueOf(part.getId()).trim().toLowerCase().contains(partsPaneSearch.getText().trim().toLowerCase()) && !partsSearchResults.contains(part)) {
                    partsSearchResults.add(part);
                }
            }
            partsTable.setItems(partsSearchResults);
            partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            if (partsSearchResults.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setContentText("No matching part found.");
                alert.showAndWait();
                return;
            }
        }
        else if (partsPaneSearch.getText().trim().isEmpty()) {
            partsSearchResults.clear();
            partsTable.setItems(Inventory.getAllParts());
            partsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }
}


