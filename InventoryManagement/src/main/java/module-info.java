module project.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.inventorymanagement to javafx.fxml, javafx.base;
    opens project.inventorymanagement.Model to javafx.base;
    exports project.inventorymanagement;
    exports project.inventorymanagement.Controller;
    opens project.inventorymanagement.Controller to javafx.fxml;
}