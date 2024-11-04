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
import project.inventorymanagement.Model.Inventory;
import project.inventorymanagement.Model.Part;
import project.inventorymanagement.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import static project.inventorymanagement.Controller.InventoryManagementSystemController.*;
import static project.inventorymanagement.Controller.InventoryManagementSystemController.isInt;

/** This class creates a controller for the Add Product form. */
public class AddProductController implements Initializable {

    public TableView addProductPartsTable;
    public TableView addProductAssociatedPartsTable;
    public TableColumn addProductTopIDColumn;
    public TableColumn addProductTopNameColumn;
    public TableColumn addProductTopInvColumn;
    public TableColumn addProductTopCostColumn;
    public TextField addProductSearch;
    public TableColumn addProductBottomIDColumn;
    public TableColumn addProductBottomNameColumn;
    public TableColumn addProductBottomInvColumn;
    public TableColumn addProductBottomCostColumn;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductInv;
    public TextField addProductName;
    public TextField addProductMin;

    /** This list holds the search results for the parts search. */
    public ObservableList<Part> addProductSearchResults = FXCollections.observableArrayList();

    /** This list holds the parts to be associated with the product. */
    public ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    /** This is the method that executes when the Add Product form is initialized.
     It populates the Parts table with all parts and the Associated Parts table with the initially empty associated parts list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductPartsTable.setItems(Inventory.getAllParts());
        addProductTopIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductTopNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductTopInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductTopCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductAssociatedPartsTable.setItems(tempAssociatedParts);
        addProductBottomIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductBottomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductBottomInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductBottomCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This is the action event method for the Cancel button.
     It clears the search results and associated parts lists, then redirects to the Inventory Management System form.
     */
    public void onAddProductCancelClick(ActionEvent actionEvent) throws IOException {
        addProductSearchResults.clear();
        tempAssociatedParts.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,850,405.5);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the action event method for the Save button.
     The method first checks for any data input errors (including min <= inv <= max and min < max), displaying an error message if any is found.
     If there are no errors, a new product is created.
     If no ID is available for recycling, the method assigns the product a unique ID using productsIdCount and updates the products ID counter.
     If an ID is available for recycling, the method assigns the product the lowest available recycled ID, removes the ID from the recycled ID list and sorts the list of all products for display.
     If the list of parts to be associated is not empty, the method adds the parts to the product's Associated Parts list.
     The method adds the product to Inventory then redirects to the Inventory Management System form.
     */
    public void onAddProductSaveClick(ActionEvent actionEvent) throws IOException {
        if (addProductName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Name field is empty.");
            alert.showAndWait();
            return;
        }
        if (addProductInv.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Inventory field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(addProductInv.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Inventory field must be a number.");
            alert.showAndWait();
            return;
        }
        if (addProductPrice.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Price field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isDouble(addProductPrice.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Price field must be a number.");
            alert.showAndWait();
            return;
        }
        if (addProductMax.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Max field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(addProductMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Max field must be a number.");
            alert.showAndWait();
            return;
        }
        if (addProductMin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Min field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(addProductMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Min field must be a number.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(addProductMin.getText()) >= Integer.parseInt(addProductMax.getText()) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Min field must be less than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(addProductInv.getText()) > Integer.parseInt(addProductMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Inventory field cannot be greater than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(addProductInv.getText()) < Integer.parseInt(addProductMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Add Error");
            alert.setContentText("Inventory field cannot be less than Min field.");
            alert.showAndWait();
            return;
        }
        if (productsIdSave.isEmpty()) {
            Product product = new Product(productsIdCount, addProductName.getText(), Double.parseDouble(addProductPrice.getText()), Integer.parseInt(addProductInv.getText()), Integer.parseInt(addProductMin.getText()), Integer.parseInt(addProductMax.getText()));
            if (!tempAssociatedParts.isEmpty()) {
                for (Part associatedPart : tempAssociatedParts) {
                    product.addAssociatedPart(associatedPart);
                }
            }
            Inventory.addProduct(product);
            productsIdCount++;
        }
        else {
            Product product = new Product(productsIdSave.get(0), addProductName.getText(), Double.parseDouble(addProductPrice.getText()), Integer.parseInt(addProductInv.getText()), Integer.parseInt(addProductMin.getText()), Integer.parseInt(addProductMax.getText()));
            if (!tempAssociatedParts.isEmpty()) {
                for (Part associatedPart : tempAssociatedParts) {
                    product.addAssociatedPart(associatedPart);
                }
            }
            Inventory.addProduct(product);
            productsIdSave.remove(0);
            Collections.sort(Inventory.getAllProducts());
        }
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,850,405.5);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the action event method that handles a character being typed into or deleted from the parts search bar.
     The method first clears any previous search results from the search results list.
     If the search bar is not empty, then a search is carried out. The method adds to the search results list every part with a name containing a partial or full match with the search bar's text.
     The method then adds to the search results list every part with an ID containing a partial or full match with the search bar's text. not including duplicates already in the search results list.
     The method then populates the Parts table with the search results, displaying an error if no matching part is found.
     If the search bar is empty, the search results list is cleared and the table is repopulated with all parts.
     */
    public void onAddProductSearchTyped(KeyEvent keyEvent) {
        addProductSearchResults.clear();
        if (!addProductSearch.getText().trim().isEmpty()) {
            addProductSearchResults.addAll(Inventory.lookupPart(addProductSearch.getText()));
            for (Part part : Inventory.getAllParts()) {
                if (String.valueOf(part.getId()).trim().toLowerCase().contains(addProductSearch.getText().trim().toLowerCase()) && !addProductSearchResults.contains(part)) {
                    addProductSearchResults.add(part);
                }
            }
            addProductPartsTable.setItems(addProductSearchResults);
            addProductTopIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            addProductTopNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            addProductTopInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            addProductTopCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            if (addProductSearchResults.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setContentText("No matching part found.");
                alert.showAndWait();
                return;
            }
        }
        else if (addProductSearch.getText().trim().isEmpty()) {
            addProductSearchResults.clear();
            addProductPartsTable.setItems(Inventory.getAllParts());
            addProductTopIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            addProductTopNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            addProductTopInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            addProductTopCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /** This is the action event method for the Add button.
     The method first checks if a part is selected, displaying an error if no part is selected.
     If the selected part is not already in the associated parts list, then the method adds it to the list and sorts the list for display.
     If the selected part is already in the associated parts list, then an error message is displayed.
     */
    public void onAddProductAddClick(ActionEvent actionEvent) {
        Part selectedPart = (Part)addProductPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Error");
            alert.setContentText("No part selected.");
            alert.showAndWait();
            return;
        }
        else {
            if (!tempAssociatedParts.contains(selectedPart)) {
                tempAssociatedParts.add(selectedPart);
                Collections.sort(tempAssociatedParts);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Error");
                alert.setContentText("Part has already been added.");
                alert.showAndWait();
                return;
            }
        }
    }

    /** This is the action event method for the Remove associated part button.
     The method first checks if a part is selected, displaying an error if no part is selected.
     The method then confirms the user's action, removing the target part from the associated parts list upon confirmation.
     */
    public void onAddProductRemoveClick(ActionEvent actionEvent) {
        Part targetPart = (Part)addProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (targetPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Remove Error");
            alert.setContentText("No part selected.");
            alert.showAndWait();
            return;
        }
        else {
            Alert comfirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to disassociate this part from the product?");
            Optional<ButtonType> result = comfirmAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                tempAssociatedParts.remove(targetPart);
            }
            else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                return;
            }
        }
    }
}
