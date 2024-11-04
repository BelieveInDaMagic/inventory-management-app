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
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import static project.inventorymanagement.Controller.InventoryManagementSystemController.isDouble;
import static project.inventorymanagement.Controller.InventoryManagementSystemController.isInt;

/** This class creates a controller for the Modify Product form. */
public class ModifyProductController implements Initializable {
    public TextField modifyProductSearch;
    public TableView modifyProductPartsTable;
    public TableColumn modifyProductTopIDColumn;
    public TableColumn modifyProductTopNameColumn;
    public TableColumn modifyProductTopInvColumn;
    public TableColumn modifyProductTopPriceColumn;
    public TableView modifyProductAssociatedPartsTable;
    public TableColumn modifyProductBottomIDColumn;
    public TableColumn modifyProductBottomNameColumn;
    public TableColumn modifyProductBottomInvColumn;
    public TableColumn modifyProductBottomPriceColumn;
    public TextField modifyProductID;
    public TextField modifyProductPrice;
    public TextField modifyProductMax;
    public TextField modifyProductInv;
    public TextField modifyProductName;
    public TextField modifyProductMin;

    private static Product targetProduct = null;

    /** This list holds the search results for the parts search. */
    public ObservableList<Part> modifyProductSearchResults = FXCollections.observableArrayList();

    /** This list holds the updated list of parts to be associated with the product. */
    public ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    /** This method is a static method used to pass a product object from the Inventory Management System form controller.
     The method sets the static targetProduct variable to the product parameter.
     @param product The part to be passed from the Inventory Management System form controller.
     */
    public static void passProduct(Product product) {
        targetProduct = product;
    }

    /** This is the method that executes when the Modify Product form is initialized.
     It populates the Parts table with all parts.
     The method then adds the product's associated parts to tempAssociatedParts, and populates the Associated Parts table with the list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductID.setText(String.valueOf(targetProduct.getId()));
        modifyProductName.setText(targetProduct.getName());
        modifyProductInv.setText(String.valueOf(targetProduct.getStock()));
        modifyProductPrice.setText(String.valueOf(targetProduct.getPrice()));
        modifyProductMax.setText(String.valueOf(targetProduct.getMax()));
        modifyProductMin.setText(String.valueOf(targetProduct.getMin()));

        modifyProductPartsTable.setItems(Inventory.getAllParts());
        modifyProductTopIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductTopNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductTopInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductTopPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tempAssociatedParts.addAll(targetProduct.getAllAssociatedParts());
        modifyProductAssociatedPartsTable.setItems(tempAssociatedParts);
        modifyProductBottomIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductBottomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductBottomInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductBottomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This is the action event method that handles a character being typed into or deleted from the parts search bar.
     The method first clears any previous search results from the search results list.
     If the search bar is not empty, then a search is carried out. The method adds to the search results list every part with a name containing a partial or full match with the search bar's text.
     The method then adds to the search results list every part with an ID containing a partial or full match with the search bar's text. not including duplicates already in the search results list.
     The method then populates the Parts table with the search results, displaying an error if no matching part is found.
     If the search bar is empty, the search results list is cleared and the table is repopulated with all parts.
     */
    public void onModifyProductSearchTyped(KeyEvent keyEvent) {
        modifyProductSearchResults.clear();
        if (!modifyProductSearch.getText().trim().isEmpty()) {
            modifyProductSearchResults.addAll(Inventory.lookupPart(modifyProductSearch.getText()));
            for (Part part : Inventory.getAllParts()) {
                if (String.valueOf(part.getId()).trim().toLowerCase().contains(modifyProductSearch.getText().trim().toLowerCase()) && !modifyProductSearchResults.contains(part)) {
                    modifyProductSearchResults.add(part);
                }
            }
            modifyProductPartsTable.setItems(modifyProductSearchResults);
            modifyProductTopIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            modifyProductTopNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            modifyProductTopInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            modifyProductTopPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            if (modifyProductSearchResults.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setContentText("No matching part found.");
                alert.showAndWait();
                return;
            }
        }
        else if (modifyProductSearch.getText().trim().isEmpty()) {
            modifyProductSearchResults.clear();
            modifyProductPartsTable.setItems(Inventory.getAllParts());
            modifyProductTopIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            modifyProductTopNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            modifyProductTopInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            modifyProductTopPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /** This is the action event method for the Add button.
     The method first checks if a part is selected, displaying an error if no part is selected.
     If the selected part is not already in the associated parts list, then the method adds it to the list ans sorts the list for display.
     If the selected part is already in the associated parts list, then an error message is displayed.
     */
    public void onModifyProductAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part)modifyProductPartsTable.getSelectionModel().getSelectedItem();
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
     The method then confirms the user's action, removing the target part from the temporary associated parts list upon confirmation.
     */
    public void onModifyPartRemove(ActionEvent actionEvent) {
        Part targetPart = (Part)modifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if (targetPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Remove Error");
            alert.setContentText("No part selected.");
            alert.showAndWait();
            return;
        }
        else {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to disassociate this part from the product?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                tempAssociatedParts.remove(targetPart);
            }
            else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                return;
            }
        }
    }

    /** This is the action event method for the Save button.
     The method first checks for any data input errors (including min <= inv <= max and min < max), displaying an error message if any is found.
     If there are no errors, the product's data is updated based on input data while preserving the Product ID.
     If the temporary list of parts to be associated is not empty, then the method updates the product's associated parts list by disassociating removed parts and adding new parts as necessary.
     If the temporary list of parts is empty, then the method removes all associated parts from the product.
     The method then redirects to the Inventory Management System form.
     */
    public void onModifyPartSave(ActionEvent actionEvent) throws IOException {
        if (modifyProductName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Name field is empty.");
            alert.showAndWait();
            return;
        }
        if (modifyProductInv.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Inventory field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(modifyProductInv.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Inventory field must be a number.");
            alert.showAndWait();
            return;
        }
        if (modifyProductPrice.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Price field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isDouble(modifyProductPrice.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Price field must be a number.");
            alert.showAndWait();
            return;
        }
        if (modifyProductMax.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Max field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(modifyProductMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Max field must be a number.");
            alert.showAndWait();
            return;
        }
        if (modifyProductMin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Min field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(modifyProductMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Min field must be a number.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(modifyProductMin.getText()) >= Integer.parseInt(modifyProductMax.getText()) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Min field must be less than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(modifyProductInv.getText()) > Integer.parseInt(modifyProductMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Inventory field cannot be greater than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(modifyProductInv.getText()) < Integer.parseInt(modifyProductMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Modify Error");
            alert.setContentText("Inventory field cannot be less than Min field.");
            alert.showAndWait();
            return;
        }
        targetProduct.setName(modifyProductName.getText());
        targetProduct.setStock(Integer.parseInt(modifyProductInv.getText()));
        targetProduct.setPrice(Double.parseDouble(modifyProductPrice.getText()));
        targetProduct.setMax(Integer.parseInt(modifyProductMax.getText()));
        targetProduct.setMin(Integer.parseInt(modifyProductMin.getText()));
        if (!tempAssociatedParts.isEmpty()) {
            Iterator<Part> removeIterator = targetProduct.getAllAssociatedParts().iterator();
            while (removeIterator.hasNext()) {
                Part removePart = removeIterator.next();
                if (!tempAssociatedParts.contains(removePart)) {
                    removeIterator.remove();
                }
            }
            Iterator<Part> addIterator = tempAssociatedParts.iterator();
            while (addIterator.hasNext()) {
                Part addPart = addIterator.next();
                if (!targetProduct.getAllAssociatedParts().contains(addPart)) {
                    targetProduct.addAssociatedPart(addPart);
                }
            }
        }
        else {
            if (!targetProduct.getAllAssociatedParts().isEmpty()) {
                Iterator<Part> clearIterator = targetProduct.getAllAssociatedParts().iterator();
                while (clearIterator.hasNext()) {
                    Part clearPart = clearIterator.next();
                    clearIterator.remove();
                }
            }
        }
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,850,405.5);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the action event method for the Cancel button.
     It clears the search results and temporary associated parts lists, then redirects to the Inventory Management System form.
     */
    public void onModifyPartCancel(ActionEvent actionEvent) throws IOException {
        modifyProductSearchResults.clear();
        tempAssociatedParts.clear();
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,850,405.5);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }


}
