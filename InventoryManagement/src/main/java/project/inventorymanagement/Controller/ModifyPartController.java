/** @author Alexandre Do */
package project.inventorymanagement.Controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.ResourceBundle;

import static project.inventorymanagement.Controller.InventoryManagementSystemController.isDouble;
import static project.inventorymanagement.Controller.InventoryManagementSystemController.isInt;

/** This class creates a controller for the Modify Part form.*/
public class ModifyPartController implements Initializable{

    public RadioButton modifyPartInHouse;
    public RadioButton modifyPartOutsourced;
    public Label modifyPart;
    public TextField modifyPartInv;
    public TextField modifyPartName;
    public TextField modifyPartID;
    public TextField modifyPartPrice;
    public TextField modifyPartSwitch;
    public TextField modifyPartMax;
    public TextField modifyPartMin;
    public Button modifyPartCancel;
    public Button modifyPartSave;
    public Label modifyPartSwitchLabel;
    public ToggleGroup modifyPartSource;

    private static Part targetPart = null;
    private int formType = 0;

    /** This method is a static method used to pass a part object from the Inventory Management System form controller.
     The method sets the static targetPart variable to the part parameter.
     @param part The part to be passed from the Inventory Management System form controller.
     */
    public static void passPart(Part part) {
        targetPart = part;
    }

    /** This is the method that executes when the Modify Part form is initialized.
     It populates the text fields with data from the part selected from the Inventory Management System form, and sets the Modify Part form to In-House or Outsourced based on the part type.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyPartID.setText(String.valueOf(targetPart.getId()));
        modifyPartName.setText(targetPart.getName());
        modifyPartInv.setText(String.valueOf(targetPart.getStock()));
        modifyPartPrice.setText(String.valueOf(targetPart.getPrice()));
        modifyPartMax.setText(String.valueOf(targetPart.getMax()));
        modifyPartMin.setText(String.valueOf(targetPart.getMin()));
        if (targetPart instanceof InHouse) {
            modifyPartSwitch.setText(String.valueOf(((InHouse) targetPart).getMachineId()));
        }
        else if (targetPart instanceof Outsourced) {
            modifyPartSwitchLabel.setText("Company Name");
            formType = 1;
            modifyPartSwitch.setText(((Outsourced) targetPart).getCompanyName());
            modifyPartInHouse.setSelected(false);
            modifyPartOutsourced.setSelected(true);
        }
    }

    /** This is the action event method for the In-House button.
     The method sets the text of Machine ID/Company Name label to Machine ID, and sets the formType to 0 to indicate the part to be added is an In-House part.
     */
    public void modifyPartInHouseIfSelected(ActionEvent actionEvent) {
        modifyPartSwitchLabel.setText("Machine ID");
        formType = 0;
    }

    /** This is the action event method for the Outsourced button.
     The method sets the text of Machine ID/Company Name label to Company Name, and sets the formType to 1 to indicate the part to be added is an Outsourced part.
     */
    public void modifyPartOutsourcedIfSelected(ActionEvent actionEvent) {
        modifyPartSwitchLabel.setText("Company Name");
        formType = 1;
    }

    /** This is the action event method for the Cancel button.
     The method redirects to the Inventory Management System form.
     */
    public void onModifyPartCancel (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,850,405.5);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the action event method for the Save button.
     The method first checks for any input errors (including min <= inv <= max and min < max), displaying an error message if any is found. If there are no errors, the method updates the part with the new data, while retaining the same part ID.
     If the part type is changed, then the method creates a new part with the corresponding data (retaining the part ID), adds the new part to inventory and deletes the old one.
     The method then redirects to the Inventory Management System form.
     */
    public void onModifyPartSave(ActionEvent actionEvent) throws IOException{
        if (modifyPartName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Name field is empty.");
            alert.showAndWait();
            return;
        }
        if (modifyPartInv.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Inventory field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(modifyPartInv.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Inventory field must be a number.");
            alert.showAndWait();
            return;
        }
        if (modifyPartPrice.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Price field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isDouble(modifyPartPrice.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Price field must be a number.");
            alert.showAndWait();
            return;
        }
        if (modifyPartMax.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Max field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(modifyPartMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Max field must be a number.");
            alert.showAndWait();
            return;
        }
        if (modifyPartMin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Min field is empty.");
            alert.showAndWait();
            return;
        }
        else if (!isInt(modifyPartMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Min field must be a number.");
            alert.showAndWait();
            return;
        }
        if (formType == 0) {
            if (modifyPartSwitch.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Modify Error");
                alert.setContentText("Machine ID field is empty.");
                alert.showAndWait();
                return;
            }
            else if (!isInt(modifyPartSwitch.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Modify Error");
                alert.setContentText("Machine ID field must be a number.");
                alert.showAndWait();
                return;
            }
        }
        else if (formType == 1) {
            if (modifyPartSwitch.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Part Modify Error");
                alert.setContentText("Company Name field is empty.");
                alert.showAndWait();
                return;
            }
        }
        if (Integer.parseInt(modifyPartMin.getText()) >= Integer.parseInt(modifyPartMax.getText()) ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Min field must be less than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(modifyPartInv.getText()) > Integer.parseInt(modifyPartMax.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Inventory field cannot be greater than Max field.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(modifyPartInv.getText()) < Integer.parseInt(modifyPartMin.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Modify Error");
            alert.setContentText("Inventory field cannot be less than Min field.");
            alert.showAndWait();
            return;
        }

        if (targetPart instanceof InHouse) {
            if (formType == 0) {
                targetPart.setName(modifyPartName.getText());
                targetPart.setStock(Integer.parseInt(modifyPartInv.getText()));
                targetPart.setPrice(Double.parseDouble(modifyPartPrice.getText()));
                targetPart.setMax(Integer.parseInt(modifyPartMax.getText()));
                targetPart.setMin(Integer.parseInt(modifyPartMin.getText()));
                ((InHouse) targetPart).setMachineId(Integer.parseInt(modifyPartSwitch.getText()));
                Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,850,405.5);
                stage.setTitle("Inventory Management");
                stage.setScene(scene);
                stage.show();
            }
            else if (formType == 1) {
                Outsourced newPart = new Outsourced(targetPart.getId(), modifyPartName.getText(), Double.parseDouble(modifyPartPrice.getText()), Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()), modifyPartSwitch.getText());
                Inventory.addPart(newPart);
                Inventory.deletePart(targetPart);
                Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,850,405.5);
                stage.setTitle("Inventory Management");
                stage.setScene(scene);
                stage.show();
            }
        }
        else if (targetPart instanceof Outsourced) {
            if (formType == 1) {
                targetPart.setName(modifyPartName.getText());
                targetPart.setStock(Integer.parseInt(modifyPartInv.getText()));
                targetPart.setPrice(Double.parseDouble(modifyPartPrice.getText()));
                targetPart.setMax(Integer.parseInt(modifyPartMax.getText()));
                targetPart.setMin(Integer.parseInt(modifyPartMin.getText()));
                ((Outsourced) targetPart).setCompanyName(modifyPartSwitch.getText());
                Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,850,405.5);
                stage.setTitle("Inventory Management");
                stage.setScene(scene);
                stage.show();
            }
            else if (formType == 0) {
                InHouse newPart = new InHouse(targetPart.getId(), modifyPartName.getText(), Double.parseDouble(modifyPartPrice.getText()), Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()), Integer.parseInt(modifyPartSwitch.getText()));
                Inventory.addPart(newPart);
                Inventory.deletePart(targetPart);
                Parent root = FXMLLoader.load(getClass().getResource("/project/inventorymanagement/View/InventoryManagementSystem.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,850,405.5);
                stage.setTitle("Inventory Management");
                stage.setScene(scene);
                stage.show();
            }

        }
    }
}
