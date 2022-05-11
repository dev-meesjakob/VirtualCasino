module com.toja.virtualcasino {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.toja.virtualcasino to javafx.fxml;
    exports com.toja.virtualcasino;
}