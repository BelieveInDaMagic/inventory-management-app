/** @author Alexandre Do */
package project.inventorymanagement;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This class creates an app that manages Inventory. */
public class InventoryManagementApplication extends Application {

    /** This is the start method.
     This method create a stage and displays the Inventory Management Main form.
     @param stage The stage on which the scene is to be set.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagementApplication.class.getResource("View/InventoryManagementSystem.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 405.5);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the main method.
     This is the first method that is called when the java program runs.
     Javadoc folder is located at src/main/Javadoc.
     */
    public static void main(String[] args) {
        launch();
    }
}