module com.example.triovision {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.triovision to javafx.fxml;
    exports com.example.triovision;
}