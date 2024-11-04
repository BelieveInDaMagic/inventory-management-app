/** @author Alexandre Do */
package project.inventorymanagement.Controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.inventorymanagement.Model.InHouse;
import project.inventorymanagement.Model.Inventory;
import project.inventorymanagement.Model.Outsourced;
import project.inventorymanagement.Model.Part;

import java.io.IOException;
import java.util.Collections;

import static project.inventorymanagement.Controller.InventoryManagementSystemController.*;

/** This class creates a controller for the Add Part form.*/
public class AddPartController {
    public RadioButton addPartInHouse;
    public ToggleGroup addPartToggle;
    public RadioButton addPartOutsourced;
    public TextField addPartInv;
    public TextField addPartName;
    public TextField addPartID;
    public TextField addPartPrice;
    public TextField addPartSwitch;
    public TextField addPartMax;
    public Label addPartSwitchLabel;
    public Button addPartSave;
    public Button addPartCancel;
    public TextField addPartMin;

    private int formType = 0;

   /** This is the action event method for the In-House button.
    The method sets the text of Machine ID/Company Name label to Machine ID, and sets the formType to 0 to indicate the part to be added is an In-House part.
    */
    public void addPartInHouseIfSelected(ActionEvent actionEvent) {
        addPartSwitchLabel.setText("Machine ID");
        formType = 0;
    }

    /** This is the action event method for the Outsourced button.
     The method sets the text of Machine ID/Company Name label to Company Name, and sets the formType to 1 to indicate the part to be added is an Outsourced part.
     */
    public void addPartOutsourcedIfSelected(ActionEvent actionEvent) {
        addPartSwitchLabel.setText("Company Name");
        formType = 1;
    }

    /** This is the action event method for the Save button.
     The method first checks for any input errors (including min <= inv <= max and min < max), displaying an error message if any is found.
     If there are no errors, an In-House/Outsourced part is created depending on formType.
     If no ID is available for recycling, the method assigns the part a new unique ID using partsIdCount, adds the part to the Inventory, and updates the parts ID counter.
     If an ID is available for recycling, the method assigns the part the lowest available recycled ID, removes the ID from the recycled IDs list, adds the part to the Inventory, and sorts the list of all parts for display.
     The method then redirects to the Inventory Management System form.
     */
    public void onAddPartSave(ActionEvent actionEvent) throws IOException {
        if (addPartName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Name field is empty.");
            alert.showAndWait();
            return;
        }
        if (addPartInv.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Inventory field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(addPartInv.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Inventory field must be a number.");
            alert.showAndWait();
            return;
        }
        if (addPartPrice.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Price field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isDouble(addPartPrice.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Price field must be a number.");
            alert.showAndWait();
            return;
        }
        if (addPartMax.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Max field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(addPartMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Max field must be a number.");
            alert.showAndWait();
            return;
        }
        if (addPartMin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Min field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(addPartMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Min field must be a number.");
            alert.showAndWait();
            return;
        }
        if (formType == 0) {
            if (addPartSwitch.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Add Error");
                alert.setContentText("Machine ID field is empty.");
                alert.showAndWait();
                return;
            }
            else if (!isInt(addPartSwitch.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Add Error");
                alert.setContentText("Machine ID field must be a number.");
                alert.showAndWait();
                return;
            }
        }
        else if (formType == 1) {
            if (addPartSwitch.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Add Error");
                alert.setContentText("Company Name field is empty.");
                alert.showAndWait();
                return;
            }
        }
        if (Integer.parseInt(addPartMin.getText()) >= Integer.parseInt(addPartMax.getText()) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Min field must be less than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(addPartInv.getText()) > Integer.parseInt(addPartMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Inventory field cannot be greater than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(addPartInv.getText()) < Integer.parseInt(addPartMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Error");
            alert.setContentText("Inventory field cannot be less than Min field.");
            alert.showAndWait();
            return;
        }
        if (formType == 0) {
            if (partsIdSave.isEmpty()) {
                Part part = new InHouse(partsIdCount, addPartName.getText(), Double.parseDouble(addPartPrice.getText()), Integer.parseInt(addPartInv.getText()), Integer.parseInt(addPartMin.getText()), Integer.parseInt(addPartMax.getText()), Integer.parseInt(addPartSwitch.getText()));
                Inventory.addPart(part);
                partsIdCount++;
            }
            else {
                Part part = new InHouse(partsIdSave.get(0), addPartName.getText(), Double.parseDouble(addPartPrice.getText()), Integer.parseInt(addPartInv.getText()), Integer.parseInt(addPartMin.getText()), Integer.parseInt(addPartMax.getText()), Integer.parseInt(addPartSwitch.getText()));
                Inventory.addPart(part);
                partsIdSave.remove(0);
                Collections.sort(Inventory.getAllParts());
            }
            Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,850,405.5);
            stage.setTitle("Inventory Management");
            stage.setScene(scene);
            stage.show();
        }
        else if (formType == 1) {
            if (partsIdSave.isEmpty()) {
                Part part = new Outsourced(partsIdCount, addPartName.getText(), Double.parseDouble(addPartPrice.getText()), Integer.parseInt(addPartInv.getText()), Integer.parseInt(addPartMin.getText()), Integer.parseInt(addPartMax.getText()), addPartSwitch.getText());
                Inventory.addPart(part);
                partsIdCount++;
            }
            else {
                Part part = new Outsourced(partsIdSave.get(0), addPartName.getText(), Double.parseDouble(addPartPrice.getText()), Integer.parseInt(addPartInv.getText()), Integer.parseInt(addPartMin.getText()), Integer.parseInt(addPartMax.getText()), addPartSwitch.getText());
                partsIdSave.remove(0);
                Inventory.addPart(part);
                Collections.sort(Inventory.getAllParts());
            }
            Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,850,405.5);
            stage.setTitle("Inventory Management");
            stage.setScene(scene);
            stage.show();
        }
    }

    /** This is the action event method for the Cancel button.
     The method redirects to the Inventory Management System form.
     */
    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,850,405.5);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }
}
